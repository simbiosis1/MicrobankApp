package org.simbiosis.api.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.api.lib.WebApiReportServlet;
import org.simbiosis.gl.model.Coa;
import org.simbiosis.gl.model.FinancialStartRpt;
import org.simbiosis.system.UserDto;

@WebServlet("/saveFinancialStart")
public class SaveFinancialStart extends WebApiReportServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7330225580206758588L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String key = request.getParameter("key");
		String data = request.getParameter("data");
		response.getWriter().println(processData(key, data));
	}

	String processData(String key, String data) {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		UserDto user = iSystem.getUserFromSession(key);
		try {
			List<FinancialStartRpt> vals = mapper.readValue(
					data,
					mapper.getTypeFactory().constructCollectionType(
							ArrayList.class, FinancialStartRpt.class));
			for (FinancialStartRpt val : vals) {
				val.setCompany(user.getCompany());
				Coa coa = iCoa.get(val.getCoa());
				val.setCoaCode(coa.getCode());
				val.setCoaDescription(coa.getDescription());
				iFinancialReport.saveStart(val);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
