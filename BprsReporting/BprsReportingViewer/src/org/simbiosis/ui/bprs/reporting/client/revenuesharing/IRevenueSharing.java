package org.simbiosis.ui.bprs.reporting.client.revenuesharing;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;

public interface IRevenueSharing {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void loadReport();
	
	void exportReport();

	void addPeriods(String text, String value);

	public abstract class Activity extends FormActivity {
	}
}
