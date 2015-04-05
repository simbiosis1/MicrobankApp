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
import org.simbiosis.microbank.ISavingReport;
import org.simbiosis.microbank.model.SavingRpt;
import org.simbiosis.microbanking.reporting.model.SummaryDv;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getSavingNominatifSummary")
public class SavingNominatifSummary extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/SavingReport")
	ISavingReport savingReport;

	long branch;
	DateTime date = new DateTime();
	String strDate;
	String url = "";

	public SavingNominatifSummary() {
		super("SavingNominatifSummary");
	}

	List<SummaryDv> prepareData() {
		List<SummaryDv> result = new ArrayList<SummaryDv>();
		List<SavingRpt> listSaving = savingReport.listDailySaving(getCompany(),
				branch, date.toDate());
		int nr = 0;
		String curProduct = "";
		for (SavingRpt savingRpt : listSaving) {
			SummaryDv sn = new SummaryDv();
			if (!savingRpt.getProductName().equalsIgnoreCase(curProduct)) {
				nr++;
				curProduct = savingRpt.getProductName();
			}
			sn.setNr(nr);
			sn.setProduct(savingRpt.getProductName());
			sn.setProductId(savingRpt.getProduct());
			sn.setValue(savingRpt.getValAfter());
			sn.setLink(url + "getSavingNominatif?" + "branch=" + branch + "&"
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
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		//
		strDate = request.getParameter("date");
		if (strDate != null) {
			date = sdf.parseDateTime(strDate);
		}
		strDate = sdf.print(date);

		String strBranch = request.getParameter("branch");
		String branchName = "KONSOLIDASI";
		branch = (strBranch == null) ? 0 : Long.parseLong(strBranch);
		if (branch != 0) {
			branchName = getBranchName(branch);
		}
		prepare();
		//
		setBeanCollection(prepareData());
		String linkAll = url + "getSavingNominatif?" + "branch=" + branch + "&"
				+ "date=" + strDate;
		//
		setParameter("Saving.company", getCompanyName());
		setParameter("Saving.branch", branchName);
		setParameter("Saving.branchId", String.valueOf(branch));
		setParameter("Saving.date", strDate);
		setParameter("Saving.linkAll", linkAll);
	}
}
