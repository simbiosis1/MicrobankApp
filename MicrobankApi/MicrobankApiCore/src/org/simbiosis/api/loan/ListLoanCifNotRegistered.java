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
import org.simbiosis.microbank.CustomerDto;

@WebServlet("/listLoanCifNotRegistered")
public class ListLoanCifNotRegistered extends WebApiCoreServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1892019576529197134L;

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
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		Date date = dtf.parseDateTime(data).toDate();
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			List<CustomerDto> customers = iLoanBp.listLoanCifNotRegistered(key,
					date);
			mapper.writeValue(sw, customers);
			result = sw.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
