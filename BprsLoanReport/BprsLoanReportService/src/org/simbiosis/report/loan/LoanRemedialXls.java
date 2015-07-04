package org.simbiosis.report.loan;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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

@WebServlet("/getLoanRemedialXls")
public class LoanRemedialXls extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/LoanReport")
	ILoanReport report;

	long branch;
	long ao;
	boolean all;
	Date date;

	String[] endMonths = { "", "31-01", "28-02", "31-03", "30-04", "31-05",
			"30-06", "31-07", "31-08", "30-09", "31-10", "30-11", "31-12" };

	public LoanRemedialXls() {
		super("LoanRemedialXls");
		setType(2);
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		DateTime today = new DateTime();
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		DateTimeFormatter sdfy = DateTimeFormat.forPattern("yyyy");
		DateTimeFormatter sdfm = DateTimeFormat.forPattern("MM");
		String strMonth = request.getParameter("month");
		//
		//
		if (strMonth == null) {
			int month = Integer.parseInt(sdfm.print(today));
			date = sdf
					.parseDateTime(endMonths[month] + "-" + sdfy.print(today))
					.toDate();
		} else {
			int month = Integer.parseInt(strMonth);
			date = sdf
					.parseDateTime(endMonths[month] + "-" + sdfy.print(today))
					.toDate();
		}
		//
		String strBranch = request.getParameter("branch");
		String branchName = "KONSOLIDASI";
		branch = (strBranch == null) ? 0 : Long.parseLong(strBranch);
		if (branch != 0) {
			branchName = getBranchName(branch);
		}
		//
		String strAo = request.getParameter("ao");
		String aoName = "SELURUH AO";
		ao = (strAo == null) ? 0 : Long.parseLong(strAo);
		if (ao != 0) {
			aoName = getUserRealName(ao);
		}
		//
		String strAll = request.getParameter("all");
		all = (strAll == null) ? true : strAll.equalsIgnoreCase("1");
		//
		prepare();
		//
		List<LoanRpt> hasil = report.listLoanBilling(getCompany(), branch,
				date, ao,all);
		Collections.sort(hasil, new Comparator<LoanRpt>() {

			@Override
			public int compare(LoanRpt o1, LoanRpt o2) {
				String s1 = o1.getAoName() + o1.getProductName();
				String s2 = o2.getAoName() + o2.getProductName();
				return s1.compareToIgnoreCase(s2);
			}
		});
		setBeanCollection(hasil);
		//

		setParameter("Loan.company", getCompanyName());
		setParameter("Loan.branch", branchName);
		setParameter("Loan.ao", aoName);
	}

}
