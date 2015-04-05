package org.simbiosis.report.loan;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.simbiosis.bp.micbank.ILoanBp;
import org.simbiosis.microbank.LoanDto;
import org.simbiosis.microbank.LoanScheduleDto;
import org.simbiosis.printing.lib.ReportServlet;
import org.simbiosis.report.loan.model.TransactionDv;

@WebServlet("/getKartuAngsuranXls")
public class KartuAngsuranXls extends ReportServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -67342964331769927L;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/LoanBp")
	ILoanBp loanBp;

	long branch;
	Date date;
	long id;
	String code;

	public KartuAngsuranXls() {
		super("KartuAngsuranXls");
	}

	List<TransactionDv> prepareData() throws ParseException {
		List<TransactionDv> result = new ArrayList<TransactionDv>();
		LoanDto transDtos = loanBp.getLoan(id);
		int nr = 1;
		code = transDtos.getCode();
		for (LoanScheduleDto transDto : transDtos.getSchedules()) {
			TransactionDv transDv = new TransactionDv();
			transDv.setNr(nr++);
			transDv.setDate(transDto.getDate());
			transDv.setDebit(transDto.getPrincipal());
			transDv.setCredit(transDto.getMargin());
			transDv.setSubTotal(transDto.getTotal());
			transDv.setCode(transDtos.getCode());
			result.add(transDv);
		}
		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		//
		try {
			String strId = request.getParameter("id");
			if (strId == null) {
				id = 0L;
			} else {
				id = Long.parseLong(strId);
			}
			prepare();

			//
			setBeanCollection(prepareData());
			//
			setParameter("angsuran.code", code);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
