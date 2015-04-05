package org.simbiosis.ui.bprs.common.client.savinghelper;

import org.simbiosis.ui.bprs.common.shared.SavingDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SavingInfoShort extends Composite implements Editor<SavingDv> {

	private static SavingInfoShortUiBinder uiBinder = GWT
			.create(SavingInfoShortUiBinder.class);

	interface SavingInfoShortUiBinder extends UiBinder<Widget, SavingInfoShort> {
	}

	interface Driver extends SimpleBeanEditorDriver<SavingDv, SavingInfoShort> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label code;
	@UiField
	Label strProduct;

	public SavingInfoShort() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		driver.initialize(this);
		driver.edit(new SavingDv());
	}

	public void showData(SavingDv savingDv) {
		driver.edit(savingDv);
	}

	public SavingDv getData() {
		return driver.flush();
	}
}
