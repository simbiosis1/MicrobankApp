package org.simbiosis.ui.bprs.admin.client.uploadcollective;

import java.util.List;

import org.simbiosis.ui.bprs.admin.client.editor.CoaListEditor;
import org.simbiosis.ui.bprs.admin.client.editor.CollectiveTransferTable;
import org.simbiosis.ui.bprs.admin.shared.CoaDv;
import org.simbiosis.ui.bprs.admin.shared.TransferCollectiveDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ConfirmCollective extends Composite {

	UploadCollective parent;

	private static TarikTunaiViewerUiBinder uiBinder = GWT
			.create(TarikTunaiViewerUiBinder.class);

	interface TarikTunaiViewerUiBinder extends
			UiBinder<Widget, ConfirmCollective> {
	}

	@UiField
	CollectiveTransferTable ctTable;
	@UiField
	CoaListEditor coa;

	public ConfirmCollective() {
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

	public List<TransferCollectiveDv> getData(){
		return ctTable.getValue();
	}
	
	public void setCoa(List<CoaDv> coas) {
		coa.setList(coas);
	}
	
	public Long getCoa(){
		return coa.getValue();
	}

	@UiHandler("btnExecute")
	public void onExecute(ClickEvent e) {
		parent.executeTransfer();
	}
}
