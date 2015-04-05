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

@WebServlet("/saveDeposit")
public class SaveDeposit extends WebApiCoreServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveDeposit() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String key = request.getParameter("key");
		String data = request.getParameter("data");
		response.getWriter().println(processData(key, data));
	}

	String processData(String key, String data) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			DepositDto deposit = mapper.readValue(data, DepositDto.class);
			Long id = iDepositBp.saveDeposit(key, deposit);
			return id == null ? "0" : id.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "0";
	}
}
