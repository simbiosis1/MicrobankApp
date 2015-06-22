package org.simbiosis.ui.bprs.cs.client.customerinfo;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.DataDv;

public interface ICustomerInfoData {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	public void showData(DataDv dataDv);

	public abstract class Activity extends FormActivity {
		public abstract void showData(DataDv dataDv);
	}
}
