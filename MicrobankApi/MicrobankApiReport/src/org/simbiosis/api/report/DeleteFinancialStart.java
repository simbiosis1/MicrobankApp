package org.simbiosis.api.report;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.simbiosis.api.lib.WebApiReportServlet;
import org.simbiosis.system.UserDto;

@WebServlet("/deleteFinancialStart")
public class DeleteFinancialStart extends WebApiReportServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7330225580206758588L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String key = request.getParameter("key");
		String data = request.getParameter("data");
		response.getWriter().println(processData(key, data));
	}

	String processData(String key, String data) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			iFinancialReport.deleteStart(user.getCompany(),
					Integer.parseInt(data), 1);
		}
		return "0";
	}
}
