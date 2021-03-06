package org.simbiosis.ui.bprs.common.client.deposithelper;

import java.util.Date;

import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class FindDeposit extends Composite {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, FindDeposit> {
	}

	@UiField
	DepositListEditor searchEditor;
	@UiField
	CheckBox hasCode;
	@UiField
	CheckBox hasName;
	@UiField
	CheckBox hasDob;
	@UiField
	TextBox code;
	@UiField
	TextBox name;
	@UiField
	DateBox dob;

	public FindDeposit() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		dob.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd-MM-yyyy")));
		// format filter
		hasCode.setValue(true);
	}

	public void showData(CariDataDv cariDataDv) {
		searchEditor.showData(cariDataDv);
	}

	public DataDv getSelectedData() {
		return searchEditor.getSelectedData();
	}

	public boolean isHasCode() {
		return hasCode.getValue();
	}

	public String getCode() {
		return code.getText();
	}

	public boolean isHasName() {
		return hasName.getValue();
	}

	public String getName() {
		return name.getText();
	}

	public boolean isHasDob() {
		return hasDob.getValue();
	}

	public Date getDob() {
		return dob.getValue();
	}
}
