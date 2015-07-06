package org.simbiosis.report.loan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.bp.micbank.ILoanBp;
import org.simbiosis.microbank.LoanInformationDto;
import org.simbiosis.printing.lib.ReportServlet;
import org.simbiosis.report.loan.model.LoanTransDv;

public class LoanDroppingEngine {

	ILoanBp loanBp;
	String sessionName;

	DateTimeFormatter dtfSort = DateTimeFormat.forPattern("yyyyMMdd");
	DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");

	DateTime beginDate;
	DateTime endDate;
	long branch;
	String branchName;

	public LoanDroppingEngine(ILoanBp loanBp, String sessionName) {
		this.loanBp = loanBp;
		this.sessionName = sessionName;
	}

	public void prepareRequest(HttpServletRequest request, ReportServlet rs) {
		//
		String strBeginDate = request.getParameter("beginDate");
		beginDate = strBeginDate != null ? dtf.parseDateTime(strBeginDate)
				: new DateTime();
		String strEndDate = request.getParameter("endDate");
		endDate = strEndDate != null ? dtf.parseDateTime(strEndDate)
				: new DateTime();
		String strBranch = request.getParameter("branch");
		branch = strBranch != null ? Long.parseLong(strBranch) : 0L;
		branchName = rs.getBranchName(branch);

	}

	private LoanTransDv createDv(LoanInformationDto dto) {
		LoanTransDv dv = new LoanTransDv();
		dv.setDate(dto.getContractDate());
		dv.setCode(dto.getCode());
		dv.setAo(dto.getAoName());
		dv.setDescription(dto.getName());
		dv.setPrincipal(dto.getPrincipal());
		dv.setMargin(dto.getMargin());
		return dv;
	}

	public List<LoanTransDv> prepareData() {
		//
		List<LoanTransDv> result = new ArrayList<LoanTransDv>();
		List<LoanInformationDto> trans = loanBp.listDroppedLoan(sessionName,
				branch, beginDate.toDate(), endDate.toDate());
		// Sort
		Collections.sort(trans, new Comparator<LoanInformationDto>() {

			@Override
			public int compare(LoanInformationDto o1, LoanInformationDto o2) {
				String c1 = o1.getAoName()
						+ dtfSort.print(new DateTime(o1.getContractDate()));
				String c2 = o2.getAoName()
						+ dtfSort.print(new DateTime(o2.getContractDate()));
				return c1.compareTo(c2);
			}
		});
		// Pindah
		for (LoanInformationDto tran : trans) {
			if (!tran.getCode().substring(tran.getCode().length()-2).equalsIgnoreCase("A1")
					&& !tran.getCode().substring(tran.getCode().length()-2).equalsIgnoreCase("A2"))
				result.add(createDv(tran));
		}
		return result;
	}

	public String getStrBeginDate() {
		return dtf.print(beginDate);
	}

	public String getStrEndDate() {
		return dtf.print(endDate);
	}

	public String getBranchName() {
		return branchName;
	}
}
