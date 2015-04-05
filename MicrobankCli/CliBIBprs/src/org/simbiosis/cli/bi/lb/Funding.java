package org.simbiosis.cli.bi.lb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.MicrobankCoreClient;
import org.simbiosis.cli.base.MicrobankReportClient;
import org.simbiosis.cli.bi.lib.StrUtils;
import org.simbiosis.cli.bi.model.BICity;
import org.simbiosis.microbank.DepositInformationDto;
import org.simbiosis.microbank.model.SavingRpt;

public class Funding {

	MicrobankCoreClient jsonClient = null;
	MicrobankReportClient jsonReport = null;
	String endDate = "";
	String newLine = "";
	DateTimeFormatter df = DateTimeFormat.forPattern("yyyyMMdd");

	String imbalanWadiah = "0023";
	String imbalanMudh = "0498";
	String imbalanDep1 = "0498";
	String imbalanDep3 = "0522";
	String imbalanDep6 = "0546";
	String imbalanDep12 = "0569";

	List<SavingRpt> listWad = new ArrayList<SavingRpt>();
	List<SavingRpt> listMudh = new ArrayList<SavingRpt>();
	List<DepositInformationDto> listDep = new ArrayList<DepositInformationDto>();

	BICityConverter biCityConverter;

	public Funding(MicrobankCoreClient jsonClient,
			MicrobankReportClient jsonReport, String endDate, String newLine) {
		this.jsonClient = jsonClient;
		this.jsonReport = jsonReport;
		this.endDate = endDate;
		this.newLine = newLine;
		//
		biCityConverter = new BICityConverter();
	}

	void retrieve() {
		listAllSaving();
		listAllDeposit();
	}

