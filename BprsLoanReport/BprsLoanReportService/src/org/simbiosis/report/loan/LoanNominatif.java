package org.simbiosis.report.loan;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.microbank.ILoanReport;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getLoanNominatif")
public class LoanNominatif extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/LoanReport")
	ILoanReport report;

	long branch;
	Date date;
	int quality;
	long product;

	public LoanNominatif() {
		super("LoanNominatif");
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		DateTime today = new DateTime();
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		//
		String strDate = request.getParameter("date");
		if (strDate == null) {
			date = today.toDate();
		} else {
			date = sdf.parseDateTime(strDate).toDate();
		}
		String strBranch = request.getParameter("branch");
		String branchName = "KONSOLIDASI";
		branch = (strBranch == null) ? 0 : Long.parseLong(strBranch);
		if (branch != 0) {
			branchName = getBranchName(branch);
		}
		String strQuality = request.getParameter("quality");
		quality = (strQuality == null) ? 0 : Integer.parseInt(strQuality);
		String strProduct = request.getParameter("product");
		product = (strProduct == null) ? 0 : Long.parseLong(strProduct);
		prepare();
		//
		setBeanCollection(report.listDailyLoanByProduct(getCompany(), branch,
				date, product, quality));
		//
		setParameter("Loan.company", getCompanyName());
		setParameter("Loan.branch", branchName);
	}

}
