package org.simbiosis.bprs.ui.config.client.rpc;

import java.util.List;

import org.kembang.module.shared.SimpleBranchDv;
import org.kembang.module.shared.UserDv;
import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.ConfigDv;
import org.simbiosis.bprs.ui.config.shared.ProductDv;
import org.simbiosis.bprs.ui.config.shared.SubBranchDv;
import org.simbiosis.bprs.ui.config.shared.TellerDv;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AppServiceAsync {

	void listTeller(String key, AsyncCallback<List<TellerDv>> callback);

	void listCoaForTransaction(String key, AsyncCallback<List<CoaDv>> callback);

	void listSavingProduct(String key, AsyncCallback<List<ProductDv>> callback);

	void saveSavingProduct(String key, ProductDv dv,
			AsyncCallback<Void> callback);

	void listDepProduct(String key, AsyncCallback<List<ProductDv>> callback);

	void saveDepProduct(String key, ProductDv dv, AsyncCallback<Void> callback);

	void listLoanProduct(String key, AsyncCallback<List<ProductDv>> callback);

	void saveLoanProduct(String key, ProductDv dv, AsyncCallback<Void> callback);

	void saveTeller(String key, TellerDv dv, AsyncCallback<Void> callback);

	void listUsers(String key, AsyncCallback<List<UserDv>> callback);

	void listSubBranch(String key, AsyncCallback<List<SubBranchDv>> callback);

	void listTerm(String key, AsyncCallback<List<SimpleBranchDv>> callback);

	void loadConfig(String key, AsyncCallback<ConfigDv> callback);

	void saveConfig(String key, ConfigDv config, AsyncCallback<Void> callback);

}
