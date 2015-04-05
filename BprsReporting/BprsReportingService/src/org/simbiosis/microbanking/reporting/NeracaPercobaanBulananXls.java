package org.simbiosis.microbanking.reporting;

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
import org.simbiosis.gl.IFinancialReport;
import org.simbiosis.gl.ILedger;
import org.simbiosis.gl.model.FinancialRpt;
import org.simbiosis.microbanking.reporting.model.NeracaItemDv;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getNeracaPercobaanBulananXls")
public class NeracaPercobaanBulananXls extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/GlEar/GlReportEjb/FinancialReport")
	IFinancialReport iFinReport;

	@EJB(lookup = "java:global/GlEar/GlEjb/LedgerImpl")
	ILedger iLedger;

	String[] types = { "", "AKTIVA", "PASIVA", "PENDAPATAN OPS", "BEBAN OPS" };
	long branch;
	DateTime today = new DateTime();
	Date date;
	Date date2;

	public NeracaPercobaanBulananXls() {
		super("NeracaPercobaanBulananXls");
		setType(2);
	}

	List<NeracaItemDv> prepareData() {
		Map<Long, NeracaItemDv> ledgerMap = new HashMap<Long, NeracaItemDv>();
		List<NeracaItemDv> result = new ArrayList<NeracaItemDv>();
		List<FinancialRpt> listSaving = iFinReport.list(getCompany(), branch,
				date);
		for (FinancialRpt dto : listSaving) {
			NeracaItemDv ledger = ledgerMap.get(dto.getId().getCoa());
			if (ledger == null) {
				ledger = new NeracaItemDv();
				ledger.setLedger(dto.getCoaCode() + " - "
						+ dto.getCoaDescription());
				ledger.setLedgerWoCode(dto.getCoaDescription());
				ledger.setValueNow(dto.getEndValue());
				ledger.setDebet(dto.getDebit());
				ledger.setKredit(dto.getCredit());
				ledgerMap.put(dto.getId().getCoa(), ledger);
				result.add(ledger);
			} else {
				ledger.setValueBefore(ledger.getValueBefore()
						+ dto.getEndValue());
				ledger.setDebet(ledger.getDebet() - dto.getDebit());
				ledger.setKredit(ledger.getKredit() - dto.getCredit());
			}
		}
		DateTime lastDayLastMonth = today.dayOfMonth().withMinimumValue()
				.minusDays(1);
		date2 = lastDayLastMonth.toDate();
		//
		List<FinancialRpt> list = iFinReport.list(getCompany(), branch, date2);

		for (FinancialRpt dto : list) {
			NeracaItemDv ledger = ledgerMap.get(dto.getId().getCoa());
			if (ledger == null) {
				ledger = new NeracaItemDv();
				ledger.setLedger(dto.getCoaCode() + " - "
						+ dto.getCoaDescription());
				ledger.setLedgerWoCode(dto.getCoaDescription());
				ledger.setValueBefore(dto.getEndValue());
				ledgerMap.put(dto.getId().getCoa(), ledger);
				result.add(ledger);
			} else {
				ledger.setValueBefore(ledger.getValueBefore()
						+ dto.getEndValue());
			}

		}
		return result;

	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		String strDate = request.getParameter("date");
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
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
		setParameter("Neraca.company", getCompanyName());
		setParameter("Neraca.branch", branchName);
		setParameter("Neraca.period", strDate);
		setParameter("Neraca.user", getUserRealName());
	}

}
