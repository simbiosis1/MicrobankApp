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
import org.codehaus.jackson.map.type.TypeFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.CliBase;
import org.simbiosis.cli.base.DateTools;
import org.simbiosis.microbank.DepositInformationDto;
import org.simbiosis.microbank.DepositProductDto;
import org.simbiosis.microbank.DepositRevSharingDto;
import org.simbiosis.microbank.RevenueSharingDto;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.SavingProductDto;
import org.simbiosis.microbank.SavingTransactionDto;
import org.simbiosis.system.ConfigDto;

public class DistributeAll extends CliBase {
	//
	NumberFormat nf = NumberFormat.getInstance();
	DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
	//
	int month;
	int nextMonth;
	int year;
	String endDate;
	Date date = null;
	//
	String period;
	String ref = "BHS";
	//
	//
	Map<Long, SavingProductDto> spMap = new HashMap<Long, SavingProductDto>();
	Map<Long, DepositProductDto> dpMap = new HashMap<Long, DepositProductDto>();
	//
	Long coaZakat = 0L;
	Long coaPPHSaving = 0L;
	DateTools dateTools = new DateTools(dtf);

	public static void main(String[] args) {
		DistributeAll distribusi = new DistributeAll();
		distribusi.execute();
	}

	public DistributeAll() {
		super("cli.properties");
		//
		// Dijalankan tanggal 1 awal bulan.....
		DateTime lastDayLastMonth = new DateTime();
		lastDayLastMonth = lastDayLastMonth.dayOfMonth().withMinimumValue()
				.minusDays(1);
		//
		DateTimeFormatter sMonth = DateTimeFormat.forPattern("MM");
		DateTimeFormatter sYear = DateTimeFormat.forPattern("yyyy");
		//
		String strMonth = sMonth.print(lastDayLastMonth);
		month = Integer.parseInt(strMonth);
		year = Integer.parseInt(sYear.print(lastDayLastMonth));
		nextMonth = (month == 12) ? 1 : month + 1;
		endDate = getEndMonths(month) + "-" + year;
		//
		period = strMonth + "/" + year;
		ref += strMonth + "" + year;
		//
		date = dtf.parseDateTime(endDate).toDate();
	}

	void execute() {
		while (next()) {
			//
			String dataZakat = getCoreClient().sendRawData("getConfig",
					"revenue.coaZakat");
			String dataPPHSaving = getCoreClient().sendRawData("getConfig",
					"revenue.coaPPHSaving");
			ObjectMapper mapperZakat = new ObjectMapper();
			ObjectMapper mapperPPHSaving = new ObjectMapper();
			try {
				ConfigDto config = mapperZakat.readValue(dataZakat,
						ConfigDto.class);
				if (config != null) {
					coaZakat = config.getLongValue();
				}
				config = mapperPPHSaving.readValue(dataPPHSaving,
						ConfigDto.class);
				if (config != null) {
					coaPPHSaving = config.getLongValue();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			//
			String data = getCoreClient().sendRawData("listRevenueSharing",
					month + ";" + year + ";0");
			ObjectMapper mapper = new ObjectMapper();
			try {
				List<RevenueSharingDto> result = mapper.readValue(data,
						TypeFactory.collectionType(ArrayList.class,
								RevenueSharingDto.class));
				for (RevenueSharingDto rs : result) {
					if (rs.getType() == 1) {
						if (rs.getCustomerSharing() > 0) {
							saveSavingTrans(rs);
						}
					} else {
						saveDepositRevSharing(rs);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	SavingTransactionDto createSavingTrans(long saving, long branch,
			double value, String ref, String description, int direction) {
		SavingTransactionDto transDto = new SavingTransactionDto();
		transDto.setDate(date);
		transDto.setTimestamp(new Date());
		transDto.setSaving(saving);
		transDto.setCode(ref);
		transDto.setRefCode(ref);
		transDto.setDescription(description);
		transDto.setValue(value);
		transDto.setDirection(direction);
		transDto.setBranch(branch);
		return transDto;
	}

	SavingInformationDto getSavingInformation(long id) {
		String data = getCoreClient().sendRawData("getSavingInformation",
				"" + id);
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

	void saveSavingTrans(RevenueSharingDto rs) {
		try {
			SavingInformationDto si = getSavingInformation(rs.getSaving());
			//
			ObjectMapper mapper = null;
			StringWriter sw = null;
			//
			if (rs.getCustomerSharing() > 0) {
				SavingTransactionDto transBasil = createSavingTrans(
						rs.getSaving(), si.getBranch(),
						rs.getCustomerSharing(), ref, "BAHAS/BONUS " + period,
						1);
				transBasil.setBranch(si.getBranch());
				long coa = si.getCoa2();
				mapper = new ObjectMapper();
				sw = new StringWriter();
				mapper.writeValue(sw, transBasil);
				// System.out.println(sw.toString());
				sendRawData("saveSavingJournalRevenueTrans", "" + coa,
						sw.toString());
			}
			//
			if (rs.getZakat() > 0) {
				SavingTransactionDto transZakat = createSavingTrans(
						rs.getSaving(), si.getBranch(), rs.getZakat(), ref,
						"ZAKAT " + period, 2);
				transZakat.setBranch(si.getBranch());
				mapper = new ObjectMapper();
				sw = new StringWriter();
				mapper.writeValue(sw, transZakat);
				// System.out.println(sw.toString());
				sendRawData("saveSavingJournalRevenueTrans",
						coaZakat.toString(), sw.toString());
			}
			//
			if (rs.getTax() > 0) {
				SavingTransactionDto transPajak = createSavingTrans(
						rs.getSaving(), si.getBranch(), rs.getTax(), ref,
						"PPH " + period, 2);
				transPajak.setBranch(si.getBranch());
				mapper = new ObjectMapper();
				sw = new StringWriter();
				mapper.writeValue(sw, transPajak);
				// System.out.println(sw.toString());
				sendRawData("saveSavingJournalRevenueTrans",
						coaPPHSaving.toString(), sw.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//
	//

	private void saveDepositRevSharing(RevenueSharingDto rs) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			//
			String strDi = getCoreClient().sendRawData("getDepositInformation",
					"" + rs.getAccount());
			DepositInformationDto di = mapper.readValue(strDi,
					DepositInformationDto.class);
			//
			DateTimeFormatter sdf = DateTimeFormat.forPattern("dd");
			String day = sdf.print(new DateTime(di.getBegin()));
			DepositRevSharingDto drs = new DepositRevSharingDto();
			drs.setCompany(rs.getCompany());
			drs.setDeposit(rs.getAccount());
			drs.setDate(dateTools.getValidDate(Integer.parseInt(day),
					nextMonth, year).toDate());
			drs.setCalcDate(date);
			drs.setCustomerSharing(rs.getCustomerSharing());
			drs.setZakat(rs.getZakat());
			drs.setTax(rs.getTax());
			drs.setSaving(rs.getSaving());
			drs.setStatus(0);
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, drs);
			getCoreClient().sendRawData("saveDepositRevSharing", sw.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	void sendRawData(String action, String id, String data) {
		getCoreClient().sendRawData(action, id, "", data);
	}

}
