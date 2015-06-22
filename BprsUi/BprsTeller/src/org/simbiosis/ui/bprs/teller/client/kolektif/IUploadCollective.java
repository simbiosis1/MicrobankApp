package org.simbiosis.ui.bprs.teller.client.kolektif;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.teller.shared.TellerDv;
import org.simbiosis.ui.bprs.teller.shared.UploadCollectiveDv;

public interface IUploadCollective {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();
	
	void gotoFirstForm();

	void confirmTransfer(List<UploadCollectiveDv> data);

	String getSrcData();
	
	List<UploadCollectiveDv> getCollectiveData();

	List<UploadCollectiveDv> getTransferData();

	void setTellers(List<TellerDv> tellers);

	Long getTeller();
	
	Date getDate();
	
	int getType();

	public abstract class Activity extends FormActivity {
		public abstract void confirmUpload(int type, Date date);

		public abstract void executeUpload(int type, Date date, Long teller);
	}

}
