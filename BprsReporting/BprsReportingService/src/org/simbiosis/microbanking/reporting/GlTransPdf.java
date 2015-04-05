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
import org.simbiosis.bp.gl.IGlBp;
import org.simbiosis.gl.model.Coa;
import org.simbiosis.gl.model.CoaGroup;
import org.simbiosis.gl.model.GlTransItem;
import org.simbiosis.microbanking.reporting.model.TransactionDv;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getGlTransPdf")
public class GlTransPdf extends ReportServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -67342964331769927L;

	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/GlBp")
	IGlBp glBp;

	long branch;

	public GlTransPdf() {
		super("GlTransPdf");
		setType(1);
	}

	List<TransactionDv> prepareData(long coa, Date beginDate, Date endDate) {
		List<TransactionDv> result = new ArrayList<TransactionDv>();
		//
		List<GlTransItem> items = glBp.listGLTransByCoa(getSession(), branch,
				beginDate, endDate, coa);
		//
		int i = 1;
		double saldo = 0;
		for (GlTransItem item : items) {
			TransactionDv dv = new TransactionDv();
			dv.setNr(i++);
			if (item.getTransaction() != null) {
				dv.setDate(item.getTransaction().getDate());
				dv.setCode(item.getTransaction().getCode());
			} else {
				dv.setDate(new DateTime(beginDate).minusDays(1).toDate());
				dv.setCode("");
			}
			dv.setDescription(item.getDescription());
			if (item.getDirection() == 1) {
				dv.setDebit(item.getValue());
				dv.setCredit(0D);
				saldo += item.getValue();
			} else {
				dv.setDebit(0D);
				dv.setCredit(item.getValue());
				saldo -= item.getValue();
			}
			CoaGroup cg = glBp.getCoaGroup(getSession(), item.getCoa()
					.getCode().substring(0, 1));
			dv.setSubTotal(saldo * cg.getFactor());
			result.add(dv);
		}
		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		DateTime beginDate = new DateTime();
		DateTime endDate = new DateTime();
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		//
		String strBeginDate = request.getParameter("beginDate");
		if (strBeginDate != null) {
			beginDate = dtf.parseDateTime(strBeginDate);
		}
		strBeginDate = dtf.print(beginDate);

		String strEndDate = request.getParameter("endDate");
		if (strEndDate != null) {
			endDate = dtf.parseDateTime(strEndDate);
		}
		strEndDate = dtf.print(endDate);

		String strCoa = request.getParameter("coa");
		long coaId = Long.parseLong(strCoa);
		Coa coa = glBp.getCoa(coaId);
		strCoa = coa.getCode() + " - " + coa.getDescription();
		String strBranch = request.getParameter("branch");
		branch = (strBranch == null) ? 0 : Long.parseLong(strBranch);
		String branchName = getBranchName(branch);
		//
		prepare();
		//
		setBeanCollection(prepareData(coaId, beginDate.toDate(),
				endDate.toDate()));
		//
		setParameter("GlTrans.company", getCompanyName());
		setParameter("GlTrans.branch", branchName);
		setParameter("GlTrans.date", strBeginDate + " - " + strEndDate);
		setParameter("GlTrans.coa", strCoa);
	}

}
