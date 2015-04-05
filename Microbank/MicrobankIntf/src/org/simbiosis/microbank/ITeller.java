package org.simbiosis.microbank;

import java.util.Date;
import java.util.List;

public interface ITeller {
	//
	long saveTeller(TellerDto tellerDto);

	TellerDto getTeller(long id);

	TellerDto getTellerByUser(long userId);

	List<TellerDto> listTeller(long company, long branch, long subBranch);

	List<TellerDto> listTellerBySubBranch(long subBranch);

	//

	long getTransCodeCounter(long company, String prefix);
	
	long saveTellerTransaction(TellerTransactionDto tellerTransactionDto);

	TellerTransactionDto getTellerTransaction(long id);

	long isTellerTransactionExistByRefId(long company, long branch, long refId);

	void saveTellerTransactionRef(long id, long idRef);

	List<TellerTransactionDto> listTellerTransaction(long company, long branch, Date startDate, Date endDate);
	
	List<TellerTransactionDto> listTellerTransactionByTeller(long id, Date date);

	double getBallance(long id, Date date);

	//

	String getMaxVaultTransCode(long company, long branch, String prefixCode);

	long saveVaultTransaction(VaultTransactionDto transDto);

	VaultTransactionDto getVaultTransaction(long id);

	VaultTransactionDto getVaultInProcess(long company, long branch,
			long teller, Date date);
}
