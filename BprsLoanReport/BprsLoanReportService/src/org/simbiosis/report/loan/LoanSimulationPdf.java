package org.simbiosis.report.loan;

import java.io.IOException;
import java.text.ParseException;
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
import org.simbiosis.bp.micbank.ILoanBp;
import org.simbiosis.microbank.LoanScheduleDto;
import org.simbiosis.printing.lib.ReportServlet;
import org.simbiosis.report.loan.model.LoanTransDv;

@WebServlet("/getLoanSimulationPdf")
public class LoanSimulationPdf extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/LoanBp")
	ILoanBp loanBp;

	long branch;
	int type;
	Date date;
	Double principal = 0D;
	Double rate = 0D;
	Double tenor = 0D;

	public LoanSimulationPdf() {
		super("LoanSimulationPdf");
		setType(1);
	}

	private LoanTransDv createScheduleDv(LoanScheduleDto scheduleDto) {
		LoanTransDv scheduleDv = new LoanTransDv();
		scheduleDv.setPrincipal(scheduleDto.getPrincipal());
		scheduleDv.setMargin(scheduleDto.getMargin());
		scheduleDv.setTotal(scheduleDto.getTotal());
		return scheduleDv;
	}

	private List<LoanTransDv> prepareData() throws ParseException {
		//
		List<LoanTransDv> result = new ArrayList<LoanTransDv>();
		DateTime beginDate = new DateTime().plusMonths(1);
		List<LoanScheduleDto> loanScheduleDtos = new ArrayList<LoanScheduleDto>();
		switch (type) {
		case 1:
			loanScheduleDtos = loanBp.createFlatSchedule(principal, tenor,
					rate, beginDate.toDate());
			break;
		case 2:
			loanScheduleDtos = loanBp.createEffectiveSchedule(principal, tenor,
					rate, beginDate.toDate());
			break;
		case 3:
			loanScheduleDtos = loanBp.createAnuitasSchedule(principal, tenor,
					rate, beginDate.toDate());
			break;
		default:
			break;
		}

		Double outstanding = 0D;
		for (LoanScheduleDto scheduleDto : loanScheduleDtos) {
			outstanding += scheduleDto.getTotal();
		}
		for (LoanScheduleDto scheduleDto : loanScheduleDtos) {
			LoanTransDv scheduleDv = createScheduleDv(scheduleDto);
			outstanding -= (scheduleDv.getTotal());
			scheduleDv.setOutstanding(outstanding);
			result.add(scheduleDv);
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
		try {
			String strDate = request.getParameter("date");
			if (strDate == null) {
				date = today.toDate();
			} else {
				date = sdf.parseDateTime(strDate).toDate();
			}
			strDate = sdf.print(new DateTime(date));
			String strBranch = request.getParameter("branch");
			if (strBranch == null) {
				branch = getBranch();
			} else {
				branch = Long.parseLong(strBranch);
			}
			//
			String strPrincipal = request.getParameter("principal");
			if (strPrincipal != null) {
				principal = Double.parseDouble(strPrincipal);
			}
			//
			String strRate = request.getParameter("rate");
			if (strPrincipal != null) {
				rate = Double.parseDouble(strRate);
			}
			//
			String strTenor = request.getParameter("tenor");
			if (strTenor != null) {
				tenor = Double.parseDouble(strTenor);
			}
			//
			String strType = request.getParameter("type");
			if (strType != null) {
				type = Integer.parseInt(strType);
			}
			//
			String strName = request.getParameter("name");
			if (strName == null) {
				strName = "Tanpa nama";
			}
			//
			prepare();
			//
			setBeanCollection(prepareData());
			//
			setParameter("LoanSim.company", getCompanyName());
			setParameter("LoanSim.branch", getBranchName());
			setParameter("LoanSim.date", strDate);
			setParameter("LoanSim.realName", getUserRealName());
			setParameter("LoanSim.name", strName);
			setParameter("LoanSim.principal", principal);
			setParameter("LoanSim.rate", rate);
			setParameter("LoanSim.tenor", tenor);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
