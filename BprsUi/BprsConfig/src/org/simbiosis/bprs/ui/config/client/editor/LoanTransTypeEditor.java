package org.simbiosis.bprs.ui.config.client.editor;

import org.kembang.editor.client.ListBoxEnumEditor;
import org.simbiosis.ui.bprs.common.shared.TransactionDv.LoanTransType;

public class LoanTransTypeEditor extends ListBoxEnumEditor<LoanTransType> {
	public LoanTransTypeEditor() {
		super(LoanTransType.class);
	}
}
