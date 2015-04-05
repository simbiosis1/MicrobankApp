package org.simbiosis.dashboard.reporting;

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
import org.simbiosis.dashboard.reporting.model.TransactionDv;
import org.simbiosis.gl.model.CoaGroup;
import org.simbiosis.gl.model.GlTransItem;
import org.simbiosis.printing.lib.ReportServlet;
import org.simbiosis.system.UserDto;

@WebServlet("/getGlTransViewPdf")
public class GlTransViewPdf extends ReportServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -67342964331769927L;

	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/GlBp")
	IGlBp glBp;

	@EJB(lookup = "java:global/SystemBpEar/SystemBpEjb/SystemBp")
	ISystemBp systemBp;

	long branch;
	Date beginDate;
	Date endDate;
	long user;
	String name;

	public GlTransViewPdf() {
		super("GlTransViewPdf");
		setType(1);
	}

	List<TransactionDv> prepareData() {
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
					+ item.getDescription());
			dv.setDescription(item.getCoa().getCode());
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
		DateTime today = new DateTime();
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		//
		String strBeginDate = request.getParameter("beginDate");
		if (strBeginDate == null) {
			beginDate = today.toDate();
		} else {
			beginDate = sdf.parseDateTime(strBeginDate).toDate();
		}
		strBeginDate = sdf.print(new DateTime(beginDate));
		String strEndDate = request.getParameter("endDate");
		if (strEndDate == null) {
			endDate = today.toDate();
		} else {
			endDate = sdf.parseDateTime(strEndDate).toDate();
		}
		strEndDate = sdf.print(new DateTime(endDate));
		String strUser = request.getParameter("user");
		user = Long.parseLong(strUser);
		name = "SELURUHNYA";
		if (user != 0) {
			UserDto userDto = systemBp.getUser(user);
			name = userDto.getRealName();
		}

		String strBranch = request.getParameter("branch");
		if (strBranch == null) {
			branch = getBranch();
		} else {
			branch = Long.parseLong(strBranch);
		}
		prepare();
		//
		setBeanCollection(prepareData());
		//
		setParameter("GlTrans.company", getCompanyName());
		setParameter("GlTrans.branch", getBranchName());
		setParameter("GlTrans.date", strBeginDate + " - " + strEndDate);
		setParameter("GlTrans.user", name);
	}

}
