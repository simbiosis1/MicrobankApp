package org.simbiosis.cli.bi.lb;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.CliBase;

public class Labul extends CliBase {

	FileCreator fileCreator;
	LapKeu lapKeu;
	Lending landing;
	Funding funding;
	DecimalFormat df = new DecimalFormat("00");
	String newLine = "\r\n";
	String kodeBank = "620113";
	String cabang = "001";
	int month;
	int year;
	String bulan = "";
	String tahun = "";
	String endDate = "";
	//
	String buffer = "";

	Other other = new Other(newLine);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Labul labul = new Labul();
		labul.execute();
	}

	//
	public Labul() {
		super("cli.properties");
		//
		DateTime now = new DateTime();
		DateTimeFormatter sMonth = DateTimeFormat.forPattern("MM");
		DateTimeFormatter sYear = DateTimeFormat.forPattern("yyyy");
		month = Integer.parseInt(sMonth.print(now));
		year = Integer.parseInt(sYear.print(now));
		if (month == 1) {
			month = 12;
			year--;
		} else {
			month--;
		}
		//
		bulan = df.format(month);
		tahun = "" + year;
		endDate = getEndMonths(month) + "-" + tahun;
		fileCreator = new FileCreator(kodeBank, cabang, bulan, tahun, newLine);
	}

	public void execute() {
		while (next()) {
			lapKeu = new LapKeu(getReportClient(), month, year, newLine);
			funding = new Funding(getCoreClient(), getReportClient(), endDate,
					newLine);
			landing = new Lending(getCoreClient(), getReportClient(), endDate,
					newLine);
			retrieve();
			create();
			createFile();
			// System.out.println(labul.getBuffer());
		}
	}

	String getBuffer() {
		return buffer;
	}

	public void createFile() {
		try {
			FileOutputStream fstream = new FileOutputStream(kodeBank + cabang
					+ bulan + tahun + ".exp");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					fstream));
			writer.write(buffer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void retrieve() {
		System.out.println("Retrieving...");
		System.out.println(" - lapKeu");
		lapKeu.retrieve();
		System.out.println(" - lending");
		landing.retrieve();
		System.out.println(" - funding");
		funding.retrieve();
	}

	public void create() {
		//
		System.out.println("Impor LB bulan lalu");
		other.readLabulUsed("BS03;BS15;BS18;BS21;BS22;BS23;BS24;BS25");
		System.out.println("Buat LB");
		System.out.println(" - Buat LB header");
		buffer += fileCreator.createHeader();
		buffer += fileCreator.createHeaderBank();
		System.out.println(" - Buat form 01");
		buffer += lapKeu.create01();
		System.out.println(" - Buat form 02");
		buffer += lapKeu.create02();
		System.out.println(" - Buat form 03");
		buffer += other.create("BS03");
		System.out.println(" - Buat form 04");
		buffer += landing.create04();
		System.out.println(" - Buat form 07");
		buffer += landing.create07();
		//System.out.println(" - Buat form 08");
		//buffer += landing.create08();
		System.out.println(" - Buat form 09");
		buffer += landing.create09();
		System.out.println(" - Buat form 12");
		buffer += funding.create12();
		System.out.println(" - Buat form 13");
		buffer += funding.create13();
		System.out.println(" - Buat form 14");
		buffer += funding.create14();
		//
		System.out.println(" - Buat form 15");
		buffer += other.create("BS15");
		//
		System.out.println(" - Buat form 18");
		buffer += other.create("BS18");
		//
		System.out.println(" - Buat form 20");
		buffer += landing.create20();
		//FIXME: tanmiya
		buffer += landing.create08();
		//
		System.out.println(" - Buat form 21");
		buffer += other.create("BS21");
		System.out.println(" - Buat form 22");
		buffer += other.create("BS22");
		System.out.println(" - Buat form 23");
		buffer += other.create("BS23");
		System.out.println(" - Buat form 24");
		buffer += other.create("BS24");
		System.out.println(" - Buat form 25");
		buffer += other.create("BS25");
	}

}
