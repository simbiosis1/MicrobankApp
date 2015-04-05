package org.simbiosis.api.report;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.api.lib.WebApiReportServlet;
import org.simbiosis.microbank.DepositInformationDto;
import org.simbiosis.microbank.model.DepositRpt;
import org.simbiosis.microbank.model.DepositRptPk;

@WebServlet("/createDailyDeposit")
public class CreateDailyDeposit extends WebApiReportServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8475332789285061548L;

	public CreateDailyDeposit() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String date = request.getParameter("date");
		String data = request.getParameter("data");
		response.getWriter().println(processData(data, date));
	}

	String processData(String data, String strDate) {
		Long id = Long.parseLong(data);
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		Date date = dtf.parseDateTime(strDate).toDate();
		//
		// Get deposit information
		DepositInformationDto depInfo = iDeposit.getDepositInformation(id);
		// Combine it
		DepositRpt deposit = new DepositRpt();
		DepositRptPk idPk = new DepositRptPk();
		idPk.setPos(date);
		idPk.setRefId(depInfo.getId());
		deposit.setId(idPk);
		deposit.setType(1);
		deposit.setCode(depInfo.getCode());
		deposit.setName(depInfo.getName());
		deposit.setRegistration(depInfo.getRegistration());
		deposit.setBegin(depInfo.getBegin());
		deposit.setEnd(depInfo.getEnd());
		deposit.setValue(depInfo.getValue());
		deposit.setCompany(depInfo.getCompany());
		deposit.setBranch(depInfo.getBranch());
		deposit.setProduct(depInfo.getProduct());
		deposit.setProductCode(depInfo.getProductCode());
		deposit.setProductName(depInfo.getProductName());
		//
		iDepositReport.createDailyDeposit(deposit);
		return "";
	}
}
