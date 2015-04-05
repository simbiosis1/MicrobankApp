package org.simbiosis.ui.bprs.admin.client.transfer;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

public interface ITransfer {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void showData(TransactionDv transDv);

	TransactionDv getData();

	public abstract class Activity extends FormActivity {
	}

}
