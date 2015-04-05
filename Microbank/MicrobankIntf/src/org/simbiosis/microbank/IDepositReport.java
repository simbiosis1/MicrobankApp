package org.simbiosis.microbank;

import java.util.Date;
import java.util.List;

import org.simbiosis.microbank.model.DepositRpt;

public interface IDepositReport {

	//
	List<Long> listDepositId(Date date);

	void createDailyDeposit(DepositRpt deposit);

	void createMonthlyDeposit(long id, int month, int year);

	List<DepositRpt> listDailyDeposit(long company, long branch, Date date);

	void deleteDailyDeposit(long company, Date pos);

	List<DepositRpt> listDailyDepositByProduct(long company, long branch,
			Date date, long product);

}
