package org.simbiosis.api.saving;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.microbank.SavingTransactionDto;
import org.simbiosis.system.UserDto;

@WebServlet("/saveSavingTrans")
public class SaveSavingTrans extends WebApiCoreServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7371553415147073967L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveSavingTrans() {
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
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				SavingTransactionDto trans = mapper.readValue(data,
						SavingTransactionDto.class);
				trans.setCompany(user.getCompany());
				iSavingBp.saveSavingTransaction(trans);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
}
