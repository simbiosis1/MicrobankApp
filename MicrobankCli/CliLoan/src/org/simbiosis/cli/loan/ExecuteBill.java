package org.simbiosis.cli.loan;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.CliBase;
import org.simbiosis.cli.loan.lib.LoanBillingDv;
import org.simbiosis.cli.loan.lib.LoanBillingGenerator;

public class ExecuteBill extends CliBase {

	String strBeginDate = new String("01-");
	String strEndDate = "";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecuteBill penagihan = new ExecuteBill();
		penagihan.execute();
	}

	public ExecuteBill() {
		super("cli.properties");
		//
		DateTimeFormatter mdf = DateTimeFormat.forPattern("MM-yyyy");
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		DateTime now = new DateTime();
		strBeginDate += mdf.print(now);
		strEndDate = sdf.print(now);
	}

	public void execute() {
		while (next()) {
			//
			System.out.println("Process billing data..." + strBeginDate
					+ " s.d " + strEndDate);
			//
			Map<Long, LoanBillingDv> loanMap = new HashMap<Long, LoanBillingDv>();

			LoanBillingGenerator generator = new LoanBillingGenerator(
					getCoreClient(), loanMap);
			generator.createBillingList(strBeginDate, strEndDate);
			//
			System.out.println("Pay billing...");
			generator.payBilling();
		}
	}

}
