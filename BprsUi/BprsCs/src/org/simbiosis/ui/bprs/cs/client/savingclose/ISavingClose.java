package org.simbiosis.ui.bprs.cs.client.savingclose;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.cs.shared.SavingCloseDv;

public interface ISavingClose {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void showData(SavingCloseDv savingDv);

	SavingCloseDv getData();

	public abstract class Activity extends FormActivity {
	}

}
