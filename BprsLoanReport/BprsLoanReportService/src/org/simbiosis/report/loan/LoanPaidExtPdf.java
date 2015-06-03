package org.simbiosis.report.loan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.simbiosis.bp.micbank.ILoanBp;
import org.simbiosis.bp.micbank.ISavingBp;
import org.simbiosis.microbank.LoanDto;
import org.simbiosis.microbank.LoanInformationDto;
import org.simbiosis.microbank.LoanTransactionDto;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.printing.lib.ReportServlet;
import org.simbiosis.report.loan.model.LoanTransDv;

@WebServlet("/getLoanPaidExtPdf")
public class LoanPaidExtPdf extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/LoanBp")
	ILoanBp loanBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/SavingBp")
	ISavingBp savingBp;

	long branch;
	String name;
	String code;
	String savingCode;
	Double principal = 0D;
	Integer tenor = 0;

	public LoanPaidExtPdf() {
		super("LoanPaidExtPdf");
		setType(1);
	}

	private LoanTransDv createScheduleDv(LoanTransactionDto dto) {
		LoanTransDv dv = new LoanTransDv();
		dv.setPrincipal(dto.getPrincipal());
		dv.setMargin(dto.getMargin());
		dv.setDiscount(dto.getDiscount());
		dv.setTotal(dto.getPrincipal() + dto.getMargin() + dv.getDiscount());
		return dv;
	}

	private List<LoanTransDv> prepareData(long id) {
		//
		List<LoanTransDv> result = new ArrayList<LoanTransDv>();
		LoanInformationDto info = loanBp.getInformation(id);
		LoanDto dto = loanBp.getLoan(id);
		if (dto != null) {
			SavingInformationDto saving = savingBp.getInformation(dto
					.getSaving());
			List<LoanTransactionDto> payments = loanBp.listLoanTransaction(dto
					.getId());
			//
			name = info.getName();
			code = info.getCode();
			savingCode = saving.getCode();
			principal = info.getPrincipal();
			tenor = dto.getTenor();
			//
			boolean noMargin = (info.getSchema() == 5 || info.getSchema() == 6 || info
					.getSchema() == 7);
			Double outstanding = noMargin ? info.getPrincipal() : info
					.getPrincipal() + info.getMargin();
			for (LoanTransactionDto payment : payments) {
				LoanTransDv dv = createScheduleDv(payment);
				dv.setDate(payment.getDate());
				outstanding -= (noMargin ? dv.getPrincipal() : dv.getTotal());
				dv.setOutstanding(outstanding);
				result.add(dv);
			}
		}
		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		String strId = request.getParameter("loan");
		long id = (strId == null) ? 0 : Long.parseLong(strId);
		//
		prepare();
		//
		setBeanCollection(prepareData(id));
		//
		setParameter("LoanSim.company", getCompanyName());
		setParameter("LoanSim.branch", getBranchName());
		setParameter("LoanSim.code", code);
		setParameter("LoanSim.savingCode", savingCode);
		setParameter("LoanSim.realName", getUserRealName());
		setParameter("LoanSim.name", name);
		setParameter("LoanSim.principal", principal);
		// setParameter("LoanSim.rate", rate);
		setParameter("LoanSim.tenor", tenor);
	}

}
