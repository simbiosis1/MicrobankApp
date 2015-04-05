package org.simbiosis.ui.bprs.loan.client.guaranteeinput;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.client.customerhelper.SimpleCustomerViewer;
import org.simbiosis.ui.bprs.common.shared.GuaranteeDv;
import org.simbiosis.ui.bprs.loan.shared.TypeDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class GuaranteeViewer extends FormWidget implements IGuaranteeInput {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, GuaranteeViewer> {
	}

	@UiField
	SimpleCustomerViewer anggota;
	@UiField
	GuaranteeViewerWidget guarantee;

	public GuaranteeViewer() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasEdit(true);
		setHasBack(true);
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
	public void showData(GuaranteeDv loanDv) {
		anggota.showData(loanDv.getCustomer());
		guarantee.showData(loanDv);
	}

	@Override
	public GuaranteeDv getSelectedData() {
		return guarantee.getData();
	}

	@Override
	public GuaranteeDv getEditedData() {
		return null;
	}

	@Override
	public void setGuaranteeTypes(List<TypeDv> typeList) {
	}

}
