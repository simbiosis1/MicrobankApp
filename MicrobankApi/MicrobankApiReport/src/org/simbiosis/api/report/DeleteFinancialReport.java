package org.simbiosis.api.report;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.api.lib.WebApiReportServlet;
import org.simbiosis.system.UserDto;

@WebServlet("/deleteFinancialReport")
public class DeleteFinancialReport extends WebApiReportServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2723067200726443049L;

	public DeleteFinancialReport() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String key = request.getParameter("key");
		String data = request.getParameter("data");
		response.getWriter().println(processData(key, data));
	}

	String processData(String key, String data) {
		String result = "OK";
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
			Date date = dtf.parseDateTime(data).toDate();
			iFinancialReport.delete(user.getCompany(), date);
		}
		return result;
	}
}
