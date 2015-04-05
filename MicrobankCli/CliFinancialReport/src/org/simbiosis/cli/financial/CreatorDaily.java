package org.simbiosis.cli.financial;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.simbiosis.cli.base.CliBase;
import org.simbiosis.cli.financial.lib.Neraca;

public class CreatorDaily extends CliBase {

	public static void main(String[] args) {
		CreatorDaily creatorDaily = new CreatorDaily();
		creatorDaily.execute();
	}

	public CreatorDaily() {
		super("cli.properties");
	}

	public void execute() {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		DateTime beginProcess = new DateTime();
		DateTime firstDateYear = beginProcess.dayOfYear().withMinimumValue();
		String strBeginDate = dtf.print(firstDateYear);
		String strEndDate = dtf.print(beginProcess);
		while (next()) {
			System.out.println("Create financial tanggal " + strBeginDate
					+ " - " + strEndDate);
			Neraca neraca = new Neraca(strBeginDate, strEndDate,
					beginProcess.toDate(), getCoreClient(),getReportClient());
			neraca.execute();
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
