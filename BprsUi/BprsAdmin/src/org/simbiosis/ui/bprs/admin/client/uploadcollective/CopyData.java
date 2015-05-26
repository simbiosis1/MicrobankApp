package org.simbiosis.ui.bprs.admin.client.uploadcollective;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class CopyData extends Composite {

	UploadCollective parent;

	private static CopyDataUiBinder uiBinder = GWT
			.create(CopyDataUiBinder.class);

	interface CopyDataUiBinder extends UiBinder<Widget, CopyData> {
	}

	@UiField
	TextArea dataSrc;

	public CopyData() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setParent(UploadCollective parent) {
		this.parent = parent;
	}

	@UiHandler("btnSendSrc")
	public void onSendSrc(ClickEvent e) {
		parent.confirmTransfer();
	}

	public String getData() {
		return dataSrc.getText();
	}
}
