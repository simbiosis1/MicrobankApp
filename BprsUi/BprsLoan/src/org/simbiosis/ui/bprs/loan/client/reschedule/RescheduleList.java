package org.simbiosis.ui.bprs.loan.client.reschedule;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.client.loanhelper.FindLoan;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class RescheduleList extends FormWidget implements IRescheduleList {

	Activity activity;

	private static ListPinjamanUiBinder uiBinder = GWT
			.create(ListPinjamanUiBinder.class);

	interface ListPinjamanUiBinder extends UiBinder<Widget, RescheduleList> {
	}

	@UiField
	FindLoan findLoan;

	public RescheduleList() {
		initWidget(uiBinder.createAndBindUi(this));
		//
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
		activity.searchData(findLoan.isHasCode(), findLoan.isHasName(),
				findLoan.isHasDob(), findLoan.getCode(), findLoan.getName(),
				findLoan.getDob());
	}

	@Override
	public void showData(CariDataDv cariDataDv) {
		findLoan.showData(cariDataDv);
	}

	@Override
	public DataDv getSelectedData() {
		return findLoan.getSelectedData();
	}

}
