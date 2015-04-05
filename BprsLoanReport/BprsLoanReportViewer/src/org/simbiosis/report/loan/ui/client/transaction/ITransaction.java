package org.simbiosis.report.loan.ui.client.transaction;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.kembang.module.shared.SimpleBranchDv;

public interface ITransaction {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void setListBranch(List<SimpleBranchDv> listBranch);

	void exportReport();

	void loadReport();

	public abstract class Activity extends FormActivity {

	}
}