	void listAllSaving() {
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		ObjectMapper mapper = new ObjectMapper();
		String data = jsonReport.sendRawData("listDailySaving", endDate, "0");
		try {
			Date begin = sdf.parseDateTime("01" + endDate.substring(2))
					.toDate();
			Date end = sdf.parseDateTime(endDate).toDate();
			List<SavingRpt> ballances = mapper.readValue(data, TypeFactory
					.collectionType(ArrayList.class, SavingRpt.class));
			for (SavingRpt ballance : ballances) {
				switch (ballance.getSchema()) {
				case 1:
					listWad.add(ballance);
					break;
				case 2:
					ballance.setBegin(begin);
					ballance.setEnd(end);
					listMudh.add(ballance);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	DepositInformationDto getDepositInfo(String id) {
		ObjectMapper mapper = new ObjectMapper();
		String strInfo = jsonClient.sendRawData("getDepositInformation", id);
		DepositInformationDto info = null;
		try {
			info = mapper.readValue(strInfo, DepositInformationDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

	void listAllDeposit() {
		String data = jsonClient.sendRawData("listDepositId", endDate);
		String[] ids = data.split(";");
		for (String id : ids) {
			DepositInformationDto info = getDepositInfo(id);
			listDep.add(info);
		}
	}

	String create12() {
		biCityConverter.clear();
		String buffer = "";
		int line = 1;
		for (SavingRpt info : listWad) {
			String cityCode = biCityConverter.get(info.getCity(),
					info.getCode());
			if (info.getValAfter() < 5000000) {
				biCityConverter.put(cityCode, info.getCode(),
						info.getValAfter());
			} else {
				buffer += create12Text(1, cityCode, info, line++) + newLine;
			}
		}
		// Group
		for (BICity biCity : biCityConverter.values()) {
			SavingRpt info = new SavingRpt();
			info.setValAfter(biCity.getValue());
			if (biCity.getCounter() == 1) {
				info.setCode(biCity.getRefCode());
			} else {
				info.setCode("");
			}
			buffer += create12Text(biCity.getCounter(), biCity.getCode(), info,
					line++) + newLine;
		}
		return buffer;
	}

	String create12Text(int group, String lokasi, SavingRpt info, int line) {
		String code = "BS12";
		String accCode = StrUtils.rpadded(info.getCode(), 15, ' ');
		String jumlahAccount = StrUtils.rpadded("" + group, 8, ' ');
		String hubDenganBank = "2";
		Double dvalue = info.getValAfter() / 1000;
		Long ivalue = Math.round(dvalue);
		String jumlah = StrUtils.lpadded(ivalue.toString(), 12, '0');
		String golNasabah = "876";
		String strLine = StrUtils.lpadded("" + line, 5, '0');
		return code + accCode + jumlahAccount + hubDenganBank + lokasi
				+ imbalanWadiah + jumlah + golNasabah + strLine;
	}

	String create13() {
		biCityConverter.clear();
		String buffer = "";
		int line = 1;
		for (SavingRpt info : listMudh) {
			String cityCode = biCityConverter.get(info.getCity(),
					info.getCode());
			if (info.getValAfter() < 5000000) {
				biCityConverter.put(cityCode, info.getCode(),
						info.getValAfter());
			} else {
				buffer += create13Text(1, cityCode, info, line++) + newLine;
			}
		}
		// Group
		for (BICity biCity : biCityConverter.values()) {
			SavingRpt info = new SavingRpt();
			info.setValAfter(biCity.getValue());
			if (biCity.getCounter() == 1) {
				info.setCode(biCity.getRefCode());
			} else {
				info.setCode("");
			}
			DateTime end = new DateTime().dayOfMonth().withMinimumValue()
					.minusDays(1);
			info.setEnd(end.toDate());
			DateTime begin = end.dayOfMonth().withMinimumValue();
			info.setBegin(begin.toDate());
			buffer += create13Text(biCity.getCounter(), biCity.getCode(), info,
					line++) + newLine;
		}
		return buffer;
	}

	String create13Text(int group, String lokasi, SavingRpt info, int line) {
		String code = "BS13";
		String accCode = StrUtils.rpadded(info.getCode(), 15, ' ');
		String jumlahAccount = StrUtils.rpadded("" + group, 8, ' ');
		String hubDenganBank = "2";
		Double dvalue = info.getValAfter() / 1000;
		Long ivalue = Math.round(dvalue);
		String jumlah = StrUtils.lpadded(ivalue.toString(), 12, '0');
		String metodeBasil = "2";
		String golNasabah = "876";
		String mulai = df.print(new DateTime(info.getBegin()));
		String tempo = df.print(new DateTime(info.getEnd()));
		String strLine = StrUtils.lpadded("" + line, 5, '0');
		return code + accCode + jumlahAccount + hubDenganBank + lokasi
				+ imbalanMudh + jumlah + metodeBasil + golNasabah + mulai
				+ tempo + strLine;
	}

	String create14() {
		biCityConverter.clear();
		String buffer = "";
		int line = 1;
		for (DepositInformationDto info : listDep) {
			String cityCode = biCityConverter.get(info.getCity(),
					info.getCode());
			buffer += create14Text(cityCode, info, line++) + newLine;
		}
		return buffer;
	}

	String create14Text(String lokasi, DepositInformationDto info, int line) {
		String code = "BS14";
		String accCode = StrUtils.rpadded(info.getCode(), 15, ' ');
		String jumlahAccount = StrUtils.rpadded("" + 1, 8, ' ');
		//
		String jenis = "21";
		String tingkatImbalan = imbalanDep1;
		String codeJenis = info.getCode().substring(3, 5);
		if (codeJenis.equalsIgnoreCase("33")) {
			jenis = "22";
			tingkatImbalan = imbalanDep3;
		} else if (codeJenis.equalsIgnoreCase("36")) {
			jenis = "23";
			tingkatImbalan = imbalanDep6;
		} else if (codeJenis.equalsIgnoreCase("38")) {
			jenis = "24";
			tingkatImbalan = imbalanDep12;
		}
		String hubDenganBank = "2";
		String mulai = df.print(new DateTime(info.getBegin()));
		String tempo = null;
		if (info.getEnd() == null) {
			System.out.println("Data gak bagus " + accCode);
			tempo = df.print(new DateTime(info.getBegin()));
		} else {
			tempo = df.print(new DateTime(info.getEnd()));
		}
		Double dvalue = info.getValue() / 1000;
		Long ivalue = Math.round(dvalue);
		String jumlah = StrUtils.lpadded(ivalue.toString(), 12, '0');
		String metodeBasil = "2";
		String golNasabah = "876";
		String strLine = StrUtils.lpadded("" + line, 5, '0');
		return code + accCode + jumlahAccount + jenis + hubDenganBank + lokasi
				+ mulai + tempo + tingkatImbalan + jumlah + metodeBasil
				+ golNasabah + strLine;
	}
}
