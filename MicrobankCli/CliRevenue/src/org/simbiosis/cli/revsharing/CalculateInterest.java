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
import org.simbiosis.cli.revsharing.lib.Interest;
import org.simbiosis.microbank.RevenueSharingDto;
import org.simbiosis.system.UserDto;

public class CalculateInterest extends CliBase {

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
	Interest interest = null;

	public static void main(String[] args) {
		CalculateInterest bagiHasil = new CalculateInterest();
		bagiHasil.execute();
	}

	public CalculateInterest() {
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
				interest = new Interest(getCoreClient(), beforeDate, beginDate,
						days, endDate);
				//
				System.out.println("Process interest data...");
				interest.execute();
				//
				System.out.println("Create revenue sharing...");
				sendRevenueSharing(user);
				createRevenueSharingFile();
			}
		}
	}

	void sendRevenueSharing(UserDto user) {
		for (Object ob : interest.getRevenueSharing()) {
			RevenueSharingDto rev = (RevenueSharingDto) ob;
			if (rev.getCustomerSharing() > 0.01 || rev.getZakat() > 0.01) {
				rev.setCompany(user.getCompany());
				rev.setMonth(month);
				rev.setYear(year);
				try {
					ObjectMapper mapper = new ObjectMapper();
					StringWriter sw = new StringWriter();
					mapper.writeValue(sw, rev);
					String result = sw.toString();
					getCoreClient().sendRawData("saveRevenueSharingItem",
							result);
				} catch (IOException e) {
					e.printStackTrace();
				}
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

	void createRevenueSharingFile() {
		int counter = 0;
		String buffer = "";
		for (Object ob : interest.getRevenueSharing()) {
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
			FileOutputStream fstream = new FileOutputStream("listbunga.csv");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					fstream));
			writer.write(buffer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
