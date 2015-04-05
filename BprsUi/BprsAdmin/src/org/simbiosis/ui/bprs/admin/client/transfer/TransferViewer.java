package org.simbiosis.ui.bprs.admin.client.transfer;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.client.savinghelper.SavingInfo;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class TransferViewer extends FormWidget implements
		Editor<TransactionDv>, ITransfer {

	Activity activity;

	private static TarikTunaiViewerUiBinder uiBinder = GWT
			.create(TarikTunaiViewerUiBinder.class);

	interface TarikTunaiViewerUiBinder extends UiBinder<Widget, TransferViewer> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<TransactionDv, TransferViewer> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	SavingInfo saving;
	@UiField
	SavingInfo savingDest;
	@UiField
	Label strDate;
	@UiField
	Label code;
	@UiField
	Label refCode;
	@UiField
	Label description;
	@UiField
	Label strValue;

	public TransferViewer() {
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
		//
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void showData(TransactionDv transactionDv) {
		driver.edit(transactionDv);
	}

	@Override
	public TransactionDv getData() {
		return driver.flush();
	}
}
