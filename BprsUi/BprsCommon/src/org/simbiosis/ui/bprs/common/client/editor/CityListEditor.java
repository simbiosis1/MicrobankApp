package org.simbiosis.ui.bprs.common.client.editor;

import org.kembang.editor.client.ListBoxListEditor;
import org.simbiosis.ui.bprs.common.shared.DataDv;

public class CityListEditor extends ListBoxListEditor<String, DataDv> {

	@Override
	public String convertItemId(DataDv data) {
		return data.getNama();
	}

	@Override
	public int compareData(String value, DataDv data) {
		return data.getNama().compareTo(value);
	}

	@Override
	public String convertData(int index, String value) {
		return index > -1 ? value : "";
	}

}