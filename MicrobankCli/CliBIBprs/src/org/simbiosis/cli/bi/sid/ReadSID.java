package org.simbiosis.cli.bi.sid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.CliBase;
import org.simbiosis.cli.bi.lib.StrUtils;
import org.simbiosis.microbank.model.LoanRpt;

public class ReadSID extends CliBase {

	DateTime now = new DateTime();
	DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
	DateTimeFormatter sdfSIDDate = DateTimeFormat.forPattern("yyyyMMdd");
	DateTimeFormatter sdfMonth = DateTimeFormat.forPattern("MM");
	DateTimeFormatter sdfYear = DateTimeFormat.forPattern("yyyy");
	String fileName = "/home/bprs/application/SIDEKS-ORI.txt";
	String outputName = "/home/bprs/application/SIDEKS-620113001-"
			+ sdfSIDDate.print(now) + ".txt";
	List<String> bufferReader = new ArrayList<String>();
	List<SIDLoan> loans = new ArrayList<SIDLoan>();
	DateTime lastMonth = null;
	String strMonth = "";
	String strYear = "";
	String strSIDDate = "";

	public class SIDLoan {
		String idData;
		String operation;
		String idLembaga;
		String idBank;
		String idKantorCabang;
		String bulan;
		String tahun;
		String jenisFasilitas;
		String idFasilitas;
		String sifat;
		String noRekening;
		String tengah;
		String plafon;
		String bakiDebet;
		String tengah1;
		String kondisi;
		String tanggalKondisi;
		String agunan;
		String ppap;
		String akhir;

		public String getIdData() {
			return idData;
		}

		public void setIdData(String idData) {
			this.idData = idData;
		}

		public String getOperation() {
			return operation;
		}

		public void setOperation(String operation) {
			this.operation = operation;
		}

		public String getIdLembaga() {
			return idLembaga;
		}

		public void setIdLembaga(String idLembaga) {
			this.idLembaga = idLembaga;
		}

		public String getIdBank() {
			return idBank;
		}

		public void setIdBank(String idBank) {
			this.idBank = idBank;
		}

		public String getIdKantorCabang() {
			return idKantorCabang;
		}

		public void setIdKantorCabang(String idKantorCabang) {
			this.idKantorCabang = idKantorCabang;
		}

		public String getBulan() {
			return bulan;
		}

		public void setBulan(String bulan) {
			this.bulan = bulan;
		}

		public String getTahun() {
			return tahun;
		}

		public void setTahun(String tahun) {
			this.tahun = tahun;
		}

		public String getJenisFasilitas() {
			return jenisFasilitas;
		}

		public void setJenisFasilitas(String jenisFasilitas) {
			this.jenisFasilitas = jenisFasilitas;
		}

		public String getIdFasilitas() {
			return idFasilitas;
		}

		public void setIdFasilitas(String idFasilitas) {
			this.idFasilitas = idFasilitas;
		}

		public String getSifat() {
			return sifat;
		}

		public void setSifat(String sifat) {
			this.sifat = sifat;
		}

		public String getNoRekening() {
			return noRekening;
		}

		public void setNoRekening(String noRekening) {
			this.noRekening = noRekening;
		}

		public String getTengah() {
			return tengah;
		}

		public void setTengah(String tengah) {
			this.tengah = tengah;
		}

		public String getPlafon() {
			return plafon;
		}

		public void setPlafon(String plafon) {
			this.plafon = plafon;
		}

		public String getBakiDebet() {
			return bakiDebet;
		}

		public void setBakiDebet(String bakiDebet) {
			this.bakiDebet = bakiDebet;
		}

		public String getTengah1() {
			return tengah1;
		}

		public void setTengah1(String tengah1) {
			this.tengah1 = tengah1;
		}

		public String getKondisi() {
			return kondisi;
		}

		public void setKondisi(String kondisi) {
			this.kondisi = kondisi;
		}

		public String getTanggalKondisi() {
			return tanggalKondisi;
		}

		public void setTanggalKondisi(String tanggalKondisi) {
			this.tanggalKondisi = tanggalKondisi;
		}

		public String getAkhir() {
			return akhir;
		}

		public void setAkhir(String akhir) {
			this.akhir = akhir;
		}

		public String getAgunan() {
			return agunan;
		}

		public void setAgunan(String agunan) {
			this.agunan = agunan;
		}

		public String getPpap() {
			return ppap;
		}

		public void setPpap(String ppap) {
			this.ppap = ppap;
		}

	}

	public static void main(String[] args) {
		ReadSID readSID = new ReadSID();
		readSID.execute();
	}

	public ReadSID() {
		super("cli.properties");
		//
		now = now.minusMonths(1);
		//
		lastMonth = getEndMonths(now);
		//
		strMonth = sdfMonth.print(lastMonth);
		strYear = sdfYear.print(lastMonth);
		strSIDDate = sdfSIDDate.print(lastMonth);
		System.out.println(strSIDDate);
	}

