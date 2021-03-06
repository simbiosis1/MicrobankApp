package org.simbiosis.api.deposit;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.simbiosis.api.lib.WebApiCoreServlet;

@WebServlet("/getDepositIdByRef")
public class GetDepositIdByRef extends WebApiCoreServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetDepositIdByRef() {
		super();
	}

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
		String[] datas = data.split(";");
		Long branch = Long.parseLong(datas[0]);
		Long refId = Long.parseLong(datas[1]);
		return "" + iDepositBp.isDepositExistByRefId(key, branch, refId);
	}
}
