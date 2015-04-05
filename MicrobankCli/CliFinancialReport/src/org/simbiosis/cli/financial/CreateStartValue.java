package org.simbiosis.cli.financial;

import org.simbiosis.cli.base.CliBase;
import org.simbiosis.cli.financial.lib.StartValue;

public class CreateStartValue extends CliBase {

	String year;

	public static void main(String[] args) {
		CreateStartValue app = new CreateStartValue();
		app.execute();
	}

	public CreateStartValue() {
		super("cli.properties");
		//
		String[] dates = getStrBulkBegin().split("-");
		year = dates[2];
	}

	public void execute() {
		while (next()) {
			System.out.println("Create start value " + year);
			StartValue neraca = new StartValue(getCoreClient(),
					getReportClient(), year);
			neraca.execute();
		}
	}

}
