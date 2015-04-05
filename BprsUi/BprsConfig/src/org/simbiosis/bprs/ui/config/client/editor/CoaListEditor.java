package org.simbiosis.bprs.ui.config.client.editor;

import org.kembang.editor.client.ListBoxListEditor;
import org.simbiosis.bprs.ui.config.shared.CoaDv;

public class CoaListEditor extends ListBoxListEditor<Long, CoaDv> {

	@Override
	public String convertItemId(CoaDv data) {
		return data.getId().toString();
	}

	@Override
	public int compareData(Long value, CoaDv data) {
		return data.getId().compareTo(value);
	}

	@Override
	public Long convertData(int index, String value) {
		return index > -1 ? Long.parseLong(value) : 0L;
	}

}
