package org.simbiosis.api.report.publicfinancial;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.api.lib.WebApiReportServlet;
import org.simbiosis.gl.model.PublicFinancialRpt;
import org.simbiosis.system.UserDto;

@WebServlet("/listPublicFinancialReport")
public class ListPublicFinancialReport extends WebApiReportServlet {

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
		String result = "";
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			String[] datas = data.split(";");
			Long branch = Long.parseLong(datas[0]);
			DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
			Date date = dtf.parseDateTime(datas[1]).toDate();
			Integer group = Integer.parseInt(datas[2]);
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			try {
				List<PublicFinancialRpt> dtos = iFinancialReport.listPublic(
						user.getCompany(), branch, date,group);
				mapper.writeValue(sw, dtos);
				result = sw.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
