package org.simbiosis.report.loan;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.microbank.ILoanReport;
import org.simbiosis.microbank.model.LoanRpt;
import org.simbiosis.printing.lib.ReportServlet;

public class LoanRemedialEngine {

	ILoanReport report;

	long company;
	long branch;
	long ao;
	boolean all = true;
	int col = 0;

	DateTime date = new DateTime();
	String branchName = "";
	String aoName = "";

	DateTimeFormatter sdfd = DateTimeFormat.forPattern("dd");

	String[] endMonths = { "", "31-01", "28-02", "31-03", "30-04", "31-05",
			"30-06", "31-07", "31-08", "30-09", "31-10", "30-11", "31-12" };

	public LoanRemedialEngine(ILoanReport report) {
		this.report = report;
	}

	public void prepareRequest(HttpServletRequest request, ReportServlet rs) {
		company = rs.getCompany();
		//
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		DateTimeFormatter sdfm = DateTimeFormat.forPattern("MM");
		DateTimeFormatter sdfy = DateTimeFormat.forPattern("yyyy");

		String strMonth = request.getParameter("month");
		if (strMonth == null) {
			int month = Integer.parseInt(sdfm.print(date));
			date = sdf.parseDateTime(endMonths[month] + "-" + sdfy.print(date));
		} else {
			int month = Integer.parseInt(strMonth);
			date = sdf.parseDateTime(endMonths[month] + "-" + sdfy.print(date));
		}
		//
		String strBranch = request.getParameter("branch");
		String branchName = "KONSOLIDASI";
		branch = (strBranch == null) ? 0 : Long.parseLong(strBranch);
		if (branch != 0) {
			branchName = rs.getBranchName(branch);
		}
		//
		String strAo = request.getParameter("ao");
		String aoName = "SELURUH AO";
		ao = (strAo == null) ? 0 : Long.parseLong(strAo);
		if (ao != 0) {
			aoName = rs.getUserRealName(ao);
		}
		//
		String strAll = request.getParameter("all");
		all = (strAll == null) ? true : strAll.equalsIgnoreCase("1");
		//
		String strCol = request.getParameter("col");
		if (strCol != null) {
			col = Integer.parseInt(strCol);
		}
		//
		this.branchName = branchName;
		this.aoName = aoName;
	}

	public List<LoanRpt> prepareData() {
		// Ambil data
		List<LoanRpt> result = report.listLoanBilling(company, branch,
				date.toDate(), ao, all);
		if (col == 12) {
			Iterator<LoanRpt> iter = result.iterator();
			while (iter.hasNext()) {
				LoanRpt rpt = iter.next();
				if (rpt.getQuality() == 3 || rpt.getQuality() == 4) {
					iter.remove();
				}
			}
		} else if (col == 34) {
			Iterator<LoanRpt> iter = result.iterator();
			while (iter.hasNext()) {
				LoanRpt rpt = iter.next();
				if (rpt.getQuality() == 1 || rpt.getQuality() == 2) {
					iter.remove();
				}
			}
		}
		// Sortir
		Collections.sort(result, new Comparator<LoanRpt>() {

			@Override
			public int compare(LoanRpt o1, LoanRpt o2) {
				String s1 = o1.getAoName()
						+ sdfd.print(new DateTime(o1.getEnd()));
				String s2 = o2.getAoName()
						+ sdfd.print(new DateTime(o2.getEnd()));
				return s1.compareToIgnoreCase(s2);
			}
		});
		return result;
	}

	public String getBranchName() {
		return branchName;
	}

	public String getAoName() {
		return aoName;
	}

}
