package org.simbiosis.dashboard.reporting;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.dashboard.reporting.model.DashboardDv;
import org.simbiosis.microbank.IDepositReport;
import org.simbiosis.microbank.ILoanReport;
import org.simbiosis.microbank.ISavingReport;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getSavingDashboardXls")
public class SavingDashboardXls extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/SavingReport")
	ISavingReport savingReport;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/DepositReport")
	IDepositReport coreBankingReport;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/LoanReport")
	ILoanReport loanReport;

	long branch;
	Date date;

	public SavingDashboardXls() {
		super("SavingDashboard1Xls");
		setType(2);
	}

	List<DashboardDv> prepareData() throws ParseException {
		Dashboard dashboard = new Dashboard();
		return dashboard.generate(
				savingReport.listDailySaving(getCompany(), branch, date),
				coreBankingReport.listDailyDeposit(getCompany(), branch, date),
				loanReport.listDailyLoan(getCompany(), branch, date));
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		DateTime today = new DateTime();
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		//
		try {
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
			setParameter("Saving.company", getCompanyName());
			setParameter("Saving.branch", branchName);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
