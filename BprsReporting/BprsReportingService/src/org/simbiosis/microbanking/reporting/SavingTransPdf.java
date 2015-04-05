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
import org.simbiosis.bp.micbank.ICustomerBp;
import org.simbiosis.bp.micbank.ISavingBp;
import org.simbiosis.microbank.CustomerDto;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.SavingTransactionDto;
import org.simbiosis.microbanking.reporting.model.TransactionDv;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getSavingTransPdf")
public class SavingTransPdf extends ReportServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -67342964331769927L;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/SavingBp")
	ISavingBp savingBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/CustomerBp")
	ICustomerBp customerBp;

	long branch;
	Date begin;
	Date end;
	String account;
	String address;
	String city;

	public SavingTransPdf() {
		super("SavingTransPdf");
		setType(1);
	}

	List<TransactionDv> prepareData(long id, Date begin, Date end) {
		//
		SavingInformationDto si = savingBp.getInformation(id);
		long cid = si.getCustomer();
		CustomerDto c = customerBp.getCustomer(cid);
		account = si.getCode() + " - " + si.getName();
		address = c.getAddress();
		city = c.getCity() + " " + c.getPostCode();
		//
		List<TransactionDv> result = new ArrayList<TransactionDv>();
		List<SavingTransactionDto> transDtos = savingBp.listSavingStatement(id,
				begin, end);
		int nr = 1;
		double saldo = 0;
		for (SavingTransactionDto transDto : transDtos) {
			TransactionDv transDv = new TransactionDv();
			transDv.setNr(nr++);
			transDv.setDate(transDto.getDate());
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
		DateTime today = new DateTime();
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		//
		String strId = request.getParameter("id");
		String strBegin = request.getParameter("begin");
		String strEnd = request.getParameter("end");
		if (strBegin == null) {
			begin = today.toDate();
		} else {
			begin = sdf.parseDateTime(strBegin).toDate();
		}
		if (strBegin == null) {
			end = today.toDate();
		} else {
			end = sdf.parseDateTime(strEnd).toDate();
		}
		prepare();
		//
		setBeanCollection(prepareData(Long.parseLong(strId), begin, end));
		//

		setParameter("SavingTrans.company", getCompanyName());
		setParameter("SavingTrans.branch", getBranchName());
		setParameter("SavingTrans.account", account);
		setParameter("SavingTrans.address", address);
		setParameter("SavingTrans.city", city);
		setParameter("SavingTrans.date", sdf.print(today));
	}

}
