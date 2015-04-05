package org.simbiosis.ui.bprs.common.client.savinghelper;

import org.simbiosis.ui.bprs.common.shared.SavingDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SavingInfo extends Composite implements Editor<SavingDv> {

	private static SimpananInfoUiBinder uiBinder = GWT
			.create(SimpananInfoUiBinder.class);

	interface SimpananInfoUiBinder extends UiBinder<Widget, SavingInfo> {
	}

	interface Driver extends SimpleBeanEditorDriver<SavingDv, SavingInfo> {
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

	public SavingInfo() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		driver.initialize(this);
		driver.edit(new SavingDv());
	}

	public void showData(SavingDv savingDv) {
		driver.edit(savingDv);
	}

	public SavingDv getData() {
		return driver.flush();
	}
}
