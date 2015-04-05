package org.simbiosis.api.loan;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.simbiosis.api.lib.WebApiCoreServlet;

@WebServlet("/updatePaydSchedule")
public class UpdatePaydSchedule extends WebApiCoreServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 581813655338424849L;

	public UpdatePaydSchedule() {
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
		// Reset loan schedule
		iLoanBp.resetLoanSchedulePayd(key);
		// Update loan schedule
		System.out.println("Data : " + data);
		String[] strids = data.split(";");
		int i = 0;
		while (i < strids.length) {
			Long idLoan = Long.parseLong(strids[i++]);
			Long maxSched = Long.parseLong(strids[i++]);
			iLoanBp.payLoanScheduleBulk(idLoan, maxSched);
		}
		return result;
	}

}
