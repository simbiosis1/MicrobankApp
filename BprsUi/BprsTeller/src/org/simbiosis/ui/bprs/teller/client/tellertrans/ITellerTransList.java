package org.simbiosis.ui.bprs.teller.client.tellertrans;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.FindTransactionDv;
import org.simbiosis.ui.bprs.teller.shared.TellerDv;

public interface ITellerTransList {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void setListTransaction(FindTransactionDv findTransactionDv);

	void setTellerList(List<TellerDv> tellerList);

	Date getDate();

	Long getTeller();

	public abstract class Activity extends FormActivity {
	}
}
