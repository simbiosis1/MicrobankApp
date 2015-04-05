package org.simbiosis.ui.bprs.admin.client.rpc;

import java.util.List;

import org.simbiosis.ui.bprs.admin.shared.CoaDv;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("admin")
public interface AppService extends RemoteService {

	List<CoaDv> listCoaForTransaction(String key)
			throws IllegalArgumentException;

	TransactionDv saveSavingJournalTrans(String key, TransactionDv transDv)
			throws IllegalArgumentException;

	TransactionDv saveLoanTrans(String key, TransactionDv transDv)
			throws IllegalArgumentException;

	TransactionDv saveDepositTrans(String key, TransactionDv transDv)
			throws IllegalArgumentException;

	TransactionDv saveTransferSaving(String key, TransactionDv transDv)
			throws IllegalArgumentException;

	List<TransactionDv> getRepaymentValue(Long id)
			throws IllegalArgumentException;

}
