package org.simbiosis.api.report;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.api.lib.WebApiReportServlet;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.model.SavingRpt;
import org.simbiosis.microbank.model.SavingRptPk;

/**
 * Servlet implementation class SavingNomId
 */
@WebServlet("/createDailySaving")
public class CreateDailySaving extends WebApiReportServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateDailySaving() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String strDate = request.getParameter("date");
		String data = request.getParameter("data");
		response.getWriter().println(processData(strDate, data));
	}

	String processData(String strDate, String data) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		Date date = dtf.parseDateTime(strDate).toDate();
		SavingInformationDto saving = iSaving.getInformation(Long
				.parseLong(data));
		double ballance = iSaving.getBallance(saving.getId(), date, true);
		//
		SavingRpt savingRpt = new SavingRpt();
		SavingRptPk idPk = new SavingRptPk();
		idPk.setPos(date);
		idPk.setRefId(saving.getId());
		savingRpt.setId(idPk);
		savingRpt.setType(1);
		savingRpt.setCode(saving.getCode());
		savingRpt.setSchema(saving.getSchema());
		savingRpt.setName(saving.getName());
		savingRpt.setCity(saving.getCity());
		savingRpt.setCompany(saving.getCompany());
		savingRpt.setBranch(saving.getBranch());
		savingRpt.setProduct(saving.getProduct());
		savingRpt.setProductCode(saving.getProductCode());
		savingRpt.setProductName(saving.getProductName());
		//
		savingRpt.setValPrev(0);
		savingRpt.setTransDebet(0);
		savingRpt.setTransCredit(ballance);
		savingRpt.setValAfter(ballance);
		//
		iSavingReport.createDailySaving(savingRpt);
		return "";
	}
}
