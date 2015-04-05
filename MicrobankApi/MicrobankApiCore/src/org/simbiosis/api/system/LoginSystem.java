package org.simbiosis.api.system;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.system.SessionDto;

@WebServlet("/loginSystem")
public class LoginSystem extends WebApiCoreServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5791222632734147478L;

	public LoginSystem() {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String data = request.getParameter("data");
		response.getWriter().println(processData(data));
	}

	String processData(String data) {
		String[] datas = data.split(";");
		SessionDto session = iSystem.login(datas[0], datas[1]);
		if (session != null) {
			return session.getName();
		}
		return "ERROR";
	}
}
