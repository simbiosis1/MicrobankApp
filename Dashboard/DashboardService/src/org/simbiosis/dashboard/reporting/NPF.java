package org.simbiosis.dashboard.reporting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.simbiosis.dashboard.reporting.model.NPFDv;
import org.simbiosis.microbank.ILoanReport;
import org.simbiosis.microbank.model.LoanRpt;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getNPF")
public class NPF extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/LoanReport")
	ILoanReport report;

	long branch;
	Date date;
	int quality;
	String year;

	String[] endDates = { "", "31-01", "28-02", "31-03", "30-04", "31-05",
			"30-06", "31-07", "31-08", "30-09", "31-10", "30-11", "31-12" };
	String[] months = { "", "Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul",
			"Agu", "Sep", "Okt", "Nov", "Des" };

	public NPF() {
		super("NPF");
	}

	List<NPFDv> prepareData() {
		List<NPFDv> result = new ArrayList<NPFDv>();
		NPFDv npfTotal = new NPFDv();
		npfTotal.setProductName("TOTAL NPF");
		List<LoanRpt> transList = report.listDailyLoan(getCompany(), branch,
				date);
		Map<Long, NPFDv> productMap = new HashMap<Long, NPFDv>();
		for (LoanRpt loan : transList) {
			NPFDv npfDv = productMap.get(loan.getProduct());
			double os = loan.getOsPrincipal();
			if (npfDv == null) {
				npfDv = new NPFDv();
				npfDv.setProductName(loan.getProductName());
				npfDv.addPrincipal(os);
				//
				npfTotal.addPrincipal(os);
				//
				if (loan.getQuality() > 1) {
					double jaminan = (0.5 * loan.getGuarantee());
					npfDv.addPrincipalNett(os - (jaminan > os ? 0 : jaminan));
					npfDv.addPrincipalGross(os);
					//
					npfTotal.addPrincipalNett(os - (jaminan > os ? 0 : jaminan));
					npfTotal.addPrincipalGross(os);
				}
			} else {
				npfDv.addPrincipal(os);
				//
				npfTotal.addPrincipal(os);
				//
				if (loan.getQuality() > 1) {
					double jaminan = (0.5 * loan.getGuarantee());
					npfDv.addPrincipalNett(os - (jaminan > os ? 0 : jaminan));
					npfDv.addPrincipalGross(os);
					//
					npfTotal.addPrincipalNett(os - (jaminan > os ? 0 : jaminan));
					npfTotal.addPrincipalGross(os);
				}
			}
			productMap.put(loan.getProduct(), npfDv);
		}
		//
		result.addAll(productMap.values());
		result.add(npfTotal);
		//
		for (NPFDv npf : result) {
			npf.calculate();
		}
		//
		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		DateTime today = new DateTime();
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
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
		setParameter("Loan.company", getCompanyName());
		setParameter("Loan.branch", branchName);
		setParameter("Loan.date", strDate);
	}

}
