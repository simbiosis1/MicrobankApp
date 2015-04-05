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

@WebServlet("/getLabaRugiPublikasi")
public class LabaRugiPublikasi extends ReportServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -67342964331769927L;

	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/PublicReportBp")
	IPublicReport publicReport;

	long branch;
	DateTime date = new DateTime();

	public LabaRugiPublikasi() {
		super("LabaRugiPublikasi");
		// setType(1);
	}

	List<PublicReportDto> prepareData(Date date, Long branch) {
		List<PublicReportDto> result = new ArrayList<PublicReportDto>();
		//
		List<PublicReportDto> revs = publicReport.listPublicReport(
				getSession(), branch, 1, 3, date);
		for (PublicReportDto rev : revs) {
			rev.setStrGroup("PENDAPATAN OPERASIONAL");
		}
		result.addAll(revs);
		//
		List<PublicReportDto> prof_shares = publicReport.listPublicReport(
				getSession(), branch, 1, 4, date);
		for (PublicReportDto prof_share : prof_shares) {
			prof_share.setStrGroup("BAGI HASIL");
		}
		result.addAll(prof_shares);
		//
		List<PublicReportDto> expenses = publicReport.listPublicReport(
				getSession(), branch, 1, 5, date);
		for (PublicReportDto expense : expenses) {
			expense.setStrGroup("BEBAN OPERASIONAL");
		}
		result.addAll(expenses);
		//
		List<PublicReportDto> no_revs = publicReport.listPublicReport(
				getSession(), branch, 1, 6, date);
		for (PublicReportDto no_rev : no_revs) {
			no_rev.setStrGroup("PENDAPATAN NON OPS");
		}
		result.addAll(no_revs);
		//
		List<PublicReportDto> no_expenses = publicReport.listPublicReport(
				getSession(), branch, 1, 7, date);
		for (PublicReportDto no_expense : no_expenses) {
			no_expense.setStrGroup("BEBAN NON OPS");
		}
		result.addAll(no_expenses);
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
