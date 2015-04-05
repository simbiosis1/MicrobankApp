package org.simbiosis.api.gl;

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
import org.simbiosis.system.UserDto;

@WebServlet("/listSumGlTransGroupRange")
public class ListSumGlTransGroupRange extends WebApiCoreServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListSumGlTransGroupRange() {
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
			DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
			try {
				String[] datas = data.split(";");
				long branch = Long.parseLong(datas[0]);
				Date beginDate = dtf.parseDateTime(datas[1]).toDate();
				Date endDate = dtf.parseDateTime(datas[2]).toDate();
				String code = datas[3];
				List<Object[]> objects = iLedger.listSumGlTransGroupRange(
						user.getCompany(), branch, beginDate, endDate, code);
				mapper.writeValue(sw, objects);
				result = sw.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}