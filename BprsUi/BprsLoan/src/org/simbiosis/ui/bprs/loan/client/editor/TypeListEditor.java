package org.simbiosis.ui.bprs.loan.client.editor;

import org.kembang.editor.client.ListBoxListEditor;
import org.simbiosis.ui.bprs.loan.shared.TypeDv;

public class TypeListEditor extends ListBoxListEditor<Integer, TypeDv> {

	@Override
	public String convertItemId(TypeDv data) {
		return data.getType().toString();
	}

	@Override
	public int compareData(Integer value, TypeDv data) {
		return value.compareTo(data.getType());
	}

	@Override
	public Integer convertData(int index, String value) {
		return index > -1 ? Integer.parseInt(value) : 0;
	}

}
