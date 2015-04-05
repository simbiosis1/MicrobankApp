package org.simbiosis.report.loan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.simbiosis.report.loan.model.LoanResume;

@WebServlet("/getLoanNominatifSummary")
public class LoanNominatifSummary extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/LoanReport")
	ILoanReport report;

	long branch;
	DateTime date;
	int quality;
	String strDate;
	String url = "";

	public LoanNominatifSummary() {
		super("LoanNominatifSummary");
	}

	List<LoanResume> prepareData() {
		Map<String, LoanResume> resultMap = new HashMap<String, LoanResume>();
		List<LoanRpt> listLoan = report.listDailyLoanByQuality(getCompany(),
				branch, date.toDate(), quality);
		for (LoanRpt loanRpt : listLoan) {
			LoanResume sn = resultMap.get(loanRpt.getProductName());
			if (sn == null) {
				sn = new LoanResume();
				sn.setProduct(loanRpt.getProduct());
				sn.setProductName(loanRpt.getProductName());
				sn.setPrincipal(loanRpt.getPrincipal());
				sn.setMargin(loanRpt.getMargin());
				sn.setTotal(loanRpt.getTotal());
				sn.setOsPrincipal(loanRpt.getOsPrincipal());
				sn.setOsMargin(loanRpt.getOsMargin());
				sn.setOsTotal(loanRpt.getOsTotal());
				sn.setPpap(loanRpt.getPpap());
				sn.setLink(url + "getLoanNominatif?" + "branch=" + branch + "&"
						+ "date=" + strDate + "&quality=" + quality
						+ "&product=" + loanRpt.getProduct());
			} else {
				sn.setPrincipal(sn.getPrincipal() + loanRpt.getPrincipal());
				sn.setMargin(sn.getMargin() + loanRpt.getMargin());
				sn.setTotal(sn.getTotal() + loanRpt.getTotal());
				sn.setOsPrincipal(sn.getOsPrincipal()
						+ loanRpt.getOsPrincipal());
				sn.setOsMargin(sn.getOsMargin() + loanRpt.getOsMargin());
				sn.setOsTotal(sn.getOsTotal() + loanRpt.getOsTotal());
				sn.setPpap(sn.getPpap() + loanRpt.getPpap());
			}
			resultMap.put(loanRpt.getProductName(), sn);
		}
		List<LoanResume> result = new ArrayList<LoanResume>();
		result.addAll(resultMap.values());
		Collections.sort(result, new Comparator<LoanResume>() {

			@Override
			public int compare(LoanResume o1, LoanResume o2) {
				return o1.getProductName().compareTo(o2.getProductName());
			}
		});
		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		url = request.getProtocol();
		int pos = url.lastIndexOf("HTTP");
		url = url.substring(0, pos);
		date = new DateTime();
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		//
		strDate = request.getParameter("date");
		if (strDate != null) {
			date = sdf.parseDateTime(strDate);
		}
		String strBranch = request.getParameter("branch");
		String branchName = "KONSOLIDASI";
		branch = (strBranch == null) ? 0 : Long.parseLong(strBranch);
		if (branch != 0) {
			branchName = getBranchName(branch);
		}
		String strQuality = request.getParameter("quality");
		quality = (strQuality == null) ? 0 : Integer.parseInt(strQuality);
		prepare();
		//
		setBeanCollection(prepareData());
		String linkAll = url + "getLoanNominatif?" + "branch=" + branch + "&"
				+ "date=" + strDate + "&quality=" + quality;
		//
		setParameter("Loan.company", getCompanyName());
		setParameter("Loan.branch", branchName);
		setParameter("Loan.date", strDate);
		setParameter("Loan.branchId", branch);
		setParameter("Loan.quality", quality);
		setParameter("Loan.linkAll", linkAll);
	}

}
