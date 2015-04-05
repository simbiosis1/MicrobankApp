package org.simbiosis.ui.bprs.loan.client.guarantee;

import java.util.Date;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.loan.client.guaranteehelper.FindGuarantee;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class GuaranteeList extends FormWidget implements IGuaranteeList {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, GuaranteeList> {
	}

	@UiField
	FindGuarantee findGuarantee;

	public GuaranteeList() {
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
		findGuarantee.showData(cariDataDv);
	}

	@Override
	public DataDv getSelectedData() {
		return findGuarantee.getSelectedData();
	}

	@Override
	public Boolean isHasCode() {
		return findGuarantee.isHasCode();
	}

	@Override
	public String getCode() {
		return findGuarantee.getCode();
	}

	@Override
	public Boolean isHasName() {
		return findGuarantee.isHasName();
	}

	@Override
	public String getName() {
		return findGuarantee.getName();
	}

	@Override
	public Boolean isHasDob() {
		return findGuarantee.isHasDob();
	}

	@Override
	public Date getDob() {
		return findGuarantee.getDob();
	}

}
