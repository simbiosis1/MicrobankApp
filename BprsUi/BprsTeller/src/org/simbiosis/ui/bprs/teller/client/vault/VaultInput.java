package org.simbiosis.ui.bprs.teller.client.vault;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class VaultInput extends Composite {

	private static VaultInputUiBinder uiBinder = GWT
			.create(VaultInputUiBinder.class);

	interface VaultInputUiBinder extends UiBinder<Widget, VaultInput> {
	}

	@UiField
	Label code;
	@UiField
	TextBox strValue;

	public VaultInput() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	String getValue(){
		return strValue.getText();
	}

}
