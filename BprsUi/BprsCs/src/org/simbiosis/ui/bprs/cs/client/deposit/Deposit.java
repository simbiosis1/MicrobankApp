package org.simbiosis.ui.bprs.cs.client.deposit;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.client.deposithelper.FindDeposit;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class Deposit extends FormWidget implements IDeposit {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, Deposit> {
	}

	@UiField
	FindDeposit findDeposit;

	public Deposit() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasNew(true);
		setHasView(true);
		setHasSearch(true);
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

	@Override
	public void searchData() {
		activity.searchData(findDeposit.isHasCode(), findDeposit.isHasName(),
				findDeposit.isHasDob(), findDeposit.getCode(),
				findDeposit.getName(), findDeposit.getDob());
	}

	@Override
	public void showData(CariDataDv cariDataDv) {
		findDeposit.showData(cariDataDv);
	}

	@Override
	public DataDv getSelectedData() {
		return findDeposit.getSelectedData();
	}

}
