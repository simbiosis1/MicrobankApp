package org.simbiosis.cli.loan;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.CliBase;

public class AccrualBegin extends CliBase {

	String strCurrentDate = "";
	DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");

	public static void main(String[] args) {
		AccrualBegin accrualBegin = new AccrualBegin();
		accrualBegin.execute();
	}

	public AccrualBegin() {
		super("cli.properties");
		//

	}

	private void initConfig() {
		// Dijalankan tanggal 1 awal bulan.....
		DateTime now = new DateTime().dayOfMonth().withMinimumValue()
				.minusDays(1);
		//
		strCurrentDate = sdf.print(now);
	}

	public void execute() {
		while (next()) {
			initConfig();
			System.out.println("Tanggal : " + strCurrentDate);
			// Ambil seluruh kol 1 dari report pembiayaan...
		}
	}

}
