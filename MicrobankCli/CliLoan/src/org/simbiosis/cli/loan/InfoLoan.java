package org.simbiosis.cli.loan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.CliBase;
import org.simbiosis.microbank.model.LoanRpt;

public class InfoLoan extends CliBase {

	DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
	DateTime now = new DateTime();

	public static void main(String[] args) {
		InfoLoan cb = new InfoLoan();
		cb.execute();
	}

	public InfoLoan() {
		super();
	}

	public void execute() {
		double totalPrincipal = 0;
		double totalPrincipalNett = 0;
		double totalPrincipalGross = 0;
		List<LoanRpt> loans = listLoan();
		// Hitung nilai2
		System.out.println("Hitung nilai2...(" + loans.size() + " pembiayaan)");
		for (LoanRpt loan : loans) {
			totalPrincipal += loan.getOsPrincipal();
			if (loan.getQuality() > 1) {
				double jaminan = (0.5 * loan.getGuarantee());
				totalPrincipalNett += (loan.getOsPrincipal() - ((jaminan > loan
						.getOsPrincipal()) ? 0 : jaminan));
				totalPrincipalGross += loan.getOsPrincipal();
			}
		}
		// Hitung semua
		System.out.println("Hitung npf...");
		System.out.println("- Nett : "
				+ (totalPrincipalNett * 100 / totalPrincipal));
		System.out.println("- Gross : "
				+ (totalPrincipalGross * 100 / totalPrincipal));
	}

	List<LoanRpt> listLoan() {
		List<LoanRpt> loans = new ArrayList<LoanRpt>();
		String data = getReportClient().sendRawData("listDailyLoan",
				"2;0;" + sdf.print(now));
		ObjectMapper mapper = new ObjectMapper();
		try {
			loans = mapper.readValue(data, mapper.getTypeFactory()
					.constructCollectionType(ArrayList.class, LoanRpt.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loans;
	}

}
