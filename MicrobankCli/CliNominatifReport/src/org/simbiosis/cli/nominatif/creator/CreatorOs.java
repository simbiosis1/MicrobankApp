package org.simbiosis.cli.nominatif.creator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.CliBase;
import org.simbiosis.cli.nominatif.lib.LoanNominatif;

public class CreatorOs extends CliBase {

	String strDate;

	public static void main(String[] args) {
		CreatorOs creator = new CreatorOs();
		creator.execute();
	}

	public CreatorOs() {
		super("cli.properties");
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		strDate = dtf.print(getEndMonths(new DateTime()));
	}

	public void execute() {
		while (next()) {
			System.out.println("Tanggal : " + strDate);
			System.out.println("Generate loan remedial...");
			LoanNominatif loanReport = new LoanNominatif(getCoreClient(),
					getReportClient(), strDate);
			loanReport.execute();
		}
	}

}
