package org.simbiosis.cli.base;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTools {

	private DateTimeFormatter dtf = null;
	private String strFormat = "dd-MM-yyyy";
	private String[] strMonths = { "00", "01", "02", "03", "04", "05", "06",
			"07", "08", "09", "10", "11", "12" };
	private String[] strDays = { "00", "01", "02", "03", "04", "05", "06",
			"07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17",
			"18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
			"29", "30", "31" };

	public DateTools() {
		dtf = DateTimeFormat.forPattern(strFormat);
	}

	public DateTools(DateTimeFormatter dtf) {
		this.dtf = dtf;
	}

	private boolean isValidDate(String dateString) {
		if (dateString == null || dateString.length() != "yyyyMMdd".length()) {
			return false;
		}

		int date;
		try {
			date = Integer.parseInt(dateString);
		} catch (NumberFormatException e) {
			return false;
		}

		int year = date / 10000;
		int month = (date % 10000) / 100;
		int day = date % 100;

		// leap years calculation not valid before 1581
		boolean yearOk = (year >= 1581) && (year <= 2500);
		boolean monthOk = (month >= 1) && (month <= 12);
		boolean dayOk = (day >= 1) && (day <= daysInMonth(year, month));

		return (yearOk && monthOk && dayOk);
	}

	private int daysInMonth(int year, int month) {
		int daysInMonth;
		switch (month) {
		case 1: // fall through
		case 3: // fall through
		case 5: // fall through
		case 7: // fall through
		case 8: // fall through
		case 10: // fall through
		case 12:
			daysInMonth = 31;
			break;
		case 2:
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
				daysInMonth = 29;
			} else {
				daysInMonth = 28;
			}
			break;
		default:
			// returns 30 even for nonexistant months
			daysInMonth = 30;
		}
		return daysInMonth;
	}

	//
	//
	public DateTime getValidDate(int iDay, int iMonth, int iYear) {
		// Cek validitas tanggal
		int currentDay = iDay > 31 ? 31 : iDay;
		int currentMonth = iMonth > 12 ? 12 : iMonth;
		String strMonthYear = iYear + "" + strMonths[currentMonth];
		String strCurrentDate = strDays[currentDay] + "-"
				+ strMonths[currentMonth] + "-" + iYear;
		String strDate = strMonthYear + "" + strDays[currentDay--];
		while (!isValidDate(strDate)) {
			strCurrentDate = strDays[currentDay] + "-"
					+ strMonths[currentMonth] + "-" + iYear;
			strDate = strMonthYear + "" + strDays[currentDay--];
		}
		DateTime date = null;
		try {
			date = dtf.parseDateTime(strCurrentDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public DateTimeFormatter getDateTimeFormatter() {
		return dtf;
	}
}
