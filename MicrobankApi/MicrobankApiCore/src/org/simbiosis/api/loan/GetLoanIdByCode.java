package org.simbiosis.api.loan;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.system.UserDto;

/**
 * Servlet implementation class Loan
 */
@WebServlet("/getLoanIdByCode")
public class GetLoanIdByCode extends WebApiCoreServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetLoanIdByCode() {
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
		UserDto user = iSystem.getUserFromSession(key);
		Long id = iLoanBp.getLoanIdByCode(user.getCompany(), data, 1);
		return id == null ? "0" : id.toString();
	}
}
