package org.simbiosis.cli.revsharing;

import java.io.IOException;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.CliBase;
import org.simbiosis.microbank.DepositInformationDto;
import org.simbiosis.microbank.DepositProductDto;
import org.simbiosis.microbank.DepositRevSharingDto;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.SavingProductDto;
import org.simbiosis.microbank.SavingTransactionDto;
import org.simbiosis.system.ConfigDto;

public class DistributeDeposit extends CliBase {

	//
	DateTimeFormatter sdf = DateTimeFormat.forPattern("MM;yyyy");
	DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
	NumberFormat nf = NumberFormat.getInstance();
	//
	String period = "";
	String ref = "";
	String strDate = "";
	//
	Map<Long, SavingProductDto> spMap = new HashMap<Long, SavingProductDto>();
	Map<Long, DepositProductDto> dpMap = new HashMap<Long, DepositProductDto>();
	//
	Long coaZakat = 0L;
	Long coaPPHDeposit = 0L;

	public static void main(String[] args) {
		DistributeDeposit distribusi = new DistributeDeposit();
		distribusi.execute();
	}

	public DistributeDeposit() {
		super("cli.properties");
		DateTime now = new DateTime();
		String strPer = sdf.print(now);
		String[] arrPer = strPer.split(";");
		if (Integer.parseInt(arrPer[0]) == 1) {
			int year = Integer.parseInt(arrPer[1]);
			year--;
			period = "12/" + year;
			ref = "BHS12" + year;
		} else {
			period = arrPer[0] + "/" + arrPer[1];
			ref = "BHS" + arrPer[0] + arrPer[1];
		}
		//
		strDate = dtf.print(now);
	}

	void execute() {
		while (next()) {
			System.out.println("Bagi hasil deposito tanggal " + strDate);
			//
			String dataZakat = getCoreClient().sendRawData("getConfig",
					"revenue.coaZakat");
			String dataPPHDeposit = getCoreClient().sendRawData("getConfig",
					"revenue.coaPPHDeposit");
			ObjectMapper mapperZakat = new ObjectMapper();
			ObjectMapper mapperPPHDeposit = new ObjectMapper();
			try {
				ConfigDto config = mapperZakat.readValue(dataZakat,
						ConfigDto.class);
				if (config != null) {
					coaZakat = config.getLongValue();
				}
				config = mapperPPHDeposit.readValue(dataPPHDeposit,
						ConfigDto.class);
				if (config != null) {
					coaPPHDeposit = config.getLongValue();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			//
			String data = getCoreClient().sendRawData("listDepositRevSharing",
					strDate);
			ObjectMapper mapper = new ObjectMapper();
			try {
				List<DepositRevSharingDto> result = mapper.readValue(
						data,
						mapper.getTypeFactory().constructCollectionType(
								ArrayList.class, DepositRevSharingDto.class));
				for (DepositRevSharingDto rs : result) {
					System.out.println("data = " + rs.getDeposit() + "=="
							+ rs.getDate() + "==" + rs.getSaving() + "=="
							+ rs.getCustomerSharing());
					saveSavingTrans(rs);
					saveDepositRevSharingStatus(rs.getId(), 1);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	SavingTransactionDto createSavingTrans(Date date, long branch, long saving,
			double value, String ref, String description, int direction) {
		SavingTransactionDto transDto = new SavingTransactionDto();
		transDto.setBranch(branch);
		transDto.setDate(date);
		transDto.setSaving(saving);
		transDto.setCode(ref);
		transDto.setRefCode(ref);
		transDto.setDescription(description);
		transDto.setValue(value);
		transDto.setDirection(direction);
		return transDto;
	}

	void saveDepositRevSharingStatus(long id, int status) {
		getCoreClient().sendRawData("saveDepositRevSharingStatus",
				"" + id + ";" + status);
	}

	DepositInformationDto getDepositInformation(Long id) {
		String data = getCoreClient().sendRawData("getDepositInformation",
				id.toString());
		try {
			//
			ObjectMapper mapper = new ObjectMapper();
			DepositInformationDto si = mapper.readValue(data,
					DepositInformationDto.class);
			return si;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	SavingInformationDto getSavingInformation(Long id) {
		String data = getCoreClient().sendRawData("getSavingInformation",
				id.toString());
		try {
			//
			ObjectMapper mapper = new ObjectMapper();
			SavingInformationDto si = mapper.readValue(data,
					SavingInformationDto.class);
			return si;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	void saveSavingTrans(DepositRevSharingDto rs) {
		try {
			DepositInformationDto deposit = getDepositInformation(rs
					.getDeposit());
			SavingInformationDto saving = getSavingInformation(rs.getSaving());
			//
			Long coaCadBaghas = deposit.getCoa3();
			//
			SavingTransactionDto transBasil = createSavingTrans(rs.getDate(),
					deposit.getBranch(), rs.getSaving(),
					rs.getCustomerSharing(), ref, "BAHAS/BONUS " + period, 1);
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, transBasil);
			sendRawData("saveSavingJournalRevenueTrans",
					coaCadBaghas.toString(), sw.toString());
			//
			if (deposit.getZakat() == 1) {
				SavingTransactionDto transZakat = createSavingTrans(
						rs.getDate(), deposit.getBranch(), rs.getSaving(),
						rs.getZakat(), ref, "ZAKAT " + period, 2);
				mapper = new ObjectMapper();
				sw = new StringWriter();
				mapper.writeValue(sw, transZakat);
				sendRawData("saveSavingJournalRevenueTrans",
						coaZakat.toString(), sw.toString());
			}
			//
			if (rs.getTax() > 0) {
				SavingTransactionDto transPajak = createSavingTrans(
						rs.getDate(), saving.getBranch(), rs.getSaving(),
						rs.getTax(), ref, "PPH " + period, 2);
				mapper = new ObjectMapper();
				sw = new StringWriter();
				mapper.writeValue(sw, transPajak);
				sendRawData("saveSavingJournalRevenueTrans",
						coaPPHDeposit.toString(), sw.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void sendRawData(String action, String id, String data) {
		getCoreClient().sendRawData(action, id, "", data);
	}
}
