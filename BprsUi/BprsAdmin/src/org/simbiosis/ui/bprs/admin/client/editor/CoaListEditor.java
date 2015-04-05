package org.simbiosis.ui.bprs.admin.client.editor;

import org.kembang.editor.client.ListBoxListEditor;
import org.simbiosis.ui.bprs.admin.shared.CoaDv;

public class CoaListEditor extends ListBoxListEditor<Long, CoaDv> {

	@Override
	public String convertItemId(CoaDv data) {
		return data.getId().toString();
	}

	@Override
	public int compareData(Long value, CoaDv data) {
		return value.compareTo(data.getId());
	}

	@Override
	public Long convertData(int index, String value) {
		return index > -1 ? Long.parseLong(value) : 0L;
	}

}
