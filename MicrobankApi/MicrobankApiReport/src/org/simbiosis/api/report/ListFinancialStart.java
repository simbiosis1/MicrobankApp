package org.simbiosis.api.report;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.api.lib.WebApiReportServlet;
import org.simbiosis.gl.model.FinancialStartRpt;
import org.simbiosis.system.UserDto;

@WebServlet("/listFinancialStart")
public class ListFinancialStart extends WebApiReportServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6671419360202317712L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String key = request.getParameter("key");
		String data = request.getParameter("data");
		response.getWriter().println(processData(key, data));
	}

	String processData(String key, String data) {
		UserDto user = iSystem.getUserFromSession(key);
		String result = "";
		String[] datas = data.split(";");
		Long branch = Long.parseLong(datas[0]);
		int month = Integer.parseInt(datas[1]);
		int year = Integer.parseInt(datas[2]);
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			List<FinancialStartRpt> dtos = iFinancialReport.listStart(
					user.getCompany(), branch, month, year);
			mapper.writeValue(sw, dtos);
			result = sw.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
