package org.simbiosis.ui.bprs.admin.client.loan;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

public interface ILoan {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void setRepayment(List<TransactionDv> repayment);

	void showData(TransactionDv transactionDv);

	TransactionDv getData();

	public abstract class Activity extends FormActivity {
	}

}
