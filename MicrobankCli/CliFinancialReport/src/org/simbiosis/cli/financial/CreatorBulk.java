package org.simbiosis.cli.financial;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.simbiosis.cli.base.CliBase;
import org.simbiosis.cli.financial.lib.Neraca;

public class CreatorBulk extends CliBase {

	public static void main(String[] args) {
		CreatorBulk creatorDaily = new CreatorBulk();
		creatorDaily.execute();
	}

	public CreatorBulk() {
		super("cli.properties");
	}

	public void execute() {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		DateTime beginProcess = new DateTime();
		DateTime beginDate = new DateTime();
		DateTime endDate = new DateTime();
		while (next()) {
			beginDate = dtf.parseDateTime(getStrBulkBegin());
			endDate = dtf.parseDateTime(getStrBulkEnd());
			//
			DateTime firstDateYear = beginDate.dayOfYear().withMinimumValue();
			String strBeginDate = dtf.print(firstDateYear);
			String strEndDate = "";
			while (!beginDate.isAfter(endDate)) {
				Date date = beginDate.toDate();
				strEndDate = dtf.print(beginDate);
				System.out.println("Neraca untuk tanggal "
						+ dtf.print(beginDate));
				Neraca neraca = new Neraca(strBeginDate, strEndDate, date,
						getCoreClient(), getReportClient());
				neraca.execute();
				beginDate = beginDate.plusDays(1);
			}
		}
		
		DateTime finishProcess = new DateTime();
		Period period = new Period(beginProcess, finishProcess);

		PeriodFormatter formatter = new PeriodFormatterBuilder()
				.appendMillis().appendSuffix(" milliseconds - ")
				.appendSeconds().appendSuffix(" seconds - ").
				appendMinutes().appendSuffix(" minutes - ").
				printZeroNever().toFormatter();
		String elapsed = formatter.print(period);
		System.out.println(elapsed);
	}
}
