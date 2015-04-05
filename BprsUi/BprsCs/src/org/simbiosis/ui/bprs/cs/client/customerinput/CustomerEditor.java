package org.simbiosis.ui.bprs.cs.client.customerinput;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.CustomerDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.cs.client.helper.CustomerEditorWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class CustomerEditor extends FormWidget implements ICustomerInput {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, CustomerEditor> {
	}

	@UiField
	CustomerEditorWidget customerEditor;

	public CustomerEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasSave(true);
		setHasBack(true);
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void showData(CustomerDv customerDv) {
		customerEditor.showData(customerDv);
	}

	@Override
	public CustomerDv getSelectedData() {
		return customerEditor.getSelectedData();
	}

	@Override
	public CustomerDv getEditedData() {
		return customerEditor.getEditedData();
	}

	@Override
	public void setSavingProvinsiList(List<DataDv> listSavingProvinsi) {
		customerEditor.setProvinceList(listSavingProvinsi);
	}

	@Override
	public void setSavingCityList(List<DataDv> listCityProvinsi) {
		customerEditor.setCityList(listCityProvinsi);
	}

	@Override
	public void setSavingJenisPekerjaanList(List<DataDv> listJenisPekerjaan) {
		customerEditor.setOccupationList(listJenisPekerjaan);
	}
}
