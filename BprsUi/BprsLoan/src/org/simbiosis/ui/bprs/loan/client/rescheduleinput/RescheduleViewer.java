package org.simbiosis.ui.bprs.loan.client.rescheduleinput;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.LoanDv;
import org.simbiosis.ui.bprs.common.shared.LoanScheduleDv;
import org.simbiosis.ui.bprs.loan.client.viewer.LoanFormViewer;
import org.simbiosis.ui.bprs.loan.shared.UserDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class RescheduleViewer extends FormWidget implements IRescheduleInput {

	Activity activity;
	
	private static RescheduleViewerUiBinder uiBinder = GWT
			.create(RescheduleViewerUiBinder.class);

	interface RescheduleViewerUiBinder extends
			UiBinder<Widget, RescheduleViewer> {
	}

	@UiField
	LoanFormViewer loanFormViewer;

	public RescheduleViewer() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasNew(true);
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
	public void showData(LoanDv loanDv) {
		loanFormViewer.showData(loanDv);
	}

	@Override
	public LoanDv getSelectedData() {
		return loanFormViewer.getSelectedData();
	}

	@Override
	public void setLoanProductList(List<DataDv> listLoanProduct) {
	}

	@Override
	public void setLoanAO(List<UserDv> listAo) {
	}

	@Override
	public void setBISektor(List<String> listBISektor) {
	}

	@Override
	public void setSchedule(List<LoanScheduleDv> newSchedule) {
	}

	@Override
	public LoanDv getEditedData() {
		return loanFormViewer.getSelectedData();
	}

}
