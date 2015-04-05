package org.simbiosis.bp.micbank;

import java.util.Date;
import java.util.List;

import org.simbiosis.microbank.DepositDto;
import org.simbiosis.microbank.DepositInformationDto;
import org.simbiosis.microbank.DepositProductDto;
import org.simbiosis.microbank.DepositTransactionDto;
import org.simbiosis.microbank.FindDepositDto;

public interface IDepositBp {
	//
	long saveDepositProduct(String key, DepositProductDto depositProductDto);

	DepositProductDto getDepositProduct(long id);

	List<DepositProductDto> listDepositProduct(String key);

	//

	long saveDeposit(String key, DepositDto depositDto);

	DepositDto getDeposit(long id);

	long getDepositByCode(String key, String code);

	List<DepositDto> listDeposit(String key, FindDepositDto findDeposit);

	List<DepositDto> findDeposit(String key, FindDepositDto findDeposit);

	//

	boolean isDepositAsGuarantee(String key, long depId);

	long isDepositExistByRefId(String key, long branch, long refId);

	DepositTransactionDto saveDepositTrans(String key,
			DepositTransactionDto trans, long savingId);

	long saveDepositTrans(String key, DepositTransactionDto trans);

	void closeDeposit(long id, Date date);

	List<Long> listDepositId(String key, Date date);

	DepositInformationDto getDepositInformation(long id);
}
