package org.simbiosis.ui.bprs.teller.client.vault;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.teller.shared.VaultTransactionDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class VaultViewer extends FormWidget implements IVault {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, VaultViewer> {
	}

	@UiField
	Label strDate;
	@UiField
	Label strType;
	@UiField
	VaultInfo info;
	
	public VaultViewer() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasNew(true);
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
	public void showData(VaultTransactionDv transDv) {
		info.showData(transDv.getCode(),transDv.getStrValue());
		strDate.setText(transDv.getStrDate());
		strType.setText((transDv.getDirection()==1) ? "PENGEMBALIAN":"PENGAMBILAN");
}

	@Override
	public void newData(VaultTransactionDv transactionDv) {
	}

	@Override
	public VaultTransactionDv getData() {
		return null;
	}

}
