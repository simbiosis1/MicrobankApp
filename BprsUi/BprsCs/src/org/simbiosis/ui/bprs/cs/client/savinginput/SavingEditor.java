package org.simbiosis.ui.bprs.cs.client.savinginput;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.client.customerhelper.SimpleCustomerViewer;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.cs.client.helper.SimpleCustomerEditor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SavingEditor extends FormWidget implements ISavingInput {

	Activity activity;

	private static SimpananEditorUiBinder uiBinder = GWT
			.create(SimpananEditorUiBinder.class);

	interface SimpananEditorUiBinder extends UiBinder<Widget, SavingEditor> {
	}

	@UiField
	VerticalPanel anggota;
	@UiField
	SavingEditorWidget simpanan;

	SimpleCustomerEditor anggotaEditor;
	SimpleCustomerViewer anggotaViewer;

	SavingDv selectedData = null;

	public SavingEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		anggotaEditor = new SimpleCustomerEditor();
		anggotaViewer = new SimpleCustomerViewer();
		//
		setHasSave(true);
		setHasBack(true);
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
		if (appStatus.isLogin()) {
			activity.loadCommonList();
			activity.loadCommonListProvinsi();
			activity.loadCommonListCity();
			activity.loadCommonListJenisPekerjaan();
		}
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void showData(SavingDv tabunganDv) {
		selectedData = tabunganDv;
		anggota.clear();
		anggota.add(anggotaViewer);
		anggotaViewer.showData(selectedData.getCustomer());
		simpanan.showData(selectedData);
	}

	@Override
	public SavingDv getSelectedData() {
		return selectedData;
	}

	@Override
	public void newData(SavingDv tabunganDv) {
		selectedData = tabunganDv;
		anggota.clear();
		if (tabunganDv.getNewCustomer()) {
			anggotaEditor.showData(selectedData.getCustomer());
			anggota.add(anggotaEditor);
		} else {
			anggotaViewer.showData(selectedData.getCustomer());
			anggota.add(anggotaViewer);
		}
		simpanan.showData(selectedData);
	}

	@Override
	public SavingDv getEditedData() {
		SavingDv savingDv = simpanan.getData();
		savingDv.setCustomer(selectedData.getNewCustomer() ? anggotaEditor
				.getData() : anggotaViewer.getData());
		return savingDv;
	}

	@Override
	public void setSavingProductList(List<DataDv> listSavingProduct) {
		simpanan.setSavingProductList(listSavingProduct);
	}
	
	@Override
	public void setSavingProvinsiList(List<DataDv> listSavingProvinsi) {
		anggotaEditor.setSavingProvinsiList(listSavingProvinsi);
	}
	
	@Override
	public void setSavingCityList(List<DataDv> listSavingCity) {
		anggotaEditor.setSavingCityList(listSavingCity);
	}
	
	@Override
	public void setSavingPekerjaanList(List<DataDv> listPekerjaan) {
		anggotaEditor.setSavingPekerjaanList(listPekerjaan);
	}

}
