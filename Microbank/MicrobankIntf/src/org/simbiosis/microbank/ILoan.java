package org.simbiosis.microbank;

import java.util.Date;
import java.util.List;

public interface ILoan {
	// Loan product

	long saveLoanProduct(LoanProductDto LoanProductDto);

	LoanProductDto getLoanProduct(long id);

	List<LoanProductDto> listLoanProduct(long company);

	long isLoanProductExistByRefId(long company, long refId);

	// Loan account

	long getCodeCounter(long company, String prefix);

	long saveLoan(LoanDto LoanDto);

	long saveLoanReschedule(LoanDto LoanDto);

	LoanDto getLoan(long id);

	LoanDto getLoanByRefId(long company, long branch, long refId);

	long isLoanExistByRefId(long company, long branch, long refId);

	List<Long> listLoanId(FindLoanDto findLoanDto, Date date);

	List<LoanDto> listLoan(FindLoanDto findLoan);

	List<LoanDto> findLoan(FindLoanDto findLoan);

	void closeLoan(long id, Date closing);

	LoanInformationDto getLoanInformation(long id);

	void droppLoan(long id, int dropped);

	Date getLoanScheduleBegin(long id);

	Date getLoanEnd(long id);

	List<LoanDto> listLoanBySchema(long company, int schema);

	//

	List<LoanInformationDto> listDroppedLoan(long company, long branch,
			Date beginDate, Date endDate);

	List<LoanScheduleDto> listRepaymentByRange(long id, Date beginDate,
			Date endDate);

	LoanScheduleDto getNormalRepayment(long id);

	LoanScheduleDto getEarlyRepayment(long id);

	List<LoanScheduleDto> listLoanSchedule(long id);

	List<LoanScheduleDto> listLoanScheduleByRange(long id, Date beginDate,
			Date endDate);

	List<LoanScheduleDto> listAllLoanScheduleByRange(long id, Date beginDate,
			Date endDate);

	List<LoanScheduleDto> createFlatSchedule(double principal, double tenor,
			double rate, Date beginDate);

	List<LoanScheduleDto> createEffectiveSchedule(double principal,
			double tenor, double rate, Date beginDate);

	List<LoanScheduleDto> createAnuitasSchedule(double principal, double tenor,
			double rate, Date beginDate);

	List<LoanScheduleDto> listLoanBill(long company, Date endDate);

	void resetLoanSchedulePayd(long company);

	void setLoanSchedulePayd(long id, int payd);

	void payLoanScheduleBulk(long idLoan, long maxSched);

	// Loan Transaction

	String getMaxLoanTransCode(long company, long branch, String prefixCode);

	long saveLoanTransaction(LoanTransactionDto savingTransactionDto);

	LoanTransactionDto getLoanTransaction(long id);

	LoanTransactionDto getLoanTransByDateCode(long loanId, Date date,
			String code);

	LoanTransInfoDto getLoanTransInfo(long id, Date date);

	long getLoanIdByCode(long company, String code, int active);

	List<LoanTransactionDto> listLoanTransaction(long id);

	List<LoanTransactionDto> listLoanTransaction(long id, Date date);

	List<LoanTransactionDto> listAllLoanTransactionByRange(long company,
			long branch, int direction, Date beginDate, Date endDate);

	LoanTransactionDto getSumLoanTransaction(long id, int direction, Date date);

	List<LoanInformationDto> listSimpleLoanInfo(long company, long branch,
			Date date);

	List<LoanInformationDto> listLoanInfo(long company, long branch, Date date);

	List<LoanTransInfoDto> listLoanTransInfo(long company, long branch,
			Date date);

	List<LoanScheduleDto> listLoanRepaymentRange(long loan, Date dateBegin,
			Date dateEnd);

	List<LoanTransInfoDto> listSimpleLoanTransInfo(long company, long branch,
			Date date);

	List<LoanTransInfoDto> listLoanTransSumGroup(long company, long branch,
			int direction, Date beginDate, Date endDate);

	long getLoanTransIdByRef(long company, long branch, long refId);

	// Guarantee
	String getMaxGuaranteeCode(long company, long branch, String prefixCode);

	long saveGuarantee(GuaranteeDto guaranteeDto);

	GuaranteeDto getGuarantee(long id);

	GuaranteeDto getGuaranteeByRefId(long company, long refId);

	List<GuaranteeDto> findGuarantee(FindLoanDto findLoan);

	boolean isDepositAsGuarantee(long company, String code);

	long isGuaranteeExistByRefId(long company, long refId);

	List<GuaranteeDto> listLoanGuarantee(long loanId);

	List<GuaranteeDto> listGuaranteeByCode(long company, String code);

	//

	LoanScheduleDto getLastPayment(long id);

	//

	List<CustomerDto> listLoanCifNotRegistered(long company, long branch,
			Date date);

	LoanQualityDto getLoanQuality(long id, int schema, Date date,
			double paidPrinciple, double paidMargin);

	List<Long> listAoLoanId(FindLoanDto findLoanDto);

}
