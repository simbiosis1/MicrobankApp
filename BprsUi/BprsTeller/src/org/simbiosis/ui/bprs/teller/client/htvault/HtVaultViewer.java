package org.simbiosis.ui.bprs.teller.client.htvault;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.teller.shared.TellerDv;
import org.simbiosis.ui.bprs.teller.shared.VaultTransactionDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class HtVaultViewer extends FormWidget implements IHtVault,
		Editor<VaultTransactionDv> {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, HtVaultViewer> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<VaultTransactionDv, HtVaultViewer> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label strType;
	@UiField
	Label strTeller;
	@UiField
	Label strDate;
	@UiField
	Label code;
	@UiField
	Label strValue;

	@UiField
	Label str50L;
	@UiField
	Label str100L;
	@UiField
	Label str200L;
	@UiField
	Label str500L;
	@UiField
	Label str1000L;
	@UiField
	Label str1000K;
	@UiField
	Label str2000K;
	@UiField
	Label str5000K;
	@UiField
	Label str10000K;
	@UiField
	Label str20000K;
	@UiField
	Label str50000K;
	@UiField
	Label str100000K;

	public HtVaultViewer() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasNew(true);
		//
		driver.initialize(this);
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
	public void showData(VaultTransactionDv transactionDv) {
	}

	@Override
	public VaultTransactionDv getData() {
		return null;
	}

	@Override
	public void setTellers(List<TellerDv> tellers) {
	}

}
