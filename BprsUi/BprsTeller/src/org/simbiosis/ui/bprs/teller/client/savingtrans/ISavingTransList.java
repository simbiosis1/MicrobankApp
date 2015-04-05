package org.simbiosis.ui.bprs.teller.client.savingtrans;

import java.util.Date;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.FindTransactionDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;

public interface ISavingTransList {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void setSaving(SavingDv savingDv);

	void setListTransaction(FindTransactionDv findTransactionDv);
	
	SavingDv getSaving();

	Date getBeginDate();
	Date getEndDate();

	public abstract class Activity extends FormActivity {
		abstract void createSavingTransList(long id, Date beginDate, Date endDate);
	}
}
