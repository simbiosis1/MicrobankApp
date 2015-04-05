package org.simbiosis.api.saving;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.api.lib.WebApiCoreServlet;

@WebServlet("/getWithdrawalSavingBallance")
public class GetWithdrawalSavingBallance extends WebApiCoreServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetWithdrawalSavingBallance() {
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
		String result = "";
		String[] datas = data.split(";");
		try {
			long id = Long.parseLong(datas[0]);
			DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
			Date date = dtf.parseDateTime(datas[1]).toDate();
			double ballance = iSavingBp.getWithdrawalBallance(id, date, true);
			result = "" + ballance;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return result;
	}
}
