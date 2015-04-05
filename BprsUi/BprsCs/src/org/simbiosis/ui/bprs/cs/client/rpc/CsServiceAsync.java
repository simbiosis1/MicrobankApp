package org.simbiosis.ui.bprs.cs.client.rpc;

import java.util.Date;
import java.util.List;

import org.simbiosis.ui.bprs.common.shared.CustomerDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.DepositDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.cs.shared.SavingBlockirDataDv;
import org.simbiosis.ui.bprs.cs.shared.SavingBlockirDv;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CsServiceAsync {
	void saveSaving(String key, SavingDv savingDv,
			AsyncCallback<Long> callback);

	void loadCommonListSaving(String key, AsyncCallback<List<DataDv>> callback);

	void saveCustomer(String key, CustomerDv customerDv,
			AsyncCallback<Long> callback);

	void loadCommonListDeposit(String key, AsyncCallback<List<DataDv>> callback);

	void saveDeposit(String key, DepositDv depositDv,
			AsyncCallback<Long> callback);

	void findDeposit(String key, Boolean hasCode, Boolean hasName,
			Boolean hasDob, String code, String name, Date dob,
			AsyncCallback<List<DataDv>> callback);

	void closeSaving(long id, String reason, AsyncCallback<Void> callback);

	void loadCommonListProvinsi(String key,
			AsyncCallback<List<DataDv>> asyncCallback);

	void loadCommonListCity(String key,
			AsyncCallback<List<DataDv>> asyncCallback);

	void loadCommonListJenisPekerjaan(String key,
			AsyncCallback<List<DataDv>> asyncCallback);

	void saveBlockir(SavingBlockirDataDv data, AsyncCallback<Void> callback);

	void listBlockir(Long id, AsyncCallback<List<SavingBlockirDv>> callback);

}
