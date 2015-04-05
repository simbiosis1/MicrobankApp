package org.simbiosis.api.deposit;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.microbank.DepositDto;

@WebServlet("/saveDepositDate")
public class SaveDepositDate extends WebApiCoreServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7090417586384928109L;

	public SaveDepositDate() {
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
		try {
			ObjectMapper mapper = new ObjectMapper();
			DepositDto dataDto = mapper.readValue(data, DepositDto.class);
			iDepositBp.saveDeposit(key, dataDto);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
