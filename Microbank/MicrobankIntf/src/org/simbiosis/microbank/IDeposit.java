package org.simbiosis.microbank;

import java.util.Date;
import java.util.List;

public interface IDeposit {
	// Deposit product
	long getCodeCounter(long company, String prefix);

	long saveDepositProduct(DepositProductDto depositProductDto);

	DepositProductDto getDepositProduct(long id);

	List<DepositProductDto> listDepositProduct(long company);

	long isDepositProductExistByRefId(long company, long refId);

	// Deposit account

	long saveDeposit(DepositDto savingDto);

	void closeDeposit(long id, Date closing);

	DepositDto getDeposit(long id);

	DepositDto getDepositByRefId(long company, long branch, long refId);

	long getDepositByCode(long company, String code);

	long isDepositExistByRefId(long company, long branch, long refId);

	List<Long> listDepositId(FindDepositDto findDepositDto, Date date);

	List<DepositDto> listDeposit(FindDepositDto findDepositDto);

	List<DepositDto> findDeposit(FindDepositDto findSavingDto);

	DepositInformationDto getDepositInformation(long id);

	// Deposit transaction

	String getMaxDepositTransCode(long company, long branch, String prefixCode);

	long saveDepositTransaction(DepositTransactionDto depositTransactionDto);

	DepositTransactionDto getDepositTransaction(long id);

	List<DepositTransactionDto> listDepositTransaction(long id, Date date);
}
