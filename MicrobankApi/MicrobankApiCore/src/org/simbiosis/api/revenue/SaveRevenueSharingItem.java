package org.simbiosis.api.revenue;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.microbank.RevenueSharingDto;

@WebServlet("/saveRevenueSharingItem")
public class SaveRevenueSharingItem extends WebApiCoreServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveRevenueSharingItem() {
		super();
	}

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
		try {
			ObjectMapper mapper = new ObjectMapper();
			RevenueSharingDto revenue = mapper.readValue(data,
					RevenueSharingDto.class);
			iRevenueBp.saveRevenueSharing(revenue);
			return "OK";
		} catch (IOException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
}
