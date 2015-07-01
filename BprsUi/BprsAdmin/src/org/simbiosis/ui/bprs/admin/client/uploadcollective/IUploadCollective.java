package org.simbiosis.ui.bprs.admin.client.uploadcollective;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.admin.shared.CoaDv;
import org.simbiosis.ui.bprs.admin.shared.TransferCollectiveDv;

public interface IUploadCollective {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void confirmTransfer(List<TransferCollectiveDv> data);

	String getSrcData();

	List<TransferCollectiveDv> getData();

	void setCoa(List<CoaDv> coas);

	Long getCoa();

	public abstract class Activity extends FormActivity {
		public abstract void confirmGaji();

		public abstract void executeTransfer();
	}

}