	public void execute() {
		open();
		readFile();
		//
		// String[] strings = bufferReader.split("\r\n");
		// System.out.println(strings.length);
		System.out.println(bufferReader.size());
		for (int i = 1; i < bufferReader.size() - 1; i++) {
			SIDLoan loan = readForm3B(bufferReader.get(i));
			LoanRpt info = getLoanTransInfo(loan.getNoRekening().trim());
			if (info == null) {
				info = getLoanTransInfo(loan.getNoRekening().trim() + "A1");
			}
			if (info != null) {
				Double dVal = info.getOsPrincipal();
				Integer iVal = dVal.intValue();
				loan.setOperation("U");
				loan.setBulan(strMonth);
				loan.setTahun(strYear);
				loan.setPlafon(StrUtils.lpadded(iVal.toString(), 15, '0'));
				loan.setBakiDebet(StrUtils.lpadded(iVal.toString(), 15, '0'));
				if (iVal == 0) {
					loan.setKondisi("02");
					loan.setTanggalKondisi(strSIDDate);
					loan.setPpap(StrUtils.lpadded("0", 15, '0'));
				} else {
					dVal = info.getPpap();
					iVal = dVal.intValue();
					loan.setPpap(StrUtils.lpadded(iVal.toString(), 15, '0'));
				}
			} else {
				loan.setOperation("U");
				loan.setBulan(strMonth);
				loan.setTahun(strYear);
				loan.setPlafon(StrUtils.lpadded("0", 15, '0'));
				loan.setBakiDebet(StrUtils.lpadded("0", 15, '0'));
				loan.setKondisi("02");
				loan.setTanggalKondisi(strSIDDate);
				loan.setPpap(StrUtils.lpadded("0", 15, '0'));
			}
			System.out.println(loan.getNoRekening().trim() + ";"
					+ loan.getPlafon());
			loans.add(loan);
		}
		close();
		//
		writeFile();
	}

	private SIDLoan readForm3B(String input) {
		SIDLoan loan = new SIDLoan();
		loan.setIdData(input.substring(0, 6));
		loan.setOperation(input.substring(6, 7));
		loan.setIdLembaga(input.substring(7, 10));
		loan.setIdBank(input.substring(10, 16));
		loan.setIdKantorCabang(input.substring(16, 19));
		loan.setBulan(input.substring(19, 21));
		loan.setTahun(input.substring(21, 25));
		loan.setJenisFasilitas(input.substring(25, 29));
		loan.setIdFasilitas(input.substring(29, 81));
		loan.setSifat(input.substring(81, 83));
		loan.setNoRekening(input.substring(83, 108));
		loan.setTengah(input.substring(108, 253));
		loan.setPlafon(input.substring(253, 268));
		loan.setBakiDebet(input.substring(268, 283));
		loan.setTengah1(input.substring(283, 528));
		loan.setKondisi(input.substring(528, 530));
		loan.setTanggalKondisi(input.substring(530, 538));
		loan.setAgunan(input.substring(538, 553));
		loan.setPpap(input.substring(553, 568));
		loan.setAkhir(input.substring(568, input.length() - 1));
		return loan;
	}

	private String writeForm3B(SIDLoan loan) {
		String buffer = loan.getIdData() + loan.getOperation()
				+ loan.getIdLembaga() + loan.getIdBank()
				+ loan.getIdKantorCabang() + loan.getBulan() + loan.getTahun()
				+ loan.getJenisFasilitas() + loan.getIdFasilitas()
				+ loan.getSifat() + loan.getNoRekening() + loan.getTengah()
				+ loan.getPlafon() + loan.getBakiDebet() + loan.getTengah1()
				+ loan.getKondisi() + loan.getTanggalKondisi()
				+ loan.getAgunan() + loan.getPpap() + loan.getAkhir();
		return buffer;
	}

	private void readFile() {
		try {
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				bufferReader.add(strLine);
			}
			// Close the input stream
			in.close();

			//
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeFile() {
		try {
			FileOutputStream fstream = new FileOutputStream(outputName);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					fstream));
			String buffer = bufferReader.get(0) + "\r\n";
			for (SIDLoan loan : loans) {
				buffer += writeForm3B(loan) + "\r\n";
			}
			buffer += bufferReader.get(bufferReader.size() - 1);
			writer.write(buffer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private LoanRpt getLoanTransInfo(String code) {
		LoanRpt info = null;
		String data = getReportClient().sendRawData("getDailyLoanByCode",
				dtf.print(lastMonth), code);
		ObjectMapper mapper = new ObjectMapper();
		try {
			info = mapper.readValue(data, LoanRpt.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}
}
