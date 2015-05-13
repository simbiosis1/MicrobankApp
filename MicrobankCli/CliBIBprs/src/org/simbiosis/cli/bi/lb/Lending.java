package org.simbiosis.cli.bi.lb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.MicrobankCoreClient;
import org.simbiosis.cli.base.MicrobankReportClient;
import org.simbiosis.cli.bi.lib.StrUtils;
import org.simbiosis.microbank.LoanScheduleDto;
import org.simbiosis.microbank.model.LoanRpt;

public class Lending {

	DateTimeFormatter df = DateTimeFormat.forPattern("yyyyMMdd");
	DateTimeFormatter ddmmyyyy = DateTimeFormat.forPattern("dd-MM-yyyy");
	DateTimeFormatter mmyyyy = DateTimeFormat.forPattern("MM-yyyy");

	List<LoanRpt> listMbh = new ArrayList<LoanRpt>();
	List<LoanRpt> listIjr = new ArrayList<LoanRpt>();
	List<LoanRpt> listPby = new ArrayList<LoanRpt>();
	List<LoanRpt> listQdh = new ArrayList<LoanRpt>();
	List<LoanRpt> listMjs = new ArrayList<LoanRpt>();

	MicrobankCoreClient jsonCore = null;
	MicrobankReportClient jsonReport = null;
	String endDate = "";
	String newLine = "";

	BICityConverter biCityConverter;
	String nextMonthBegin = "";
	String nextMonthEnd = "";

	ManualPPAPJam dataManualPpapJam = new ManualPPAPJam();

	// Map<String, Double> nilai = new HashMap<String, Double>();

	public Lending(MicrobankCoreClient jsonCore,
			MicrobankReportClient jsonReport, String endDate, String newLine) {
		this.jsonReport = jsonReport;
		this.jsonCore = jsonCore;
		this.endDate = endDate;
		this.newLine = newLine;
		//
		biCityConverter = new BICityConverter();
		//
		DateTime now = new DateTime();
		String monthYear = mmyyyy.print(now);
		nextMonthBegin = "01-" + monthYear;
		nextMonthEnd = ddmmyyyy.print(now.dayOfMonth().withMaximumValue());
		//
		// insertRepayment();
	}

	public void retrieve() {
		dataManualPpapJam.loadData();
		listAllLoan();
	}

	private String getJenisAgunan(int jenisAgunan) {
		if (jenisAgunan == 4) {
			return "1";
		} else if (jenisAgunan == 5) {
			return "2";
		} else if (jenisAgunan == 6) {
			return "4";
		} else if (jenisAgunan == 7) {
			return "3";
		} else if (jenisAgunan == 1) {
			return "9";
		}
		return "0";
	}

