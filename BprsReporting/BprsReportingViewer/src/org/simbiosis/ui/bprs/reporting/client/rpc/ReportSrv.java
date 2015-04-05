package org.simbiosis.ui.bprs.reporting.client.rpc;

import java.util.List;

import org.kembang.module.shared.UserDv;
import org.simbiosis.ui.bprs.reporting.shared.DataDv;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("reportsrv")
public interface ReportSrv extends RemoteService {

	List<UserDv> listAo(String key, long branch) throws IllegalArgumentException;

	List<DataDv> loadListLoanProduct(String key);

	List<DataDv> loadListSavingProduct(String key);

	List<DataDv> loadListDepositProduct(String key);

}
