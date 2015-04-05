package org.simbiosis.ui.bprs.common.client.rpc;

import java.util.Date;
import java.util.List;

import org.simbiosis.ui.bprs.common.shared.CustomerDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.DepositDv;
import org.simbiosis.ui.bprs.common.shared.LoanDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("micbankcomm")
public interface MicBankService extends RemoteService {

	CustomerDv getCustomer(Long id);

	List<DataDv> findCustomer(String key, Boolean hasName, Boolean hasDob,
			String name, Date dob);

	SavingDv getSaving(Long id);

	List<DataDv> findSaving(String key, Boolean isTellerTransaction,
			Boolean hasCode, Boolean hasName, Boolean hasDob, String code,
			String name, Date dob);

	DepositDv getDeposit(Long id);

	List<DataDv> findDeposit(String key, Boolean hasCode, Boolean hasName,
			Boolean hasDob, String code, String name, Date dob);

	LoanDv getLoan(Long id);

	List<DataDv> findLoan(String key, Boolean hasCode, Boolean hasName,
			Boolean hasDob, String code, String name, Date dob);

}
