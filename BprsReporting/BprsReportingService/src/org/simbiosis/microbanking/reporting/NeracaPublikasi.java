package org.simbiosis.microbanking.reporting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.bp.gl.IPublicReport;
import org.simbiosis.bp.gl.model.PublicReportDto;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getNeracaPublikasi")
public class NeracaPublikasi extends ReportServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -67342964331769927L;

	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/PublicReportBp")
	IPublicReport publicReport;

	long branch;
	DateTime date = new DateTime();

	public NeracaPublikasi() {
		super("NeracaPublikasi");
		// setType(1);
	}

	List<PublicReportDto> prepareData(Date date, Long branch) {
		List<PublicReportDto> result = new ArrayList<PublicReportDto>();
		//
		List<PublicReportDto> activas = publicReport.listPublicReport(
				getSession(), branch, 1, 1, date);
		for (PublicReportDto activa : activas) {
			activa.setStrGroup("AKTIVA");
		}
		result.addAll(activas);
		//
		List<PublicReportDto> passivas = publicReport.listPublicReport(
				getSession(), branch, 1, 2, date);
		for (PublicReportDto passiva : passivas) {
			passiva.setStrGroup("PASIVA");
		}
		result.addAll(passivas);
		//
		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		String strDate = request.getParameter("date");
		if (strDate != null) {
			date = sdf.parseDateTime(strDate);
		}
		strDate = sdf.print(date);

		String strBranch = request.getParameter("branch");
		if (strBranch == null) {
			branch = getBranch();
		} else {
			branch = Long.parseLong(strBranch);
		}

		prepare();
		//
		setBeanCollection(prepareData(date.toDate(), branch));
		//
		setParameter("Publikasi.company", getCompanyName());
		setParameter("Publikasi.branch", getBranchName());
		setParameter("Publikasi.period", strDate);
	}
}
