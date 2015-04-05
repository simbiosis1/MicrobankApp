package org.simbiosis.report.loan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.bp.micbank.ILoanBp;
import org.simbiosis.microbank.LoanTransactionDto;
import org.simbiosis.printing.lib.ReportServlet;
import org.simbiosis.report.loan.model.LoanTransDv;

@WebServlet("/getLoanRepayment")
public class LoanRepayment extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/LoanBp")
	ILoanBp loanBp;

	long branch;
	String name;
	String code;
	String savingCode;
	Double principal = 0D;
	Double tenor = 0D;

	public LoanRepayment() {
		super("LoanRepayment");
	}

	private LoanTransDv createDv(LoanTransactionDto dto) {
		LoanTransDv dv = new LoanTransDv();
		dv.setDate(dto.getDate());
		dv.setDescription(dto.getDescription());
		dv.setPrincipal(dto.getPrincipal());
		dv.setMargin(dto.getMargin());
		dv.setDiscount(dto.getDiscount());
		dv.setTotal(dto.getPrincipal() + dto.getMargin());
		return dv;
	}

	private List<LoanTransDv> prepareData(Long branch, Date beginDate,
			Date endDate) {
		//
		List<LoanTransDv> result = new ArrayList<LoanTransDv>();
		List<LoanTransactionDto> trans = loanBp.listTransactionByRange(
				getSession(), branch, 2, beginDate, endDate);
		for (LoanTransactionDto tran : trans) {
			result.add(createDv(tran));
		}
		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		//
		String strBeginDate = request.getParameter("beginDate");
		DateTime beginDate = strBeginDate != null ? dtf
				.parseDateTime(strBeginDate) : new DateTime();
		String strEndDate = request.getParameter("endDate");
		DateTime endDate = strEndDate != null ? dtf.parseDateTime(strEndDate)
				: new DateTime();
		String strBranch = request.getParameter("branch");
		Long branch = strBranch != null ? Long.parseLong(strBranch) : 0L;
		//
		prepare();
		//
		setBeanCollection(prepareData(branch, beginDate.toDate(),
				endDate.toDate()));
		//
		setParameter("LoanRepayment.company", getCompanyName());
		setParameter("LoanRepayment.branch", getBranchName(branch));
		setParameter("LoanRepayment.beginDate", strBeginDate);
		setParameter("LoanRepayment.endDate", strEndDate);
	}

}
