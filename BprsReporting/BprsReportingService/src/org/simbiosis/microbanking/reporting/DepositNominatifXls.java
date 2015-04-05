package org.simbiosis.microbanking.reporting;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.microbank.IDepositReport;
import org.simbiosis.microbank.model.DepositRpt;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getDepositNominatifXls")
public class DepositNominatifXls extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/DepositReport")
	IDepositReport report;

	long branch;
	DateTime date = new DateTime();
	long product;

	public DepositNominatifXls() {
		super("DepositNominatifXls");
		setType(2);
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		//
		String strDate = request.getParameter("date");
		if (strDate != null) {
			date = dtf.parseDateTime(strDate);
		}
		strDate = dtf.print(date);

		String strBranch = request.getParameter("branch");
		String branchName = "KONSOLIDASI";
		branch = (strBranch == null) ? 0 : Long.parseLong(strBranch);
		if (branch != 0) {
			branchName = getBranchName(branch);
		}
		String strProduct = request.getParameter("product");
		product = (strProduct == null) ? 0 : Long.parseLong(strProduct);
		prepare();
		//
		List<DepositRpt> listSaving = report.listDailyDepositByProduct(
				getCompany(), branch, date.toDate(), product);
		setBeanCollection(listSaving);
		//
		setParameter("Deposit.company", getCompanyName());
		setParameter("Deposit.branch", branchName);
	}

}
