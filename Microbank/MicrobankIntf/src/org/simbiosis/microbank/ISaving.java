package org.simbiosis.microbank;

import java.util.Date;
import java.util.List;

public interface ISaving {
	// Saving product
	long saveProduct(SavingProductDto savingProductDto);

	SavingProductDto getProduct(long id);

	long isProductExistByRefId(long company, long refId);

	List<SavingProductDto> listProduct(long company);

	// Saving account

	long save(SavingDto savingDto);

	void close(long id, Date closing, String closingReason);

	SavingDto get(long id);

	SavingDto getByCode(long company, String code);

	SavingDto getByRefId(long company, long branch, long refId);

	long isExistByRefId(long company, long branch, long refId);

	long isExistByRefId(long company, long refId);

	long isSavingTransExist(long company, long branch, long refId);

	List<Long> listSavingId(FindSavingDto findSavingDto, Date date);

	List<SavingDto> list(FindSavingDto findSavingDto);

	List<SavingDto> find(FindSavingDto findSavingDto);

	List<SavingDto> listByCustomer(long company, long idCustomer);

	SavingInformationDto getInformation(long id);

	void startCloseSaving(long id, String reason);

	void closeSaving(long id, Date closing);

	// Saving transaction

	List<SavingPrintCodeRefDto> listPrintCode(long company);

	String getMaxTransCode(long company, long branch, String prefixCode);

	long saveTransaction(SavingTransactionDto savingTransactionDto);

	SavingTransactionDto getTransaction(long id);

	List<SavingTransactionDto> listTrans(long company, Date beginDate,
			Date endDate);

	List<SavingTransactionDto> listTransUntil(long id, Date date);

	List<SavingTransactionDto> listTransRange(long id, Date beginDate,
			Date endDate);

	List<SavingTransactionDto> listTransFrom(long id, Date beginDate);

	List<Long> listIdTransacting(FindSavingDto findSavingDto, Date date);

	List<SavingTransInfoDto> listBallance(long company, long branch, Date date);

	double getBallance(long id, Date date, boolean includeDate);

	double getWithdrawalBallance(long id, Date date, boolean repayment);

	// double getTotalTransDay(long id, int direction, Date date);

	List<SavingTransactionDto> listTransWithoutNuc(long id);

	List<Object[]> getTotalTransBeforeNuc(long id);

	// Cetak buku

	void saveNUC(long id, int nuc);

	int getLastNuc(long id);

	void setupAllNUCBefore(long id, Date date);

	// Code

	long getCodeCounter(long company, String prefix);

	// Blockir

	long saveSavingBlock(SavingBlockirDto block);

	void removeSavingBlock(long id);

	SavingBlockirDto getSavingBlock(long id);

	List<SavingBlockirDto> listSavingBlock(long id);

	double getSavingBlockValue(long id);

	double getSavingBlockValueExcept(long id, int type);

}
