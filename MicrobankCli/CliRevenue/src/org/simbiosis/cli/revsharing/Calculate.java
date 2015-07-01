package org.simbiosis.cli.revsharing;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.text.NumberFormat;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.CliBase;
import org.simbiosis.cli.revsharing.lib.RevSharing;
import org.simbiosis.microbank.RevenueDto;
import org.simbiosis.microbank.RevenueSharingDto;
import org.simbiosis.system.UserDto;

public class Calculate extends CliBase {

	//
	NumberFormat nf = NumberFormat.getInstance();
	//
	int[] monthDays = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	//
	int month;
	int year;
	String beforeDate;
	String beginDate;
	String endDate;
	int days;
	//
	RevSharing revSharing = null;

	public static void main(String[] args) {
		Calculate bagiHasil = new Calculate();
		bagiHasil.execute();
	}

	public Calculate() {
		super("cli.properties");
		// Dijalankan tanggal 1 awal bulan.....
		DateTime now = new DateTime().dayOfMonth().withMinimumValue()
				.minusDays(1);
		//
		DateTimeFormatter sDate = DateTimeFormat.forPattern("dd-MM-yyyy");
		DateTimeFormatter sMonth = DateTimeFormat.forPattern("MM");
		DateTimeFormatter sYear = DateTimeFormat.forPattern("yyyy");
		//
		String strMonth = sMonth.print(now);
		month = Integer.parseInt(strMonth);
		year = Integer.parseInt(sYear.print(now));
		beginDate = "01-" + strMonth + "-" + year;
		endDate = getEndMonths(month) + "-" + year;
		DateTime beforeDateTime = sDate.parseDateTime(beginDate).minusDays(1);
		beforeDate = sDate.print(beforeDateTime);
		days = monthDays[month];
	}

	public void execute() {
		while (next()) {
			UserDto user = getUserFromSession();
			if (user != null) {
				revSharing = new RevSharing(getCoreClient(), beforeDate,
						beginDate, days, endDate);
				//
				System.out.println("Tanggal : " + beginDate + " - " + endDate);
				System.out.println("Process revSharing data...");
				//
				revSharing.execute();
				//
				System.out.println("Create revenue...");
				sendRevenue(user);
				createRevenueFile();
				//
				System.out.println("Create revenue sharing...");
				sendRevenueSharing(user);
				createRevenueSharingFile();
			}
		}
	}

	void sendRevenue(UserDto user) {
		for (Object ob : revSharing.getRevenue()) {
			RevenueDto rev = (RevenueDto) ob;
			rev.setCompany(user.getCompany());
			rev.setMonth(month);
			rev.setYear(year);
			try {
				ObjectMapper mapper = new ObjectMapper();
				StringWriter sw = new StringWriter();
				mapper.writeValue(sw, rev);
				String result = sw.toString();
				getCoreClient().sendRawData("saveRevenueItem", result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	void sendRevenueSharing(UserDto user) {
		for (Object ob : revSharing.getRevenueSharing()) {
			RevenueSharingDto rev = (RevenueSharingDto) ob;
			rev.setCompany(user.getCompany());
			rev.setMonth(month);
			rev.setYear(year);
			try {
				ObjectMapper mapper = new ObjectMapper();
				StringWriter sw = new StringWriter();
				mapper.writeValue(sw, rev);
				String result = sw.toString();
				getCoreClient().sendRawData("saveRevenueSharingItem", result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	UserDto getUserFromSession() {
		ObjectMapper mapper = new ObjectMapper();
		String data = getCoreClient().sendRawData("getUserFromSession", "");
		UserDto user = null;
		try {
			user = mapper.readValue(data, UserDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}

	void createRevenueFile() {
		int counter = 0;
		String buffer = "";
		for (Object ob : revSharing.getRevenue()) {
			RevenueDto rev = (RevenueDto) ob;
			counter++;
			//
			String line = "" + counter + "\t" + rev.getProductName() + "\t"
					+ nf.format(rev.getValue());
			buffer += line;
			if (!buffer.isEmpty())
				buffer += "\n";
		}
		//
		try {
			FileOutputStream fstream = new FileOutputStream("pendapatan.csv");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					fstream));
			writer.write(buffer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void createRevenueSharingFile() {
		int counter = 0;
		String buffer = "";
		for (Object ob : revSharing.getRevenueSharing()) {
			RevenueSharingDto dto = (RevenueSharingDto) ob;
			counter++;
			//
			String line = "" + counter + "\t" + dto.getProduct() + "\t"
					+ dto.getCode() + "\t" + dto.getName() + "\t"
					+ nf.format(dto.getStartValue()) + "\t"
					+ nf.format(dto.getEndValue()) + "\t"
					+ nf.format(dto.getAverageValue()) + "\t"
					+ nf.format(dto.getSharing()) + "\t"
					+ nf.format(dto.getTotalSharing()) + "\t"
					+ nf.format(dto.getCustomerSharing()) + "\t"
					+ nf.format(dto.getZakat()) + "\t"
					+ nf.format(dto.getTax());
			buffer += line;
			if (!buffer.isEmpty())
				buffer += "\n";
		}
		//
		try {
			FileOutputStream fstream = new FileOutputStream("bagihasil.csv");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					fstream));
			writer.write(buffer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
