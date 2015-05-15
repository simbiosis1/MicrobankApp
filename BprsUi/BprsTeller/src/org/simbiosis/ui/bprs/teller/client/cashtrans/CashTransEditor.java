package org.simbiosis.ui.bprs.teller.client.cashtrans;

import org.kembang.editor.client.DoubleTextBox;
import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;
import org.simbiosis.ui.bprs.teller.client.editor.CashTypeEditor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CashTransEditor extends FormWidget implements ICashTrans,
		Editor<TransactionDv> {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, CashTransEditor> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<TransactionDv, CashTransEditor> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	DateLabel date;
	@UiField
	Label code;
	@UiField
	TextBox refCode;
	@UiField
	DoubleTextBox value;
	@UiField
	TextBox maker;
	@UiField
	TextBox description;
	@UiField
	CashTypeEditor type;

	public CashTransEditor() {
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
	public void showData(TransactionDv transDv) {
		driver.edit(transDv);
	}

	@Override
	public TransactionDv getData() {
		TransactionDv result = driver.flush();
		result.setDirection(result.getType());
		return result;
	}

}
