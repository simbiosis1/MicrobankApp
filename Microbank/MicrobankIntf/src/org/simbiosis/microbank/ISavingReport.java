package org.simbiosis.microbank;

import java.util.Date;
import java.util.List;

import org.simbiosis.microbank.model.SavingRpt;

public interface ISavingReport {
	List<SavingTransInfoDto> listSavingId(long company, long branch, Date date);

	List<Long> listSavingIdTransacting(Date date);

	void createStartSaving(int month, int year, SavingTransInfoDto saving);

	void createDailySaving(SavingRpt saving);

	void updateDailySaving(long id, Date date);

	void createMonthlySaving(long id, int month, int year);

	List<SavingRpt> listDailySaving(long company, long branch, Date date);

	List<SavingRpt> listDailySavingBySchema(long company, Date date, int schema);

	List<SavingRpt> listDailySavingAtRange(long company, Date beginDate,
			Date endDate);

	void deleteDailySaving(long company, Date pos);

	List<SavingRpt> listDailySavingByProduct(long company, long branch,
			Date date, long product);

}
