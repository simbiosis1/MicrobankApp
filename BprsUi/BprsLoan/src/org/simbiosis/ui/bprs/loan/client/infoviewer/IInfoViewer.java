package org.simbiosis.ui.bprs.loan.client.infoviewer;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.LoanDv;
import org.simbiosis.ui.bprs.loan.shared.InfoLoanDv;

public interface IInfoViewer {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void showLoan(LoanDv loanDv);
	
	LoanDv getSelectedData();

	void showPayment(InfoLoanDv transDv);

	public abstract class Activity extends FormActivity {

	}

}
