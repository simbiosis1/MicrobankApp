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
import org.simbiosis.bp.system.ISystemBp;
import org.simbiosis.gl.model.CoaGroup;
import org.simbiosis.gl.model.GlTransItem;
import org.simbiosis.microbanking.reporting.model.TransactionDv;
import org.simbiosis.printing.lib.ReportServlet;
import org.simbiosis.system.UserDto;

@WebServlet("/getGlTransViewAllPdf")
public class GlTransViewAllPdf extends ReportServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -67342964331769927L;

	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/GlBp")
	IGlBp glBp;

	@EJB(lookup = "java:global/SystemBpEar/SystemBpEjb/SystemBp")
	ISystemBp systemBp;

	long branch;
	long user;
	String name;

	public GlTransViewAllPdf() {
		super("GlTransViewAllPdf");
		setType(1);
	}

	List<TransactionDv> prepareData(Date beginDate, Date endDate) {
		List<TransactionDv> result = new ArrayList<TransactionDv>();
		List<GlTransItem> items = glBp.listGLTransByUser(getSession(),
				beginDate, endDate, user);
		int i = 0;
		String code = "";
		double saldo = 0;
		for (GlTransItem item : items) {
			TransactionDv dv = new TransactionDv();
			dv.setCode(item.getTransaction().getDate() + " "
					+ item.getTransaction().getCode() + " "
					+ item.getTransaction().getDescription());
			dv.setDescription(item.getCoa().getCode() + " - "
					+ item.getCoa().getDescription() + " : "
					+ item.getDescription());
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
			if (!dv.getCode().equalsIgnoreCase(code)) {
				i++;
				code = dv.getCode();
			}
			dv.setNr(i);
			result.add(dv);

		}
		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		DateTime beginDate = new DateTime();
		DateTime endDate = new DateTime();
		//
		String strBeginDate = request.getParameter("beginDate");
		if (strBeginDate != null) {
			beginDate = sdf.parseDateTime(strBeginDate);
		}
		strBeginDate = sdf.print(beginDate);
		String strEndDate = request.getParameter("endDate");
		if (strEndDate != null) {
			endDate = sdf.parseDateTime(strEndDate);
		}
		strEndDate = sdf.print(endDate);
		String strUser = request.getParameter("user");
		user = Long.parseLong(strUser);
		name = "SELURUHNYA";
		if (user != 0) {
			UserDto userDto = systemBp.getUser(user);
			name = userDto.getRealName();
		}
		String strBranch = request.getParameter("branch");
		branch = (strBranch == null) ? getBranch() : Long.parseLong(strBranch);
		//
		prepare();
		//
		setBeanCollection(prepareData(beginDate.toDate(), endDate.toDate()));
		//
		setParameter("GlTrans.company", getCompanyName());
		setParameter("GlTrans.branch", getBranchName());
		setParameter("GlTrans.date", strBeginDate + " - " + strEndDate);
		setParameter("GlTrans.user", name);
	}

}
