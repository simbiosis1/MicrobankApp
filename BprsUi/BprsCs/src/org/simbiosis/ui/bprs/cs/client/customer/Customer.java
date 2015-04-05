package org.simbiosis.ui.bprs.cs.client.customer;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.client.customerhelper.CustomerFind;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class Customer extends FormWidget implements ICustomer {

	Activity activity;

	private static FormListAnggotaUiBinder uiBinder = GWT
			.create(FormListAnggotaUiBinder.class);

	interface FormListAnggotaUiBinder extends UiBinder<Widget, Customer> {
	}

	@UiField
	CustomerFind cariAnggota;

	public Customer() {
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
		activity.searchData(cariAnggota.isHasName(), cariAnggota.isHasDob(),
				cariAnggota.getName(), cariAnggota.getDob());
	}

	@Override
	public void showData(CariDataDv cariDataDv) {
		cariAnggota.showData(cariDataDv);
	}

	@Override
	public DataDv getSelectedData() {
		return cariAnggota.getSelectedData();
	}

}
