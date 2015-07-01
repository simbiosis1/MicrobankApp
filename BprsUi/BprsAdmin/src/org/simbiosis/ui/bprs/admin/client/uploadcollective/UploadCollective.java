package org.simbiosis.ui.bprs.admin.client.uploadcollective;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.admin.shared.CoaDv;
import org.simbiosis.ui.bprs.admin.shared.TransferCollectiveDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UploadCollective extends FormWidget implements IUploadCollective {

	Activity activity;

	private static UploadCollectiveUiBinder uiBinder = GWT
			.create(UploadCollectiveUiBinder.class);

	interface UploadCollectiveUiBinder extends
			UiBinder<Widget, UploadCollective> {
	}

	@UiField
	DateLabel transDate;
	@UiField
	ListBox transType;
	@UiField
	VerticalPanel dataPanel;

	CopyData copyData = new CopyData();
	ConfirmGajiCollective confirmGaji = new ConfirmGajiCollective();

	public UploadCollective() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		transType.addItem("Pilih jenis transaksi");
		transType.addItem("Gaji");
		transType.addItem("Transfer antar rek");
		//
		transDate.setValue(new Date());
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
		//
		copyData.setParent(this);
		confirmGaji.setParent(this);
		//
		dataPanel.add(copyData);
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	public void confirmTrans() {
		dataPanel.clear();
		switch (transType.getSelectedIndex()) {
		case 1:
			dataPanel.add(confirmGaji);
			activity.confirmGaji();
			break;
		}
		//
	}

	public void executeTransfer() {
		activity.executeTransfer();
	}

	@Override
	public void confirmTransfer(List<TransferCollectiveDv> data) {
		switch (transType.getSelectedIndex()) {
		case 1:
			confirmGaji.setData(data);
			break;
		}
	}

	@Override
	public String getSrcData() {
		return copyData.getData();
	}

	@Override
	public List<TransferCollectiveDv> getData() {
		switch (transType.getSelectedIndex()) {
		case 1:
			return confirmGaji.getData();
		default:
			return new ArrayList<TransferCollectiveDv>();
		}
	}

	@Override
	public void setCoa(List<CoaDv> coas) {
		confirmGaji.setCoa(coas);
	}

	@Override
	public Long getCoa() {
		return confirmGaji.getCoa();
	}

}
