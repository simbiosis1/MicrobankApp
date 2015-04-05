package org.simbiosis.ui.bprs.security.client.auth;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.security.shared.AuthDv;
import org.simbiosis.ui.bprs.security.shared.AuthListDv;

public interface IAuthList {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void setData(AuthListDv data);

	public abstract class Activity extends FormActivity {
		public abstract void authorize(AuthDv data);
	}
}
