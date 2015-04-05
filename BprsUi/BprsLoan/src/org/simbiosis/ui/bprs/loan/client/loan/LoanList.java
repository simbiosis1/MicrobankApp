package org.simbiosis.ui.bprs.loan.client.loan;

import java.util.Date;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.client.loanhelper.FindLoan;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class LoanList extends FormWidget implements ILoanList {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, LoanList> {
	}

	@UiField
	FindLoan findLoan;

	public LoanList() {
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
	public void showData(CariDataDv cariDataDv) {
		findLoan.showData(cariDataDv);
	}

	@Override
	public DataDv getSelectedData() {
		return findLoan.getSelectedData();
	}

	@Override
	public Boolean isHasCode() {
		return findLoan.isHasCode();
	}

	@Override
	public String getCode() {
		return findLoan.getCode();
	}

	@Override
	public Boolean isHasName() {
		return findLoan.isHasName();
	}

	@Override
	public String getName() {
		return findLoan.getName();
	}

	@Override
	public Boolean isHasDob() {
		return findLoan.isHasDob();
	}

	@Override
	public Date getDob() {
		return findLoan.getDob();
	}

}
