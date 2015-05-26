package org.simbiosis.bp.micbank;

import java.util.Date;
import java.util.List;

import org.simbiosis.microbank.FindSavingDto;
import org.simbiosis.microbank.SavingBlockirDto;
import org.simbiosis.microbank.SavingDto;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.SavingPrintCodeRefDto;
import org.simbiosis.microbank.SavingProductDto;
import org.simbiosis.microbank.SavingTransInfoDto;
import org.simbiosis.microbank.SavingTransactionDto;

public interface ISavingBp {
	long saveSavingProduct(String key, SavingProductDto savingProductDto);

	SavingProductDto getSavingProduct(long id);

	List<SavingProductDto> listSavingProduct(String key);

	long saveSaving(String key, SavingDto savingDto);

	long saveSaving(SavingDto savingDto);

	SavingDto getSaving(long id);

	long getSavingIdByCode(String key, String code);

	List<SavingDto> listSaving(String key);

	List<SavingDto> findSaving(String key, FindSavingDto findSavingDto);

	long saveSavingTransaction(SavingTransactionDto dto);

	long saveTransaction(String key, SavingTransactionDto dto);

	long saveSavingTrfTrans(String key, SavingTransactionDto srcDto,
			SavingTransactionDto destDto);

	SavingTransactionDto saveSavingJournalTrans(String key,
			SavingTransactionDto savingTransactionDto, long coa);

	SavingTransactionDto saveSavingJournalRevenueTrans(String key,
			SavingTransactionDto savingTransactionDto, long otherBranch,
			long coa);

	SavingTransactionDto getSavingTransaction(long id);

	List<SavingTransactionDto> listTrans(String key, Date beginDate,
			Date endDate);

	List<SavingTransactionDto> listTransFrom(long id, Date beginDate);

	List<SavingTransactionDto> listTransUntil(long id, Date endDate);

	List<SavingTransactionDto> listTransRange(long id, Date beginDate,
			Date endDate);

	List<SavingTransactionDto> listTransForPrint(long id, Date date, int status);

	List<SavingTransactionDto> listSavingStatement(long id, Date begin, Date end);

	double getBallanceBefore(long id, Date date);

	double getBallanceBeforeNuc(long id);

	int getLastNuc(long id);

	void saveNUC(long id, int nuc);

	SavingInformationDto getInformation(long id);

	List<SavingTransInfoDto> listBallance(String key, Date date);

	double getBallance(long id, Date date, boolean includeDate);

	boolean isWithdrawable(long id, double withdrawal, Date date,
			boolean repayment);

	double getWithdrawalBallance(long id, Date date, boolean repayment);

	List<Long> listSavingId(String key, Date date);

	long isExistByRefId(long company, long branch, long refId);

	long isSavingTransExist(long company, long branch, long refId);

	void startCloseSaving(long id, String reason);

	void closeSaving(long id, Date closing);

	List<SavingBlockirDto> listBlockir(long id);

	void saveBlockir(SavingBlockirDto dto);

	void removeBlockir(long id);

	List<SavingPrintCodeRefDto> listPrintCode(String session);
}
