package org.simbiosis.bp.micbank;

import java.util.Date;
import java.util.List;

import org.simbiosis.bp.dto.ValidationDto;
import org.simbiosis.microbank.CustomerDto;
import org.simbiosis.microbank.FindLoanDto;
import org.simbiosis.microbank.GuaranteeDto;
import org.simbiosis.microbank.LoanDto;
import org.simbiosis.microbank.LoanInformationDto;
import org.simbiosis.microbank.LoanProductDto;
import org.simbiosis.microbank.LoanQualityDto;
import org.simbiosis.microbank.LoanScheduleDto;
import org.simbiosis.microbank.LoanTransInfoDto;
import org.simbiosis.microbank.LoanTransactionDto;

public interface ILoanBp {

	long saveLoanProduct(String key, LoanProductDto loanProductDto);

	LoanProductDto getLoanProduct(long id);

	List<LoanProductDto> listLoanProduct(String key);

	long saveLoan(String key, LoanDto loanDto);

	long saveLoanReschedule(String key, LoanDto loanDto);

	LoanDto getLoan(long id);

	long getLoanIdByCode(long company, String code, int active);

	List<LoanDto> listLoan(String key, FindLoanDto findLoan);

	List<LoanDto> findLoan(String key, FindLoanDto findLoan);

	List<LoanScheduleDto> createFlatSchedule(double principal, double tenor,
			double rate, Date beginDate);

	List<LoanScheduleDto> createEffectiveSchedule(double principal,
			double tenor, double rate, Date beginDate);

	List<LoanScheduleDto> createAnuitasSchedule(double principal, double tenor,
			double rate, Date beginDate);

	LoanTransactionDto saveCompleteLoanTrans(String key,
			LoanTransactionDto loanTrans);

	void saveLoanJournal(LoanTransactionDto loanTrans,
			LoanInformationDto loanInfo);

	long saveLoanTrans(String key, LoanTransactionDto loanTrans);

	List<LoanTransactionDto> listLoanTransaction(long id);

	LoanTransactionDto getLoanTransByDateCode(long loanId, Date date, String code);

	List<LoanTransInfoDto> listLoanTransSumGroup(long company, long branch,
			int direction, Date beginDate, Date endDate);

	long isLoanExistByRefId(String key, long refId);

	//

	ValidationDto validateGuarantee(String key, GuaranteeDto dto);

	long saveGuarantee(String key, GuaranteeDto dto);

	GuaranteeDto getGuarantee(long id);

	List<GuaranteeDto> findGuarantee(String key, FindLoanDto findLoan);

	List<String> listGuaranteeType(String key);

	LoanInformationDto getInformation(long id);

	List<Long> listLoanId(String key, Date date);

	List<LoanScheduleDto> listLoanSchedule(long id);

	List<LoanScheduleDto> listRepaymentByRange(long id, Date beginDate,
			Date endDate);

	LoanScheduleDto getNormalRepayment(long id);

	LoanScheduleDto getEarlyRepayment(long id);

	List<LoanTransactionDto> listTransactionByRange(String key, long branch,
			int direction, Date beginDate, Date endDate);

	List<LoanScheduleDto> listLoanBill(String key, Date endDate);

	LoanTransactionDto getSumLoanTransaction(long parseLong, int i,
			Date strToDate);

	List<LoanScheduleDto> listLoanScheduleByRange(String key, Date beginDate,
			Date endDate);

	List<LoanInformationDto> listLoanInformation(String key, Date date);

	LoanQualityDto getLoanQuality(LoanTransInfoDto trans, Date date);

	long getLoanTransIdByRef(String key, long refId);

	List<LoanTransInfoDto> listSimpleLoanTransInfo(String key, Date endDate);

	List<LoanInformationDto> listSimpleLoanInfo(String key, Date endDate);

	List<CustomerDto> listLoanCifNotRegistered(String key, Date date);

	void resetLoanSchedulePayd(String key);

	void payLoanScheduleBulk(long idLoan, long maxSched);

	List<Long> listAoLoanId(String key, long branch);

	void closeLoan(long loanId);

	List<LoanInformationDto> listDroppedLoan(String key, long branch,
			Date beginDate, Date endDate);

	List<LoanDto> listLoanBySchema(String key, int scheme);
}
