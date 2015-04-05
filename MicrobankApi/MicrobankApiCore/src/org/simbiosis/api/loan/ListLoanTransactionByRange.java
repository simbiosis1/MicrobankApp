package org.simbiosis.api.loan;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

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
import org.simbiosis.system.UserDto;

@WebServlet("/listLoanTransactionByRange")
public class ListLoanTransactionByRange extends WebApiCoreServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListLoanTransactionByRange() {
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
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			//
			String[] dates = data.split(";");
			try {
				int direction = Integer.parseInt(dates[0]);
				DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
				Date beginDate = dtf.parseDateTime(dates[1]).toDate();
				Date endDate = dtf.parseDateTime(dates[2]).toDate();
				List<LoanTransactionDto> trans = iLoanBp
						.listTransactionByRange(key, 0, direction, beginDate,
								endDate);
				mapper.writeValue(sw, trans);
				result = sw.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
