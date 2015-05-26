package org.simbiosis.ui.bprs.admin.client.uploadcollective;

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
	VerticalPanel dataPanel;

	CopyData copyData = new CopyData();
	ConfirmCollective confirm = new ConfirmCollective();

	public UploadCollective() {
		initWidget(uiBinder.createAndBindUi(this));
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

	public void executeTransfer(){
		activity.executeTransfer();
	}
	
	@Override
	public void confirmTransfer(List<TransferCollectiveDv> data) {
		confirm.setData(data);
	}

	@Override
	public String getSrcData() {
		return copyData.getData();
	}

	@Override
	public List<TransferCollectiveDv> getTransferData() {
		return confirm.getData();
	}

	@Override
	public void setCoa(List<CoaDv> coas) {
		confirm.setCoa(coas);
	}

	@Override
	public Long getCoa() {
		return confirm.getCoa();
	}

}
