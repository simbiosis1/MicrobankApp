package org.simbiosis.report.loan.ui.client.rpc;

import java.util.List;

import org.kembang.module.shared.UserDv;
import org.simbiosis.report.loan.ui.shared.DataDv;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ReportSrvAsync {

	void listAo(String key, long branch, AsyncCallback<List<UserDv>> callback);

	void loadListLoanProduct(String key, AsyncCallback<List<DataDv>> callback);

	void loadListSavingProduct(String key, AsyncCallback<List<DataDv>> callback);

	void loadListDepositProduct(String key, AsyncCallback<List<DataDv>> callback);

}
