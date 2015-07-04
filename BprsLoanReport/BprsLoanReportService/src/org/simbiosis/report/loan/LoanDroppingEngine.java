package org.simbiosis.report.loan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.simbiosis.bp.micbank.ILoanBp;
import org.simbiosis.microbank.LoanInformationDto;
import org.simbiosis.report.loan.model.LoanTransDv;

public class LoanDroppingEngine {

	ILoanBp loanBp;

	String sessionName;

	public LoanDroppingEngine(ILoanBp loanBp, String sessionName) {
		this.loanBp = loanBp;
		this.sessionName = sessionName;
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

	public List<LoanTransDv> prepareData(Long branch, Date beginDate,
			Date endDate) {
		//
		List<LoanTransDv> result = new ArrayList<LoanTransDv>();
		List<LoanInformationDto> trans = loanBp.listDroppedLoan(sessionName,
				branch, beginDate, endDate);
		Collections.sort(trans, new Comparator<LoanInformationDto>() {

			@Override
			public int compare(LoanInformationDto o1, LoanInformationDto o2) {
				return o1.getAoName().compareTo(o2.getAoName());
			}
		});
		
		for (LoanInformationDto tran : trans) {
			result.add(createDv(tran));
		}
		return result;
	}

}
