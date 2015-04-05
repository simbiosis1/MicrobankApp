package org.simbiosis.api.revenue;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.simbiosis.api.lib.WebApiCoreServlet;

@WebServlet("/saveDepositRevSharingStatus")
public class SaveDepositRevSharingStatus extends WebApiCoreServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveDepositRevSharingStatus() {
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
		String[] datas = data.split(";");
		long id = Long.parseLong(datas[0]);
		int status = Integer.parseInt(datas[1]);
		iRevenueBp.saveDepositRevSharingStatus(id, status);
		return "OK";
	}

}
