package org.simbiosis.bprs.ui.config.client.gl;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.ConfigDv;

public interface IGlConfig {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void setCoaList(List<CoaDv> coaList);

	void setData(ConfigDv config);

	ConfigDv getData();

	void editData();

	public abstract class Activity extends FormActivity {
	}

}
