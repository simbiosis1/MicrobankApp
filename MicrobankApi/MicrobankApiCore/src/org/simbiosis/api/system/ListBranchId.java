package org.simbiosis.api.system;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.simbiosis.api.lib.WebApiCoreServlet;
import org.simbiosis.system.BranchDto;

@WebServlet("/listBranchId")
public class ListBranchId extends WebApiCoreServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5791222632734147478L;

	public ListBranchId() {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String key = request.getParameter("key");
		// String data = request.getParameter("data");
		response.getWriter().println(processData(key));
	}

	String processData(String key) {
		List<BranchDto> branches = iSystem.listBranch(key);
		String result = "";
		for (BranchDto branch : branches) {
			if (!result.isEmpty()) {
				result += ";";
			}
			result += branch.getId();
		}
		return result;
	}
}
