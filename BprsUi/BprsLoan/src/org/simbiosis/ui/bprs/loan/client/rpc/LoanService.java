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

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("micbank")
public interface LoanService extends RemoteService {

	List<DataDv> loadCommonListLoan(String key);

	LoanDv getLoan(Long id);

	List<DataDv> findLoan(String key, Boolean hasCode, Boolean hasName,
			Boolean hasDob, String code, String name, Date dob);

	LoanDv saveLoan(String key, LoanDv financingDv);

	LoanDv saveLoanReschedule(String key, LoanDv loanDv);

	List<LoanScheduleDv> createLoanSchedule(String principal, String tenor,
			String rate, Date beginDate, int type);

	InfoLoanDv getPaymentInfo(Long id);

	List<TypeDv> loadCommonListGuarantee(String key);

	ValidationDv validateGuarantee(String key, GuaranteeDv guaranteeDv);

	GuaranteeDv saveGuarantee(String key, GuaranteeDv guaranteeDv);

	GuaranteeDv getGuarantee(Long id);

	List<DataDv> findGuarantee(String key, Boolean hasCode, Boolean hasName,
			Boolean hasDob, String code, String name, Date dob);

	List<UserDv> listUsers(String key);

	List<String> listBISektor();

	LoanDv newLoan();

	LoanDv newReschedule(Long oldLoan);

}
