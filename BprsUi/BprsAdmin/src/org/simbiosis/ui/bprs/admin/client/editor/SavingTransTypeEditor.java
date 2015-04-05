package org.simbiosis.ui.bprs.admin.client.editor;

import org.kembang.editor.client.ListBoxEnumEditor;
import org.simbiosis.ui.bprs.common.shared.TransactionDv.SavingTransType;

public class SavingTransTypeEditor extends ListBoxEnumEditor<SavingTransType> {
	public SavingTransTypeEditor() {
		super(SavingTransType.class);
	}
}
