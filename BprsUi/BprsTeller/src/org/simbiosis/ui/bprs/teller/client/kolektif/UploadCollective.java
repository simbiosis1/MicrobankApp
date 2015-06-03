package org.simbiosis.ui.bprs.teller.client.kolektif;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.teller.shared.TellerDv;
import org.simbiosis.ui.bprs.teller.shared.UploadCollectiveDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class UploadCollective extends FormWidget implements IUploadCollective {

	Activity activity;

	private static UploadCollectiveUiBinder uiBinder = GWT
			.create(UploadCollectiveUiBinder.class);

	interface UploadCollectiveUiBinder extends
			UiBinder<Widget, UploadCollective> {
	}

	@UiField
	DateBox transDate;
	@UiField
	ListBox transType;
	@UiField
	VerticalPanel dataPanel;

	CopyData copyData = new CopyData();
	ConfirmCollective confirm = new ConfirmCollective();

	public UploadCollective() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		transType.addItem("Tabungan");
		transType.addItem("Angsuran");
		//
		copyData.setParent(this);
		confirm.setParent(this);
		//
		dataPanel.add(copyData);
		//
		transDate.setValue(new Date());
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	public void confirmTransfer() {
		dataPanel.clear();
		dataPanel.add(confirm);
		//
		activity.confirmTransfer();
	}

	public void executeTransfer() {
		activity.executeTransfer();
	}

	@Override
	public void confirmTransfer(List<UploadCollectiveDv> data) {
		confirm.setData(data);
	}

	@Override
	public String getSrcData() {
		return copyData.getData();
	}

	@Override
	public List<UploadCollectiveDv> getTransferData() {
		return confirm.getData();
	}

	@Override
	public void setTellers(List<TellerDv> tellers) {
		confirm.setTellers(tellers);
	}

	@Override
	public Long getTeller() {
		return confirm.getTeller();
	}

	@Override
	public Date getDate() {
		return transDate.getValue();
	}

	@Override
	public int getType() {
		return transType.getSelectedIndex() + 1;
	}

}
