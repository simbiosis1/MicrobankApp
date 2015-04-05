package org.simbiosis.api.gl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.bp.gl.model.PublicReportDto;

@WebServlet("/listFinancialReportRef")
public class ListFinancialReportRef extends WebApiCoreServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6671419360202317712L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String key = request.getParameter("key");
		String data = request.getParameter("data");
		response.getWriter().println(processData(key, data));
	}

	String processData(String key, String data) {
		String result = "";
		String[] datas = data.split(";");
		int schema = Integer.parseInt(datas[0]);
		int group = Integer.parseInt(datas[1]);
		List<PublicReportDto> dtos = iPublicReport.listCombinedFinancialReportRef(key,
				schema, group);
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, dtos);
			result = sw.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
