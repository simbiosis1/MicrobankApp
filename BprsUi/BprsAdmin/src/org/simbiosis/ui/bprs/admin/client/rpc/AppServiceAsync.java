package org.simbiosis.ui.bprs.admin.client.rpc;

import java.util.List;

import org.simbiosis.ui.bprs.admin.shared.CoaDv;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AppServiceAsync {
	void listCoaForTransaction(String key, AsyncCallback<List<CoaDv>> callback);

	void saveSavingJournalTrans(String key, TransactionDv transDv,
			AsyncCallback<TransactionDv> callback)
			throws IllegalArgumentException;

	void saveLoanTrans(String key, TransactionDv transDv,
			AsyncCallback<TransactionDv> callback)
			throws IllegalArgumentException;

	void saveDepositTrans(String key, TransactionDv transDv,
			AsyncCallback<TransactionDv> callback)
			throws IllegalArgumentException;

	void saveTransferSaving(String key, TransactionDv transDv,
			AsyncCallback<TransactionDv> callback)
			throws IllegalArgumentException;

	void getRepaymentValue(Long id, AsyncCallback<List<TransactionDv>> callback)
			throws IllegalArgumentException;

}
