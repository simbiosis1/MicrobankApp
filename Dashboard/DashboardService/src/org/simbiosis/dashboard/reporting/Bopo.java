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
import org.simbiosis.dashboard.reporting.model.NeracaItemDv;
import org.simbiosis.gl.IFinancialReport;
import org.simbiosis.gl.model.FinancialRpt;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getBopo")
public class Bopo extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/GlEar/GlReportEjb/FinancialReport")
	IFinancialReport report;

	String[] months = { "", "Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul",
			"Agu", "Sep", "Okt", "Nov", "Des" };
	String[] types = { "", "AKTIVA", "PASIVA", "PENDAPATAN OPS", "BEBAN OPS" };
	long branch;

	int month,year;
	
	public Bopo() {
		super("Bopo");
	}

	List<NeracaItemDv> prepareData(String date) {
		Map<Long, NeracaItemDv> ledgerMap = new HashMap<Long, NeracaItemDv>();
		Map<Long, NeracaItemDv> subLedgerMap = new HashMap<Long, NeracaItemDv>();
		List<NeracaItemDv> result = new ArrayList<NeracaItemDv>();
		List<FinancialRpt> listSaving = report.list(getCompany(), branch,new Date());
		for (FinancialRpt dto : listSaving) {
			if (dto.getGroup() == 3 || dto.getGroup() == 4) {
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
					subLedger.setLedgerWoCode(dto
							.getCoaGrandParentDescription());
					subLedger.setSubLedgerWoCode(dto.getCoaParentDescription());
					subLedger.setSubSubLedgerWoCode(dto.getCoaDescription());
					subLedger.setValueNow(dto.getEndValue());
					ledger.getChildren().add(subLedger);
				}
				ledgerMap.put(dto.getCoaGrandParent(), ledger);
			}
		}
		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		DateTimeFormatter mm = DateTimeFormat.forPattern("MM");
		DateTimeFormatter yyyy = DateTimeFormat.forPattern("yyyy");
		DateTime today = new DateTime();
		//
		String strMonth = request.getParameter("month");
		if (strMonth == null) {
			month = Integer.parseInt(mm.print(today));
		} else {
			month = Integer.parseInt(strMonth);
		}
		String strYear = request.getParameter("year");
		if (strYear == null) {
			year = Integer.parseInt(yyyy.print(today));
		} else {
			month = Integer.parseInt(strMonth);
		}
		String strBranch = request.getParameter("branch");
		if (strBranch == null) {
			branch = getBranch();
		} else {
			branch = Long.parseLong(strBranch);
		}

		prepare();
		//
		setBeanCollection(prepareData(""));
		//
		setParameter("Neraca.company", getCompanyName());
		setParameter("Neraca.address", getCompanyAddress());
		setParameter("Neraca.period", months[month] + " " + year);
	}

}
