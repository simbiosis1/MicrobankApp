package org.simbiosis.ui.bprs.cs.client.saving;

import java.util.Date;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;

public interface ISaving {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	public void searchData();

	public void showData(CariDataDv cariDataDv);

	public DataDv getSelectedData();

	public abstract class Activity extends FormActivity {
		public abstract void initViewerEditor();

		public abstract void searchData(Boolean hasCode, String code,
				Boolean hasName, Boolean hasDob, String name, Date dob);
	}
}
