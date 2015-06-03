package org.simbiosis.ui.bprs.teller.client.rpc;

import java.util.Date;
import java.util.List;

import org.simbiosis.ui.bprs.common.shared.TransactionDv;
import org.simbiosis.ui.bprs.teller.shared.TellerDv;
import org.simbiosis.ui.bprs.teller.shared.UploadCollectiveDv;
import org.simbiosis.ui.bprs.teller.shared.VaultTransactionDv;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AppServiceAsync {
	void saveSavingTrans(String key, TransactionDv transDv,
			AsyncCallback<TransactionDv> callback)
			throws IllegalArgumentException;

	void saveCashTrans(String key, TransactionDv transDv,
			AsyncCallback<TransactionDv> callback)
			throws IllegalArgumentException;

	void saveVaultTrans(String key, VaultTransactionDv transDv,
			AsyncCallback<VaultTransactionDv> callback)
			throws IllegalArgumentException;

	void listTellerTransactionByTeller(String key, Long tellerId, Date date,
			AsyncCallback<List<TransactionDv>> callback)
			throws IllegalArgumentException;

	void getAvailableVaultTrans(String key, VaultTransactionDv transDv,
			AsyncCallback<VaultTransactionDv> callback)
			throws IllegalArgumentException;

	void approveVaultTrans(String key, VaultTransactionDv transDv,
			AsyncCallback<VaultTransactionDv> callback)
			throws IllegalArgumentException;

	void listTeller(String key, AsyncCallback<List<TellerDv>> callback)
			throws IllegalArgumentException;

	void listSavingTransaction(String key, Long savingId, Date beginDate,
			Date endDate, AsyncCallback<List<TransactionDv>> callback)
			throws IllegalArgumentException;

	void hasTellerApproval(String key, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;

	void listConfirmSavingCollective(String key, String srcData,
			AsyncCallback<List<UploadCollectiveDv>> callback);

	void executeCollective(String key, Long coa,
			List<UploadCollectiveDv> data, AsyncCallback<Void> callback);

}
