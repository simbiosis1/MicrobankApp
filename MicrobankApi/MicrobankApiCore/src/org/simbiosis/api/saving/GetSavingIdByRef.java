package org.simbiosis.api.saving;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.system.UserDto;

@WebServlet("/getSavingIdByRef")
public class GetSavingIdByRef extends WebApiCoreServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetSavingIdByRef() {
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
		String result = "0";
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			String[] datas = data.split(";");
			Long branch = Long.parseLong(datas[0]);
			Long refId = Long.parseLong(datas[1]);
			Long id = iSavingBp
					.isExistByRefId(user.getCompany(), branch, refId);
			result = id.toString();
		}
		return result;
	}
}
