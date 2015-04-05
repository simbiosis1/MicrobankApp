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

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.dashboard.reporting.model.NeracaItemDv;
import org.simbiosis.gl.IFinancialReport;
import org.simbiosis.gl.ILedger;
import org.simbiosis.gl.model.FinancialRpt;
import org.simbiosis.gl.model.GlTransItem;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getNeracaPeriodik")
public class NeracaPeriodik extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/GlEar/GlReportEjb/FinancialReport")
	IFinancialReport report;

	@EJB(lookup = "java:global/GlEar/GlEjb/LedgerImpl")
	ILedger iLedger;

	String[] types = { "", "AKTIVA", "PASIVA", "PENDAPATAN OPS", "BEBAN OPS" };
	long branch;
	Date date;

	public NeracaPeriodik() {
		super("NeracaPeriodik");
	}

	List<NeracaItemDv> prepareData() {
		Map<Long, NeracaItemDv> ledgerMap = new HashMap<Long, NeracaItemDv>();
		Map<Long, NeracaItemDv> subLedgerMap = new HashMap<Long, NeracaItemDv>();
		List<NeracaItemDv> result = new ArrayList<NeracaItemDv>();
		List<FinancialRpt> listSaving = report.list(getCompany(), branch, date);
		for (FinancialRpt dto : listSaving) {
			Double valueD = 0D;
			Double valueK = 0D;
			for (GlTransItem glItem : iLedger.listGLTransByCoa(getCompany(),
					branch, date, date, dto.getId().getCoa())) {
				if (glItem.getDirection() == 1) {
					valueD += glItem.getValue();
				} else {
					valueK += glItem.getValue();
				}
			}
			NeracaItemDv ledger = ledgerMap.get(dto.getCoaGrandParent());
			if (ledger == null) {
				ledger = new NeracaItemDv();
				ledger.setTipeNeraca(types[dto.getGroup()]);
				ledger.setLedger(dto.getCoaGrandParentCode() + " - "
						+ dto.getCoaGrandParentDescription());
				ledger.setLedgerWoCode(dto.getCoaGrandParentDescription());
				ledger.setValueNow(dto.getEndValue());
				ledger.setDebet(valueD);
				ledger.setKredit(valueK);
				result.add(ledger);
			} else {
				ledger.setValueNow(ledger.getValueNow() + dto.getEndValue());
				ledger.setDebet(ledger.getDebet() + valueD);
				ledger.setKredit(ledger.getKredit() + valueK);
			}
			ledgerMap.put(dto.getCoaGrandParent(), ledger);
			//
			NeracaItemDv subLedger = subLedgerMap.get(dto.getCoaParent());
			if (subLedger == null) {
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
				subLedger.setDebet(valueD);
				subLedger.setKredit(valueK);
				ledger.getChildren().add(subLedger);
			}
			ledgerMap.put(dto.getCoaGrandParent(), ledger);
		}
		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		Date today = new Date();
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
	}

}
