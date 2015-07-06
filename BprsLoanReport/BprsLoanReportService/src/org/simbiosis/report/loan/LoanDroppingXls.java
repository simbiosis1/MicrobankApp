package org.simbiosis.report.loan;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.simbiosis.bp.micbank.ILoanBp;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getLoanDroppingXls")
public class LoanDroppingXls extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/LoanBp")
	ILoanBp loanBp;

	public LoanDroppingXls() {
		super("LoanDroppingXls");
		setType(2);
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		LoanDroppingEngine engine = new LoanDroppingEngine(loanBp, getSession());
		engine.prepareRequest(request, this);
		//
		prepare();
		//
		setBeanCollection(engine.prepareData());
		//
		setParameter("LoanDropping.company", getCompanyName());
		setParameter("LoanDropping.branch", engine.getBranchName());
		setParameter("LoanDropping.beginDate", engine.getStrBeginDate());
		setParameter("LoanDropping.endDate", engine.getStrEndDate());
	}

}
