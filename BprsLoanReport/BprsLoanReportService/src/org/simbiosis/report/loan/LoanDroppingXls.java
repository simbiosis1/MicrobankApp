package org.simbiosis.report.loan;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		//
		String strBeginDate = request.getParameter("beginDate");
		DateTime beginDate = strBeginDate != null ? dtf
				.parseDateTime(strBeginDate) : new DateTime();
		String strEndDate = request.getParameter("endDate");
		DateTime endDate = strEndDate != null ? dtf.parseDateTime(strEndDate)
				: new DateTime();
		String strBranch = request.getParameter("branch");
		Long branch = strBranch != null ? Long.parseLong(strBranch) : 0L;
		//
		prepare();
		//
		LoanDroppingEngine engine = new LoanDroppingEngine(loanBp, getSession());
		setBeanCollection(engine.prepareData(branch, beginDate.toDate(),
				endDate.toDate()));
		//
		setParameter("LoanDropping.company", getCompanyName());
		setParameter("LoanDropping.branch", getBranchName(branch));
		setParameter("LoanDropping.beginDate", strBeginDate);
		setParameter("LoanDropping.endDate", strEndDate);
	}

}
