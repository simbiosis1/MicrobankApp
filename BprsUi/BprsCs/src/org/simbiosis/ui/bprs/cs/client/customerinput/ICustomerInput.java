package org.simbiosis.ui.bprs.cs.client.customerinput;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.CustomerDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;

public interface ICustomerInput {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void showData(CustomerDv customerDv);
	
	CustomerDv getSelectedData();
	
	CustomerDv getEditedData();
	
	void setSavingProvinsiList(List<DataDv> listSavingProvinsi);
	
	void setSavingCityList(List<DataDv> listSavingCity);
	
	void setSavingJenisPekerjaanList(List<DataDv> listSavingJenisPekerjaan);
	
	public abstract class Activity extends FormActivity {
		public abstract void showData(DataDv data);
	
	}
}
