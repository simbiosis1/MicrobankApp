package org.simbiosis.ui.bprs.common.client.customerhelper;

import org.simbiosis.ui.bprs.common.client.editor.ResultCustomersTableEditor;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CustomerListWidget extends Composite implements Editor<CariDataDv> {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, CustomerListWidget> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<CariDataDv, CustomerListWidget> {
	}

	private Driver editorDriver = GWT.create(Driver.class);

	@UiField
	ResultCustomersTableEditor resultTable;

	public CustomerListWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		editorDriver.initialize(this);
		editorDriver.edit(new CariDataDv());
	}

	public void showData(CariDataDv data) {
		editorDriver.edit(data);
	}

	public DataDv getSelectedData() {
		return resultTable.getSelectedData();
	}

}
