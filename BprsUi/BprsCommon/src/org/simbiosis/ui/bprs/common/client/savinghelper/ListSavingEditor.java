package org.simbiosis.ui.bprs.common.client.savinghelper;

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

public class ListSavingEditor extends Composite implements Editor<CariDataDv> {

	private static ListTabunganUiBinder uiBinder = GWT
			.create(ListTabunganUiBinder.class);

	interface ListTabunganUiBinder extends UiBinder<Widget, ListSavingEditor> {
	}

	interface Driver extends SimpleBeanEditorDriver<CariDataDv, ListSavingEditor> {
	}

	private Driver editorDriver = GWT.create(Driver.class);

	@UiField
	ResultProductsTableEditor resultTable;

	public ListSavingEditor() {
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
