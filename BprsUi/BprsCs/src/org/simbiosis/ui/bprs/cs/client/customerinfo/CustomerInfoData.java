package org.simbiosis.ui.bprs.cs.client.customerinfo;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.DataDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

public class CustomerInfoData extends FormWidget implements ICustomerInfoData {

	private static CustomerInfoDataUiBinder uiBinder = GWT
			.create(CustomerInfoDataUiBinder.class);

	interface CustomerInfoDataUiBinder extends
			UiBinder<Widget, CustomerInfoData> {
	}

	public CustomerInfoData() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		// TODO Auto-generated method stub

	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void showData(DataDv dataDv) {
		// TODO Auto-generated method stub

	}

}
