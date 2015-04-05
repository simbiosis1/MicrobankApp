package org.simbiosis.dashboard.reporting;

import java.io.IOException;
import java.text.ParseException;
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

@WebServlet("/getBopoHarian")
public class BopoHarian extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/GlEar/GlReportEjb/FinancialReport")
	IFinancialReport report;

	String[] months = { "", "Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul",
			"Agu", "Sep", "Okt", "Nov", "Des" };
	String[] types = { "", "AKTIVA", "PASIVA", "PENDAPATAN OPS", "BEBAN OPS" };
	long branch;
	Date date;

	public BopoHarian() {
		super("Bopo");
	}

	List<NeracaItemDv> prepareData() throws ParseException {
		Map<Long, NeracaItemDv> ledgerMap = new HashMap<Long, NeracaItemDv>();
		Map<Long, NeracaItemDv> subLedgerMap = new HashMap<Long, NeracaItemDv>();
		List<NeracaItemDv> result = new ArrayList<NeracaItemDv>();
		List<FinancialRpt> listSaving = report.list(getCompany(), branch, date);
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
					if (dto.getGroup() == 3) {
						ledger.setValueNowNo3(dto.getEndValue());
					} else if (dto.getGroup() == 4) {
						ledger.setValueNowNo4(dto.getEndValue());
					}
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
					if (dto.getGroup() == 3) {
						subLedger.setValueNowNo3(dto.getEndValue());
					} else if (dto.getGroup() == 4) {
						subLedger.setValueNowNo4(dto.getEndValue());
					}
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
		DateTime today = new DateTime();
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		//
		try {
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
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
