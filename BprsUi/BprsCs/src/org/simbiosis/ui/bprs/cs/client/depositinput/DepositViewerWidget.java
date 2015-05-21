package org.simbiosis.ui.bprs.cs.client.depositinput;

import org.simbiosis.ui.bprs.common.shared.DepositDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class DepositViewerWidget extends Composite implements Editor<DepositDv> {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, DepositViewerWidget> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<DepositDv, DepositViewerWidget> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label code;
	@UiField
	DateLabel registration;
	@UiField
	Label strProduct;
	@UiField
	Label bilyet;
	@UiField
	Label strValue;
	@UiField
	Label strRate;
	@UiField
	Label strSpecialRate;
	@UiField
	Label strAro;
	@UiField
	Label strZakat;

	public DepositViewerWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		driver.initialize(this);
	}

	public void showData(DepositDv depositDv) {
		driver.edit(depositDv);
	}

}
