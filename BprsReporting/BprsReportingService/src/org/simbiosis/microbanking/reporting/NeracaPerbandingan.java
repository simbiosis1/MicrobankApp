package org.simbiosis.microbanking.reporting;

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
import org.simbiosis.gl.IFinancialReport;
import org.simbiosis.gl.model.FinancialRpt;
import org.simbiosis.microbanking.reporting.model.NeracaItemDv;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getNeracaPerbandingan")
public class NeracaPerbandingan extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/GlEar/GlReportEjb/FinancialReport")
	IFinancialReport iFinReport;

	String[] types = { "", "AKTIVA", "PASIVA", "PENDAPATAN OPS", "BEBAN OPS" };
	long branch;
	DateTime date = new DateTime();
	DateTime date2;

	public NeracaPerbandingan() {
		super("NeracaPerbandingan");
	}

	List<NeracaItemDv> prepareData() {
		Map<Long, NeracaItemDv> ledgerMap = new HashMap<Long, NeracaItemDv>();
		Map<Long, NeracaItemDv> subLedgerMap = new HashMap<Long, NeracaItemDv>();
		List<NeracaItemDv> result = new ArrayList<NeracaItemDv>();
		List<FinancialRpt> listSaving = iFinReport.list(getCompany(), branch,
				date.toDate());
		for (FinancialRpt dto : listSaving) {
			if (dto.getGroup() == 1 || dto.getGroup() == 2) {
				NeracaItemDv ledger = ledgerMap.get(dto.getCoaGrandParent());
				if (ledger == null) {
					ledger = new NeracaItemDv();
					ledger.setTipeNeraca(types[dto.getGroup()]);
					ledger.setLedger(dto.getCoaGrandParentCode() + " - "
							+ dto.getCoaGrandParentDescription());
					ledger.setLedgerWoCode(dto.getCoaGrandParentDescription());
					ledger.setValueNow(dto.getEndValue());
					result.add(ledger);
				} else {
					ledger.setValueNow(ledger.getValueNow() + dto.getEndValue());
				}
				ledgerMap.put(dto.getCoaGrandParent(), ledger);
				//
				NeracaItemDv subLedger = subLedgerMap.get(dto.getId().getCoa());
				subLedger = new NeracaItemDv();
				subLedger.setTipeNeraca(types[dto.getGroup()]);
				subLedger.setLedger(dto.getCoaGrandParentCode() + " - "
						+ dto.getCoaGrandParentDescription());
				subLedger.setSubLedger(dto.getCoaParentCode() + " - "
						+ dto.getCoaParentDescription());
				subLedger.setSubSubLedger(dto.getCoaCode() + " - "
						+ dto.getCoaDescription());
				subLedger.setLedgerWoCode(dto.getCoaGrandParentDescription());
				subLedger.setSubLedgerWoCode(dto.getCoaParentDescription());
				subLedger.setSubSubLedgerWoCode(dto.getCoaDescription());
				subLedger.setValueNow(dto.getEndValue());
				subLedgerMap.put(dto.getId().getCoa(), subLedger);
				ledger.getChildren().add(subLedger);
				ledgerMap.put(dto.getCoaGrandParent(), ledger);
			}
		}
		// akhir bulan kemarin
		date2 = date.dayOfMonth().withMinimumValue().minusDays(1);
		//
		List<FinancialRpt> listCompare = iFinReport.list(getCompany(), branch,
				date2.toDate());
		for (FinancialRpt dto : listCompare) {
			if (dto.getGroup() == 1 || dto.getGroup() == 2) {
				NeracaItemDv ledger = ledgerMap.get(dto.getCoaGrandParent());
				if (ledger == null) {
					ledger = new NeracaItemDv();
					ledger.setTipeNeraca(types[dto.getGroup()]);
					ledger.setLedger(dto.getCoaGrandParentCode() + " - "
							+ dto.getCoaGrandParentDescription());
					ledger.setLedgerWoCode(dto.getCoaGrandParentDescription());
					ledger.setValueBefore(dto.getEndValue());
					result.add(ledger);
				} else {
					ledger.setValueBefore(ledger.getValueBefore()
							+ dto.getEndValue());
				}
				ledgerMap.put(dto.getCoaGrandParent(), ledger);
				//
				NeracaItemDv subLedger = subLedgerMap.get(dto.getId().getCoa());
				if (subLedger == null) {
					subLedger = new NeracaItemDv();
					subLedger.setTipeNeraca(types[dto.getGroup()]);
					subLedger.setLedger(dto.getCoaGrandParentCode() + " - "
							+ dto.getCoaGrandParentDescription());
					subLedger.setSubLedger(dto.getCoaParentCode() + " - "
							+ dto.getCoaParentDescription());
					subLedger.setLedgerWoCode(dto
							.getCoaGrandParentDescription());
					subLedger.setSubLedgerWoCode(dto.getCoaParentDescription());
					subLedger.setValueBefore(dto.getEndValue());
					subLedger.setSubSubLedger(dto.getCoaCode() + " - "
							+ dto.getCoaDescription());
					subLedger.setSubSubLedgerWoCode(dto.getCoaDescription());
					ledger.getChildren().add(subLedger);
				} else {
					subLedger.setValueBefore(dto.getEndValue());
				}
				ledgerMap.put(dto.getCoaGrandParent(), ledger);
			}
		}

		Collections.sort(result, new Comparator<NeracaItemDv>() {

			@Override
			public int compare(NeracaItemDv o1, NeracaItemDv o2) {
				return o1.getLedger().compareToIgnoreCase(o2.getLedger());
			}
		});

		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
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

		prepare();
		//
		setBeanCollection(prepareData());
		//
		String strDate2 = dtf.print(date2);
		setParameter("Neraca.company", getCompanyName());
		setParameter("Neraca.branch", branchName);
		setParameter("Neraca.period", strDate);
		setParameter("Neraca.compare", strDate2);
		setParameter("Neraca.user", getUserRealName());
	}

}
