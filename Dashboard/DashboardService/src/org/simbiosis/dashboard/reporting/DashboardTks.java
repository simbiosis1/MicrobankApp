package org.simbiosis.dashboard.reporting;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.dashboard.reporting.model.DashboardTksDv;
import org.simbiosis.microbank.IDepositReport;
import org.simbiosis.microbank.ILoanReport;
import org.simbiosis.microbank.ISavingReport;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getDashboardTks")
public class DashboardTks extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/SavingReport")
	ISavingReport savingReport;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/DepositReport")
	IDepositReport coreBankingReport;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/LoanReport")
	ILoanReport loanReport;

	long branch;
	Date date;

	public DashboardTks() {
		super("DashboardTks");
	}

	String[] items = { "01. Asset", "02. % dari total", "03. Dana DPK",
			"04. Tab", "05. Nsb tab", "06. Dep", "07. Nsb dep", "08. Mbh",
			"09. Multi", "10. Mdh & Syirkah", "11. Gadai", "12. Tal haji",
			"13. Total", "14. Nsb pemb", "15. Kol lancar",
			"16. % total lancar", "17. Nsb lancar", "18. Kol kr lancar",
			"19. % total kr lancar", "20. Nsb kr lancar", "21. Kol diragukan",
			"22. % total diragukan", "23. Nsb diragukan", "24. Kol macet",
			"25. % total macet", "26. Nsb macet", "27. % tidak lancar",
			"28. Nsb tidak lancar", "40. CAR", "41. ROA", "41. BOPO",
			"42. Cash ratio", "43. FDR" };
	String[] cabangs = { "00-Pus", "01-Mjk", "02-Tgk", "03-Lam", "04-Lum",
			"9-Kon" };
	String[] months = { "2014-7", "2014-8", "2014-9" };

	List<DashboardTksDv> prepareData() throws ParseException {
		List<DashboardTksDv> result = new ArrayList<DashboardTksDv>();
		for (String item : items) {
			for (String cabang : cabangs) {
				for (String month : months) {
					DashboardTksDv dv = new DashboardTksDv();
					dv.setItem(item);
					dv.setCabang(cabang);
					dv.setMonth(month);
					dv.setItemValue(0D);
					result.add(dv);
				}
			}
		}
		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		Date today = new Date();
		//
		try {
			String strDate = request.getParameter("date");
			if (strDate == null) {
				date = today;
			} else {
				DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
				date = dtf.parseDateTime(strDate).toDate();
			}
			// String strBranch = request.getParameter("branch");
			String branchName = "KONSOLIDASI";
			// branch = (strBranch == null) ? 0 : Long.parseLong(strBranch);
			// if (branch != 0) {
			// branchName = getBranchName(branch);
			// }
			prepare();
			//
			setBeanCollection(prepareData());
			//
			setParameter("Lapkin.company", getCompanyName());
			setParameter("Lapkin.branch", branchName);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
