package org.simbiosis.bp.micbank;

import java.util.Date;
import java.util.List;

import org.simbiosis.microbank.TellerDto;
import org.simbiosis.microbank.TellerTransactionDto;
import org.simbiosis.microbank.VaultTransactionDto;
import org.simbiosis.system.UserDto;

public interface ITellerBp {
	long saveTeller(String key, TellerDto tellerDto);

	List<TellerDto> listTellerByLevel(String key);

	List<TellerDto> listAllTeller(String key);

	List<TellerDto> listTeller(String key);

	TellerDto getTellerByUser(String key);

	//
	long saveTellerSavingTrans(String key, TellerTransactionDto transDto);

	long saveTellerCashTrans(String key, TellerTransactionDto transDto);

	TellerTransactionDto getTellerTransaction(long id);

	List<TellerTransactionDto> listTellerTransaction(long teller, Date date);

	List<TellerTransactionDto> listTellerTransactionByUser(String key, Date date);

	List<TellerTransactionDto> listTellerTransaction(String key,
			Date startDate, Date endDate);

	double getBallance(long id, Date date);

	//

	long saveVaultTrans(String key, VaultTransactionDto transDto);

	VaultTransactionDto getVaultTrans(long id);

	VaultTransactionDto getVaultInProcess(String key, long tellerId, Date date);

	VaultTransactionDto approveVaultTrans(String key,
			VaultTransactionDto transDto);

	//

	int getAuthorizedLevel(String key, String description, double value);

	boolean hasTellerApproval(String key);

	UserDto getUserTeller(long tellerId);

	//

	void saveGlVaultTellerTrans(String key, TellerTransactionDto transDto);

	void saveGlCashTellerTrans(String key, TellerTransactionDto transDto);

	void saveGlSavingTellerTrans(String key, TellerTransactionDto transDto);

}
