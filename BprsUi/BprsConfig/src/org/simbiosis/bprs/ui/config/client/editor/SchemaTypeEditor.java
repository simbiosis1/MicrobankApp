package org.simbiosis.bprs.ui.config.client.editor;

import org.kembang.editor.client.ListBoxEnumEditor;
import org.simbiosis.bprs.ui.config.shared.ProductDv.SchemaTypeEnum;

public class SchemaTypeEditor extends ListBoxEnumEditor<SchemaTypeEnum> {

	public SchemaTypeEditor() {
		super(SchemaTypeEnum.class);
	}

}
