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

@WebServlet("/getLoanNominatifAoSummary")
public class LoanNominatifAoSummary extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/LoanReport")
	ILoanReport report;

	long branch;
	String branchName = "";
	DateTime date;
	int quality;
	String strDate;
	String url = "";

	public LoanNominatifAoSummary() {
		super("LoanNominatifAoSummary");
	}

	List<LoanResume> prepareData() {
		Map<String, LoanResume> resultMap = new HashMap<String, LoanResume>();
		List<LoanRpt> listSaving = report.listDailyLoanByQuality(getCompany(),
				branch, date.toDate(), quality);
		for (LoanRpt savingRpt : listSaving) {
			LoanResume sn = resultMap.get(savingRpt.getAoName());
			if (sn == null) {
				sn = new LoanResume();
				sn.setProductName(savingRpt.getAoName());
				sn.setPrincipal(savingRpt.getPrincipal());
				sn.setMargin(savingRpt.getMargin());
				sn.setTotal(savingRpt.getTotal());
				sn.setOsPrincipal(savingRpt.getOsPrincipal());
				sn.setOsMargin(savingRpt.getOsMargin());
				sn.setOsTotal(savingRpt.getOsTotal());
				sn.setPpap(savingRpt.getPpap());
				sn.setLink(url + "getLoanNominatifAo?" + "date=" + strDate
						+ "&ao=" + savingRpt.getAo() + "&quality=" + quality
						+ "&branchName=" + branchName + " - "
						+ sn.getProductName());
			} else {
				sn.setPrincipal(sn.getPrincipal() + savingRpt.getPrincipal());
				sn.setMargin(sn.getMargin() + savingRpt.getMargin());
				sn.setTotal(sn.getTotal() + savingRpt.getTotal());
				sn.setOsPrincipal(sn.getOsPrincipal()
						+ savingRpt.getOsPrincipal());
				sn.setOsMargin(sn.getOsMargin() + savingRpt.getOsMargin());
				sn.setOsTotal(sn.getOsTotal() + savingRpt.getOsTotal());
				sn.setPpap(sn.getPpap() + savingRpt.getPpap());
			}
			resultMap.put(savingRpt.getAoName(), sn);
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
		//
		date = new DateTime();
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		//
		strDate = request.getParameter("date");
		if (strDate != null) {
			date = sdf.parseDateTime(strDate);
		}
		strDate = sdf.print(date);

		String strBranch = request.getParameter("branch");
		branchName = "KONSOLIDASI";
		branch = (strBranch == null) ? 0 : Long.parseLong(strBranch);
		if (branch != 0) {
			branchName = getBranchName(branch);
		}
		String strQuality = request.getParameter("quality");
		quality = (strQuality == null) ? 0 : Integer.parseInt(strQuality);
		prepare();
		//
		setBeanCollection(prepareData());
		//
		setParameter("Loan.company", getCompanyName());
		setParameter("Loan.branch", branchName);
		setParameter("Loan.date", strDate);
		setParameter("Loan.branchId", branch);
		setParameter("Loan.quality", quality);
	}

}
