package org.simbiosis.ui.bprs.admin.client.savingjournal;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.admin.shared.CoaDv;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

public interface ISavingJournal {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void showData(TransactionDv transactionDv);

	TransactionDv getData();

	void setListCoa(List<CoaDv> listCoa);

	public abstract class Activity extends FormActivity {
	}

}
