package org.simbiosis.api.gl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.gl.model.Coa;
import org.simbiosis.gl.model.GlTrans;
import org.simbiosis.gl.model.GlTransItem;
import org.simbiosis.system.UserDto;

@WebServlet("/saveGlTrans")
public class SaveGlTrans extends WebApiCoreServlet {

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
			GlTrans val = mapper.readValue(data, GlTrans.class);
			val.setCompany(user.getCompany());
			val.setUser(user.getId());
			for (GlTransItem item : val.getItems()) {
				Coa coa = iCoa.get(item.getCoa().getId());
				item.setCoa(coa);
			}
			iLedger.saveGLTrans(val);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
