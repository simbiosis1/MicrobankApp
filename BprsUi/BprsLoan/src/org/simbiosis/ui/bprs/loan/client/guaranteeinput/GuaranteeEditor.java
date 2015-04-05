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

public class GuaranteeEditor extends FormWidget implements IGuaranteeInput {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, GuaranteeEditor> {
	}

	@UiField
	SimpleCustomerViewer anggota;
	@UiField
	GuaranteeEditorWidget guarantee;

	GuaranteeDv selectedData;

	public GuaranteeEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasSave(true);
		setHasBack(true);
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
		if (appStatus.isLogin()) {
			activity.loadCommonList();
			//
			guarantee.setActivity(activity);
		}
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void showData(GuaranteeDv loanDv) {
		selectedData = loanDv;
		anggota.showData(selectedData.getCustomer());
		guarantee.showData(selectedData);
	}

	@Override
	public GuaranteeDv getSelectedData() {
		return selectedData;
	}

	@Override
	public GuaranteeDv getEditedData() {
		GuaranteeDv loanDv = guarantee.getData();
		loanDv.setCustomer(selectedData.getCustomer());
		return loanDv;
	}

	@Override
	public void setGuaranteeTypes(List<TypeDv> typeList) {
		guarantee.setGuaranteeTypes(typeList);
	}

}
