package org.simbiosis.ui.bprs.teller.client.loanrep;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

public interface ILoanRepayment {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void showData(TransactionDv transactionDv);

	TransactionDv getData();

	public abstract class Activity extends FormActivity {
	}

}
