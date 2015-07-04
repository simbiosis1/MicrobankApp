package org.simbiosis.api.report;

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
import org.simbiosis.microbank.model.LoanRpt;
import org.simbiosis.system.UserDto;

@WebServlet("/listLoanBilling")
public class ListLoanBilling extends WebApiReportServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2723067200726443049L;

	public ListLoanBilling() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String key = request.getParameter("key");
		String data = request.getParameter("data");
		String date = request.getParameter("date");
		String all = request.getParameter("all");
		response.getWriter().println(processData(key, data, date, all));
	}

	String processData(String key, String data, String strDate, String all) {
		String result = "";
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			String[] datas = data.split(";");
			Long branch = Long.parseLong(datas[0]);
			Long ao = Long.parseLong(datas[1]);
			DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
			Date date = dtf.parseDateTime(strDate).toDate();
			try {
				List<LoanRpt> loans = iLoanReport.listLoanBilling(user
						.getCompany(), branch, date, ao, all == null ? false
						: all.equalsIgnoreCase("1"));
				mapper.writeValue(sw, loans);
				result = sw.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
