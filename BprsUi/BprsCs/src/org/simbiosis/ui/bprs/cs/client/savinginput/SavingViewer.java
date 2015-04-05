package org.simbiosis.ui.bprs.cs.client.savinginput;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.client.customerhelper.SimpleCustomerViewer;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class SavingViewer extends FormWidget implements ISavingInput{
	
	Activity activity;

	private static SimpananViewerUiBinder uiBinder = GWT
			.create(SimpananViewerUiBinder.class);

	interface SimpananViewerUiBinder extends UiBinder<Widget, SavingViewer> {
	}

	@UiField
	SimpleCustomerViewer anggota;
	@UiField
	SavingViewerWidget simpanan;
	
	SavingDv selectedData=null;
	
	public SavingViewer() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasEdit(true);
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
	public void showData(SavingDv tabunganDv) {
		selectedData = tabunganDv;
		anggota.showData(selectedData.getCustomer());
		simpanan.showData(selectedData);
	}

	@Override
	public SavingDv getSelectedData() {
		return selectedData;
	}

	@Override
	public void newData(SavingDv tabunganDv) {
	}

	@Override
	public SavingDv getEditedData() {
		return null;
	}

	@Override
	public void setSavingProductList(List<DataDv> listSavingProduct) {
	}

	@Override
	public void setSavingProvinsiList(List<DataDv> listSavingProvinsi) {
	}

	@Override
	public void setSavingCityList(List<DataDv> listSavingCity) {
	}

	@Override
	public void setSavingPekerjaanList(List<DataDv> listPekerjaan) {
	}

}
