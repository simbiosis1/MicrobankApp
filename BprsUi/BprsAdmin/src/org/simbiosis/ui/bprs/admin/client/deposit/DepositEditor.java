package org.simbiosis.ui.bprs.admin.client.deposit;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.admin.client.editor.DepositTransTypeEditor;
import org.simbiosis.ui.bprs.common.client.deposithelper.DepositInfo;
import org.simbiosis.ui.bprs.common.client.savinghelper.SavingInfo;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class DepositEditor extends FormWidget implements IDeposit,
		Editor<TransactionDv> {

	Activity activity;

	private static TarikTunaiUiBinder uiBinder = GWT
			.create(TarikTunaiUiBinder.class);

	interface TarikTunaiUiBinder extends UiBinder<Widget, DepositEditor> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<TransactionDv, DepositEditor> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	DepositInfo deposit;
	@UiField
	SavingInfo saving;
	@UiField
	DateLabel date;
	@UiField
	TextBox refCode;
	@UiField
	NumberLabel<Double> value;
	@UiField
	DepositTransTypeEditor type;

	public DepositEditor() {
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
