package org.simbiosis.api.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.simbiosis.api.lib.WebApiReportServlet;
import org.simbiosis.gl.model.Coa;
import org.simbiosis.gl.model.FinancialRpt;
import org.simbiosis.system.UserDto;

/**
 * Servlet implementation class SavingNomId
 */
@WebServlet("/saveFinancialReport")
public class SaveFinancialReport extends WebApiReportServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveFinancialReport() {
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
				List<FinancialRpt> frs = mapper.readValue(data, TypeFactory
						.collectionType(ArrayList.class, FinancialRpt.class));
				//
				for (FinancialRpt val : frs) {
					val.getId().setCompany(company);
					Coa coa = iCoa.get(val.getId().getCoa());
					val.setCoaCode(coa.getCode());
					val.setCoaDescription(coa.getDescription());
					Coa parent = coa.getParent();
					if (parent != null) {
						val.setCoaParent(parent.getId());
						val.setCoaParentCode(parent.getCode());
						val.setCoaParentDescription(parent.getDescription());
						Coa grandParent = parent.getParent();
						if (grandParent != null) {
							val.setCoaGrandParent(grandParent.getId());
							val.setCoaGrandParentCode(grandParent.getCode());
							val.setCoaGrandParentDescription(grandParent
									.getDescription());
						}
					}
					iFinancialReport.save(val);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
