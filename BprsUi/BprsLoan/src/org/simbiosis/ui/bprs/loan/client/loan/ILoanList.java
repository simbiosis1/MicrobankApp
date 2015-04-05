package org.simbiosis.ui.bprs.loan.client.loan;

import java.util.Date;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;

public interface ILoanList {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	public void showData(CariDataDv cariDataDv);

	public DataDv getSelectedData();

	//
	public Boolean isHasCode();

	public String getCode();

	public Boolean isHasName();

	public String getName();

	public Boolean isHasDob();

	public Date getDob();

	//
	public abstract class Activity extends FormActivity {
	}

}