	void listAllLoan() {
		ObjectMapper mapper = new ObjectMapper();
		String data = jsonReport.sendRawData("listDailyLoan", endDate, "0");
		try {
			List<LoanRpt> infos = mapper.readValue(
					data,
					mapper.getTypeFactory().constructCollectionType(
							ArrayList.class, LoanRpt.class));
			for (LoanRpt info : infos) {
				if (info.getQuality() == 0) {
					System.out.println("Warning : " + info.getCode());
				}
				switch (info.getSchema()) {
				case 1:
				case 2:
				case 3:
					listMbh.add(info);
					break;
				case 4:
					listIjr.add(info);
					break;
				case 5:
				case 6:
					listPby.add(info);
					break;
				case 7:
					listQdh.add(info);
					break;
				case 8:
					listMjs.add(info);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	String getJenisPenggunaan(String code) {
		String productCode = code.substring(3, 5);
		// System.out.println(productCode);
		if (productCode.equalsIgnoreCase("41")) {
			return "10";
		} else if (productCode.equalsIgnoreCase("42")) {
			return "40";
		} else if (productCode.equalsIgnoreCase("43")) {
			return "70";
		}
		return "10";
	}

	String getGolonganPiutang(String jenisPenggunaan) {
		if (jenisPenggunaan.equalsIgnoreCase("70")) {
			return "4";
		}
		return "1";
	}

	String getSektorE(String jenisPenggunaan) {
		if (jenisPenggunaan.equalsIgnoreCase("70")) {
			return "1020";
		}
		return "1007";
	}

	String create04() {
		String buffer = "";
		int line = 1;
		for (LoanRpt info : listMbh) {
			buffer += create04Text(info, line++) + newLine;
		}
		return buffer;
	}

	String create04Text(LoanRpt info, int line) {
		String code = "BS04";
		String loanCode = StrUtils.rpadded(info.getCode(), 15, ' ');
		String kodeKhusus = "1       ";
		String jenisPenggunaan = getJenisPenggunaan(loanCode);
		String hubDenganBank = "2";
		String registrasi = df.print(new DateTime(info.getBegin()));
		String tempo = df.print(new DateTime(info.getEnd()));
		String col = "" + info.getQuality();
		Double dImbalan = (info.getMargin() * 10000) / info.getPrincipal();
		Long iImbalan = Math.round(dImbalan);
		if (iImbalan > 9900L) {
			iImbalan = 9900L;
		}
		String imbalan = "1850";// StrUtils.lpadded(iImbalan.toString(), 4,
								// '0');
		String sektorE = getSektorE(jenisPenggunaan);
		Double dHargaJual = (info.getPrincipal() + info.getMargin()) / 1000;
		Long iHargaJual = Math.round(dHargaJual);
		String hargaJual = StrUtils.lpadded(iHargaJual.toString(), 12, '0');
		Double dSaldoPokok = info.getOsPrincipal() / 1000;
		Long iSaldoPokok = Math.round(dSaldoPokok);
		String saldoPokok = StrUtils.lpadded(iSaldoPokok.toString(), 12, '0');
		Double dSaldoMargin = info.getOsMargin() / 1000;
		Long iSaldoMargin = Math.round(dSaldoMargin);
		String saldoMargin = StrUtils.lpadded(iSaldoMargin.toString(), 12, '0');
		Long iSaldoPiutang = iSaldoPokok + iSaldoMargin;
		String saldoPiutang = StrUtils.lpadded(iSaldoPiutang.toString(), 12,
				'0');
		// Double dNilaiAgunan = info.getGuarantee() / 1000;
		Double dNilaiAgunan = dataManualPpapJam.getData(loanCode.trim())
				.getJaminan() / 1000;
		Long iNilaiAgunan = Math.round(dNilaiAgunan);
		String nilaiAgunan = StrUtils.lpadded(iNilaiAgunan.toString(), 12, '0');
		String agunan = getJenisAgunan(info.getGuaranteeType());
		// Double dPpap = info.getPpap() / 1000;
		Double dPpap = dataManualPpapJam.getData(loanCode.trim()).getPpap() / 1000;
		Long iPpap = Math.round(dPpap);
		String ppap = StrUtils.lpadded(iPpap.toString(), 12, '0');
		String metodeBasil = "2";
		String golPenjamin = "880";
		String bagPenjamin = "9900";
		String golNasabah = "876";
		String lokasiUsaha = biCityConverter
				.get(info.getCity(), info.getCode());
		String golPiutang = getGolonganPiutang(jenisPenggunaan);
		String tujKepemilikan = "73";
		String pendapatanAkanDiterima = StrUtils.lpadded("0", 12, '0');
		if (info.getQuality() == 1) {
			// Pakai yang baru
			LoanScheduleDto sched = getRepayment(info.getId().getRefId());
			if (sched != null) {
				Double dMargin = sched.getMargin() / 1000;
				Long iMargin = Math.round(dMargin);
				pendapatanAkanDiterima = StrUtils.lpadded(iMargin.toString(),
						12, '0');
			}
			// Pakai yang lama
			// Double dMargin = getRepayment(info.getCode());
			// Long iMargin = Math.round(dMargin);
			// pendapatanAkanDiterima = StrUtils.lpadded(iMargin.toString(), 12,
			// '0');
		}
		String strLine = StrUtils.lpadded("" + line, 6, '0');
		return code + loanCode + kodeKhusus + jenisPenggunaan + hubDenganBank
				+ registrasi + tempo + col + imbalan + sektorE + hargaJual
				+ saldoPokok + saldoMargin + saldoPiutang + agunan
				+ nilaiAgunan + ppap + metodeBasil + golPenjamin + bagPenjamin
				+ golNasabah + lokasiUsaha + golPiutang + tujKepemilikan
				+ pendapatanAkanDiterima + strLine;
	}

	String create07() {
		String buffer = "";
		int line = 1;
		for (LoanRpt info : listPby) {
			buffer += create07Text(info, line++) + newLine;
		}
		return buffer;
	}

	String create07Text(LoanRpt info, int line) {
		String code = "BS07";
		String loanCode = StrUtils.rpadded(info.getCode(), 15, ' ');
		String kodeKhusus = "1       ";
		String jenisPembiayaan = info.getSchema() == 2 ? "10" : "20";
		String jenisPenggunaan = getJenisPenggunaan(loanCode);
		String hubDenganBank = "2";
		String registrasi = df.print(new DateTime(info.getBegin()));
		String tempo = df.print(new DateTime(info.getEnd()));
		String col = "" + info.getQuality();
		Double dImbalan = (info.getMargin() * 10000) / info.getPrincipal();
		Long iImbalan = Math.round(dImbalan);
		if (iImbalan > 9900L) {
			iImbalan = 9900L;
		}
		String imbalan = "1850";// StrUtils.lpadded(iImbalan.toString(), 4,
								// '0');
		String sektorE = getSektorE(jenisPenggunaan);
		Double dPlafond = info.getOsPrincipal() / 1000;
		Long iPlafond = Math.round(dPlafond);
		String plafond = StrUtils.lpadded(iPlafond.toString(), 12, '0');
		String longgarTarik = StrUtils.lpadded("0", 12, '0');
		Double dSaldoPembiayaan = info.getOsPrincipal() / 1000;
		Long iSaldoPembiayaan = Math.round(dSaldoPembiayaan);
		String saldoPembiayaan = StrUtils.lpadded(iSaldoPembiayaan.toString(),
				12, '0');
		// Double dNilaiAgunan = info.getGuarantee() / 1000;
		Double dNilaiAgunan = dataManualPpapJam.getData(loanCode.trim())
				.getJaminan() / 1000;
		Long iNilaiAgunan = Math.round(dNilaiAgunan);
		String nilaiAgunan = StrUtils.lpadded(iNilaiAgunan.toString(), 12, '0');
		String agunan = getJenisAgunan(info.getGuaranteeType());
		// Double dPpap = info.getPpap() / 1000;
		Double dPpap = dataManualPpapJam.getData(loanCode.trim()).getPpap() / 1000;
		Long iPpap = Math.round(dPpap);
		String ppap = StrUtils.lpadded(iPpap.toString(), 12, '0');
		String sifatPlafond = "1";
		String metodeBasil = "2";
		String metodeBasilDana = "2";
		String golPenjamin = "880";
		String bagPenjamin = "9900";
		String golNasabah = "876";
		String lokasiUsaha = biCityConverter
				.get(info.getCity(), info.getCode());
		String golPiutang = getGolonganPiutang(jenisPenggunaan);
		String strLine = StrUtils.lpadded("" + line, 6, '0');
		return code + loanCode + kodeKhusus + jenisPembiayaan + jenisPenggunaan
				+ hubDenganBank + registrasi + tempo + col + imbalan + sektorE
				+ plafond + longgarTarik + saldoPembiayaan + agunan
				+ nilaiAgunan + ppap + sifatPlafond + metodeBasil
				+ metodeBasilDana + golPenjamin + bagPenjamin + golNasabah
				+ lokasiUsaha + golPiutang + strLine;
	}

	String create08() {
		String buffer = "";
		int line = 1;
		for (LoanRpt info : listIjr) {
			buffer += create20Text(info, line++) + newLine;
		}
		return buffer;
	}

	// String create08Text(LoanRpt info, int line) {
	// String code = "BS08";
	// String loanCode = StrUtils.rpadded(info.getCode(), 15, ' ');
	// String kodeKhusus = "1       ";
	// String jenisPembiayaan = info.getSchema() == 2 ? "10" : "20";
	// String jenisPenggunaan = getJenisPenggunaan(loanCode);
	// String hubDenganBank = "2";
	// String registrasi = df.print(new DateTime(info.getBegin()));
	// String tempo = df.print(new DateTime(info.getEnd()));
	// String col = "" + info.getQuality();
	// Double dImbalan = (info.getMargin() * 10000) / info.getPrincipal();
	// Long iImbalan = Math.round(dImbalan);
	// if (iImbalan > 9900L) {
	// iImbalan = 9900L;
	// }
	// String imbalan = "1850";// StrUtils.lpadded(iImbalan.toString(), 4,
	// // '0');
	// String sektorE = getSektorE(jenisPenggunaan);
	// Double dPlafond = info.getOsPrincipal() / 1000;
	// Long iPlafond = Math.round(dPlafond);
	// String plafond = StrUtils.lpadded(iPlafond.toString(), 12, '0');
	// String longgarTarik = StrUtils.lpadded("0", 12, '0');
	// Double dSaldoPembiayaan = info.getOsPrincipal() / 1000;
	// Long iSaldoPembiayaan = Math.round(dSaldoPembiayaan);
	// String saldoPembiayaan = StrUtils.lpadded(iSaldoPembiayaan.toString(),
	// 12, '0');
	// Double dNilaiAgunan = info.getGuarantee() / 1000;
	// Long iNilaiAgunan = Math.round(dNilaiAgunan);
	// String nilaiAgunan = StrUtils.lpadded(iNilaiAgunan.toString(), 12, '0');
	// String agunan = getJenisAgunan(info.getGuaranteeType());
	// Double dPpap = info.getPpap() / 1000;
	// Long iPpap = Math.round(dPpap);
	// String ppap = StrUtils.lpadded(iPpap.toString(), 12, '0');
	// String sifatPlafond = "1";
	// String metodeBasil = "2";
	// String metodeBasilDana = "2";
	// String golPenjamin = "880";
	// String bagPenjamin = "9900";
	// String golNasabah = "876";
	// String lokasiUsaha = biCityConverter
	// .get(info.getCity(), info.getCode());
	// String golPiutang = getGolonganPiutang(jenisPenggunaan);
	// String strLine = StrUtils.lpadded("" + line, 6, '0');
	// return code + loanCode + kodeKhusus + jenisPembiayaan + jenisPenggunaan
	// + hubDenganBank + registrasi + tempo + col + imbalan + sektorE
	// + plafond + longgarTarik + saldoPembiayaan + agunan
	// + nilaiAgunan + ppap + sifatPlafond + metodeBasil
	// + metodeBasilDana + golPenjamin + bagPenjamin + golNasabah
	// + lokasiUsaha + golPiutang + strLine;
	// }

	String create09() {
		String buffer = "";
		int line = 1;
		for (LoanRpt info : listQdh) {
			buffer += create09Text(info, line++) + newLine;
		}
		return buffer;
	}

	String create09Text(LoanRpt info, int line) {
		String code = "BS09";
		String loanCode = StrUtils.rpadded(info.getCode(), 15, ' ');
		String kodeKhusus = "1       ";
		String jenisPenggunaan = getJenisPenggunaan(loanCode);
		String hubDenganBank = "2";
		String registrasi = df.print(new DateTime(info.getBegin()));
		String tempo = df.print(new DateTime(info.getEnd()));
		String col = "" + info.getQuality();
		String sektorE = getSektorE(jenisPenggunaan);
		Double dSaldoPiutang = info.getOsPrincipal() / 1000;
		Long iSaldoPiutang = Math.round(dSaldoPiutang);
		String saldoPiutang = StrUtils.lpadded(iSaldoPiutang.toString(), 12,
				'0');
		// Double dNilaiAgunan = info.getGuarantee() / 1000;
		Double dNilaiAgunan = dataManualPpapJam.getData(loanCode.trim())
				.getJaminan() / 1000;
		Long iNilaiAgunan = Math.round(dNilaiAgunan);
		String nilaiAgunan = StrUtils.lpadded(iNilaiAgunan.toString(), 12, '0');
		String agunan = getJenisAgunan(info.getGuaranteeType());
		if (iNilaiAgunan > 0 && agunan.equalsIgnoreCase("0")) {
			agunan = "3";
			System.out.println(loanCode + " - Agunan bermasalah....");
		}
		// Double dPpap = info.getPpap() / 1000;
		Double dPpap = dataManualPpapJam.getData(loanCode.trim()).getPpap() / 1000;
		Long iPpap = Math.round(dPpap);
		String ppap = StrUtils.lpadded(iPpap.toString(), 12, '0');
		String metodeBasil = "2";
		String golPenjamin = "880";
		String bagPenjamin = "9999";
		String golNasabah = "876";
		String lokasiUsaha = biCityConverter
				.get(info.getCity(), info.getCode());
		String golPiutang = getGolonganPiutang(jenisPenggunaan);
		String strLine = StrUtils.lpadded("" + line, 6, '0');
		return code + loanCode + kodeKhusus + jenisPenggunaan + hubDenganBank
				+ registrasi + tempo + col + sektorE + saldoPiutang + agunan
				+ nilaiAgunan + ppap + metodeBasil + golPenjamin + bagPenjamin
				+ golNasabah + lokasiUsaha + golPiutang + strLine;
	}

	String create20() {
		String buffer = "";
		int line = 1;
		for (LoanRpt info : listMjs) {
			buffer += create20Text(info, line++) + newLine;
		}
		return buffer;
	}

	String create20Text(LoanRpt info, int line) {
		String code = "BS20";
		String loanCode = StrUtils.rpadded(info.getCode(), 15, ' ');
		String kodeKhusus = "1       ";
		String jenisPenggunaan = getJenisPenggunaan(loanCode);
		String hubDenganBank = "2";
		String registrasi = df.print(new DateTime(info.getBegin()));
		String tempo = df.print(new DateTime(info.getEnd()));
		String col = "" + info.getQuality();
		String sektorE = getSektorE(jenisPenggunaan);
		String metodeBasilDana = "2";
		String golPenjamin = "880";
		String bagPenjamin = "9999";
		String golNasabah = "876";
		String lokasiUsaha = biCityConverter
				.get(info.getCity(), info.getCode());
		String golPiutang = getGolonganPiutang(jenisPenggunaan);
		Double dHargaJual = (info.getPrincipal() + info.getMargin()) / 1000;
		Long iHargaJual = Math.round(dHargaJual);
		String hargaJual = StrUtils.lpadded(iHargaJual.toString(), 12, '0');
		Double dSaldoPokok = info.getOsPrincipal() / 1000;
		Long iSaldoPokok = Math.round(dSaldoPokok);
		String saldoPokok = StrUtils.lpadded(iSaldoPokok.toString(), 12, '0');
		Double dSaldoMargin = info.getOsMargin() / 1000;
		Long iSaldoMargin = Math.round(dSaldoMargin);
		String saldoMargin = StrUtils.lpadded(iSaldoMargin.toString(), 12, '0');
		Long iSaldoPiutang = iSaldoPokok + iSaldoMargin;
		String saldoPiutang = StrUtils.lpadded(iSaldoPiutang.toString(), 12,
				'0');
		// Double dNilaiAgunan = info.getGuarantee() / 1000;
		Double dNilaiAgunan = dataManualPpapJam.getData(loanCode.trim())
				.getJaminan() / 1000;
		Long iNilaiAgunan = Math.round(dNilaiAgunan);
		String nilaiAgunan = StrUtils.lpadded(iNilaiAgunan.toString(), 12, '0');
		String agunan = getJenisAgunan(info.getGuaranteeType());
		// Double dPpap = info.getPpap() / 1000;
		Double dPpap = dataManualPpapJam.getData(loanCode.trim()).getPpap() / 1000;
		Long iPpap = Math.round(dPpap);
		String ppap = StrUtils.lpadded(iPpap.toString(), 12, '0');
		String strLine = StrUtils.lpadded("" + line, 6, '0');
		return code + loanCode + kodeKhusus + jenisPenggunaan + hubDenganBank
				+ registrasi + tempo + col + sektorE + metodeBasilDana
				+ golPenjamin + bagPenjamin + golNasabah + lokasiUsaha
				+ golPiutang + hargaJual + saldoPokok + saldoMargin
				+ saldoPiutang + agunan + nilaiAgunan + ppap + strLine;
	}

	public List<LoanRpt> getListMbh() {
		return listMbh;
	}

	public List<LoanRpt> getListPby() {
		return listPby;
	}

	public List<LoanRpt> getListQdh() {
		return listQdh;
	}

	public List<LoanRpt> getListMjs() {
		return listMjs;
	}

	// private void insertRepayment() {
	// nilai.put("0014100456A", 31240.00);
	// nilai.put("0014100473A1", 380201.00);
	// nilai.put("0014100499", 754832.00);
	// nilai.put("0014100500", 587604.00);
	// nilai.put("0014100517", 114065.00);
	// nilai.put("0014100522", 68796.00);
	// nilai.put("0014100524", 2192.00);
	// nilai.put("0014100525", 61944.00);
	// nilai.put("0014100526", 60000.00);
	// nilai.put("0014100527", 36904.00);
	// nilai.put("0014100528", 71806.00);
	// nilai.put("0014100529", 92124.00);
	// nilai.put("0024100015", 458505.00);
	// nilai.put("0024100018", 4499804.00);
	// nilai.put("0024100024", 82420.00);
	// nilai.put("0024100027", 4065866.00);
	// nilai.put("0024100029", 1074836.00);
	// nilai.put("0034100009", 489040.00);
	// nilai.put("0034100010", 53808.00);
	// nilai.put("0034100011", 203224.00);
	// nilai.put("0034100012", 348194.00);
	// nilai.put("0034100014", 132938.00);
	// nilai.put("0104101695", 514296.77);
	// nilai.put("0104101698", 900870.97);
	// nilai.put("0104101704", 387096.77);
	// nilai.put("0104101705", 283870.97);
	// nilai.put("0104101706", 1032258.06);
	// nilai.put("0104101707", 413709.68);
	// nilai.put("0104101708", 371612.90);
	// nilai.put("0104101709", 743225.81);
	// nilai.put("0104101711", 653225.81);
	// nilai.put("0104101712", 653225.81);
	// nilai.put("0104101713", 326612.90);
	// nilai.put("0104101714", 5922.58);
	// nilai.put("0104101715", 283870.97);
	// nilai.put("0104101716", 77419.35);
	// nilai.put("0104101717", 169290.32);
	// nilai.put("0104101718", 18064.52);
	// nilai.put("0104101719", 561290.32);
	// nilai.put("0104101720", 41290.32);
	// nilai.put("0104101722", 980645.16);
	// nilai.put("0104101723", 193032.26);
	// nilai.put("0104101724", 21290.32);
	// nilai.put("0104101725", 433548.39);
	// nilai.put("0104101726", 82580.65);
	// nilai.put("0104101727", 30967.74);
	// nilai.put("0104101729", 46451.61);
	// nilai.put("0104101730", 101161.29);
	// nilai.put("0104101731", 43870.97);
	// nilai.put("0204100001", 60368.00);
	// nilai.put("0204100002", 587902.00);
	// nilai.put("0204100004", 18060.00);
	// nilai.put("0204100011", 18739.00);
	// nilai.put("0204100013", 657540.00);
	// nilai.put("0204100016", 169353.00);
	// nilai.put("0204100028", 274355.00);
	// nilai.put("0204100032", 1500000.00);
	// nilai.put("0204100034", 1291932.00);
	// nilai.put("0204100035", 691125.00);
	// nilai.put("0204100038", 45164.00);
	// nilai.put("0204100039", 1437545.00);
	// nilai.put("0204100041", 80000.00);
	// nilai.put("0204100042", 406455.00);
	// nilai.put("0204100045", 34000.00);
	// nilai.put("0204100046", 140331.00);
	// nilai.put("0204100047", 410000.00);
	// nilai.put("0204100048", 330000.00);
	// nilai.put("0204100049", 30000.00);
	// nilai.put("0204100050", 9680.00);
	// nilai.put("0204100051", 19360.00);
	// nilai.put("0204100052", 9680.00);
	// nilai.put("0204100053", 9680.00);
	// nilai.put("0204100054", 19360.00);
	// nilai.put("0204100056A1", 82263.00);
	// nilai.put("0204100057", 5160.00);
	// nilai.put("0204100058", 197432.00);
	// nilai.put("0204100059", 94836.00);
	// nilai.put("0204100062", 275814.00);
	// nilai.put("0204100063", 30492.00);
	// nilai.put("0204100065", 30492.00);
	// nilai.put("0204100066", 30492.00);
	// nilai.put("0204100069", 10164.00);
	// nilai.put("0204100070", 297580.00);
	// nilai.put("0204100072", 51934.00);
	// nilai.put("0204100073", 11323.00);
	// nilai.put("0204100074", 189680.00);
	// nilai.put("0204100075", 120120.00);
	// nilai.put("0204100076", 292354.00);
	// nilai.put("0204100078", 134186.00);
	// nilai.put("0204100081", 139356.00);
	// nilai.put("0204100082", 569808.00);
	// nilai.put("0204100083", 52649.00);
	// nilai.put("0204100084", 233885.00);
	// nilai.put("0204100085", 370545.00);
	// nilai.put("0204100086", 62000.00);
	// nilai.put("0204100087", 595000.00);
	// nilai.put("0204100088", 87093.00);
	// nilai.put("0204100089", 44514.00);
	// nilai.put("0204100090", 49355.00);
	// nilai.put("0204100091", 1694450.00);
	// nilai.put("0204100092", 1643870.00);
	// nilai.put("0204100093", 1096774.00);
	// nilai.put("0204100094", 41059.00);
	// nilai.put("0204100095", 186314.00);
	// nilai.put("0204100096", 18000.00);
	// nilai.put("0204100097", 139352.00);
	// nilai.put("0204100098", 29820.00);
	// nilai.put("0204100099", 323752.00);
	// nilai.put("0204100100", 24134.00);
	// nilai.put("0204100101", 218060.00);
	// nilai.put("0304100003", 152418.00);
	// nilai.put("0304100004", 56782.00);
	// nilai.put("0404100001", 44352.00);
	// nilai.put("0404100002", 9680.00);
	// nilai.put("0404100003A1", 24190.00);
	// nilai.put("0404100004", 74849.00);
	// nilai.put("0404100005", 69000.00);
	// nilai.put("0404100006", 77217.00);
	// nilai.put("0404100007", 56771.00);
	// nilai.put("0404100008", 109110.00);
	// nilai.put("0404100009", 193550.00);
	// nilai.put("0404100011", 79044.00);
	// nilai.put("0404100013", 63876.00);
	// nilai.put("0404100014", 49548.00);
	// nilai.put("0404100015", 15486.00);
	// nilai.put("0404100016", 67106.00);
	// nilai.put("0404100017", 103225.00);
	// nilai.put("0404100018", 48000.00);
	// nilai.put("0404100019", 23419.00);
	// nilai.put("0404100020", 25967.00);
	// nilai.put("0404100021", 50000.00);
	// nilai.put("0404100022", 160000.00);
	// nilai.put("0404100023", 40715.00);
	// nilai.put("0404100024", 53680.00);
	// nilai.put("0404100025", 89813.00);
	// nilai.put("0404100026", 96876.00);
	// nilai.put("0404100027", 123386.00);
	// nilai.put("0404100028", 12900.00);
	// nilai.put("0404100029", 149032.00);
	// nilai.put("0404100030", 40299.00);
	// nilai.put("0404100031", 46458.00);
	// nilai.put("0404100032", 42585.00);
	// nilai.put("0404100033", 12776.00);
	// nilai.put("0404100034", 23034.00);
	// nilai.put("0404100035", 46452.00);
	// nilai.put("0404100036", 33870.00);
	// nilai.put("0404100037", 116928.00);
	// nilai.put("0404100038", 55755.00);
	// nilai.put("0404100039", 60963.00);
	// nilai.put("0404100040", 43877.00);
	// nilai.put("0404100041", 43358.00);
	// nilai.put("0404100042", 91448.00);
	// nilai.put("0404100043", 60385.00);
	// nilai.put("0404100044", 42584.00);
	// nilai.put("0014200178", 1847486.00);
	// nilai.put("0014200205", 397935.00);
	// nilai.put("0014200219A1", 708065.00);
	// nilai.put("0014200230", 125814.00);
	// nilai.put("0014200236", 619354.00);
	// nilai.put("0014200240", 156294.00);
	// nilai.put("0014200243A1", 299424.00);
	// nilai.put("0014200244", 706447.00);
	// nilai.put("0014200245", 145805.00);
	// nilai.put("0014200250", 958062.00);
	// nilai.put("0014200253", 189782.00);
	// nilai.put("0014200254", 781460.00);
	// nilai.put("0014200263A1", 582261.00);
	// nilai.put("0014200265", 30582.00);
	// nilai.put("0014200269", 34398.00);
	// nilai.put("0014200271", 247805.00);
	// nilai.put("0014200272", 191124.00);
	// nilai.put("0014200273", 45870.00);
	// nilai.put("0014200274", 6195.00);
	// nilai.put("0014200277", 65297.00);
	// nilai.put("0014200278", 87193.00);
	// nilai.put("0014200279", 111331.00);
	// nilai.put("0014200280", 12384.00);
	// nilai.put("0014200281", 88780.00);
	// nilai.put("0014200282", 116904.00);
	// nilai.put("0014200283", 11352.00);
	// nilai.put("0014200284", 95508.00);
	// nilai.put("0014200285", 51620.00);
	// nilai.put("0014200286", 19352.00);
	// nilai.put("0014200287", 19352.00);
	// nilai.put("0014200288", 19352.00);
	// nilai.put("0014200289", 19352.00);
	// nilai.put("0014200290", 19352.00);
	// nilai.put("0014200291", 19352.00);
	// nilai.put("0014200292", 16933.00);
	// nilai.put("0014200293", 16933.00);
	// nilai.put("0014200294", 16933.00);
	// nilai.put("0014200295", 16933.00);
	// nilai.put("0014200296", 16933.00);
	// nilai.put("0014200297", 96873.00);
	// nilai.put("0014200298", 138186.00);
	// nilai.put("0014200299", 14190.00);
	// nilai.put("0014200301", 98328.00);
	// nilai.put("0014200302", 7743.00);
	// nilai.put("0014200303", 133354.00);
	// nilai.put("0014200304", 69095.00);
	// nilai.put("0014200305", 41300.00);
	// nilai.put("0014200306", 194190.00);
	// nilai.put("0014200307", 28903.00);
	// nilai.put("0014200308", 49560.00);
	// nilai.put("0014200309", 145863.00);
	// nilai.put("0014200310", 24768.00);
	// nilai.put("0014200311", 229932.00);
	// nilai.put("0024200021", 208940.00);
	// nilai.put("0024200025", 3340198.00);
	// nilai.put("0024200026", 1256460.00);
	// nilai.put("0024200027", 239031.00);
	// nilai.put("0024200028", 13644.00);
	// nilai.put("0024200029", 54580.00);
	// nilai.put("0024200030", 14448.00);
	// nilai.put("0024200031", 3145161.00);
	// nilai.put("0024200032", 38715.00);
	// nilai.put("0034200017A1", 370920.00);
	// nilai.put("0034200026", 7645.00);
	// nilai.put("0034200031", 510777.00);
	// nilai.put("0034200039", 41938.00);
	// nilai.put("0034200047", 77633.00);
	// nilai.put("0034200048", 306960.00);
	// nilai.put("0034200050", 128225.00);
	// nilai.put("0034200051", 58926.00);
	// nilai.put("0034200053", 12260.00);
	// nilai.put("0034200054", 321226.00);
	// nilai.put("0034200055A1", 344955.00);
	// nilai.put("0034200058", 1081410.00);
	// nilai.put("0034200060", 33124.00);
	// nilai.put("0034200061", 664254.00);
	// nilai.put("0034200062", 54200.00);
	// nilai.put("0034200063", 55170.00);
	// nilai.put("0034200064", 357224.00);
	// nilai.put("0034200065", 112455.00);
	// nilai.put("0034200066", 598832.00);
	// nilai.put("0034200067", 39391.00);
	// nilai.put("0034200069", 83619.00);
	// nilai.put("0034200071", 63864.00);
	// nilai.put("0034200072", 677418.00);
	// nilai.put("0034200074", 34544.00);
	// nilai.put("0034200075", 89415.00);
	// nilai.put("0034200076", 319552.00);
	// nilai.put("0034200077", 440712.00);
	// nilai.put("0034200078", 61550.00);
	// nilai.put("0034200079", 60384.00);
	// nilai.put("0034200080", 143220.00);
	// nilai.put("0034200081", 265876.00);
	// nilai.put("0034200082", 56910.00);
	// nilai.put("0034200083", 417291.00);
	// nilai.put("0034200084", 75480.00);
	// nilai.put("0044200007A1", 4871225.81);
	// nilai.put("0044200010", 27480.00);
	// nilai.put("0044200011", 18392.00);
	// nilai.put("0044200012", 174780.00);
	// nilai.put("0044200015", 1693548.00);
	// nilai.put("0044200016", 52416.00);
	// nilai.put("0044200017", 56056.00);
	// nilai.put("0044200018", 69416.00);
	// nilai.put("0044200019", 90134.00);
	// nilai.put("0044200020", 38700.00);
	// nilai.put("0044200022", 398712.00);
	// nilai.put("0044200023", 4644.00);
	// nilai.put("0044200024", 14448.00);
	// nilai.put("0044200025", 43344.00);
	// nilai.put("0044200026", 28380.00);
	// nilai.put("0044200027", 85944.00);
	// nilai.put("0104201702", 306419.35);
	// nilai.put("0104201704", 185806.45);
	// nilai.put("0104201709", 15580.65);
	// nilai.put("0104201710", 1225677.42);
	// nilai.put("0104201712", 248516.13);
	// nilai.put("0104201718", 114967.74);
	// nilai.put("0104201720", 147225.81);
	// nilai.put("0104201722", 56206.45);
	// nilai.put("0104201723", 1070967.74);
	// nilai.put("0104201730", 247741.94);
	// nilai.put("0104201732", 43870.97);
	// nilai.put("0104201734A1", 2137500.00);
	// nilai.put("0104201740", 583432.26);
	// nilai.put("0104201741", 691612.90);
	// nilai.put("0104201743", 282120.97);
	// nilai.put("0104201744", 72258.06);
	// nilai.put("0104201745", 495483.87);
	// nilai.put("0104201747", 202322.58);
	// nilai.put("0104201749", 41290.32);
	// nilai.put("0104201750", 193548.39);
	// nilai.put("0104201751", 89187.10);
	// nilai.put("0104201752", 39838.71);
	// nilai.put("0104201753", 41290.32);
	// nilai.put("0104201756", 1416774.19);
	// nilai.put("0104201757", 820645.16);
	// nilai.put("0104201758", 270967.74);
	// nilai.put("0104201759", 185806.45);
	// nilai.put("0104201760", 36354.84);
	// nilai.put("0104201761", 21677.42);
	// nilai.put("0104201763", 412903.23);
	// nilai.put("0104201764", 47535.48);
	// nilai.put("0104201765", 1316129.03);
	// nilai.put("0104201766", 37161.29);
	// nilai.put("0104201767", 670967.74);
	// nilai.put("0104201768", 34064.52);
	// nilai.put("0104201769", 54193.55);
	// nilai.put("0104201770", 24774.19);
	// nilai.put("0104201771", 283870.97);
	// nilai.put("0104201773A1", 6451.61);
	// nilai.put("0104201774", 371612.90);
	// nilai.put("0104201775", 206451.61);
	// nilai.put("0104201776", 201290.32);
	// nilai.put("0104201777", 392258.06);
	// nilai.put("0104201778", 180645.16);
	// nilai.put("0104201779", 180645.16);
	// nilai.put("0104201780", 1246451.61);
	// nilai.put("0104201781", 268387.10);
	// nilai.put("0104201782", 40258.06);
	// nilai.put("0104201783", 270967.74);
	// nilai.put("0104201784", 59274.19);
	// nilai.put("0104201785", 59274.19);
	// nilai.put("0104201786", 30967.74);
	// nilai.put("0104201789", 108387.10);
	// nilai.put("0104201790", 9290.32);
	// nilai.put("0104201791", 30967.74);
	// nilai.put("0104201792", 516129.03);
	// nilai.put("0104201793", 294193.55);
	// nilai.put("0104201794", 21935.48);
	// nilai.put("0104201795", 30967.74);
	// nilai.put("0104201796", 851612.90);
	// nilai.put("0104201797", 103225.81);
	// nilai.put("0104201798", 77419.35);
	// nilai.put("0104201799", 609677.42);
	// nilai.put("0104201800", 30967.74);
	// nilai.put("0104201801", 6193.55);
	// nilai.put("0104201802", 91935.48);
	// nilai.put("0104201803", 619354.84);
	// nilai.put("0104201804", 24774.19);
	// nilai.put("0104201805", 108387.10);
	// nilai.put("0104201806", 216774.19);
	// nilai.put("0104201807", 309677.42);
	// nilai.put("0104201808", 116129.03);
	// nilai.put("0104201809", 116129.03);
	// nilai.put("0104201810", 361290.32);
	// nilai.put("0104201811", 37161.29);
	// nilai.put("0104201812", 191935.48);
	// nilai.put("0104201813", 191935.48);
	// nilai.put("0104201814", 903225.81);
	// nilai.put("0104201815", 20645.16);
	// nilai.put("0104201816", 83612.90);
	// nilai.put("0104201817", 309677.42);
	// nilai.put("0104201818", 33548.39);
	// nilai.put("0104201819", 123870.97);
	// nilai.put("0104201820", 20645.16);
	// nilai.put("0104201821", 33870.97);
	// nilai.put("0104201822", 108387.10);
	// nilai.put("0104201823", 90322.58);
	// nilai.put("0104201824", 180645.16);
	// nilai.put("0204200003", 1372479.00);
	// nilai.put("0204200004", 174192.00);
	// nilai.put("0204200015", 18000.00);
	// nilai.put("0204200016", 10000.00);
	// nilai.put("0204200017", 58000.00);
	// nilai.put("0204200019", 541925.00);
	// nilai.put("0204200020", 139525.00);
	// nilai.put("0204200025", 304830.00);
	// nilai.put("0204200026", 109350.00);
	// nilai.put("0204200028", 217740.00);
	// nilai.put("0204200029", 1415322.00);
	// nilai.put("0204200033", 733410.00);
	// nilai.put("0204200034", 1548125.00);
	// nilai.put("0204200035", 580656.00);
	// nilai.put("0204200036", 608190.00);
	// nilai.put("0204200037", 15358.00);
	// nilai.put("0204200039", 195000.00);
	// nilai.put("0204200040", 600000.00);
	// nilai.put("0204200041", 367739.00);
	// nilai.put("0204200043", 37152.00);
	// nilai.put("0204200044", 38700.00);
	// nilai.put("0204200045", 19360.00);
	// nilai.put("0204200046", 38700.00);
	// nilai.put("0204200047", 9680.00);
	// nilai.put("0204200048", 29040.00);
	// nilai.put("0204200049", 181455.00);
	// nilai.put("0204200051", 566128.00);
	// nilai.put("0204200053", 1741932.00);
	// nilai.put("0204200054", 67746.00);
	// nilai.put("0204200055", 203224.00);
	// nilai.put("0204200056", 5031.00);
	// nilai.put("0204200057", 180000.00);
	// nilai.put("0204200058", 675000.00);
	// nilai.put("0204200059", 600000.00);
	// nilai.put("0204200060", 270956.00);
	// nilai.put("0204200061", 2073800.00);
	// nilai.put("0204200062", 82580.00);
	// nilai.put("0204200063", 96687.00);
	// nilai.put("0204200064", 446712.00);
	// nilai.put("0204200065", 62703.00);
	// nilai.put("0204200067", 821000.00);
	// nilai.put("0204200068", 821000.00);
	// nilai.put("0204200069", 821000.00);
	// nilai.put("0204200070", 139355.00);
	// nilai.put("0204200072", 47495.00);
	// nilai.put("0204200073", 133538.00);
	// nilai.put("0204200075", 280000.00);
	// nilai.put("0204200076", 49478.00);
	// nilai.put("0204200078", 691000.00);
	// nilai.put("0204200079", 755000.00);
	// nilai.put("0204200080", 1403870.00);
	// nilai.put("0204200081", 145644.00);
	// nilai.put("0204200082", 1490324.00);
	// nilai.put("0204200083", 498582.00);
	// nilai.put("0204200084", 24768.00);
	// nilai.put("0204200085", 65480.00);
	// nilai.put("0204200086", 28390.00);
	// nilai.put("0204200087", 17288.00);
	// nilai.put("0204200088", 707291.00);
	// nilai.put("0204200089", 1643870.00);
	// nilai.put("0204200090", 497910.00);
	// nilai.put("0204200091", 30975.00);
	// nilai.put("0204200092", 38475.00);
	// nilai.put("0204200093", 743000.00);
	// nilai.put("0204200094", 903229.00);
	// nilai.put("0204200095", 141127.00);
	// nilai.put("0204200096", 100805.00);
	// nilai.put("0204200097", 784000.00);
	// nilai.put("0204200098", 1694450.00);
	// nilai.put("0204200099", 1694450.00);
	// nilai.put("0204200100", 60483.00);
	// nilai.put("0204200102", 372647.00);
	// nilai.put("0204200103", 134843.00);
	// nilai.put("0204200104", 46458.00);
	// nilai.put("0204200105", 74025.00);
	// nilai.put("0204200106", 12582.00);
	// nilai.put("0204200107", 105675.00);
	// nilai.put("0204200108", 123870.00);
	// nilai.put("0204200109", 8776.00);
	// nilai.put("0204200110", 5420.00);
	// nilai.put("0204200111", 450000.00);
	// nilai.put("0204200112", 853551.00);
	// nilai.put("0204200113", 574848.00);
	// nilai.put("0204200114", 574848.00);
	// nilai.put("0204200115", 574848.00);
	// nilai.put("0204200116", 574848.00);
	// nilai.put("0204200117", 353165.00);
	// nilai.put("0204200118", 105160.00);
	// nilai.put("0204200119", 158067.00);
	// nilai.put("0304200001", 30972.00);
	// nilai.put("0304200004", 24420.00);
	// nilai.put("0304200005", 24420.00);
	// nilai.put("0304200007", 1323870.00);
	// nilai.put("0304200008", 19536.00);
	// nilai.put("0304200009", 17094.00);
	// nilai.put("0304200010", 81808.00);
	// nilai.put("0304200011", 60390.00);
	// nilai.put("0304200015", 39235.00);
	// nilai.put("0304200016", 96509.00);
	// nilai.put("0304200017", 13938.00);
	// nilai.put("0304200018", 120000.00);
	// nilai.put("0304200019", 60324.00);
	// nilai.put("0304200020", 41059.00);
	// nilai.put("0304200021", 30972.00);
	// nilai.put("0304200022", 6582.00);
	// nilai.put("0304200023", 44505.00);
	// nilai.put("0304200024", 40635.00);
	// nilai.put("0304200025", 87740.00);
	// nilai.put("0404200001", 131610.00);
	// nilai.put("0404200002", 541940.00);
	// nilai.put("0404200004a1", 55637.00);
	// nilai.put("0404200005", 356132.00);
	// nilai.put("0404200006a1", 51828.00);
	// nilai.put("0404200007", 101137.00);
	// nilai.put("0404200008", 483863.00);
	// nilai.put("0404200009", 2025000.00);
	// nilai.put("0404200010", 483863.00);
	// nilai.put("0404200011", 474186.00);
	// nilai.put("0404200012", 474186.00);
	// nilai.put("0404200013", 758712.00);
	// nilai.put("0404200014", 40000.00);
	// nilai.put("0404200015", 667740.00);
	// nilai.put("0404200016A1", 42585.00);
	// nilai.put("0404200017A1", 38388.00);
	// nilai.put("0404200018", 41522.00);
	// nilai.put("0404200019", 55354.00);
	// nilai.put("0404200021", 38712.00);
	// nilai.put("0404200022", 28390.00);
	// nilai.put("0404200023A1", 1741940.00);
	// nilai.put("0404200024", 70455.00);
	// nilai.put("0404200025", 28224.00);
	// nilai.put("0404200026", 21678.00);
	// nilai.put("0404200027a1", 120970.00);
	// nilai.put("0404200028", 870970.00);
	// nilai.put("0404200029", 1620970.00);
	// nilai.put("0404200030", 1620970.00);
	// nilai.put("0404200031", 247500.00);
	// nilai.put("0404200032", 21290.00);
	// nilai.put("0014300317A2", 184710.00);
	// nilai.put("0014300572A1", 44709.00);
	// nilai.put("0014300593", 929975.00);
	// nilai.put("0014300606A1", 77870.00);
	// nilai.put("0014300636", 228684.00);
	// nilai.put("0014300644", 1403625.00);
	// nilai.put("0014300650", 40768.00);
	// nilai.put("0014300651", 40768.00);
	// nilai.put("0014300652", 40768.00);
	// nilai.put("0014300680A1", 144582.00);
	// nilai.put("0014300681", 46775.00);
	// nilai.put("0014300687", 480616.00);
	// nilai.put("0014300689", 171390.00);
	// nilai.put("0014300690", 33124.00);
	// nilai.put("0014300694", 388582.00);
	// nilai.put("0014300695", 189774.00);
	// nilai.put("0014300696A1", 75192.00);
	// nilai.put("0014300697", 8130.00);
	// nilai.put("0014300699", 410324.00);
	// nilai.put("0014300701", 196972.00);
	// nilai.put("0014300708", 155679.00);
	// nilai.put("0014300713", 1531646.00);
	// nilai.put("0014300714", 188708.00);
	// nilai.put("0014300715", 411297.00);
	// nilai.put("0014300719", 164509.00);
	// nilai.put("0014300722", 502585.00);
	// nilai.put("0014300723", 725801.00);
	// nilai.put("0014300729", 130653.00);
	// nilai.put("0014300730A1", 88700.00);
	// nilai.put("0014300737", 648385.00);
	// nilai.put("0014300740", 102425.00);
	// nilai.put("0014300742", 342193.00);
	// nilai.put("0014300748", 68656.00);
	// nilai.put("0014300751A1", 1147675.00);
	// nilai.put("0014300752", 929432.00);
	// nilai.put("0014300753", 50517.00);
	// nilai.put("0014300763", 14064.00);
	// nilai.put("0014300772", 102260.00);
	// nilai.put("0014300773", 270268.00);
	// nilai.put("0014300779", 236520.00);
	// nilai.put("0014300781", 91938.00);
	// nilai.put("0014300782", 92905.00);
	// nilai.put("0014300785", 20648.00);
	// nilai.put("0014300787", 664678.00);
	// nilai.put("0014300788", 67106.00);
	// nilai.put("0014300789", 311525.00);
	// nilai.put("0014300790", 77424.00);
	// nilai.put("0014300791", 50799.00);
	// nilai.put("0014300793", 58068.00);
	// nilai.put("0014300796", 71806.00);
	// nilai.put("0014300797", 176128.00);
	// nilai.put("0014300798", 20648.00);
	// nilai.put("0014300799", 151582.00);
	// nilai.put("0014300800", 6195.00);
	// nilai.put("0014300801", 1354056.00);
	// nilai.put("0014300802", 57582.00);
	// nilai.put("0014300804", 37260.00);
	// nilai.put("0014300805", 22932.00);
	// nilai.put("0014300807", 7580.00);
	// nilai.put("0014300809", 37100.00);
	// nilai.put("0014300810", 11352.00);
	// nilai.put("0014300812", 22932.00);
	// nilai.put("0014300814", 246772.00);
	// nilai.put("0014300815", 201316.00);
	// nilai.put("0014300816", 93676.00);
	// nilai.put("0014300817", 175650.00);
	// nilai.put("0014300818", 137910.00);
	// nilai.put("0014300819", 19352.00);
	// nilai.put("0014300820", 16933.00);
	// nilai.put("0014300821", 19352.00);
	// nilai.put("0014300822", 19352.00);
	// nilai.put("0014300823", 19352.00);
	// nilai.put("0014300824", 19352.00);
	// nilai.put("0014300825", 19352.00);
	// nilai.put("0014300826", 115612.00);
	// nilai.put("0014300827", 64525.00);
	// nilai.put("0014300829", 139363.00);
	// nilai.put("0014300830", 291137.00);
	// nilai.put("0014300831", 108391.00);
	// nilai.put("0014300832", 46062.00);
	// nilai.put("0014300833", 61356.00);
	// nilai.put("0014300834", 276385.00);
	// nilai.put("0014300835", 2613.00);
	// nilai.put("0014300836", 81300.00);
	// nilai.put("0014300837", 89418.00);
	// nilai.put("0014300838", 105000.00);
	// nilai.put("0014300839", 10320.00);
	// nilai.put("0014300840", 24510.00);
	// nilai.put("0014300841", 72941.00);
	// nilai.put("0014300842", 513870.00);
	// nilai.put("0014300843", 21455.00);
	// nilai.put("0014300844", 75201.00);
	// nilai.put("0014300845", 46458.00);
	// nilai.put("0014300846", 16254.00);
	// nilai.put("0014300847", 17808.00);
	// nilai.put("0014300848", 9808.00);
	// nilai.put("0014300849", 50320.00);
	// nilai.put("0014300850", 9969.00);
	// nilai.put("0014300851", 121775.00);
	// nilai.put("0014300852", 60475.00);
	// nilai.put("0014300853", 58056.00);
	// nilai.put("0014300854", 7224.00);
	// nilai.put("0014300855", 6708.00);
	// nilai.put("0014300856", 6450.00);
	// nilai.put("0014300857", 87743.00);
	// nilai.put("0014300858", 49039.00);
	// nilai.put("0014300859", 43877.00);
	// nilai.put("0014300860", 61455.00);
	// nilai.put("0014300861", 18585.00);
	// nilai.put("0014300862", 18584.00);
	// nilai.put("0024300035", 242095.00);
	// nilai.put("0024300063", 193676.00);
	// nilai.put("0024300064", 193676.00);
	// nilai.put("0024300076", 224518.00);
	// nilai.put("0024300078", 253351.00);
	// nilai.put("0024300105A1", 15609.00);
	// nilai.put("0024300111", 184068.00);
	// nilai.put("0024300123", 64512.00);
	// nilai.put("0024300125", 6002000.00);
	// nilai.put("0024300126", 289170.00);
	// nilai.put("0024300127A1", 205930.00);
	// nilai.put("0024300130", 229680.00);
	// nilai.put("0024300135", 69672.00);
	// nilai.put("0024300140", 32805.00);
	// nilai.put("0024300141", 32805.00);
	// nilai.put("0024300142", 32805.00);
	// nilai.put("0024300143", 32805.00);
	// nilai.put("0024300144", 29160.00);
	// nilai.put("0024300145", 32805.00);
	// nilai.put("0024300146", 29160.00);
	// nilai.put("0024300147", 29160.00);
	// nilai.put("0024300148", 29160.00);
	// nilai.put("0024300149", 29160.00);
	// nilai.put("0024300186", 160966.00);
	// nilai.put("0024300191", 537096.00);
	// nilai.put("0024300193", 811254.00);
	// nilai.put("0024300195", 195866.00);
	// nilai.put("0024300196", 217585.00);
	// nilai.put("0024300200", 105350.00);
	// nilai.put("0024300201", 142876.00);
	// nilai.put("0024300204", 117231.00);
	// nilai.put("0024300207", 61152.00);
	// nilai.put("0024300208", 260495.00);
	// nilai.put("0024300209", 44896.00);
	// nilai.put("0024300210", 2540320.00);
	// nilai.put("0024300211", 204520.00);
	// nilai.put("0024300212", 140975.00);
	// nilai.put("0024300213", 29424.00);
	// nilai.put("0024300215", 81864.00);
	// nilai.put("0024300216", 91746.00);
	// nilai.put("0024300217", 117672.00);
	// nilai.put("0024300218", 65028.00);
	// nilai.put("0024300219", 445165.00);
	// nilai.put("0024300220", 59619.00);
	// nilai.put("0024300221", 80508.00);
	// nilai.put("0024300222", 276385.00);
	// nilai.put("0024300223", 38385.00);
	// nilai.put("0024300225", 122328.00);
	// nilai.put("0024300226", 41296.00);
	// nilai.put("0024300227", 61944.00);
	// nilai.put("0024300228", 28391.00);
	// nilai.put("0024300229", 42585.00);
	// nilai.put("0024300230", 18390.00);
	// nilai.put("0024300231", 40904.00);
	// nilai.put("0024300232", 243550.00);
	// nilai.put("0024300233", 204384.00);
	// nilai.put("0024300234", 41032.00);
	// nilai.put("0034300038", 22452.00);
	// nilai.put("0034300043", 628230.00);
	// nilai.put("0034300049A1", 669174.00);
	// nilai.put("0034300054", 245677.00);
	// nilai.put("0034300064", 47488.00);
	// nilai.put("0034300082", 87102.00);
	// nilai.put("0034300091", 56396.00);
	// nilai.put("0034300092", 56396.00);
	// nilai.put("0034300093", 56396.00);
	// nilai.put("0034300094", 56396.00);
	// nilai.put("0034300096", 56396.00);
	// nilai.put("0034300097", 56396.00);
	// nilai.put("0034300101", 56396.00);
	// nilai.put("0034300106", 56396.00);
	// nilai.put("0034300107", 56396.00);
	// nilai.put("0034300109", 56396.00);
	// nilai.put("0034300110", 56396.00);
	// nilai.put("0034300113", 76625.00);
	// nilai.put("0034300114A1", 217661.00);
	// nilai.put("0034300121A1", 169568.00);
	// nilai.put("0034300128", 451354.00);
	// nilai.put("0034300129", 290522.00);
	// nilai.put("0034300133", 168677.00);
	// nilai.put("0034300134", 873864.00);
	// nilai.put("0034300135", 82642.00);
	// nilai.put("0034300142", 149040.00);
	// nilai.put("0034300145", 350910.00);
	// nilai.put("0034300146", 35480.00);
	// nilai.put("0034300147", 184739.00);
	// nilai.put("0034300148", 2248248.00);
	// nilai.put("0034300149", 145150.00);
	// nilai.put("0034300151", 471935.00);
	// nilai.put("0034300155", 116292.00);
	// nilai.put("0034300157", 70150.00);
	// nilai.put("0034300158", 53508.00);
	// nilai.put("0034300159", 53715.00);
	// nilai.put("0034300160", 131780.00);
	// nilai.put("0034300163", 235198.00);
	// nilai.put("0034300164", 64365.00);
	// nilai.put("0034300165", 929030.00);
	// nilai.put("0034300168", 87296.00);
	// nilai.put("0034300169", 126000.00);
	// nilai.put("0034300170", 156300.00);
	// nilai.put("0034300171", 19866.00);
	// nilai.put("0034300172", 18483.00);
	// nilai.put("0034300173", 46777.00);
	// nilai.put("0034300175", 37940.00);
	// nilai.put("0034300176", 40650.00);
	// nilai.put("0034300177", 109770.00);
	// nilai.put("0034300178", 228245.00);
	// nilai.put("0034300179", 200525.00);
	// nilai.put("0034300180", 122328.00);
	// nilai.put("0034300182", 94440.00);
	// nilai.put("0034300183", 60640.00);
	// nilai.put("0034300184", 35480.00);
	// nilai.put("0034300185", 16062.00);
	// nilai.put("0034300186", 136755.00);
	// nilai.put("0034300187", 157690.00);
	// nilai.put("0034300188", 76310.00);
	// nilai.put("0034300189", 306576.00);
	// nilai.put("0034300190", 49039.00);
	// nilai.put("0034300191", 16000.00);
	// nilai.put("0034300192", 32512.00);
	// nilai.put("0034300193", 24192.00);
	// nilai.put("0034300194", 35604.00);
	// nilai.put("0034300195", 67746.00);
	// nilai.put("0034300196", 32229.00);
	// nilai.put("0034300197", 442750.00);
	// nilai.put("0034300198", 65416.00);
	// nilai.put("0034300199", 76608.00);
	// nilai.put("0034300200", 45509.00);
	// nilai.put("0034300201", 45509.00);
	// nilai.put("0034300202", 45509.00);
	// nilai.put("0034300203", 32285.00);
	// nilai.put("0034300204", 18060.00);
	// nilai.put("0034300205", 9030.00);
	// nilai.put("0034300206", 122836.00);
	// nilai.put("0034300207", 85347.00);
	// nilai.put("0034300208", 98328.00);
	// nilai.put("0034300209", 53715.00);
	// nilai.put("0044300001", 151538.00);
	// nilai.put("0044300006", 5000.00);
	// nilai.put("0044300009", 49680.00);
	// nilai.put("0044300011", 10324.00);
	// nilai.put("0044300012", 114716.00);
	// nilai.put("0044300013", 204520.00);
	// nilai.put("0044300014", 142876.00);
	// nilai.put("0044300015", 103740.00);
	// nilai.put("0044300018", 55170.00);
	// nilai.put("0044300019", 291137.00);
	// nilai.put("0044300020", 107478.00);
	// nilai.put("0044300021", 36285.00);
	// nilai.put("0044300023", 193552.00);
	// nilai.put("0044300024", 91449.00);
	// nilai.put("0044300025", 18067.00);
	// nilai.put("0044300026", 191616.00);
	// nilai.put("0044300027", 170678.00);
	// nilai.put("0044300028", 36000.00);
	// nilai.put("0044300029", 83615.00);
	// nilai.put("0044300030", 60398.00);
	// nilai.put("0044300031", 45430.00);
	// nilai.put("0044300032", 239032.00);
	// nilai.put("0044300033", 9288.00);
	// nilai.put("0044300034", 20640.00);
	// nilai.put("0044300035", 82064.00);
	// nilai.put("0044300036", 36134.00);
	// nilai.put("0104301709", 109741.94);
	// nilai.put("0104301714", 47419.35);
	// nilai.put("0104301729", 15725.81);
	// nilai.put("0104301731", 24774.19);
	// nilai.put("0104301743", 37935.48);
	// nilai.put("0104301744", 21677.42);
	// nilai.put("0104301747", 867096.77);
	// nilai.put("0104301752", 107354.84);
	// nilai.put("0104301753", 209032.26);
	// nilai.put("0104301754", 133161.29);
	// nilai.put("0104301756", 157935.48);
	// nilai.put("0104301761", 46451.61);
	// nilai.put("0104301764", 138322.58);
	// nilai.put("0104301765", 77419.35);
	// nilai.put("0104301766", 133064.52);
	// nilai.put("0104301767", 72258.06);
	// nilai.put("0104301769", 49548.39);
	// nilai.put("0104301770", 120967.74);
	// nilai.put("0104301772", 53629.03);
	// nilai.put("0104301773", 3819354.84);
	// nilai.put("0104301775", 179612.90);
	// nilai.put("0104301776", 232258.06);
	// nilai.put("0104301777", 139354.84);
	// nilai.put("0104301779", 20645.16);
	// nilai.put("0104301780", 23225.81);
	// nilai.put("0104301782", 2903.23);
	// nilai.put("0104301783", 209032.26);
	// nilai.put("0104301784", 29032.26);
	// nilai.put("0104301785", 1070967.74);
	// nilai.put("0104301786", 251612.90);
	// nilai.put("0104301787", 77419.35);
	// nilai.put("0104301788", 70451.61);
	// nilai.put("0104301789", 34274.19);
	// nilai.put("0104301790", 227096.77);
	// nilai.put("0104301791", 668387.10);
	// nilai.put("0104301793", 119741.94);
	// nilai.put("0104301794", 35612.90);
	// nilai.put("0104301795", 36129.03);
	// nilai.put("0104301796", 77419.35);
	// nilai.put("0104301797", 82580.65);
	// nilai.put("0104301798", 139354.84);
	// nilai.put("0104301800", 71419.35);
	// nilai.put("0104301802", 55741.94);
	// nilai.put("0104301803", 23225.81);
	// nilai.put("0104301804", 15483.87);
	// nilai.put("0104301805", 191935.48);
	// nilai.put("0104301806", 12903.23);
	// nilai.put("0104301807", 1209.68);
	// nilai.put("0104301808", 28903.23);
	// nilai.put("0104301809", 135483.87);
	// nilai.put("0104301810", 541935.48);
	// nilai.put("0104301811", 54193.55);
	// nilai.put("0104301812", 20645.16);
	// nilai.put("0104301813", 338709.68);
	// nilai.put("0104301814", 216774.19);
	// nilai.put("0104301815", 41290.32);
	// nilai.put("0104301816", 15483.87);
	// nilai.put("0204300005", 43365.00);
	// nilai.put("0204300007", 210482.00);
	// nilai.put("0204300012", 226776.00);
	// nilai.put("0204300016", 280328.00);
	// nilai.put("0204300017A1", 137099.00);
	// nilai.put("0204300030", 28000.00);
	// nilai.put("0204300032", 28000.00);
	// nilai.put("0204300033", 28000.00);
	// nilai.put("0204300034", 34593.00);
	// nilai.put("0204300036", 26208.00);
	// nilai.put("0204300037", 15730.00);
	// nilai.put("0204300038", 9672.00);
	// nilai.put("0204300039", 18000.00);
	// nilai.put("0204300040", 18000.00);
	// nilai.put("0204300042", 12000.00);
	// nilai.put("0204300043", 12000.00);
	// nilai.put("0204300044", 4836.00);
	// nilai.put("0204300047", 12000.00);
	// nilai.put("0204300048", 12000.00);
	// nilai.put("0204300049", 9678.00);
	// nilai.put("0204300054", 10000.00);
	// nilai.put("0204300055", 10000.00);
	// nilai.put("0204300056", 36580.00);
	// nilai.put("0204300057", 10080.00);
	// nilai.put("0204300058", 6450.00);
	// nilai.put("0204300059", 10080.00);
	// nilai.put("0204300061", 8644.00);
	// nilai.put("0204300062", 145170.00);
	// nilai.put("0204300063", 46777.00);
	// nilai.put("0204300065", 35090.00);
	// nilai.put("0204300067", 58000.00);
	// nilai.put("0204300069", 111104.00);
	// nilai.put("0204300070", 56000.00);
	// nilai.put("0204300071", 54000.00);
	// nilai.put("0204300075", 26620.00);
	// nilai.put("0204300076", 26620.00);
	// nilai.put("0204300078", 151616.00);
	// nilai.put("0204300079", 264192.00);
	// nilai.put("0204300082", 26000.00);
	// nilai.put("0204300086", 69192.00);
	// nilai.put("0204300090", 38710.00);
	// nilai.put("0204300100", 188000.00);
	// nilai.put("0204300101", 24331.00);
	// nilai.put("0204300105", 53229.00);
	// nilai.put("0204300106", 301940.00);
	// nilai.put("0204300109", 446610.00);
	// nilai.put("0204300110", 28679.00);
	// nilai.put("0204300114A1", 114830.00);
	// nilai.put("0204300116", 998712.00);
	// nilai.put("0204300117", 159676.00);
	// nilai.put("0204300118", 988190.00);
	// nilai.put("0204300122", 555480.00);
	// nilai.put("0204300123", 15314.00);
	// nilai.put("0204300124", 170712.00);
	// nilai.put("0204300125", 54195.00);
	// nilai.put("0204300126", 54195.00);
	// nilai.put("0204300131", 16933.00);
	// nilai.put("0204300137", 121940.00);
	// nilai.put("0204300140", 66204.00);
	// nilai.put("0204300141", 54864.00);
	// nilai.put("0204300142", 125814.00);
	// nilai.put("0204300143", 215752.00);
	// nilai.put("0204300144", 279478.00);
	// nilai.put("0204300145", 18538.00);
	// nilai.put("0204300146", 335106.00);
	// nilai.put("0204300147", 1734190.00);
	// nilai.put("0204300148", 1517430.00);
	// nilai.put("0204300149", 1853430.00);
	// nilai.put("0204300150", 1734190.00);
	// nilai.put("0204300151", 52260.00);
	// nilai.put("0204300152", 28000.00);
	// nilai.put("0204300153", 23907.00);
	// nilai.put("0204300154", 280646.00);
	// nilai.put("0204300155", 377416.00);
	// nilai.put("0204300156", 264972.00);
	// nilai.put("0204300157", 19740.00);
	// nilai.put("0204300158", 249672.00);
	// nilai.put("0204300160", 82260.00);
	// nilai.put("0204300161", 29610.00);
	// nilai.put("0204300162", 158064.00);
	// nilai.put("0204300163", 158000.00);
	// nilai.put("0204300164", 91740.00);
	// nilai.put("0204300165", 20645.00);
	// nilai.put("0204300167", 145170.00);
	// nilai.put("0204300168", 58000.00);
	// nilai.put("0204300170", 175392.00);
	// nilai.put("0204300171", 185813.00);
	// nilai.put("0204300174", 225538.00);
	// nilai.put("0204300175", 257900.00);
	// nilai.put("0204300176", 40000.00);
	// nilai.put("0204300177", 38700.00);
	// nilai.put("0204300178", 38700.00);
	// nilai.put("0204300180", 19360.00);
	// nilai.put("0204300181", 9680.00);
	// nilai.put("0204300182", 19360.00);
	// nilai.put("0204300184", 19360.00);
	// nilai.put("0204300185", 38700.00);
	// nilai.put("0204300186", 19360.00);
	// nilai.put("0204300187", 991940.00);
	// nilai.put("0204300188", 29040.00);
	// nilai.put("0204300191", 230448.00);
	// nilai.put("0204300192", 96780.00);
	// nilai.put("0204300193", 20969.00);
	// nilai.put("0204300195", 218230.00);
	// nilai.put("0204300196", 128900.00);
	// nilai.put("0204300197", 23949.00);
	// nilai.put("0204300200", 24192.00);
	// nilai.put("0204300201", 557420.00);
	// nilai.put("0204300202", 557420.00);
	// nilai.put("0204300203", 557420.00);
	// nilai.put("0204300204", 557420.00);
	// nilai.put("0204300205", 518710.00);
	// nilai.put("0204300206", 207580.00);
	// nilai.put("0204300207", 84918.00);
	// nilai.put("0204300208", 671226.00);
	// nilai.put("0204300209", 671226.00);
	// nilai.put("0204300210", 671226.00);
	// nilai.put("0204300211", 232259.00);
	// nilai.put("0204300212", 76776.00);
	// nilai.put("0204300213", 107136.00);
	// nilai.put("0204300214", 1362584.00);
	// nilai.put("0204300215", 735478.00);
	// nilai.put("0204300216", 36300.00);
	// nilai.put("0204300217", 141944.00);
	// nilai.put("0204300219", 10164.00);
	// nilai.put("0204300220", 101619.00);
	// nilai.put("0204300221", 555480.00);
	// nilai.put("0204300222", 123040.00);
	// nilai.put("0204300223", 72000.00);
	// nilai.put("0204300224", 55746.00);
	// nilai.put("0204300226", 40579.00);
	// nilai.put("0204300227", 106447.00);
	// nilai.put("0204300228", 211933.00);
	// nilai.put("0204300230", 132260.00);
	// nilai.put("0204300231", 22356.00);
	// nilai.put("0204300233", 18705.00);
	// nilai.put("0204300234", 58000.00);
	// nilai.put("0204300235", 58928.00);
	// nilai.put("0204300236", 108388.00);
	// nilai.put("0204300237", 140238.00);
	// nilai.put("0204300238", 1378068.00);
	// nilai.put("0204300239", 157250.00);
	// nilai.put("0204300240", 232248.00);
	// nilai.put("0204300241", 83028.00);
	// nilai.put("0204300242", 42570.00);
	// nilai.put("0204300243", 36582.00);
	// nilai.put("0204300244", 169365.00);
	// nilai.put("0204300245", 335492.00);
	// nilai.put("0204300246", 357100.00);
	// nilai.put("0204300247", 182322.00);
	// nilai.put("0204300248", 40068.00);
	// nilai.put("0204300251", 133360.00);
	// nilai.put("0204300252", 1126260.00);
	// nilai.put("0204300253", 143935.00);
	// nilai.put("0204300255", 34067.00);
	// nilai.put("0204300256", 30000.00);
	// nilai.put("0204300257", 31290.00);
	// nilai.put("0204300258", 128290.00);
	// nilai.put("0204300259", 1408551.00);
	// nilai.put("0204300260", 1408551.00);
	// nilai.put("0204300261", 793551.00);
	// nilai.put("0204300262", 61416.00);
	// nilai.put("0204300264", 694356.00);
	// nilai.put("0204300265", 694356.00);
	// nilai.put("0204300266", 108388.00);
	// nilai.put("0204300267", 60508.00);
	// nilai.put("0204300268", 67932.00);
	// nilai.put("0204300270", 76625.00);
	// nilai.put("0204300271", 77425.00);
	// nilai.put("0204300272", 632250.00);
	// nilai.put("0204300273", 127425.00);
	// nilai.put("0204300274", 29424.00);
	// nilai.put("0204300276", 48967.00);
	// nilai.put("0204300277", 39330.00);
	// nilai.put("0204300278", 30492.00);
	// nilai.put("0204300279", 21000.00);
	// nilai.put("0204300280", 10836.00);
	// nilai.put("0204300281", 129314.00);
	// nilai.put("0204300282", 42294.00);
	// nilai.put("0204300283", 327412.00);
	// nilai.put("0204300284", 404158.00);
	// nilai.put("0204300285", 249104.00);
	// nilai.put("0204300286", 194064.00);
	// nilai.put("0204300287", 90320.00);
	// nilai.put("0204300288", 22646.00);
	// nilai.put("0204300289", 28093.00);
	// nilai.put("0204300291", 77229.00);
	// nilai.put("0204300292", 165970.00);
	// nilai.put("0204300293", 753873.00);
	// nilai.put("0204300294", 138873.00);
	// nilai.put("0204300295", 150970.00);
	// nilai.put("0204300298", 556388.00);
	// nilai.put("0204300299", 53225.00);
	// nilai.put("0204300301", 36582.00);
	// nilai.put("0204300302", 52836.00);
	// nilai.put("0204300303", 79254.00);
	// nilai.put("0204300304", 198380.00);
	// nilai.put("0204300305", 10412.00);
	// nilai.put("0204300308", 41296.00);
	// nilai.put("0204300309", 309680.00);
	// nilai.put("0204300310", 415168.00);
	// nilai.put("0204300311", 72716.00);
	// nilai.put("0204300312", 86716.00);
	// nilai.put("0204300313", 215739.00);
	// nilai.put("0204300314", 49478.00);
	// nilai.put("0204300315", 6192.00);
	// nilai.put("0204300316", 12067.00);
	// nilai.put("0204300317", 23419.00);
	// nilai.put("0204300318", 16676.00);
	// nilai.put("0204300319", 40000.00);
	// nilai.put("0204300320", 1005160.00);
	// nilai.put("0204300321", 27870.00);
	// nilai.put("0204300322", 27420.00);
	// nilai.put("0204300323", 10645.00);
	// nilai.put("0204300324", 5325.00);
	// nilai.put("0204300325", 91740.00);
	// nilai.put("0204300326", 849032.00);
	// nilai.put("0204300327", 1745808.00);
	// nilai.put("0204300328", 220777.00);
	// nilai.put("0204300329", 74849.00);
	// nilai.put("0204300330", 46777.00);
	// nilai.put("0204300331", 143412.00);
	// nilai.put("0204300332", 95000.00);
	// nilai.put("0204300333", 52105.00);
	// nilai.put("0204300334", 27585.00);
	// nilai.put("0204300335", 38220.00);
	// nilai.put("0204300336", 26130.00);
	// nilai.put("0204300338", 24195.00);
	// nilai.put("0204300339", 74060.00);
	// nilai.put("0204300340", 60060.00);
	// nilai.put("0204300341", 29806.00);
	// nilai.put("0204300342", 304836.00);
	// nilai.put("0204300343", 515616.00);
	// nilai.put("0204300344", 17032.00);
	// nilai.put("0204300345", 24520.00);
	// nilai.put("0204300346", 116256.00);
	// nilai.put("0204300347", 29226.00);
	// nilai.put("0204300348", 1694450.00);
	// nilai.put("0204300349", 208075.00);
	// nilai.put("0204300351", 50462.00);
	// nilai.put("0204300352", 13363.00);
	// nilai.put("0204300353", 345736.00);
	// nilai.put("0204300354", 19170.00);
	// nilai.put("0204300355", 27090.00);
	// nilai.put("0204300356", 23220.00);
	// nilai.put("0204300357", 23220.00);
	// nilai.put("0204300358", 38475.00);
	// nilai.put("0204300359", 23220.00);
	// nilai.put("0204300360", 38475.00);
	// nilai.put("0204300361", 38475.00);
	// nilai.put("0204300362", 38475.00);
	// nilai.put("0204300363", 23220.00);
	// nilai.put("0204300364", 38475.00);
	// nilai.put("0204300365", 150970.00);
	// nilai.put("0204300366", 32522.00);
	// nilai.put("0204300367", 95095.00);
	// nilai.put("0204300369", 24776.00);
	// nilai.put("0204300370", 13416.00);
	// nilai.put("0204300372", 11530.00);
	// nilai.put("0204300373", 16257.00);
	// nilai.put("0204300374", 632268.00);
	// nilai.put("0204300375", 35721.00);
	// nilai.put("0204300376", 40932.00);
	// nilai.put("0204300377", 101478.00);
	// nilai.put("0204300378", 106522.00);
	// nilai.put("0204300379", 66925.00);
	// nilai.put("0204300380", 125424.00);
	// nilai.put("0204300381", 89040.00);
	// nilai.put("0204300382", 33201.00);
	// nilai.put("0204300383", 53865.00);
	// nilai.put("0204300384", 25800.00);
	// nilai.put("0204300385", 43220.00);
	// nilai.put("0204300386", 34830.00);
	// nilai.put("0204300387", 19746.00);
	// nilai.put("0204300388", 30780.00);
	// nilai.put("0204300389", 40644.00);
	// nilai.put("0204300390", 50456.00);
	// nilai.put("0204300391", 32415.00);
	// nilai.put("0204300392", 49672.00);
	// nilai.put("0204300393", 25060.00);
	// nilai.put("0204300394", 43615.00);
	// nilai.put("0204300395", 7124.00);
	// nilai.put("0204300396", 20969.00);
	// nilai.put("0204300397", 16644.00);
	// nilai.put("0204300398", 42581.00);
	// nilai.put("0204300399", 49676.00);
	// nilai.put("0204300400", 47100.00);
	// nilai.put("0204300401", 13680.00);
	// nilai.put("0204300402", 25808.00);
	// nilai.put("0204300403", 6776.00);
	// nilai.put("0204300404", 15127.00);
	// nilai.put("0204300405", 15127.00);
	// nilai.put("0204300407", 52836.00);
	// nilai.put("0204300408", 16290.00);
	// nilai.put("0204300409", 18385.00);
	// nilai.put("0204300410", 8870.00);
	// nilai.put("0204300411", 35325.00);
	// nilai.put("0204300412", 140331.00);
	// nilai.put("0204300413", 49590.00);
	// nilai.put("0204300414", 44892.00);
	// nilai.put("0204300415", 30885.00);
	// nilai.put("0204300416", 566136.00);
	// nilai.put("0204300417", 117416.00);
	// nilai.put("0204300418", 188708.00);
	// nilai.put("0204300419", 19297.00);
	// nilai.put("0204300420", 39330.00);
	// nilai.put("0204300421", 27090.00);
	// nilai.put("0204300422", 45381.00);
	// nilai.put("0204300423", 50960.00);
	// nilai.put("0204300424", 22686.00);
	// nilai.put("0204300425", 27421.00);
	// nilai.put("0204300426", 43360.00);
	// nilai.put("0204300427", 17552.00);
	// nilai.put("0204300428", 36582.00);
	// nilai.put("0204300429", 45164.00);
	// nilai.put("0204300430", 45164.00);
	// nilai.put("0204300431", 39746.00);
	// nilai.put("0204300432", 67093.00);
	// nilai.put("0204300433", 46124.00);
	// nilai.put("0204300434", 12155.00);
	// nilai.put("0204300435", 57876.00);
	// nilai.put("0204300436", 31164.00);
	// nilai.put("0204300437", 32970.00);
	// nilai.put("0304300003", 144508.00);
	// nilai.put("0304300004", 24420.00);
	// nilai.put("0304300005", 24420.00);
	// nilai.put("0304300006", 24420.00);
	// nilai.put("0304300007", 24420.00);
	// nilai.put("0304300008", 24420.00);
	// nilai.put("0304300009", 24420.00);
	// nilai.put("0304300010", 24420.00);
	// nilai.put("0304300011", 24420.00);
	// nilai.put("0304300012", 24420.00);
	// nilai.put("0304300015", 19536.00);
	// nilai.put("0304300016", 16520.00);
	// nilai.put("0304300017", 438709.00);
	// nilai.put("0304300018", 258075.00);
	// nilai.put("0304300020", 39491.00);
	// nilai.put("0304300021", 37260.00);
	// nilai.put("0304300023", 10320.00);
	// nilai.put("0304300025", 10320.00);
	// nilai.put("0304300026", 100000.00);
	// nilai.put("0304300027", 46062.00);
	// nilai.put("0304300029", 43550.00);
	// nilai.put("0304300030", 28904.00);
	// nilai.put("0304300031", 37752.00);
	// nilai.put("0304300032", 632250.00);
	// nilai.put("0304300033", 52105.00);
	// nilai.put("0304300034", 24684.00);
	// nilai.put("0304300035", 72268.00);
	// nilai.put("0304300037", 21762.00);
	// nilai.put("0304300038", 569040.00);
	// nilai.put("0304300039", 41296.00);
	// nilai.put("0304300041", 13932.00);
	// nilai.put("0304300042", 41040.00);
	// nilai.put("0304300043", 569040.00);
	// nilai.put("0304300044", 35604.00);
	// nilai.put("0304300045", 98059.00);
	// nilai.put("0304300046", 221424.00);
	// nilai.put("0304300047", 62907.00);
	// nilai.put("0304300049", 20969.00);
	// nilai.put("0304300050", 165970.00);
	// nilai.put("0304300051", 9768.00);
	// nilai.put("0304300052", 9768.00);
	// nilai.put("0304300053", 9768.00);
	// nilai.put("0304300054", 9768.00);
	// nilai.put("0304300055", 9768.00);
	// nilai.put("0304300056", 9768.00);
	// nilai.put("0304300057", 9768.00);
	// nilai.put("0304300058", 9768.00);
	// nilai.put("0304300059", 9768.00);
	// nilai.put("0304300060", 9768.00);
	// nilai.put("0304300061", 9768.00);
	// nilai.put("0304300062", 9768.00);
	// nilai.put("0304300063", 9768.00);
	// nilai.put("0304300064", 9768.00);
	// nilai.put("0304300065", 9768.00);
	// nilai.put("0304300066", 338716.00);
	// nilai.put("0304300067", 41300.00);
	// nilai.put("0304300068", 309680.00);
	// nilai.put("0304300069", 260810.00);
	// nilai.put("0304300070", 11291.00);
	// nilai.put("0304300071", 9030.00);
	// nilai.put("0304300072", 4388.00);
	// nilai.put("0304300073", 149669.00);
	// nilai.put("0304300074", 24768.00);
	// nilai.put("0304300075", 153540.00);
	// nilai.put("0304300076", 49039.00);
	// nilai.put("0304300077", 10320.00);
	// nilai.put("0304300078", 19740.00);
	// nilai.put("0304300079", 7257.00);
	// nilai.put("0304300080", 7326.00);
	// nilai.put("0304300081", 7326.00);
	// nilai.put("0304300082", 7326.00);
	// nilai.put("0304300083", 38836.00);
	// nilai.put("0304300084", 50462.00);
	// nilai.put("0304300085", 25231.00);
	// nilai.put("0304300086", 48268.00);
	// nilai.put("0304300087", 53724.00);
	// nilai.put("0304300088", 41686.00);
	// nilai.put("0304300089", 32910.00);
	// nilai.put("0304300090", 8776.00);
	// nilai.put("0304300091", 17288.00);
	// nilai.put("0304300092", 7317.00);
	// nilai.put("0304300093", 171585.00);
	// nilai.put("0304300094", 19344.00);
	// nilai.put("0304300095", 113542.00);
	// nilai.put("0304300096", 41162.00);
	// nilai.put("0304300097", 51282.00);
	// nilai.put("0304300098", 206450.00);
	// nilai.put("0304300099", 14652.00);
	// nilai.put("0304300100", 13161.00);
	// nilai.put("0304300101", 3291.00);
	// nilai.put("0304300102", 557415.00);
	// nilai.put("0304300103", 71292.00);
	// nilai.put("0304300104", 161040.00);
	// nilai.put("0304300105", 22440.00);
	// nilai.put("0304300106", 606960.00);
	// nilai.put("0304300107", 167232.00);
	// nilai.put("0304300108", 139360.00);
	// nilai.put("0304300109", 354060.00);
	// nilai.put("0304300110", 12192.00);
	// nilai.put("0304300111", 18576.00);
	// nilai.put("0304300112", 11616.00);
	// nilai.put("0404300004", 8866.00);
	// nilai.put("0404300007", 8866.00);
	// nilai.put("0404300009", 15972.00);
	// nilai.put("0404300010", 48390.00);
	// nilai.put("0404300011", 23870.00);
	// nilai.put("0404300012", 30483.00);
	// nilai.put("0404300013", 36288.00);
	// nilai.put("0404300014A1", 387093.00);
	// nilai.put("0404300015", 80776.00);
	// nilai.put("0404300016", 9678.00);
	// nilai.put("0404300017", 11935.00);
	// nilai.put("0404300018", 77420.00);
	// nilai.put("0404300019", 528516.00);
	// nilai.put("0404300020", 92120.00);
	// nilai.put("0404300022", 427500.00);
	// nilai.put("0404300023", 67106.00);
	// nilai.put("0404300024", 795000.00);
	// nilai.put("0404300025", 407740.00);
	// nilai.put("0404300026A1", 64520.00);
	// nilai.put("0404300027", 929765.00);
	// nilai.put("0404300029", 38388.00);
	// nilai.put("0404300030", 161229.00);
	// nilai.put("0404300031", 123872.00);
	// nilai.put("0404300033", 10836.00);
	// nilai.put("0404300034a1", 667739.00);
	// nilai.put("0404300035", 104328.00);
	// nilai.put("0404300036", 144508.00);
	// nilai.put("0404300037", 126450.00);
	// nilai.put("0404300038", 112900.00);
	// nilai.put("0404300039a1", 822571.00);
	// nilai.put("0404300040", 77000.00);
	// nilai.put("0404300041", 21296.00);
	// nilai.put("0404300042", 63866.00);
	// nilai.put("0404300043", 89418.00);
	// nilai.put("0404300044", 47418.00);
	// nilai.put("0404300045", 59283.00);
	// nilai.put("0404300046", 100000.00);
	// nilai.put("0404300048", 33040.00);
	// nilai.put("0404300049", 119754.00);
	// nilai.put("0404300052", 26609.00);
	// nilai.put("0404300053a1", 396770.00);
	// nilai.put("0404300054A1", 64520.00);
	// nilai.put("0404300055A1", 64520.00);
	// nilai.put("0404300056", 23229.00);
	// nilai.put("0404300057", 158067.00);
	// nilai.put("0404300059", 74508.00);
	// nilai.put("0404300060", 140313.00);
	// nilai.put("0404300061", 185238.00);
	// nilai.put("0404300062", 322983.00);
	// nilai.put("0404300063", 210483.00);
	// nilai.put("0404300064", 110708.00);
	// nilai.put("0404300065A1", 551602.00);
	// nilai.put("0404300067", 30250.00);
	// nilai.put("0404300068", 32250.00);
	// nilai.put("0404300069", 40400.00);
	// nilai.put("0404300070", 95975.00);
	// nilai.put("0404300071", 108375.00);
	// nilai.put("0404300073", 59363.00);
	// nilai.put("0404300074", 75873.00);
	// nilai.put("0404300075", 150963.00);
	// nilai.put("0404300076", 235157.00);
	// nilai.put("0404300077", 154830.00);
	// nilai.put("0404300078", 1300649.00);
	// nilai.put("0404300079", 131614.00);
	// nilai.put("0404300080", 58070.00);
	// nilai.put("0404300081", 28910.00);
	// nilai.put("0404300082", 239030.00);
	// nilai.put("0404300083", 15806.00);
	// nilai.put("0404300084", 417345.00);
	// nilai.put("0404300085", 14520.00);
	// nilai.put("0404300086", 169358.00);
	// nilai.put("0404300087", 21679.00);
	// nilai.put("0404300088", 21678.00);
	// nilai.put("0404300089", 447582.00);
	// nilai.put("0404300090", 7740.00);
	// nilai.put("0404300091", 80522.00);
	// nilai.put("0404300092", 106450.00);
	// nilai.put("0404300094", 528480.00);
	// nilai.put("0404300095", 228864.00);
	// nilai.put("0404300096", 86712.00);
	// nilai.put("0404300097", 232248.00);
	// nilai.put("0404300098", 253008.00);
	// nilai.put("0404300099", 281127.00);
	// nilai.put("0404300100", 60963.00);
	// nilai.put("0404300101", 602418.00);
	// nilai.put("0404300102", 24520.00);
	// nilai.put("0404300103", 27421.00);
	// nilai.put("0404300104", 14448.00);
	// nilai.put("0404300105", 149031.00);
	// nilai.put("0404300106", 106447.00);
	// nilai.put("0404300108", 99190.00);
	// nilai.put("0404300109", 3612.00);
	// nilai.put("0404300110", 15486.00);
	// nilai.put("0404300111", 86716.00);
	// nilai.put("0404300112", 144508.00);
	// nilai.put("0404300113", 39480.00);
	// nilai.put("0404300114", 61203.00);
	// nilai.put("0404300115", 308467.00);
	// nilai.put("0404300116", 1190320.00);
	// nilai.put("0404300117", 1983880.00);
	// nilai.put("0404300118", 90960.00);
	// nilai.put("0404300119", 970320.00);
	// nilai.put("0404300120", 301936.00);
	// nilai.put("0404300121", 69440.00);
	// nilai.put("0404300122", 89270.00);
	// nilai.put("0404300123", 126450.00);
	// nilai.put("0404300124", 247746.00);
	// nilai.put("0404300125", 39483.00);
	// nilai.put("0404300126", 13068.00);
	// nilai.put("0404300127", 154839.00);
	// nilai.put("0404300128", 37160.00);
	// nilai.put("0404300129", 551612.00);
	// nilai.put("0404300130", 8226.00);
	// nilai.put("0404300131", 74849.00);
	// nilai.put("0404300132", 74849.00);
	// nilai.put("0404300133", 72250.00);
	// nilai.put("0404300134", 74328.00);
	// nilai.put("0404300135", 49560.00);
	// nilai.put("0404300136", 35604.00);
	// nilai.put("0404300137", 45056.00);
	// nilai.put("0404300138", 106458.00);
	// nilai.put("0404300139", 87737.00);
	// nilai.put("0404300140", 350965.00);
	// nilai.put("0404300141", 24195.00);
	// nilai.put("0404300142", 34845.00);
	// nilai.put("0404300143", 29616.00);
	// nilai.put("0404300144", 18576.00);
	// nilai.put("0404300145", 14520.00);
	// nilai.put("0404300146", 179870.00);
	// nilai.put("0404300147", 23229.00);
	// nilai.put("0404300148", 12384.00);
	// nilai.put("0404300149", 74508.00);
	// nilai.put("0404300150", 96876.00);
	// nilai.put("0404300151", 322062.00);
	// nilai.put("0404300152", 53218.00);
	// nilai.put("0404300153", 56782.00);
	// nilai.put("0404300154", 51620.00);
	// nilai.put("0404300155", 103220.00);
	// nilai.put("0404300156", 62510.00);
	// nilai.put("0404300157", 98059.00);
	// nilai.put("0404300158", 119760.00);
	// nilai.put("0404300159", 17612.00);
	// nilai.put("0404300160", 36134.00);
	// nilai.put("0404300161", 22582.00);
	// nilai.put("0404300162", 67093.00);
	// nilai.put("0404300163", 100646.00);
	// nilai.put("0404300164", 17028.00);
	// nilai.put("0404300165", 25810.00);
	// nilai.put("0404300166", 31936.00);
	// nilai.put("0404300167", 21936.00);
	// nilai.put("0404300168", 19194.00);
	// nilai.put("0404300169", 13433.00);
	// nilai.put("0404300170", 9288.00);
	// nilai.put("0404300171", 54840.00);
	// nilai.put("0404300172", 59885.00);
	// nilai.put("0404300173", 57820.00);
	// nilai.put("0404300174", 433560.00);
	// nilai.put("0404300175", 61944.00);
	// nilai.put("0404300176", 612099.00);
	// nilai.put("0404300177", 593538.00);
	// nilai.put("0404300178", 25800.00);
	// nilai.put("0404300179", 20640.00);
	// nilai.put("0404300180", 86870.00);
	// nilai.put("0404300181", 25810.00);
	// nilai.put("0404300182", 15480.00);
	// nilai.put("0404300183", 83835.00);
	// nilai.put("0404300184", 24678.00);
	// nilai.put("0404300185", 54194.00);
	// nilai.put("0404300186", 21679.00);
	// }

	// private double getRepayment(String code) {
	// Double result = nilai.get(code);
	// if (result==null){
	// System.out.println(code);
	// }
	// return result == null ? 0 : result;
	// }

	private LoanScheduleDto getRepayment(long id) {
		ObjectMapper mapper = new ObjectMapper();
		String data = jsonCore.sendRawData("listRepaymentByRange", id + ";"
				+ nextMonthBegin + ";" + nextMonthEnd);
		try {
			List<LoanScheduleDto> infos = mapper.readValue(
					data,
					mapper.getTypeFactory().constructCollectionType(
							ArrayList.class, LoanScheduleDto.class));
			if (infos.size() > 0) {
				LoanScheduleDto result = new LoanScheduleDto();
				result.setMargin(0);
				for (LoanScheduleDto info : infos) {
					result.setMargin(result.getMargin() + info.getMargin());
				}
				return result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
