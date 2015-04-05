package org.simbiosis.cli.nominatif.creator;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.simbiosis.cli.nominatif.lib.Nominatif;

public class CreatorDaily {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CreatorDaily cd = new CreatorDaily();
		cd.execute();
	}

	public void execute() {
		DateTime start = new DateTime();
		//
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		Nominatif creator = new Nominatif(dtf.print(new DateTime()));
		creator.execute();
		//
		DateTime finish = new DateTime();
		Period period = new Period(start, finish);

		PeriodFormatter formatter = new PeriodFormatterBuilder()
				.appendMillis().appendSuffix(" milliseconds - ")
				.appendSeconds().appendSuffix(" seconds - ").
				appendMinutes().appendSuffix(" minutes - ").
				printZeroNever().toFormatter();
		String elapsed = formatter.print(period);
		System.out.println(elapsed);
	}
}
