package org.simbiosis.api.saving;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.microbank.SavingProductDto;

@WebServlet("/listSavingProduct")
public class ListSavingProduct extends WebApiCoreServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListSavingProduct() {
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
		response.getWriter().println(processData(key));
	}

	String processData(String key) {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		//
		try {
			// Ambil revenue product
			List<SavingProductDto> list = iSavingBp.listSavingProduct(key);
			mapper.writeValue(sw, list);
			result = sw.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
