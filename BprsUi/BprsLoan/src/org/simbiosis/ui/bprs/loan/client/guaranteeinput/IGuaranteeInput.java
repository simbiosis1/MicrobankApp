package org.simbiosis.ui.bprs.loan.client.guaranteeinput;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.GuaranteeDv;
import org.simbiosis.ui.bprs.loan.shared.TypeDv;

public interface IGuaranteeInput {

	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void showData(GuaranteeDv tabunganDv);

	GuaranteeDv getSelectedData();

	GuaranteeDv getEditedData();

	void setGuaranteeTypes(List<TypeDv> typeList);

	public abstract class Activity extends FormActivity {
		public abstract void showData(DataDv dataDv);

		public abstract void newData();

		public abstract void loadCommonList();
	}
}
