package org.simbiosis.api.loan;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.microbank.LoanDto;
import org.simbiosis.system.UserDto;

@WebServlet("/saveLoan")
public class SaveLoan extends WebApiCoreServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5126943662916223970L;

	public SaveLoan() {
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
		String result = "";
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				LoanDto dto = mapper.readValue(data, LoanDto.class);
				dto.setCompany(user.getCompany());
				iLoanBp.saveLoan(key, dto);
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		}
		return result;

	}
}
