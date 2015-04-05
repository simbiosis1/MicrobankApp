package org.simbiosis.microbanking.reporting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.simbiosis.gl.model.FinancialRpt;
import org.simbiosis.microbanking.reporting.model.NeracaItemDv;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getLabaRugiHarianXls")
public class LabaRugiHarianXls extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/GlEar/GlReportEjb/FinancialReport")
	IFinancialReport iFinReport;

	String[] months = { "", "Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul",
			"Agu", "Sep", "Okt", "Nov", "Des" };
	String[] types = { "", "AKTIVA", "PASIVA", "PENDAPATAN OPS", "BEBAN OPS" };
	int[] factors = { 1, 1, 1, 1, -1 };
	long branch;
	Date date;

	public LabaRugiHarianXls() {
		super("LabaRugiHarianXls");
		setType(2);
	}

	List<NeracaItemDv> prepareData() {
		Map<Long, NeracaItemDv> ledgerMap = new HashMap<Long, NeracaItemDv>();
		Map<Long, NeracaItemDv> subLedgerMap = new HashMap<Long, NeracaItemDv>();
		List<NeracaItemDv> result = new ArrayList<NeracaItemDv>();
		List<FinancialRpt> listSaving = iFinReport.list(getCompany(), branch,
				date);
		for (FinancialRpt dto : listSaving) {
			if (dto.getGroup() == 3 || dto.getGroup() == 4) {
				NeracaItemDv ledger = ledgerMap.get(dto.getCoaGrandParent());
				if (ledger == null) {
					ledger = new NeracaItemDv();
					ledger.setGroup(dto.getGroup());
					ledger.setTipeNeraca(types[dto.getGroup()]);
					ledger.setFactor(factors[dto.getGroup()]);
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
				NeracaItemDv subLedger = subLedgerMap.get(dto.getCoaParent());
				if (subLedger == null) {
					subLedger = new NeracaItemDv();
					subLedger.setTipeNeraca(types[dto.getGroup()]);
					subLedger.setFactor(factors[dto.getGroup()]);
					subLedger.setLedger(dto.getCoaGrandParentCode() + " - "
							+ dto.getCoaGrandParentDescription());
					subLedger.setSubLedger(dto.getCoaParentCode() + " - "
							+ dto.getCoaParentDescription());
					subLedger.setSubSubLedger(dto.getCoaCode() + " - "
							+ dto.getCoaDescription());
					subLedger.setLedgerWoCode(dto
							.getCoaGrandParentDescription());
					subLedger.setSubLedgerWoCode(dto.getCoaParentDescription());
					subLedger.setSubSubLedgerWoCode(dto.getCoaDescription());
					subLedger.setValueNow(dto.getEndValue());
					ledger.getChildren().add(subLedger);
				}
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
		strDate = sdf.print(new DateTime(date));

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
