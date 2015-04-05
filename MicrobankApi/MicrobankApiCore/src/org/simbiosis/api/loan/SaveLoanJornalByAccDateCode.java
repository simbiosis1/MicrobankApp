package org.simbiosis.api.loan;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.microbank.LoanInformationDto;
import org.simbiosis.microbank.LoanTransactionDto;
import org.simbiosis.system.UserDto;

/**
 * Servlet implementation class Loan
 */
@WebServlet("/saveLoanJournalByAccDateCode")
public class SaveLoanJornalByAccDateCode extends WebApiCoreServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveLoanJornalByAccDateCode() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String key = request.getParameter("key");
		String data = request.getParameter("data");
		response.getWriter().println(processData(key, data));
	}

	String processData(String key, String data) {
		String result = "";
		String datas[] = data.split(";");
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			String loanCode = datas[0];
			DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
			Date date = dtf.parseDateTime(datas[1]).toDate();
			String code = datas[2];
			long loanId = iLoanBp.getLoanIdByCode(user.getCompany(), loanCode,
					2);
			LoanTransactionDto transDto = iLoanBp.getLoanTransByDateCode(
					loanId, date, code);
			LoanInformationDto loanInfo = iLoanBp.getInformation(loanId);
			//
			LoanTransactionDto loanTrans = new LoanTransactionDto();
			loanTrans.setCompany(transDto.getCompany());
			loanTrans.setBranch(transDto.getBranch());
			loanTrans.setDate(transDto.getDate());
			loanTrans.setCode(transDto.getCode());
			loanTrans.setDescription(transDto.getDescription());
			loanTrans.setDirection(transDto.getDirection());
			loanTrans.setType(transDto.getType());
			loanTrans.setHasCode(true);
			loanTrans.setLoan(transDto.getLoan());
			loanTrans.setRefCode(transDto.getRefCode());
			loanTrans.setPrincipal(transDto.getPrincipal());
			loanTrans.setMargin(transDto.getMargin());
			loanTrans.setDiscount(transDto.getDiscount());
			loanTrans.setCustomer(transDto.getCustomer());
			//
			iLoanBp.saveLoanJournal(loanTrans, loanInfo);
		}
		return result;
	}
}
