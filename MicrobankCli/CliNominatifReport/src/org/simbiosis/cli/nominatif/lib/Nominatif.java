package org.simbiosis.cli.nominatif.lib;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.CliBase;

public class Nominatif extends CliBase {

	private boolean bulk = false;
	private String strDate = null;

	public Nominatif() {
		super("cli.properties");
		bulk = true;
	}

	public Nominatif(String strDate) {
		super("cli.properties");
		this.strDate = strDate;
	}

	private void _execute() {
		System.out.println("Nominatif tanggal : " + strDate);
		if (bulk) {
			if (isBulkSaving()) {
				SavingNominatif savingReport = new SavingNominatif(
						getCoreClient(), getReportClient(), strDate);
				savingReport.execute();
			}
			if (isBulkDeposit()) {
				DepositNominatif depositReport = new DepositNominatif(
						getCoreClient(), getReportClient(), strDate);
				depositReport.execute();
			}
			if (isBulkLoan()) {
				LoanNominatif loanReport = new LoanNominatif(getCoreClient(),
						getReportClient(), strDate);
				loanReport.execute();
			}
		} else {
			SavingNominatif savingReport = new SavingNominatif(getCoreClient(),
					getReportClient(), strDate);
			savingReport.execute();
			DepositNominatif depositReport = new DepositNominatif(
					getCoreClient(), getReportClient(), strDate);
			depositReport.execute();
			LoanNominatif loanReport = new LoanNominatif(getCoreClient(),
					getReportClient(), strDate);
			loanReport.execute();
		}
	}

	public void execute() {
		if (bulk) {
			executeBulk();
		} else {
			while (next()) {
				_execute();
			}
		}
	}

	private void executeBulk() {
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		while (next()) {
			DateTime beginDate = sdf.parseDateTime(getStrBulkBegin());
			DateTime endDate = sdf.parseDateTime(getStrBulkEnd());
			while (!beginDate.isAfter(endDate)) {
				strDate = sdf.print(beginDate);
				_execute();
				beginDate = beginDate.plusDays(1);
			}
		}
	}

}
