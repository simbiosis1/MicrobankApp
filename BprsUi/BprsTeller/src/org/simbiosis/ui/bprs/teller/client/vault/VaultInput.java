package org.simbiosis.ui.bprs.teller.client.vault;

import org.kembang.editor.client.DoubleTextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class VaultInput extends Composite {

	private static VaultInputUiBinder uiBinder = GWT
			.create(VaultInputUiBinder.class);

	interface VaultInputUiBinder extends UiBinder<Widget, VaultInput> {
	}

	@UiField
	Label code;
	@UiField
	DoubleTextBox value;

	public VaultInput() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	Double getValue() {
		return value.getValue();
	}

}
