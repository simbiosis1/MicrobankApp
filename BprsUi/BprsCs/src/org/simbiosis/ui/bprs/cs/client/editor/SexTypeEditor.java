package org.simbiosis.ui.bprs.cs.client.editor;

import org.kembang.editor.client.ListBoxEnumEditor;
import org.simbiosis.ui.bprs.common.shared.CustomerDv.SexTypeEnum;

public class SexTypeEditor extends ListBoxEnumEditor<SexTypeEnum> {

	public SexTypeEditor() {
		super(SexTypeEnum.class);
	}

}
