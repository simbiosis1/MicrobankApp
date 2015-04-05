package org.simbiosis.cli.financial;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.simbiosis.cli.base.CliBase;
import org.simbiosis.cli.financial.lib.PublicFinancialReport;

public class CreatePublicReport extends CliBase {

	String strDate = "";
	boolean bulk = false;

	public static void main(String[] args) {
		CreatePublicReport creatorDaily = null;
		if (args.length > 0) {
			creatorDaily = new CreatePublicReport(args[0]);
		} else {
			creatorDaily = new CreatePublicReport();
		}
		creatorDaily.execute();
	}

	public CreatePublicReport() {
		super("cli.properties");
		bulk = true;
	}

	public CreatePublicReport(String strDate) {
		super("cli.properties");
		this.strDate = strDate;
	}

	public void execute() {
		DateTime beginProcess = new DateTime();
		if (bulk) {
			executeBulk();
		} else {
			executeHarian();
		}
		DateTime finishProcess = new DateTime();
		Period period = new Period(beginProcess, finishProcess);

		PeriodFormatter formatter = new PeriodFormatterBuilder().appendMillis()
				.appendSuffix(" milliseconds - ").appendSeconds()
				.appendSuffix(" seconds - ").appendMinutes()
				.appendSuffix(" minutes - ").printZeroNever().toFormatter();
		String elapsed = formatter.print(period);
		System.out.println(elapsed);
	}

	private void executeHarian() {
		while (next()) {
			System.out.println("Create public financial tanggal " + strDate);
			PublicFinancialReport neracaPublik = new PublicFinancialReport(
					strDate, getCoreClient(), getReportClient());
			neracaPublik.execute();
		}
	}

	private void executeBulk() {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		DateTime currentDate = dtf.parseDateTime(getStrBulkBegin());
		DateTime endDate = dtf.parseDateTime(getStrBulkEnd()).plusDays(1);
		while (next()) {
			while (currentDate.isBefore(endDate)) {
				System.out.println("Create public financial tanggal "
						+ dtf.print(currentDate));
				PublicFinancialReport neracaPublik = new PublicFinancialReport(
						dtf.print(currentDate), getCoreClient(),
						getReportClient());
				neracaPublik.execute();
				currentDate = currentDate.plusDays(1);
			}
		}
	}

}
