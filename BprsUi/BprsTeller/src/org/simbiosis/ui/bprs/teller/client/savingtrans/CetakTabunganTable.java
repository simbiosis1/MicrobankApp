package org.simbiosis.ui.bprs.teller.client.savingtrans;

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

public class CetakTabunganTable extends Composite implements
		Editor<FindTransactionDv> {

	private static ListTransaksiTellerTableUiBinder uiBinder = GWT
			.create(ListTransaksiTellerTableUiBinder.class);

	interface ListTransaksiTellerTableUiBinder extends
			UiBinder<Widget, CetakTabunganTable> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<FindTransactionDv, CetakTabunganTable> {
	}

	private Driver editorDriver = GWT.create(Driver.class);

	@UiField
	ResultTransactionTableEditor resultTable;

	public CetakTabunganTable() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		editorDriver.initialize(this);
		editorDriver.edit(new FindTransactionDv());
	}

	public void showData(FindTransactionDv data) {
		editorDriver.edit(data);
	}

	public TransactionDv getSelectedData() {
		return resultTable.getSelectedData();
	}

}
