package org.simbiosis.bprs.ui.config.client.editor;

import org.kembang.editor.client.ListBoxListEditor;
import org.kembang.module.shared.SimpleBranchDv;

public class TermListBox extends ListBoxListEditor<Long, SimpleBranchDv> {

	@Override
	public String convertItemId(SimpleBranchDv data) {
		return data.getId().toString();
	}

	@Override
	public int compareData(Long value, SimpleBranchDv data) {
		return data.getId().compareTo(value);
	}

	@Override
	public Long convertData(int index, String value) {
		return index > -1 ? Long.parseLong(value) : 0L;
	}

}
