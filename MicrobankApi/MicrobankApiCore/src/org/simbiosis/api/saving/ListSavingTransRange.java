package org.simbiosis.api.saving;

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
import org.simbiosis.microbank.SavingTransactionDto;

@WebServlet("/listSavingTransRange")
public class ListSavingTransRange extends WebApiCoreServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListSavingTransRange() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String data = request.getParameter("data");
		response.getWriter().println(processData(data));
	}

	String processData(String data) {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		//
		String[] datas = data.split(";");
		try {
			long id = Long.parseLong(datas[0]);
			DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
			Date beginDate = dtf.parseDateTime(datas[1]).toDate();
			Date endDate = dtf.parseDateTime(datas[2]).toDate();
			List<SavingTransactionDto> trans = iSavingBp.listTransRange(id,
					beginDate, endDate);
			mapper.writeValue(sw, trans);
			result = sw.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}
}
