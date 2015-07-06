package org.simbiosis.report.loan;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.simbiosis.microbank.ILoanReport;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getLoanRemedialXls")
public class LoanRemedialXls extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/LoanReport")
	ILoanReport report;

	public LoanRemedialXls() {
		super("LoanRemedialXls");
		setType(2);
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		LoanRemedialEngine engine = new LoanRemedialEngine(report);
		engine.prepareRequest(request, this);
		//
		prepare();
		//
		setBeanCollection(engine.prepareData());
		//

		setParameter("Loan.company", getCompanyName());
		setParameter("Loan.branch", engine.getBranchName());
		setParameter("Loan.ao", engine.getAoName());
	}

}
