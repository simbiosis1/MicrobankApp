package org.simbiosis.ui.bprs.teller.client.kolektif;

import java.util.List;

import org.simbiosis.ui.bprs.teller.client.editor.UploadCollectiveTable;
import org.simbiosis.ui.bprs.teller.client.editor.TellerListEditor;
import org.simbiosis.ui.bprs.teller.shared.TellerDv;
import org.simbiosis.ui.bprs.teller.shared.UploadCollectiveDv;

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
	UploadCollectiveTable ctTable;
	@UiField
	TellerListEditor tellers;

	public ConfirmCollective() {
		initWidget(uiBinder.createAndBindUi(this));
		//
	}

	public void setParent(UploadCollective parent) {
		this.parent = parent;
	}

	public void setData(List<UploadCollectiveDv> data) {
		ctTable.clear();
		ctTable.setValue(data);
	}

	public List<UploadCollectiveDv> getData() {
		return ctTable.getValue();
	}

	public void setTellers(List<TellerDv> coas) {
		tellers.setList(coas);
	}

	public Long getTeller() {
		return tellers.getValue();
	}

	@UiHandler("btnExecute")
	public void onExecute(ClickEvent e) {
		parent.executeTransfer();
	}
}
