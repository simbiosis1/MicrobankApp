package org.simbiosis.api.cif;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.system.UserDto;

@WebServlet("/getCifIdByRef")
public class GetCifIdByRef extends WebApiCoreServlet {

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
			Long customerId = iCustomer.isCustomerExistByRefId(
					user.getCompany(), Long.parseLong(data));
			return customerId.toString();
		}
		return "0";
	}
}
