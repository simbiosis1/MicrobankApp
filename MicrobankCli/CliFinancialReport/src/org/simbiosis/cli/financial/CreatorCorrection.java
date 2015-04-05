package org.simbiosis.cli.financial;

import java.text.DecimalFormat;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class CreatorCorrection {

	static long[] branches = { 0, 3, 4, 5 };

	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("00");
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		String strDate = sdf.print(new DateTime());
		String[] strDates = strDate.split("-");
		int maxDate = Integer.parseInt(strDates[0]);
		for (int i = 1; i <= maxDate; i++) {
			String date = df.format(i) + "-" + strDates[1] + "-" + strDates[2];
			System.out.println("Neraca untuk tanggal " + date);
			// Neraca neraca = new Neraca(DateUtils.strToDate(date));
			// neraca.execute();
		}
	}

}
