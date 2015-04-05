package org.simbiosis.ui.bprs.common.client.loanhelper;

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

public class LoanListEditor extends Composite implements Editor<CariDataDv> {

	private static ListPinjamanEditorUiBinder uiBinder = GWT
			.create(ListPinjamanEditorUiBinder.class);

	interface ListPinjamanEditorUiBinder extends
			UiBinder<Widget, LoanListEditor> {
	}

	interface Driver extends SimpleBeanEditorDriver<CariDataDv, LoanListEditor> {
	}

	private Driver editorDriver = GWT.create(Driver.class);

	@UiField
	ResultProductsTableEditor resultTable;

	public LoanListEditor() {
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
