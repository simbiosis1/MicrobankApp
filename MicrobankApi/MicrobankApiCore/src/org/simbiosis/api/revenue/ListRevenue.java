package org.simbiosis.api.revenue;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
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
import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.microbank.RevenueDto;

@WebServlet("/listRevenue")
public class ListRevenue extends WebApiCoreServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListRevenue() {
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
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		//
		String[] datas = data.split(";");
		try {
			DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
			Date beginDate = dtf.parseDateTime(datas[0]).toDate();
			Date endDate = dtf.parseDateTime(datas[1]).toDate();
			List<RevenueDto> revenues = new ArrayList<RevenueDto>();
			// Ambil revenue product
			revenues.addAll(iRevenueBp.listRevenueByProduct(key, beginDate,
					endDate));
			// Ambil revenue tambahan dari coa
			List<RevenueDto> revenueAdd = iRevenueBp.listRevenueByCoa(key,
					beginDate, endDate);
			if (revenueAdd.size() > 0) {
				revenues.addAll(revenueAdd);
			}
			// Ambil revenue dari coa
			/*
			 * long coaId = 546; Double value = iLedger.getSumGlTrans(coaId, 2,
			 * beginDate, endDate); RevenueDto coaRevenue = new RevenueDto();
			 * coaRevenue.setCompany(2); coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("BAGI HASIL TABUNGAN MUDHARABAH");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 547; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("BAGI HASIL DEPOSITO MUDHARABAH");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 563; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("PENDAPATAN SEWA IJARAH");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 850; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("PENDAPATAN IJARAH - HAJI");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); //
			 * ======================================
			 * ============================== // coaId = 399; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("PENDAPATAN TAKSASI MULTIJASA");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 810; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("PENDAPATAN ADM MULTIJASA");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 404; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("PENDAPATAN ADM ISTISHNA LAINNYA");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 394; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId); coaRevenue
			 * .setProductName("PENDAPATAN ADM ISTISHNA PEMILIKAN KENDARAAN");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 386; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId); coaRevenue
			 * .setProductName("PENDAPATAN ADM ISTISHNA PEMILIKAN RUMAH");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 692; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("PENDAPATAN ADM ISTISHNA KONSUMSI");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 371; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("PENDAPATAN ADM ISTISHNA INVESTASI");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 691; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("PENDAPATAN ADM ISTISHNA MODAL KERJA");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 364; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("PENDAPATAN ADM SALAM");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 741; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("PENDAPATAN ADM MURABAHAH LAINNYA");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 784; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("PENDAPATAN TAKSASI MURABAHAH");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 738; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("PENDAPATAN ADM MBA PEMILIKAN KENDARAAN"
			 * ); coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 477; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("PENDAPATAN ADM QARDH MODAL KERJA");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 750; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("PENDAPATAN ADM QARDH INVESTASI");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 479; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("PENDAPATAN ADM QARDH KONSUMSI");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 484; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("PENDAPATAN ADM QARDH HAJI");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue); // coaId = 798; value =
			 * iLedger.getSumGlTrans(coaId, 2, beginDate, endDate); coaRevenue =
			 * new RevenueDto(); coaRevenue.setCompany(2);
			 * coaRevenue.setProduct(coaId);
			 * coaRevenue.setProductName("PENDAPATAN TRANSPORT TAKS QARDH");
			 * coaRevenue.setType(2); coaRevenue.setValue(value);
			 * revenues.add(coaRevenue);
			 */
			//
			mapper.writeValue(sw, revenues);
			result = sw.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
