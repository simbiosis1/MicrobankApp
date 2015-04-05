package org.simbiosis.api.saving;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.microbank.SavingDto;
import org.simbiosis.system.UserDto;

@WebServlet("/saveSaving")
public class SaveSaving extends WebApiCoreServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveSaving() {
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
		String result = "";
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				SavingDto dto = mapper.readValue(data, SavingDto.class);
				dto.setCompany(user.getCompany());
				iSavingBp.saveSaving(dto);
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
