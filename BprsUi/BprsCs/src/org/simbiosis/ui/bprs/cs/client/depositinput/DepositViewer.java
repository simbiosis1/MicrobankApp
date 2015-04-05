package org.simbiosis.ui.bprs.cs.client.depositinput;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.client.customerhelper.SimpleCustomerViewer;
import org.simbiosis.ui.bprs.common.client.savinghelper.SavingInfoShort;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.DepositDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class DepositViewer extends FormWidget implements IDepositInput {

	Activity activity;
	
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, DepositViewer> {
	}

	@UiField
	SimpleCustomerViewer anggota;
	@UiField
	SavingInfoShort saving;
	@UiField
	DepositViewerWidget deposito;

	DepositDv selectedData;
	
	public DepositViewer() {
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
	public void showData(DepositDv tabunganDv) {
		selectedData = tabunganDv;
		anggota.showData(selectedData.getCustomer());
		saving.showData(selectedData.getSaving());
		deposito.showData(selectedData);
	}

	@Override
	public DepositDv getSelectedData() {
		return selectedData;
	}

	@Override
	public DepositDv getEditedData() {
		return null;
	}

	@Override
	public void setDepositProductList(List<DataDv> listDepositProduct) {
	}
}
