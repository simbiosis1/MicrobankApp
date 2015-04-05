package org.simbiosis.ui.bprs.cs.client.savingblock;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.cs.shared.SavingBlockirDataDv;

public interface ISavingBlockEditor {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void showData(SavingBlockirDataDv data);

	SavingBlockirDataDv getData();

	void enableButtons(Boolean status);

	public abstract class Activity extends FormActivity {

	}
}
