package org.simbiosis.api.report;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.api.lib.WebApiReportServlet;
import org.simbiosis.microbank.SavingTransInfoDto;
import org.simbiosis.microbank.model.SavingRpt;
import org.simbiosis.microbank.model.SavingRptPk;

/**
 * Servlet implementation class SavingNomId
 */
@WebServlet("/createSavingReport")
public class CreateSavingReport extends WebApiReportServlet {
	private static final long serialVersionUID = 1L;

	public CreateSavingReport() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("command");
		//
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			if (command.equalsIgnoreCase("listId")) {
				String strDate = request.getParameter("date");
				Date date = dtf.parseDateTime(strDate).toDate();
				List<SavingTransInfoDto> listTrans = iSavingReport
						.listSavingId(2, 0, date);
				mapper.writeValue(sw, listTrans);
				response.getWriter().println(sw.toString());
			} else if (command.equalsIgnoreCase("listIdTransacting")) {
				String strDate = request.getParameter("date");
				Date date = dtf.parseDateTime(strDate).toDate();
				List<Long> listId = iSavingReport.listSavingIdTransacting(date);
				mapper.writeValue(sw, listId);
				response.getWriter().println(sw.toString());
			} else if (command.equalsIgnoreCase("init")) {
				String[] periods = request.getParameter("period").split(";");
				ObjectMapper myMapper = new ObjectMapper();
				SavingTransInfoDto data = myMapper.readValue(
						request.getParameter("data"), SavingTransInfoDto.class);
				iSavingReport.createStartSaving(Integer.parseInt(periods[0]),
						Integer.parseInt(periods[1]), data);
				response.getWriter().println("OK");
			} else if (command.equalsIgnoreCase("createDaily")) {
				String strDate = request.getParameter("date");
				Date date = dtf.parseDateTime(strDate).toDate();
				ObjectMapper myMapper = new ObjectMapper();
				SavingTransInfoDto saving = myMapper.readValue(
						request.getParameter("data"), SavingTransInfoDto.class);
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
				savingRpt.setValPrev(0);
				savingRpt.setTransDebet(0);
				savingRpt.setTransCredit(saving.getValue());
				savingRpt.setValAfter(saving.getValue());
				//
				iSavingReport.createDailySaving(savingRpt);
				response.getWriter().println("OK");
			} else if (command.equalsIgnoreCase("updateDaily")) {
				String strDate = request.getParameter("date");
				Date date = dtf.parseDateTime(strDate).toDate();
				Long id = Long.parseLong(request.getParameter("id"));
				iSavingReport.updateDailySaving(id, date);
				response.getWriter().println("OK");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
