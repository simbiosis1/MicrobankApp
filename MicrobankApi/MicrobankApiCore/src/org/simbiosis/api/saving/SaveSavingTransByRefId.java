package org.simbiosis.api.saving;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.SavingTransactionDto;

@WebServlet("/saveSavingTransByRefId")
public class SaveSavingTransByRefId extends WebApiCoreServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7371553415147073967L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveSavingTransByRefId() {
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
		String[] datas = data.split(";");
		String month = datas[0];
		Integer type = Integer.parseInt(datas[1]);
		Long refId = Long.parseLong(datas[2]);
		Double value = Double.parseDouble(datas[3]);
		String result = "";

		long savingId = iSavingBp.isExistByRefId(2, 3, refId);
		SavingInformationDto si = iSavingBp.getInformation(savingId);
		String strType = (type == 0) ? "TAB" : "DEP";
		String desc = "PB KOR BASIL " + strType + " " + month + "/2012 - "
				+ si.getCode() + "(" + si.getName() + ")";
		SavingTransactionDto trans = new SavingTransactionDto();
		trans.setCode("KB" + month + "12");
		trans.setCompany(2);
		trans.setBranch(3);
		trans.setDescription(desc);
		trans.setSaving(savingId);
		trans.setValue(value);
		trans.setDirection(1);
		trans.setHasCode(true);
		trans.setType(5);
		// trans.setDate(du.strToDate("28-09-2012"));
		// trans.setTimestamp(du.strToTs("28-09-2012 18:30:30"));
		long id = iSavingBp.saveSavingTransaction(trans);
		result = "OK;" + id;
		return result;
	}
}
