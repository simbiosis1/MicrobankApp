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
import org.simbiosis.microbank.DepositDto;
import org.simbiosis.microbank.DepositProductDto;
import org.simbiosis.microbank.DepositRevSharingDto;
import org.simbiosis.microbank.SavingProductDto;
import org.simbiosis.microbank.SavingTransactionDto;

public class DistributeDepositInterest extends CliBase {
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

	public static void main(String[] args) {
		DistributeDepositInterest distribusi = new DistributeDepositInterest();
		distribusi.execute();
	}

	public DistributeDepositInterest() {
		super("cli.properties");
		DateTime now = new DateTime();
		String strPer = sdf.print(now);
		String[] arrPer = strPer.split(";");
		if (Integer.parseInt(arrPer[0]) == 1) {
			int year = Integer.parseInt(arrPer[1]);
			year--;
			period = "12/" + year;
			ref = "BD12" + year;
		} else {
			period = arrPer[0] + "/" + arrPer[1];
			ref = "BD" + arrPer[0] + arrPer[1];
		}
		//
		strDate = dtf.print(now);
	}

	void execute() {
		while (next()) {
			System.out.println("Bunga deposito tanggal " + strDate);
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

	SavingTransactionDto createSavingTrans(Date date, long saving,
			double value, String ref, String description, int direction) {
		SavingTransactionDto transDto = new SavingTransactionDto();
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

	DepositDto getDeposit(long id) {
		String data = getCoreClient().sendRawData("getDeposit", "" + id);
		try {
			//
			ObjectMapper mapper = new ObjectMapper();
			DepositDto si = mapper.readValue(data, DepositDto.class);
			return si;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	void saveSavingTrans(DepositRevSharingDto rs) {
		try {
			// DepositDto deposit = getDeposit(rs.getDeposit());
			//
			String coaCadBaghas = "727";
			// String coaZakat = "680";
			// String coaPPHDeposit = "307";
			//
			SavingTransactionDto transBasil = createSavingTrans(rs.getDate(),
					rs.getSaving(), rs.getCustomerSharing(), ref, "BUNGA DEP "
							+ period, 1);
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, transBasil);
			sendRawData("saveSavingJournalRevenueTrans", coaCadBaghas,
					sw.toString());
			//
			// if (deposit.getZakat() == 1) {
			// SavingTransactionDto transZakat = createSavingTrans(
			// rs.getDate(), rs.getSaving(), rs.getZakat(), ref,
			// "ZAKAT " + period, 2);
			// mapper = new ObjectMapper();
			// sw = new StringWriter();
			// mapper.writeValue(sw, transZakat);
			// sendRawData("saveSavingJournalRevenueTrans", coaZakat,
			// sw.toString());
			// }
			//
			// if (rs.getTax() > 0) {
			// SavingTransactionDto transPajak = createSavingTrans(
			// rs.getDate(), rs.getSaving(), rs.getTax(), ref, "PPH "
			// + period, 2);
			// mapper = new ObjectMapper();
			// sw = new StringWriter();
			// mapper.writeValue(sw, transPajak);
			// sendRawData("saveSavingJournalRevenueTrans", coaPPHDeposit,
			// sw.toString());
			// }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void sendRawData(String action, String id, String data) {
		getCoreClient().sendRawData(action, id, "", data);
	}
}
