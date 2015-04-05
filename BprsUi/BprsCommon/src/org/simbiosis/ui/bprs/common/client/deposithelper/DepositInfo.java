package org.simbiosis.ui.bprs.common.client.deposithelper;

import org.simbiosis.ui.bprs.common.shared.DepositDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class DepositInfo extends Composite implements Editor<DepositDv> {

	private static SimpananInfoUiBinder uiBinder = GWT
			.create(SimpananInfoUiBinder.class);

	interface SimpananInfoUiBinder extends UiBinder<Widget, DepositInfo> {
	}

	interface Driver extends SimpleBeanEditorDriver<DepositDv, DepositInfo> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label code;
	@UiField
	Label strProduct;
	@UiField
	Label name;
	@UiField
	Label strSex;
	@UiField
	Label strIdType;
	@UiField
	Label idCode;
	@UiField
	Label address;
	@UiField
	Label city;
	@UiField
	Label postCode;
	@UiField
	Label province;

	public DepositInfo() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		driver.initialize(this);
		driver.edit(new DepositDv());
	}

	public void showData(DepositDv savingDv) {
		driver.edit(savingDv);
	}

	public DepositDv getData() {
		return driver.flush();
	}
}
