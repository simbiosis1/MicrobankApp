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

public class DepositEditor extends FormWidget implements IDepositInput {

	Activity activity;
	
	private static MyUiBinder uiBinder = GWT
			.create(MyUiBinder.class);

	interface MyUiBinder extends
			UiBinder<Widget, DepositEditor> {
	}

	@UiField
	SimpleCustomerViewer anggota;
	@UiField
	SavingInfoShort saving;
	@UiField
	DepositEditorWidget deposito;

	DepositDv selectedData;

	public DepositEditor() {
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
		if (appStatus.isLogin()) {
			activity.loadCommonList();
		}
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void showData(DepositDv depositDv) {
		selectedData = depositDv;
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
		DepositDv depositDv = deposito.getData();
		depositDv.setCustomer(selectedData.getCustomer());
		return depositDv;
	}

	@Override
	public void setDepositProductList(List<DataDv> listDepositProduct) {
		deposito.setDepositProductList(listDepositProduct);
	}
	
}
