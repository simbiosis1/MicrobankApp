package org.simbiosis.ui.bprs.dashboard.client.tks;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;

public interface IDashboardTks {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void loadReport();

	void exportReport();

	public abstract class Activity extends FormActivity {
	}
}
