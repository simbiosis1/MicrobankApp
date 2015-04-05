package org.simbiosis.dashboard.reporting;

import java.io.IOException;
import java.text.DecimalFormat;
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
import org.simbiosis.bp.micbank.ILoanBp;
import org.simbiosis.dashboard.reporting.model.LoanDroppingDv;
import org.simbiosis.microbank.LoanTransInfoDto;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getLoanAngsuran")
public class LoanAngsuran extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/LoanBp")
	ILoanBp loanBp;

	long branch;
	Date date;
	String year;

	String[] endDates = { "", "31-01", "28-02", "31-03", "30-04", "31-05",
			"30-06", "31-07", "31-08", "30-09", "31-10", "30-11", "31-12" };
	String[] months = { "", "Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul",
			"Agu", "Sep", "Okt", "Nov", "Des" };

	DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");

	public LoanAngsuran() {
		super("LoanAngsuran");
	}

	private List<LoanDroppingDv> prepareData() {
		DecimalFormat df = new DecimalFormat("00");
		String strDate = dtf.print(new DateTime(date));
		int cicle = 4;
		//
		int month = Integer.parseInt(strDate.substring(3, 5));
		year = strDate.substring(6);
		if (month - cicle < 1) {
			month = 12 - (cicle - month);
			if (month < 12) {
				Integer iYear = Integer.parseInt(year);
				iYear--;
				year = iYear.toString();
			} else {
				month = 0;
			}
		} else {
			month -= cicle;
		}
		//
		List<LoanDroppingDv> result = new ArrayList<LoanDroppingDv>();
		for (int i = 1; i <= cicle; i++) {
			if (month + i > 12) {
				month = 1 - i;
				Integer iYear = Integer.parseInt(year);
				iYear++;
				year = iYear.toString();
			}
			Date beginDate = dtf.parseDateTime(
					"01-" + df.format(month + i) + "-" + year).toDate();
			Date endDate = dtf.parseDateTime(endDates[month + i] + "-" + year)
					.toDate();
			Map<String, LoanDroppingDv> loanMap = new HashMap<String, LoanDroppingDv>();
			List<LoanTransInfoDto> transList = loanBp.listLoanTransSumGroup(
					getCompany(), branch, 2, beginDate, endDate);
			for (LoanTransInfoDto trans : transList) {
				LoanDroppingDv dv = loanMap.get(trans.getProductName());
				dv = new LoanDroppingDv();
				dv.setMonth(df.format(i) + " - " + months[month + i] + " "
						+ year);
				dv.setProduct(trans.getProductName());
				dv.setAngsuran(trans.getPaidPrincipal() / 1000);
				dv.setMargin(trans.getPaidMargin() / 1000);
				dv.setCode(trans.getProductCode());
				dv.setDiscount(trans.getPaidDiscount() / 1000);
				result.add(dv);
				loanMap.put(trans.getProductName(), dv);
			}
			// List<LoanTransInfoDto> list = loanBp.listLoanTransSumGroup(
			// getCompany(), branch, 2, beginDate, endDate);
			// for (LoanTransInfoDto trans : list) {
			// LoanDroppingDv dv = loanMap.get(trans.getProductName());
			// if (dv == null) {
			// dv = new LoanDroppingDv();
			// dv.setMonth(df.format(i) + " - " + months[month + i] + " "
			// + year);
			// dv.setProduct(trans.getProductName());
			// dv.setAngsuran(trans.getPaydPrincipal() / 1000);
			// dv.setMargin(trans.getPaydMargin() / 1000);
			// dv.setCode(trans.getProductCode());
			// dv.setDiscount(trans.getPaydDiscount() / 1000);
			// dv.setDropping(trans.getPaydPrincipal() / 1000);
			// result.add(dv);
			// } else {
			// dv.setDropping(trans.getPaydPrincipal() / 1000);
			// }
			//
			// loanMap.put(trans.getProductName(), dv);
			// }

		}
		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		DateTime today = new DateTime();
		//
		String strDate = request.getParameter("date");
		if (strDate == null) {
			date = today.toDate();
		} else {
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
		setParameter("LoanDropping.company", getCompanyName());
		setParameter("LoanDropping.branch", branchName);
		setParameter("LoanDropping.period", year);
	}

}
