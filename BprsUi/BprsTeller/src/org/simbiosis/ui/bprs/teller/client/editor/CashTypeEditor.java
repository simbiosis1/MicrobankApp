package org.simbiosis.ui.bprs.teller.client.editor;

import org.kembang.editor.client.ListBoxEnumEditor;
import org.simbiosis.ui.bprs.common.shared.TransactionDv.TransactionType;

public class CashTypeEditor extends ListBoxEnumEditor<TransactionType> {

	public CashTypeEditor() {
		super(TransactionType.class);
	}

}
