package org.simbiosis.ui.bprs.admin.client.editor;

import org.kembang.editor.client.ListBoxEnumEditor;
import org.simbiosis.ui.bprs.common.shared.TransactionDv.DepositTransType;

public class DepositTransTypeEditor extends ListBoxEnumEditor<DepositTransType> {
	public DepositTransTypeEditor() {
		super(DepositTransType.class);
	}
}
