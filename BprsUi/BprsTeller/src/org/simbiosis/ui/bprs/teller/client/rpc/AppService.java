package org.simbiosis.ui.bprs.teller.client.rpc;

import java.util.Date;
import java.util.List;

import org.simbiosis.ui.bprs.common.shared.TransactionDv;
import org.simbiosis.ui.bprs.teller.shared.TellerDv;
import org.simbiosis.ui.bprs.teller.shared.UploadCollectiveDv;
import org.simbiosis.ui.bprs.teller.shared.VaultTransactionDv;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("teller")
public interface AppService extends RemoteService {

	TransactionDv saveSavingTrans(String key, TransactionDv transDv)
			throws IllegalArgumentException;

	TransactionDv saveCashTrans(String key, TransactionDv transDv)
			throws IllegalArgumentException;

	VaultTransactionDv saveVaultTrans(String key, VaultTransactionDv transDv)
			throws IllegalArgumentException;

	VaultTransactionDv approveVaultTrans(String key, VaultTransactionDv transDv)
			throws IllegalArgumentException;

	VaultTransactionDv getAvailableVaultTrans(String key,
			VaultTransactionDv transDv) throws IllegalArgumentException;

	List<TransactionDv> listTellerTransactionByTeller(String key,
			Long tellerId, Date date) throws IllegalArgumentException;

	List<TransactionDv> listSavingTransaction(String key, Long savingId,
			Date beginDate, Date endDate) throws IllegalArgumentException;

	List<TellerDv> listTeller(String key) throws IllegalArgumentException;

	Boolean hasTellerApproval(String key) throws IllegalArgumentException;

	List<UploadCollectiveDv> listConfirmSavingCollective(String key, Date date, String srcData)
			throws IllegalArgumentException;

	void uploadSavingCollective(String key, Date date, Long teller, List<UploadCollectiveDv> data)
			throws IllegalArgumentException;
	
	void executeUpdate(String key, Date date, Long teller, List<UploadCollectiveDv> data)
			throws IllegalArgumentException;

	void executeCollective(String key, Long coa, List<UploadCollectiveDv> data)
			throws IllegalArgumentException;

}
