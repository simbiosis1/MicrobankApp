package org.simbiosis.ui.bprs.cs.client.editor;

import org.kembang.editor.client.ListBoxEnumEditor;
import org.simbiosis.ui.bprs.common.shared.CustomerDv.IdTypeEnum;

public class IdTypeEditor extends ListBoxEnumEditor<IdTypeEnum> {

	public IdTypeEditor() {
		super(IdTypeEnum.class);
	}

}
