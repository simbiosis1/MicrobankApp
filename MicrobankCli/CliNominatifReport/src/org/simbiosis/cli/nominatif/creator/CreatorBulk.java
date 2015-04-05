package org.simbiosis.cli.nominatif.creator;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.simbiosis.cli.nominatif.lib.Nominatif;

public class CreatorBulk {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CreatorBulk creatorBulk = new CreatorBulk();
		creatorBulk.execute();
	}

	public void execute() {
		DateTime start = new DateTime();
		//
		Nominatif creator = new Nominatif();
		creator.execute();
		//
		DateTime finish = new DateTime();
		Period period = new Period(start, finish);

		PeriodFormatter formatter = new PeriodFormatterBuilder().appendMillis()
				.appendSuffix(" milliseconds - ").appendSeconds()
				.appendSuffix(" seconds - ").appendMinutes()
				.appendSuffix(" minutes - ").printZeroNever().toFormatter();
		String elapsed = formatter.print(period);
		System.out.println(elapsed);
	}

}
