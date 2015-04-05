package org.simbiosis.cli.base;

import org.joda.time.DateTime;

public class TestUtils {

	private DateTools dateTools = new DateTools();

	public static void main(String[] args) {
		new TestUtils();
	}

	public TestUtils() {
		DateTime date = dateTools.getValidDate(35, 2,2015);
		System.out.println("Tanggal yang keluar = "+dateTools.getDateTimeFormatter().print(date));
	}
}
