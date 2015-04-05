package org.simbiosis.ui.bprs.cs.client.saving;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.client.savinghelper.FindSaving;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class Saving extends FormWidget implements ISaving {

	Activity activity;

	private static FormListTabunganUiBinder uiBinder = GWT
			.create(FormListTabunganUiBinder.class);

	interface FormListTabunganUiBinder extends UiBinder<Widget, Saving> {
	}

	@UiField
	FindSaving cariSimpanan;

	public Saving() {
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
		if (appStatus.isLogin()){
			activity.initViewerEditor();
		}
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void searchData() {
		activity.searchData(cariSimpanan.isHasCode(), cariSimpanan.getCode(),
				cariSimpanan.isHasName(), cariSimpanan.isHasDob(),
				cariSimpanan.getName(), cariSimpanan.getDob());
	}

	@Override
	public void showData(CariDataDv cariDataDv) {
		cariSimpanan.showData(cariDataDv);
	}

	@Override
	public DataDv getSelectedData() {
		return cariSimpanan.getSelectedData();
	}

}
