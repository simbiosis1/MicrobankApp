package org.simbiosis.api.report;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.api.lib.WebApiReportServlet;
import org.simbiosis.microbank.GuaranteeDto;
import org.simbiosis.microbank.LoanInformationDto;
import org.simbiosis.microbank.LoanQualityDto;
import org.simbiosis.microbank.LoanTransInfoDto;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.model.LoanRpt;
import org.simbiosis.microbank.model.LoanRptPk;
import org.simbiosis.system.UserDto;

/**
 * Servlet implementation class SavingNomId
 */
@WebServlet("/createDailyLoan")
public class CreateDailyLoan extends WebApiReportServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateDailyLoan() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String strDate = request.getParameter("date");
		String data = request.getParameter("data");
		response.getWriter().println(processData(strDate, data));
	}

	private Double createPpap(LoanRpt info) {
		double ppap = 0.5 * info.getOsPrincipal() / 100;
		double osPpap = info.getOsPrincipal() - (0.5 * info.getGuarantee());
		if (osPpap < 0) {
			osPpap = 0;
		}
		switch (info.getQuality()) {
		case 2:
			ppap = 10 * osPpap / 100;
			break;
		case 3:
			ppap = 50 * osPpap / 100;
			break;
		case 4:
			ppap = osPpap;
			break;
		}
		if (ppap < 1)
			ppap = 0;
		return ppap;
	}

	private LoanRpt getLoanTransInfo(long id, Date date) {
		//
		//
		LoanInformationDto loanInfo = iLoan.getLoanInformation(id);
		LoanRpt loan = new LoanRpt();
		LoanRptPk idPk = new LoanRptPk();
		idPk.setRefId(loanInfo.getId());
		idPk.setPos(date);
		loan.setId(idPk);
		loan.setType(1);
		// Loan info
		loan.setCompany(loanInfo.getCompany());
		loan.setBranch(loanInfo.getBranch());
		loan.setCode(loanInfo.getCode());
		loan.setCustomer(loanInfo.getCustomer());
		loan.setName(loanInfo.getName());
		loan.setAddress(loanInfo.getAddress());
		loan.setCity(loanInfo.getCity());
		loan.setPostCode(loanInfo.getPostCode());
		loan.setPhone(loanInfo.getPhone());
		loan.setHandphone(loanInfo.getHandphone());
		loan.setSchema(loanInfo.getSchema());
		loan.setContract(loanInfo.getContract());
		loan.setContractDate(loanInfo.getContractDate());
		loan.setProduct(loanInfo.getProduct());
		loan.setProductName(loanInfo.getProductName());
		loan.setPrincipal(loanInfo.getPrincipal());
		loan.setMargin(loanInfo.getMargin());
		loan.setBegin(loanInfo.getBegin());
		loan.setEnd(loanInfo.getEnd() == null ? loanInfo.getBegin() : loanInfo
				.getEnd());
		loan.setFine(loanInfo.getFine());
		loan.setSaving(loanInfo.getSaving());
		loan.setAo(loanInfo.getAo());
		// AO
		if (loanInfo.getAo() == 0) {
			loan.setAoName("TANPA AO");
		} else {
			UserDto userDto = iSystem.getUser(loanInfo.getAo());
			loan.setAoName(userDto.getRealName());
		}
		// Loan trans
		LoanTransInfoDto transInfo = iLoan.getLoanTransInfo(id, date);
		if (transInfo == null) {
			loan.setPaidPrincipal(0);
			loan.setPaidMargin(0);
			loan.setPaidDiscount(0);
		} else {
			loan.setPaidPrincipal(transInfo.getPaidPrincipal());
			loan.setPaidMargin(transInfo.getPaidMargin());
			loan.setPaidDiscount(transInfo.getPaidDiscount());
		}
		loan.setPaidTotal(loan.getPaidPrincipal() + loan.getPaidMargin()
				+ loan.getPaidDiscount());
		//
		loan.setOsPrincipal(loan.getPrincipal() - loan.getPaidPrincipal());
		// Prinsip jual beli, multijasa, konvensional
		if (loan.getSchema() < 4 || loan.getSchema() == 8
				|| loan.getSchema() == 11) {
			loan.setTotal(loan.getPrincipal() + loan.getMargin());
			loan.setOsMargin(loan.getMargin() - loan.getPaidMargin()
					- loan.getPaidDiscount());
			loan.setOsTotal(loan.getOsPrincipal() + loan.getOsMargin());
		} else {
			loan.setTotal(loan.getPrincipal());
			loan.setOsMargin(0);
			loan.setOsTotal(loan.getOsPrincipal());
		}
		return loan;
	}

	String processData(String strDate, String data) {
		String[] collateralTypes = { "TANPA JAMINAN", "PERSONAL GUARANTEE",
				"DOKUMEN", "TABUNGAN", "DEPOSITO", "EMAS DAN LOGAM MULIA",
				"KENDARAAN BERMOTOR", "TANAH DAN BANGUNAN" };
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		Date date = dtf.parseDateTime(strDate).toDate();
		long id = Long.parseLong(data);
		LoanRpt loan = getLoanTransInfo(id, date);
		if (Math.abs(loan.getOsPrincipal()) <= 0.01
				&& Math.abs(loan.getOsMargin()) <= 0.01) {
			iLoanBp.closeLoan(id);
		} else {
			SavingInformationDto savingInfo = iSaving.getInformation(loan
					.getSaving());
			Double savingBallance = iSaving.getWithdrawalBallance(
					loan.getSaving(), date, true);
			// Loan quality
			LoanQualityDto quality = iLoan.getLoanQuality(id, loan.getSchema(),
					date, loan.getPaidPrincipal(),
					loan.getPaidMargin() + loan.getPaidDiscount());
			loan.setQuality(quality.getValue());
			// Satu debitur satu kualitas, cari kualitas yang tertinggi
			// (terjelek)
			int cstQuality = iLoanReport.getCustomerQuality(loan.getCustomer(),
					date);
			if (cstQuality != 0) {
				if (loan.getQuality() > cstQuality) {
					iLoanReport.setCustomerQuality(loan.getCustomer(), date,
							loan.getQuality());
				} else {
					loan.setQuality(cstQuality);
				}
			}
			loan.setLastPaid(quality.getLastPaid());
			loan.setDueOs(quality.getDueOs());
			loan.setDueOsPrincipal(quality.getOsPrincipal());
			loan.setDueOsMargin(quality.getOsMargin());
			if (loan.getSchema() < 4 || loan.getSchema() == 8) {
				loan.setDueOsTotal(quality.getOsPrincipal()
						+ quality.getOsMargin());
			} else {
				loan.setDueOsTotal(quality.getOsPrincipal());
			}

			List<GuaranteeDto> collaterals = iLoan.listLoanGuarantee(loan
					.getId().getRefId());
			int collateralBondType = 0;
			if (collaterals.size() > 0) {
				double values = 0;
				String description = "";
				for (GuaranteeDto collateral : collaterals) {
					collateralBondType = collateral.getBondType();
					values += collateral.getAppraisalMarkValue();
					int type = collateral.getType() > 7 ? 0 : collateral
							.getType();
					description += description.isEmpty() ? collateralTypes[type]
							: (collateralTypes[type] + ", ");
				}
				loan.setGuarantee(values);
				GuaranteeDto collateral = collaterals.get(0);
				loan.setGuaranteeType(collateral.getType());
				loan.setGuaranteeDescription(description);
			}
			loan.setSavingCode(savingInfo.getCode());
			loan.setSavingBallance(savingBallance);
			//
			loan.setPpap(createPpap(loan));
			//
			iLoanReport.createDailyLoan(loan);
		}
		return "0";
	}
}
