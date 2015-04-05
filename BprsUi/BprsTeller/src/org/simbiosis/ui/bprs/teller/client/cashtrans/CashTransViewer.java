package org.simbiosis.ui.bprs.teller.client.cashtrans;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CashTransViewer extends FormWidget implements ICashTrans,
		Editor<TransactionDv> {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, CashTransViewer> {
	}

	interface Driver extends SimpleBeanEditorDriver<TransactionDv, CashTransViewer> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label strDate;
	@UiField
	Label code;
	@UiField
	Label refCode;
	@UiField
	Label strValue;
	@UiField
	Label maker;
	@UiField
	Label description;
	@UiField
	Label strType;

	public CashTransViewer() {
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
	public void showData(TransactionDv transactionDv) {
		driver.edit(transactionDv);
	}

	@Override
	public TransactionDv getData() {
		return null;
	}

}
