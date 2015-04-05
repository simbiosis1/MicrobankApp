package org.simbiosis.ui.bprs.common.client.editor;

import org.kembang.editor.client.ListBoxListEditor;
import org.simbiosis.ui.bprs.common.shared.DataDv;

public class ProductListEditor extends ListBoxListEditor<Long, DataDv> {

	@Override
	public String convertItemId(DataDv data) {
		return data.getId().toString();
	}

	@Override
	public int compareData(Long value, DataDv data) {
		return data.getId().compareTo(value);
	}

	@Override
	public Long convertData(int index, String value) {
		return index > -1 ? Long.parseLong(value) : 0L;
	}

}