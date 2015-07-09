package org.simbiosis.ui.bprs.admin.client.uploadcollective;

import java.util.List;

import org.simbiosis.ui.bprs.admin.client.editor.CoaListEditor;
import org.simbiosis.ui.bprs.admin.client.editor.GajiCollectiveTable;
import org.simbiosis.ui.bprs.admin.shared.CoaDv;
import org.simbiosis.ui.bprs.admin.shared.TransferCollectiveDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ConfirmPotonganCollective extends Composite {

	UploadCollective parent;

	private static TarikTunaiViewerUiBinder uiBinder = GWT
			.create(TarikTunaiViewerUiBinder.class);

	interface TarikTunaiViewerUiBinder extends
			UiBinder<Widget, ConfirmPotonganCollective> {
	}

	@UiField
	GajiCollectiveTable ctTable;
	@UiField
	RadioButton typeCoa;
	@UiField
	CoaListEditor coa;
	@UiField
	RadioButton typeAcc;
	@UiField
	TextBox acc;

	public ConfirmPotonganCollective() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		typeCoa.setValue(true);
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

	public void setCoa(List<CoaDv> coas) {
		coa.setList(coas);
	}

	public Integer getSource() {
		if (typeCoa.getValue())
			return 1;
		else if (typeAcc.getValue())
			return 2;
		return 0;
	}

	public Long getCoa() {
		return coa.getValue();
	}

	public String getAcc() {
		return acc.getText();
	}

	@UiHandler("btnExecute")
	public void onExecute(ClickEvent e) {
		if (typeAcc.getValue() && acc.getText().isEmpty()) {
			Window.alert("Nomer rekening asal belum di tulis");
		} else if (parent.getDescription().isEmpty()) {
			Window.alert("Keterangan harus diisi");
		} else {
			parent.executeUpload();
		}
	}
}
