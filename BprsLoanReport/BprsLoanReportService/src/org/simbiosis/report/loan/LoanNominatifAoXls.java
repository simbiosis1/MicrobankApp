package org.simbiosis.report.loan;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.microbank.ILoanReport;
import org.simbiosis.microbank.model.LoanRpt;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getLoanNominatifAoXls")
public class LoanNominatifAoXls extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/LoanReport")
	ILoanReport report;

	long branch;
	int quality;
	long product;

	public LoanNominatifAoXls() {
		super("LoanNominatifXls");
		setType(2);
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		DateTime date = new DateTime();
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		//
		String strDate = request.getParameter("date");
		if (strDate != null) {
			date = sdf.parseDateTime(strDate);
		}
		String strAo = request.getParameter("ao");
		long ao = (strAo == null) ? 0 : Long.parseLong(strAo);
		String strQuality = request.getParameter("quality");
		int quality = (strQuality == null) ? 0 : Integer.parseInt(strQuality);
		prepare();
		//
		List<LoanRpt> result = report.listDailyLoanByAo(getCompany(),
				date.toDate(), ao, quality);
		for (LoanRpt loan : result) {
			loan.setProductName(loan.getAoName());
		}
		Collections.sort(result, new Comparator<LoanRpt>() {

			@Override
			public int compare(LoanRpt o1, LoanRpt o2) {
				return o1.getProductName().compareToIgnoreCase(
						o2.getProductName());
			}
		});
		setBeanCollection(result);
		//
		setParameter("Loan.company", getCompanyName());
		// setParameter("Loan.branch", branchName);
	}

}
