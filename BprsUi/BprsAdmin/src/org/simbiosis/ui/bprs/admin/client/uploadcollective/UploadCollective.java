package org.simbiosis.ui.bprs.admin.client.uploadcollective;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.admin.shared.CoaDv;
import org.simbiosis.ui.bprs.admin.shared.TransferCollectiveDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
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
	TextBox description;
	@UiField
	VerticalPanel dataPanel;

	CopyData copyData = new CopyData();
	ConfirmGajiCollective confirmGaji = new ConfirmGajiCollective();
	ConfirmPotonganCollective confirmPotongan = new ConfirmPotonganCollective();
	ConfirmTransferCollective confirmTransfer = new ConfirmTransferCollective();

	public UploadCollective() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		transType.addItem("Pilih jenis transaksi");
		transType.addItem("Gaji, Bonus, Honor");
		transType.addItem("Potongan");
		transType.addItem("Transfer antar rek");
		//
		//
		transDate.setValue(new Date());
		transDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd-MM-yyyy")));
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
		//
		copyData.setParent(this);
		confirmGaji.setParent(this);
		confirmPotongan.setParent(this);
		confirmTransfer.setParent(this);
		//
		dataPanel.add(copyData);
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	public void gotoFirst() {
		dataPanel.clear();
		copyData.clear();
		dataPanel.add(copyData);
	}

	public void confirmTrans() {
		if (transType.getSelectedIndex() == 0) {
			Window.alert("Anda harus memilih salah satu fungsi");
		} else {
			dataPanel.clear();
			switch (transType.getSelectedIndex()) {
			case 1:
				dataPanel.add(confirmGaji);
				activity.confirmGajiPotongan();
				break;
			case 2:
				dataPanel.add(confirmPotongan);
				activity.confirmGajiPotongan();
				break;
			case 3:
				dataPanel.add(confirmTransfer);
				activity.confirmTransfer();
				break;
			}
		}
	}

	public void executeUpload() {
		switch (transType.getSelectedIndex()) {
		case 1:
			activity.executeGaji();
			break;
		case 2:
			activity.executePotongan();
			break;
		case 3:
			activity.executeTransfer();
			break;
		}
	}

	@Override
	public void confirmUpload(List<TransferCollectiveDv> data) {
		switch (transType.getSelectedIndex()) {
		case 1:
			confirmGaji.setData(data);
			break;
		case 2:
			confirmPotongan.setData(data);
			break;
		case 3:
			confirmTransfer.setData(data);
			break;
		}
	}

	@Override
	public String getDescription() {
		return description.getText();
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
		case 2:
			return confirmPotongan.getData();
		case 3:
			return confirmTransfer.getData();
		default:
			return new ArrayList<TransferCollectiveDv>();
		}
	}

	@Override
	public void setCoa(List<CoaDv> coas) {
		confirmGaji.setCoa(coas);
		confirmPotongan.setCoa(coas);
	}

	@Override
	public ConfirmGajiCollective getFormGaji() {
		return confirmGaji;
	}

	@Override
	public ConfirmPotonganCollective getFormPotongan() {
		return confirmPotongan;
	}
	
	@Override
	public Date getDate(){
		return transDate.getValue();
	}

	@Override
	public Integer getSource() {
		if (transType.getSelectedIndex() == 1)
			return confirmGaji.getSource();
		return 0;
	}

}
