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
import org.simbiosis.microbank.LoanDto;

@WebServlet("/listLoanByScheme")
public class ListLoanByScheme extends WebApiCoreServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListLoanByScheme() {
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
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		//
		try {
			int scheme = Integer.parseInt(data);
			// Cari semua daftar loan
			List<LoanDto> infos = iLoanBp.listLoanBySchema(key, scheme);
			//
			mapper.writeValue(sw, infos);
			result = sw.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
