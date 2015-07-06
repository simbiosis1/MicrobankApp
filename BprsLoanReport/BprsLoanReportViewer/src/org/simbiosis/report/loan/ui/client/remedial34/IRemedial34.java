package org.simbiosis.report.loan.ui.client.remedial34;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.kembang.module.shared.SimpleBranchDv;
import org.kembang.module.shared.UserDv;

public interface IRemedial34 {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void loadReport();

	void addBranchList(List<SimpleBranchDv> branchList);

	void addAoList(List<UserDv> aoList);

	void exportReport();

	public abstract class Activity extends FormActivity {
		public abstract void loadAo(long branch);
	}
}
