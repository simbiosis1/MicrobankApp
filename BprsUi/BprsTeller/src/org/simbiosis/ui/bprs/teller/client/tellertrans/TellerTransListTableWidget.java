package org.simbiosis.ui.bprs.teller.client.tellertrans;

import org.simbiosis.ui.bprs.common.client.editor.ResultTransactionTableEditor;
import org.simbiosis.ui.bprs.common.shared.FindTransactionDv;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TellerTransListTableWidget extends Composite implements
		Editor<FindTransactionDv> {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, TellerTransListTableWidget> {
	}

	interface Driver
			extends
			SimpleBeanEditorDriver<FindTransactionDv, TellerTransListTableWidget> {
	}

	private Driver editorDriver = GWT.create(Driver.class);

	@UiField
	ResultTransactionTableEditor resultTable;

	public TellerTransListTableWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		editorDriver.initialize(this);
	}

	public void showData(FindTransactionDv data) {
		editorDriver.edit(data);
	}

	public TransactionDv getSelectedData() {
		return resultTable.getSelectedData();
	}

}
