package org.simbiosis.dashboard.reporting;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.dashboard.reporting.model.FinancialDv;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getFinancialHarian")
public class FinancialHarian extends ReportServlet {
	private static final long serialVersionUID = 1L;

	String[] types = { "", "AKTIVA", "PASIVA", "PENDAPATAN OPS", "BEBAN OPS" };
	long branch;
	Date date;

	public FinancialHarian() {
		super("Financial");
	}

	List<FinancialDv> prepareData() throws ParseException {
		List<FinancialDv> result = new ArrayList<FinancialDv>();
		for (int i = 1; i <= 20; i++) {
			FinancialDv dv = new FinancialDv();

			dv.setType(1);
			dv.setGroup(types[1]);
			dv.setOrder(i);
			dv.setNumber("" + i);
			dv.setDesc("akun " + i);
			dv.setCode(i + "00");
			dv.setValue(100D * i);
			if (i >= 10) {
				dv.setGroup(types[2]);
			}
			result.add(dv);
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
			setParameter("Neraca.company", getCompanyName());
			setParameter("Neraca.branch", branchName);
			setParameter("Neraca.period", strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
