package org.simbiosis.ui.bprs.cs.client.rpc;

import java.util.Date;
import java.util.List;

import org.simbiosis.ui.bprs.common.shared.CustomerDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.DepositDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.cs.shared.SavingBlockirDataDv;
import org.simbiosis.ui.bprs.cs.shared.SavingBlockirDv;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("cs")
public interface CsService extends RemoteService {

	List<DataDv> loadCommonListSaving(String key);

	List<DataDv> loadCommonListDeposit(String key);

	Long saveCustomer(String key, CustomerDv customerDv);

	Long saveSaving(String key, SavingDv savingDv);

	void closeSaving(long id, String reason);

	Long saveDeposit(String key, DepositDv depositDv);

	List<DataDv> findDeposit(String key, Boolean hasCode, Boolean hasName,
			Boolean hasDob, String code, String name, Date dob);

	List<DataDv> loadCommonListProvinsi(String key);

	List<DataDv> loadCommonListCity(String key);

	List<DataDv> loadCommonListJenisPekerjaan(String key);

	List<SavingBlockirDv> listBlockir(Long id);

	void saveBlockir(SavingBlockirDataDv data);

}
