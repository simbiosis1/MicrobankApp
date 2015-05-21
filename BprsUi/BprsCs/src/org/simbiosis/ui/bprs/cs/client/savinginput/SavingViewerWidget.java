package org.simbiosis.ui.bprs.cs.client.savinginput;

import org.simbiosis.ui.bprs.common.shared.SavingDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SavingViewerWidget extends Composite implements Editor<SavingDv> {

	private static SimpananViewerUiBinder uiBinder = GWT
			.create(SimpananViewerUiBinder.class);

	interface SimpananViewerUiBinder extends
			UiBinder<Widget, SavingViewerWidget> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<SavingDv, SavingViewerWidget> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label code;
	@UiField
	DateLabel registration;
	@UiField
	Label strProduct;
	@UiField
	Label strZakat;

	public SavingViewerWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		driver.initialize(this);
	}

	public void showData(SavingDv tabunganDv) {
		driver.edit(tabunganDv);
	}

}
