package org.simbiosis.api.system;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.simbiosis.api.lib.WebApiCoreServlet;

@WebServlet("/logoutSystem")
public class LogoutSystem extends WebApiCoreServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1886152626100219638L;

	public LogoutSystem() {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String data = request.getParameter("data");
		response.getWriter().println(processData(data));
	}

	String processData(String data) {
		iSystem.logout(data);
		return "OK";
	}
}
