package org.simbiosis.api.report.publicfinancial;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.api.lib.WebApiReportServlet;
import org.simbiosis.system.UserDto;

/**
 * Servlet implementation class SavingNomId
 */
@WebServlet("/deletePublicFinancialReport")
public class DeletePublicFinancialReport extends WebApiReportServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeletePublicFinancialReport() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String key = request.getParameter("key");
		String data = request.getParameter("data");
		//
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			long company = user.getCompany();
			DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
			Date date = dtf.parseDateTime(data).toDate();
			iFinancialReport.deletePublic(company, date);
		}
	}
}
