package org.simbiosis.api.saving;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.microbank.SavingTransactionDto;

@WebServlet("/saveSavingJournalTrans")
public class SaveSavingJournalTrans extends WebApiCoreServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6317010436858184663L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveSavingJournalTrans() {
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
		String coa = request.getParameter("id");
		response.getWriter().println(processData(key, data, coa));
	}

	String processData(String key, String data, String coa) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			SavingTransactionDto transDto = mapper.readValue(data,
					SavingTransactionDto.class);
			iSavingBp
					.saveSavingJournalTrans(key, transDto, Long.parseLong(coa));
			return "OK";
		} catch (IOException e) {
			System.out.println("Data " + data);
			e.printStackTrace();
			return "ERROR";
		}
	}
}
