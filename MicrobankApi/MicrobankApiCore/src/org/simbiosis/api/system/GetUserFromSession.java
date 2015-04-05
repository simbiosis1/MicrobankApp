package org.simbiosis.api.system;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.system.UserDto;

@WebServlet("/getUserFromSession")
public class GetUserFromSession extends WebApiCoreServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5791222632734147478L;

	public GetUserFromSession() {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String key = request.getParameter("key");
		// String data = request.getParameter("data");
		response.getWriter().println(processData(key));
	}

	String processData(String key) {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		UserDto user = iSystem.getUserFromSession(key);
		try {
			mapper.writeValue(sw, user);
			result = sw.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
