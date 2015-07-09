package org.simbiosis.ui.bprs.admin.client.uploadcollective;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.admin.shared.CoaDv;
import org.simbiosis.ui.bprs.admin.shared.TransferCollectiveDv;

public interface IUploadCollective {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void confirmUpload(List<TransferCollectiveDv> data);

	String getDescription();

	String getSrcData();

	List<TransferCollectiveDv> getData();

	void setCoa(List<CoaDv> coas);

	Integer getSource();

	Date getDate();

	ConfirmGajiCollective getFormGaji();

	ConfirmPotonganCollective getFormPotongan();

	void gotoFirst();

	public abstract class Activity extends FormActivity {
		public abstract void confirmGajiPotongan();

		public abstract void confirmTransfer();

		public abstract void executeGaji();
		
		public abstract void executePotongan();

		public abstract void executeTransfer();
	}

}
