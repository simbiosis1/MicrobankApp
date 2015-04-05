package org.simbiosis.bprs.ui.config.client.editor;

import org.kembang.editor.client.ListBoxListEditor;
import org.simbiosis.bprs.ui.config.shared.SubBranchDv;

public class SubBranchListBox extends ListBoxListEditor<Long, SubBranchDv> {

	@Override
	public String convertItemId(SubBranchDv data) {
		return data.getId().toString();
	}

	@Override
	public int compareData(Long value, SubBranchDv data) {
		return data.getId().compareTo(value);
	}

	@Override
	public Long convertData(int index, String value) {
		return index > -1 ? Long.parseLong(value) : 0L;
	}

}
