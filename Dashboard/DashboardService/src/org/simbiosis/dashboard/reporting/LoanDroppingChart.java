package org.simbiosis.dashboard.reporting;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.bp.micbank.ILoanBp;
import org.simbiosis.dashboard.reporting.model.LoanDroppingDv;
import org.simbiosis.microbank.LoanTransInfoDto;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getLoanDroppingChart")
public class LoanDroppingChart extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/LoanBp")
	ILoanBp loanBp;

	long branch;
	Date date;

	String[] endDates = { "", "31-01", "28-02", "31-03", "30-04", "31-05",
			"30-06", "31-07", "31-08", "30-09", "31-10", "30-11", "31-12" };
	String[] months = { "", "Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul",
			"Agu", "Sep", "Okt", "Nov", "Des" };

	DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");

	public LoanDroppingChart() {
		super("LoanDroppingChart");
	}

	private List<LoanDroppingDv> prepareData() {
		DecimalFormat df = new DecimalFormat("00");
		List<LoanDroppingDv> result = new ArrayList<LoanDroppingDv>();
		for (int i = 1; i <= 12; i++) {
			Date beginDate = sdf.parseDateTime("01-" + df.format(i) + "-2013")
					.toDate();
			Date endDate = sdf.parseDateTime(endDates[i] + "-2013").toDate();
			List<LoanTransInfoDto> transList = loanBp.listLoanTransSumGroup(
					getCompany(), branch, 1, beginDate, endDate);
			for (LoanTransInfoDto trans : transList) {
				LoanDroppingDv dv = new LoanDroppingDv();
				dv.setMonth(months[i]);
				dv.setProduct(trans.getProductName());
				dv.setDropping(trans.getPaidPrincipal() / 1000);
				result.add(dv);
			}
		}
		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		DateTime today = new DateTime();
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
		prepare();
		//
		setBeanCollection(prepareData());
		//
		setParameter("LoanDropping.company", getCompanyName());
		setParameter("LoanDropping.branch", branchName);
	}

}
