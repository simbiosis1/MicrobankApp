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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TransferEditor extends FormWidget implements ITransfer,
		Editor<TransactionDv> {

	Activity activity;

	private static TarikTunaiUiBinder uiBinder = GWT
			.create(TarikTunaiUiBinder.class);

	interface TarikTunaiUiBinder extends UiBinder<Widget, TransferEditor> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<TransactionDv, TransferEditor> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	SavingInfo saving;
	@UiField
	SavingInfo savingDest;
	@UiField
	Label strDate;
	@UiField
	TextBox refCode;
	@UiField
	TextBox description;
	@UiField
	TextBox strValue;

	public TransferEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasSave(true);
		setHasBack(true);
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
	public void showData(TransactionDv transactionDv) {
		driver.edit(transactionDv);
	}

	@Override
	public TransactionDv getData() {
		return driver.flush();
	}

}
