package org.simbiosis.ui.bprs.cs.client.depositinput;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.DepositDv;

public interface IDepositInput {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void showData(DepositDv depositDv);

	DepositDv getSelectedData();

	DepositDv getEditedData();

	void setDepositProductList(List<DataDv> listDepositProduct);
	
	public abstract class Activity extends FormActivity {
		public abstract void showData(DataDv dataDv);

		public abstract void newData();

		public abstract void loadCommonList();
		
	}

}
