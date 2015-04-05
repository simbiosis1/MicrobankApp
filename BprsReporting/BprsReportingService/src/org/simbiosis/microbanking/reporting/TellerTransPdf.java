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
import org.simbiosis.bp.micbank.ITellerBp;
import org.simbiosis.microbank.TellerTransactionDto;
import org.simbiosis.microbanking.reporting.model.TransactionDv;
import org.simbiosis.printing.lib.ReportServlet;
import org.simbiosis.system.UserDto;

@WebServlet("/getTellerTransPdf")
public class TellerTransPdf extends ReportServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -67342964331769927L;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/TellerBp")
	ITellerBp tellerBp;

	long branch;
	DateTime date = new DateTime();

	public TellerTransPdf() {
		super("TellerTransPdf");
		setType(1);
	}

	List<TransactionDv> prepareData(long teller, Date date) {
		List<TransactionDv> result = new ArrayList<TransactionDv>();
		List<TellerTransactionDto> transDtos = tellerBp.listTellerTransaction(
				teller, date);
		int nr = 1;
		double saldo = 0;
		for (TellerTransactionDto transDto : transDtos) {
			TransactionDv transDv = new TransactionDv();
			transDv.setNr(nr++);
			transDv.setCode(transDto.getCode());
			transDv.setDescription(transDto.getDescription());
			if (transDto.getDirection() == 1) {
				transDv.setDebit(transDto.getValue());
				transDv.setCredit(0D);
				saldo += transDto.getValue();
			} else {
				transDv.setDebit(0D);
				transDv.setCredit(transDto.getValue());
				saldo -= transDto.getValue();
			}
			transDv.setSubTotal(saldo);
			result.add(transDv);
		}
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

		String strTeller = request.getParameter("teller");

		prepare();
		//
		long tellerId = strTeller != null ? Long.parseLong(strTeller) : 0;
		setBeanCollection(prepareData(tellerId, date.toDate()));
		//
		UserDto tellerUser = tellerBp.getUserTeller(tellerId);
		//
		setParameter("TellerTrans.company", getCompanyName());
		setParameter("TellerTrans.branch", tellerUser.getBranchName());
		setParameter("TellerTrans.subBranch", tellerUser.getSubBranchName());
		setParameter("TellerTrans.date", strDate);
		setParameter("TellerTrans.realName", tellerUser.getRealName());
	}
}
