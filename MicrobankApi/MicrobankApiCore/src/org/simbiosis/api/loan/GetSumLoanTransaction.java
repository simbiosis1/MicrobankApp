package org.simbiosis.api.loan;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.microbank.LoanTransactionDto;

@WebServlet("/getSumLoanTransaction")
public class GetSumLoanTransaction extends WebApiCoreServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetSumLoanTransaction() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String data = request.getParameter("data");
		response.getWriter().println(processData(data));
	}

	String processData(String data) {
		// Parameter
		String[] params = data.split(";");
		//
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
			Date date = dtf.parseDateTime(params[1]).toDate();
			LoanTransactionDto loan = iLoanBp.getSumLoanTransaction(
					Long.parseLong(params[0]), 2, date);
			// .getLoan(Long.parseLong(data));
			mapper.writeValue(sw, loan);
			result = sw.toString();
		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
		}
		return result;
	}
}
