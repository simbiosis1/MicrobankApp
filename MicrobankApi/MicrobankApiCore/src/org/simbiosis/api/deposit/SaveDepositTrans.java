package org.simbiosis.api.deposit;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.microbank.DepositTransactionDto;

@WebServlet("/saveDepositTrans")
public class SaveDepositTrans extends WebApiCoreServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveDepositTrans() {
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
			DepositTransactionDto deposit = mapper.readValue(data,
					DepositTransactionDto.class);
			if (deposit.getDirection() == 2) {
				iDepositBp
						.closeDeposit(deposit.getDeposit(), deposit.getDate());
			}
			Long id = iDepositBp.saveDepositTrans(key, deposit);
			return id == null ? "0" : id.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "0";
	}
}
