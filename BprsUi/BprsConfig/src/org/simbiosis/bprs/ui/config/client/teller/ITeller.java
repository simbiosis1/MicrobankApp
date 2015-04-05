package org.simbiosis.bprs.ui.config.client.teller;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.kembang.module.shared.SimpleBranchDv;
import org.kembang.module.shared.UserDv;
import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.SubBranchDv;
import org.simbiosis.bprs.ui.config.shared.TellerDv;

public interface ITeller {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void setUsers(List<UserDv> users);

	void setCoa(List<CoaDv> coa);

	void setTellers(List<TellerDv> tellers);

	void setBranches(List<SimpleBranchDv> branches);

	void setSubBranches(List<SubBranchDv> subBranches);

	void editSelected();

	void newUser();

	void viewSelected();

	void clearViewer();

	TellerDv getUser();

	public abstract class Activity extends FormActivity {
	}

}
