package org.simbiosis.api.report;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.api.lib.WebApiReportServlet;
import org.simbiosis.microbank.model.LoanRpt;
import org.simbiosis.system.UserDto;

@WebServlet("/getDailyLoanByCode")
public class GetDailyLoanByCode extends WebApiReportServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2723067200726443049L;

	public GetDailyLoanByCode() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String key = request.getParameter("key");
		String data = request.getParameter("data");
		String date = request.getParameter("date");
		response.getWriter().println(processData(key, data, date));
	}

	String processData(String key, String data, String strDate) {
		String result = "";
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
			Date date = dtf.parseDateTime(strDate).toDate();
			try {
				ObjectMapper mapper = new ObjectMapper();
				StringWriter sw = new StringWriter();
				LoanRpt loan = iLoanReport.getDailyLoanByCode(
						user.getCompany(), data, date);
				mapper.writeValue(sw, loan);
				result = sw.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
