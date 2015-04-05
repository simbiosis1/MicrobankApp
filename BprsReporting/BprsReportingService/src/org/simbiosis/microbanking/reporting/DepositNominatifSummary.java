package org.simbiosis.microbanking.reporting;

import java.io.IOException;
import java.util.ArrayList;
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
import org.simbiosis.microbanking.reporting.model.SummaryDv;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getDepositNominatifSummary")
public class DepositNominatifSummary extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/DepositReport")
	IDepositReport report;

	long branch;
	DateTime date = new DateTime();
	String strDate;
	String url = "";

	public DepositNominatifSummary() {
		super("DepositNominatifSummary");
	}

	List<SummaryDv> prepareData() {
		List<SummaryDv> result = new ArrayList<SummaryDv>();
		List<DepositRpt> listDeposit = report.listDailyDeposit(getCompany(),
				branch, date.toDate());
		int nr = 0;
		String curProduct = "";
		for (DepositRpt depRpt : listDeposit) {
			SummaryDv sn = new SummaryDv();
			if (!depRpt.getProductName().equalsIgnoreCase(curProduct)) {
				nr++;
				curProduct = depRpt.getProductName();
			}
			sn.setNr(nr);
			sn.setProduct(depRpt.getProductName());
			sn.setValue(depRpt.getValue());
			sn.setProductId(depRpt.getProduct());
			sn.setLink(url + "getDepositNominatif?" + "branch=" + branch + "&"
					+ "date=" + strDate + "&product=" + sn.getProductId());
			result.add(sn);
		}
		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		url = request.getProtocol();
		int pos = url.lastIndexOf("HTTP");
		url = url.substring(0, pos);
		//
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		//
		strDate = request.getParameter("date");
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
		prepare();
		//
		setBeanCollection(prepareData());
		//
		String linkAll = url + "getDepositNominatif?" + "branch=" + branch
				+ "&" + "date=" + strDate;

		setParameter("Deposit.company", getCompanyName());
		setParameter("Deposit.branch", branchName);
		setParameter("Deposit.branchId", String.valueOf(branch));
		setParameter("Deposit.date", strDate);
		setParameter("Deposit.linkAll", linkAll);
	}

}
