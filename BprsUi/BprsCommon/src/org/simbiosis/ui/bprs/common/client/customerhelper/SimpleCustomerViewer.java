package org.simbiosis.ui.bprs.common.client.customerhelper;

import org.simbiosis.ui.bprs.common.shared.CustomerDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SimpleCustomerViewer extends Composite implements
		Editor<CustomerDv> {

	private static AnggotaViewerSingkatUiBinder uiBinder = GWT
			.create(AnggotaViewerSingkatUiBinder.class);

	interface AnggotaViewerSingkatUiBinder extends
			UiBinder<Widget, SimpleCustomerViewer> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<CustomerDv, SimpleCustomerViewer> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label code;
	@UiField
	Label name;
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

	public SimpleCustomerViewer() {
		initWidget(uiBinder.createAndBindUi(this));
		driver.initialize(this);
	}

	public void showData(CustomerDv anggotaDv) {
		driver.edit(anggotaDv);
	}

	public CustomerDv getData() {
		return driver.flush();
	}

}
