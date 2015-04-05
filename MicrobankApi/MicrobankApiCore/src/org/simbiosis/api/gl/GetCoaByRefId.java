package org.simbiosis.api.gl;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.gl.model.Coa;
import org.simbiosis.system.UserDto;

@WebServlet("/getCoaByRefId")
public class GetCoaByRefId extends WebApiCoreServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6153491128807617945L;

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
			long refId = Long.parseLong(data);
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			try {
				Coa coa = iCoa.getByRef(user.getCompany(), refId);
				mapper.writeValue(sw, coa);
				result = sw.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
