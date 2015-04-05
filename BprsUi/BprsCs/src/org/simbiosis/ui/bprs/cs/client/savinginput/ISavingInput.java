package org.simbiosis.ui.bprs.cs.client.savinginput;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;

public interface ISavingInput {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void showData(SavingDv tabunganDv);

	void newData(SavingDv tabunganDv);

	SavingDv getSelectedData();

	SavingDv getEditedData();

	void setSavingProductList(List<DataDv> listSavingProduct);
	
	void setSavingProvinsiList(List<DataDv> listSavingProvinsi);
	
	void setSavingCityList(List<DataDv> listSavingCity);
	
	void setSavingPekerjaanList(List<DataDv> listPekerjaan); 

	public abstract class Activity extends FormActivity {
		public abstract void showData(DataDv dataDv);

		public abstract void newData();

		public abstract void loadCommonList();
		
		public abstract void loadCommonListProvinsi();
		
		public abstract void loadCommonListCity();
		
		public abstract void loadCommonListJenisPekerjaan();
	}
}
