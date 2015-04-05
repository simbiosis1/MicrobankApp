package org.simbiosis.api.cif;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.microbank.CustomerDto;
import org.simbiosis.system.UserDto;

@WebServlet("/saveCif")
public class SaveCif extends WebApiCoreServlet {

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
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				CustomerDto customer = mapper
						.readValue(data, CustomerDto.class);
				customer.setCompany(user.getCompany());
				Long id = iCustomer.saveCustomer(customer);
				return id.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "0";
	}

}
