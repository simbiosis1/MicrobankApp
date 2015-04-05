package org.simbiosis.ui.bprs.teller.client.vault;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.teller.shared.VaultTransactionDv;

public interface IVault {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void newData(VaultTransactionDv transDv);

	void showData(VaultTransactionDv transDv);

	VaultTransactionDv getData();

	public abstract class Activity extends FormActivity {
		public abstract void getReadyVault(VaultTransactionDv transDv);
	}
}
