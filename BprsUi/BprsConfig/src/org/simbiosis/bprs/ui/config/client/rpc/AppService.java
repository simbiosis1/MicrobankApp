package org.simbiosis.bprs.ui.config.client.rpc;

import java.util.List;

import org.kembang.module.shared.SimpleBranchDv;
import org.kembang.module.shared.UserDv;
import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.ProductDv;
import org.simbiosis.bprs.ui.config.shared.SubBranchDv;
import org.simbiosis.bprs.ui.config.shared.TellerDv;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("config")
public interface AppService extends RemoteService {

	List<TellerDv> listTeller(String key) throws IllegalArgumentException;

	List<CoaDv> listCoaForTransaction(String key)
			throws IllegalArgumentException;

	List<ProductDv> listSavingProduct(String key) throws IllegalArgumentException;

	void saveSavingProduct(String key, ProductDv dv)
			throws IllegalArgumentException;

	List<ProductDv> listDepProduct(String key) throws IllegalArgumentException;

	void saveDepProduct(String key, ProductDv dv)
			throws IllegalArgumentException;

	List<ProductDv> listLoanProduct(String key) throws IllegalArgumentException;

	void saveLoanProduct(String key, ProductDv dv)
			throws IllegalArgumentException;

	void saveTeller(String key, TellerDv dv) throws IllegalArgumentException;

	List<UserDv> listUsers(String key) throws IllegalArgumentException;

	List<SubBranchDv> listSubBranch(String key) throws IllegalArgumentException;

	List<SimpleBranchDv> listTerm(String key) throws IllegalArgumentException;
}
