package org.simbiosis.report.loan;

import java.io.IOException;
import java.text.ParseException;
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
import org.simbiosis.microbank.LoanScheduleDto;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.printing.lib.ReportServlet;
import org.simbiosis.report.loan.model.LoanTransDv;

@WebServlet("/getLoanPaymentExtPdf")
public class LoanPaymentExtPdf extends ReportServlet {
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
	Double tenor = 0D;

	public LoanPaymentExtPdf() {
		super("LoanPaymentExtPdf");
		setType(1);
	}

	private LoanTransDv createScheduleDv(LoanScheduleDto scheduleDto) {
		LoanTransDv scheduleDv = new LoanTransDv();
		scheduleDv.setPrincipal(scheduleDto.getPrincipal());
		scheduleDv.setMargin(scheduleDto.getMargin());
		scheduleDv.setTotal(scheduleDto.getTotal());
		return scheduleDv;
	}

	private List<LoanTransDv> prepareData(Long id) throws ParseException {
		//
		List<LoanTransDv> result = new ArrayList<LoanTransDv>();
		LoanInformationDto info = loanBp.getInformation(id);
		LoanDto dto = loanBp.getLoan(id);
		if (dto != null) {
			SavingInformationDto saving = savingBp.getInformation(dto
					.getSaving());
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
			for (LoanScheduleDto repayment : dto.getSchedules()) {
				LoanTransDv scheduleDv = createScheduleDv(repayment);
				scheduleDv.setDate(repayment.getDate());
				outstanding -= (noMargin ? scheduleDv.getPrincipal()
						: scheduleDv.getTotal());
				scheduleDv.setOutstanding(outstanding);
				result.add(scheduleDv);
			}
		}
		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		try {
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
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
