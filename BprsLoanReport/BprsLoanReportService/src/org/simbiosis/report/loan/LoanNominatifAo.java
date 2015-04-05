package org.simbiosis.report.loan;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.microbank.ILoanReport;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getLoanNominatifAo")
public class LoanNominatifAo extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/LoanReport")
	ILoanReport report;

	long branch;

	public LoanNominatifAo() {
		super("LoanNominatif");
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		DateTime date = new DateTime();
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		//
		String strDate = request.getParameter("date");
		if (strDate != null) {
			date = sdf.parseDateTime(strDate);
		}

		String branchName = request.getParameter("branchName");

		String strAo = request.getParameter("ao");
		long ao = (strAo == null) ? 0 : Long.parseLong(strAo);
		String strQuality = request.getParameter("quality");
		int quality = (strQuality == null) ? 0 : Integer.parseInt(strQuality);
		prepare();
		//
		setBeanCollection(report.listDailyLoanByAo(getCompany(), date.toDate(),
				ao, quality));
		//
		setParameter("Loan.company", getCompanyName());
		setParameter("Loan.branch", branchName);
	}

}
