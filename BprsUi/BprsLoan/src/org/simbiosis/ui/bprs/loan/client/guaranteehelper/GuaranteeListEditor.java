package org.simbiosis.ui.bprs.loan.client.guaranteehelper;

import org.simbiosis.ui.bprs.common.client.editor.ResultProductsTableEditor;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class GuaranteeListEditor extends Composite implements
		Editor<CariDataDv> {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, GuaranteeListEditor> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<CariDataDv, GuaranteeListEditor> {
	}

	private Driver editorDriver = GWT.create(Driver.class);

	@UiField
	ResultProductsTableEditor resultTable;

	public GuaranteeListEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		editorDriver.initialize(this);
	}

	public void showData(CariDataDv data) {
		editorDriver.edit(data);
	}

	public DataDv getSelectedData() {
		return resultTable.getSelectedData();
	}

}
