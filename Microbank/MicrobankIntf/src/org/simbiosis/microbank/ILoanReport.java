package org.simbiosis.microbank;

import java.util.Date;
import java.util.List;

import org.simbiosis.microbank.model.LoanRpt;

public interface ILoanReport {

	void deleteDailyLoan(long company, Date pos);

	void createDailyLoan(LoanRpt loan);

	void createMonthlyLoan(long id, int month, int year);

	List<LoanRpt> listDailyLoan(long company, long branch, Date date);

	List<LoanRpt> listDailyLoanBySchema(long company, Date date, int schema);

	//

	LoanRpt getDailyLoan(long loanId, Date pos);

	LoanRpt getDailyLoanByCode(long company, String code, Date pos);

	List<LoanRpt> listLoanBilling(long company, long branch, Date pos, long ao);

	List<LoanRpt> listDailyLoanByAo(long company, Date pos, long ao, int quality);

	List<LoanRpt> listDailyLoanByProduct(long company, long branch, Date date,
			long product, int quality);

	List<LoanRpt> listDailyLoanByQuality(long company, long branch, Date pos,
			int quality);

	//

	int getCustomerQuality(long customer, Date pos);

	void setCustomerQuality(long customer, Date pos, int quality);
}
