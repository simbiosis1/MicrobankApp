package org.simbiosis.ui.bprs.teller.client.vault;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class VaultInfo extends Composite {

	private static VaultInfoUiBinder uiBinder = GWT
			.create(VaultInfoUiBinder.class);

	interface VaultInfoUiBinder extends UiBinder<Widget, VaultInfo> {
	}

	@UiField
	Label code;
	@UiField
	Label strValue;

	public VaultInfo() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void showData(String code, String strValue){
		this.code.setText(code);
		this.strValue.setText(strValue);
	}
}
