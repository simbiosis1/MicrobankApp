package org.simbiosis.ui.bprs.loan.client.simulation;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.loan.shared.LoanScheduleGenDv;

public interface ISimulation {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void setData(LoanScheduleGenDv data);

	LoanScheduleGenDv getData();

	public abstract class Activity extends FormActivity {
		public abstract void generate(LoanScheduleGenDv data);
	}
}
