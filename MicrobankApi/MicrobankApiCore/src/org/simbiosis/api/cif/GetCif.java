package org.simbiosis.api.cif;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.microbank.CustomerDto;

@WebServlet("/getCif")
public class GetCif extends WebApiCoreServlet {

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
		String data = request.getParameter("data");
		response.getWriter().println(processData(data));
	}

	String processData(String data) {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			CustomerDto customer = iCustomer.getCustomer(Long.parseLong(data));
			mapper.writeValue(sw, customer);
			return sw.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
