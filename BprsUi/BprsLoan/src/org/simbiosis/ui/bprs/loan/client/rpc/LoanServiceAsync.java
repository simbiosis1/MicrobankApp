package org.simbiosis.ui.bprs.loan.client.rpc;

import java.util.Date;
import java.util.List;

import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.GuaranteeDv;
import org.simbiosis.ui.bprs.common.shared.LoanDv;
import org.simbiosis.ui.bprs.common.shared.LoanScheduleDv;
import org.simbiosis.ui.bprs.common.shared.ValidationDv;
import org.simbiosis.ui.bprs.loan.shared.InfoLoanDv;
import org.simbiosis.ui.bprs.loan.shared.TypeDv;
import org.simbiosis.ui.bprs.loan.shared.UserDv;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoanServiceAsync {

	void findLoan(String key, Boolean hasCode, Boolean hasName, Boolean hasDob,
			String code, String name, Date dob,
			AsyncCallback<List<DataDv>> callback);

	void getLoan(Long id, AsyncCallback<LoanDv> callback);

	void loadCommonListLoan(String key, AsyncCallback<List<DataDv>> callback);

	void saveLoan(String key, LoanDv financingDv, AsyncCallback<LoanDv> callback);

	void createLoanSchedule(String principal, String tenor, String rate,
			Date beginDate, int type,
			AsyncCallback<List<LoanScheduleDv>> callback);

	void getPaymentInfo(Long id, AsyncCallback<InfoLoanDv> callback);

	void saveGuarantee(String key, GuaranteeDv financingDv,
			AsyncCallback<GuaranteeDv> callback);

	void getGuarantee(Long id, AsyncCallback<GuaranteeDv> callback);

	void findGuarantee(String key, Boolean hasCode, Boolean hasName,
			Boolean hasDob, String code, String name, Date dob,
			AsyncCallback<List<DataDv>> callback);

	void loadCommonListGuarantee(String key,
			AsyncCallback<List<TypeDv>> callback);

	void validateGuarantee(String key, GuaranteeDv guaranteeDv,
			AsyncCallback<ValidationDv> callback);

	void listUsers(String key, AsyncCallback<List<UserDv>> callback);

	void listBISektor(AsyncCallback<List<String>> callback);

	void newLoan(AsyncCallback<LoanDv> callback);

	void newReschedule(Long oldLoan,AsyncCallback<LoanDv> callback);

	void saveLoanReschedule(String key, LoanDv loanDv,
			AsyncCallback<LoanDv> callback);

}
