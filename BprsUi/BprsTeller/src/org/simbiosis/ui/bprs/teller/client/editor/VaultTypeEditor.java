package org.simbiosis.ui.bprs.teller.client.editor;

import org.kembang.editor.client.ListBoxEnumEditor;
import org.simbiosis.ui.bprs.common.shared.TransactionDv.VaultTransactionType;

public class VaultTypeEditor extends ListBoxEnumEditor<VaultTransactionType> {

	public VaultTypeEditor() {
		super(VaultTransactionType.class);
	}

}
