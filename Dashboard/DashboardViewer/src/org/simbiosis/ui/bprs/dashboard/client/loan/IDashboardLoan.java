package org.simbiosis.ui.bprs.dashboard.client.loan;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.kembang.module.shared.SimpleBranchDv;

public interface IDashboardLoan {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void loadReport();

	void addBranchList(List<SimpleBranchDv> branchList);

	void exportReport();

	public abstract class Activity extends FormActivity {
	}
}
