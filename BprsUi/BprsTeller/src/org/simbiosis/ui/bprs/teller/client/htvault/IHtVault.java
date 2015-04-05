package org.simbiosis.ui.bprs.teller.client.htvault;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.teller.shared.TellerDv;
import org.simbiosis.ui.bprs.teller.shared.VaultTransactionDv;

public interface IHtVault {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void showData(VaultTransactionDv transactionDv);

	void setTellers(List<TellerDv> tellers);

	VaultTransactionDv getData();

	public abstract class Activity extends FormActivity {
		public abstract void listAvailableTeller();

		public abstract void getReadyVault(VaultTransactionDv transDv);
	}
}
