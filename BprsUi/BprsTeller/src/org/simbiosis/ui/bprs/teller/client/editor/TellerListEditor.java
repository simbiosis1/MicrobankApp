package org.simbiosis.ui.bprs.teller.client.editor;

import org.kembang.editor.client.ListBoxListEditor;
import org.simbiosis.ui.bprs.teller.shared.TellerDv;

public class TellerListEditor extends ListBoxListEditor<Long, TellerDv> {

	@Override
	public String convertItemId(TellerDv data) {
		return data.getId().toString();
	}

	@Override
	public int compareData(Long value, TellerDv data) {
		return value == null ? 0 : value.compareTo(data.getId());
	}

	@Override
	public Long convertData(int index, String value) {
		return index > -1 ? Long.parseLong(value) : 0L;
	}

}
