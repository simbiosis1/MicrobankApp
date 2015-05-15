package org.simbiosis.ui.bprs.cs.client.helper;

import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.CustomerDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CustomerViewerWidget extends FormWidget implements
		Editor<CustomerDv> {

	private static AnggotaViewerUiBinder uiBinder = GWT
			.create(AnggotaViewerUiBinder.class);

	interface AnggotaViewerUiBinder extends
			UiBinder<Widget, CustomerViewerWidget> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<CustomerDv, CustomerViewerWidget> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label code;
	@UiField
	DateLabel registration;
	@UiField
	Label name;
	@UiField
	Label title;
	@UiField
	Label strSex;
	@UiField
	Label pob;
	@UiField
	DateLabel dob;
	@UiField
	Label strIdType;
	@UiField
	Label idCode;
	@UiField
	Label motherName;
	@UiField
	Label spouseName;
	@UiField
	Label address;
	@UiField
	Label village;
	@UiField
	Label district;
	@UiField
	Label city;
	@UiField
	Label postCode;
	@UiField
	Label province;
	@UiField
	Label phone;
	@UiField
	Label handphone;
	@UiField
	Label occupation;
	@UiField
	Label officeName;
	@UiField
	Label officeAddress;
	@UiField
	Label officeCity;
	@UiField
	Label income;
	@UiField
	Label strTaxable;
	@UiField
	Label taxNr;
	@UiField
	Label descendant;
	@UiField
	Label descAddress;
	@UiField
	Label strBankRel;

	CustomerDv selectedData;

	public CustomerViewerWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasEdit(true);
		setHasBack(true);
		//
		driver.initialize(this);
	}

	public void showData(CustomerDv customerDv) {
		selectedData = customerDv;
		driver.edit(customerDv);
	}

	public CustomerDv getSelectedData() {
		return selectedData;
	}

	public CustomerDv getEditedData() {
		return selectedData;
	}

}
