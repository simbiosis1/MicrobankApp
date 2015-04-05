package org.simbiosis.bprs.ui.config.client.editor;

import org.kembang.editor.client.ListBoxEnumEditor;
import org.simbiosis.bprs.ui.config.shared.ProductDv.SchemaLoanTypeEnum;

public class SchemaLoanTypeEditor extends ListBoxEnumEditor<SchemaLoanTypeEnum> {

	public SchemaLoanTypeEditor() {
		super(SchemaLoanTypeEnum.class);
	}

}
