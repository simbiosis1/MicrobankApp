package org.simbiosis.api.loan;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.microbank.LoanScheduleDto;
import org.simbiosis.system.UserDto;

@WebServlet("/listLoanSchedule")
public class ListLoanSchedule extends WebApiCoreServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListLoanSchedule() {
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
			Long id = Long.parseLong(data);
			try {
				List<LoanScheduleDto> schedules = iLoanBp.listLoanSchedule(id);
				mapper.writeValue(sw, schedules);
				result = sw.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
