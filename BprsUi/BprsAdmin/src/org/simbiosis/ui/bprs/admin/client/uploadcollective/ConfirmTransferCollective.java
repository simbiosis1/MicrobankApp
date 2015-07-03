package org.simbiosis.ui.bprs.admin.client.uploadcollective;

import java.util.List;

import org.simbiosis.ui.bprs.admin.client.editor.TransferCollectiveTable;
import org.simbiosis.ui.bprs.admin.shared.TransferCollectiveDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ConfirmTransferCollective extends Composite {

	UploadCollective parent;

	private static TarikTunaiViewerUiBinder uiBinder = GWT
			.create(TarikTunaiViewerUiBinder.class);

	interface TarikTunaiViewerUiBinder extends
			UiBinder<Widget, ConfirmTransferCollective> {
	}

	@UiField
	TransferCollectiveTable ctTable;

	public ConfirmTransferCollective() {
		initWidget(uiBinder.createAndBindUi(this));
		//
	}

	public void setParent(UploadCollective parent) {
		this.parent = parent;
	}

	public void setData(List<TransferCollectiveDv> data) {
		ctTable.clear();
		ctTable.setValue(data);
	}

	public List<TransferCollectiveDv> getData() {
		return ctTable.getValue();
	}

	@UiHandler("btnExecute")
	public void onExecute(ClickEvent e) {
		if (parent.getDescription().isEmpty()) {
			Window.alert("Keterangan harus diisi");
		} else {
			parent.executeUpload();
		}
	}
}
