package org.simbiosis.ui.bprs.teller.client.savingprint;

import java.util.Date;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.SavingDv;

public interface ISavingPrint {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void setSaving(SavingDv savingDv);

	public abstract class Activity extends FormActivity {
		public abstract void printMaster(SavingDv saving);

		public abstract void printBook(SavingDv saving, Date date, String nuc);
	}
}
