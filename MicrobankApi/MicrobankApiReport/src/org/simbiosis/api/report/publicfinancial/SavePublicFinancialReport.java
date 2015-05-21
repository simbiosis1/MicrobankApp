package org.simbiosis.api.report.publicfinancial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.api.lib.WebApiReportServlet;
import org.simbiosis.bp.gl.model.PublicReportDto;
import org.simbiosis.gl.model.PublicFinancialRpt;
import org.simbiosis.gl.model.PublicFinancialRptPK;
import org.simbiosis.system.UserDto;

/**
 * Servlet implementation class SavingNomId
 */
@WebServlet("/savePublicFinancialReport")
public class SavePublicFinancialReport extends WebApiReportServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SavePublicFinancialReport() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String key = request.getParameter("key");
		String data = request.getParameter("data");
		//
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			long company = user.getCompany();
			ObjectMapper mapper = new ObjectMapper();
			try {
				//
				List<PublicReportDto> prs = mapper.readValue(
						data,
						mapper.getTypeFactory().constructCollectionType(
								ArrayList.class, PublicReportDto.class));
				//
				for (PublicReportDto pr : prs) {
					PublicFinancialRpt rpt = new PublicFinancialRpt();
					PublicFinancialRptPK id = new PublicFinancialRptPK(
							pr.getDate(), pr.getGroup(), pr.getCode(), company,
							pr.getBranch());
					rpt.setId(id);
					rpt.setDescription(pr.getDescription());
					rpt.setValue(pr.getValue());
					iFinancialReport.savePublic(rpt);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
