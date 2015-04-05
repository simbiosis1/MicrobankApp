package org.simbiosis.ui.bprs.common.client.rpc;

import java.util.Date;
import java.util.List;

import org.simbiosis.ui.bprs.common.shared.CustomerDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.DepositDv;
import org.simbiosis.ui.bprs.common.shared.LoanDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MicBankServiceAsync {
	void getCustomer(Long id, AsyncCallback<CustomerDv> callback);

	void findCustomer(String key, Boolean hasName, Boolean hasDob, String name,
			Date dob, AsyncCallback<List<DataDv>> callback);

	void getSaving(Long id, AsyncCallback<SavingDv> callback);

	void findSaving(String key, Boolean isTellerTransaction, Boolean hasCode,
			Boolean hasName, Boolean hasDob, String code, String name,
			Date dob, AsyncCallback<List<DataDv>> callback);

	void getDeposit(Long id, AsyncCallback<DepositDv> callback);

	void findDeposit(String key, Boolean hasCode, Boolean hasName,
			Boolean hasDob, String code, String name, Date dob,
			AsyncCallback<List<DataDv>> callback);

	void findLoan(String key, Boolean hasCode, Boolean hasName, Boolean hasDob,
			String code, String name, Date dob,
			AsyncCallback<List<DataDv>> callback);

	void getLoan(Long id, AsyncCallback<LoanDv> callback);

}
