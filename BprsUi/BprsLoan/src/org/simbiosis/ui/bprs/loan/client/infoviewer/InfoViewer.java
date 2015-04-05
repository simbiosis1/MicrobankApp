package org.simbiosis.ui.bprs.loan.client.infoviewer;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.LoanDv;
import org.simbiosis.ui.bprs.loan.client.viewer.LoanFormViewer;
import org.simbiosis.ui.bprs.loan.client.viewer.LoanPaymentViewer;
import org.simbiosis.ui.bprs.loan.shared.InfoLoanDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class InfoViewer extends FormWidget implements IInfoViewer {

	Activity activity;

	private static InfoViewerUiBinder uiBinder = GWT
			.create(InfoViewerUiBinder.class);

	interface InfoViewerUiBinder extends UiBinder<Widget, InfoViewer> {
	}

	LoanDv selectedData;

	@UiField
	LoanFormViewer loanFormViewer;
	@UiField
	LoanPaymentViewer loanPaymentViewer;

	public InfoViewer() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasExportPdf(true);
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
	public void showLoan(LoanDv loanDv) {
		selectedData = loanDv;
		loanFormViewer.showData(loanDv);
	}

	@Override
	public void showPayment(InfoLoanDv transDv) {
		loanPaymentViewer.showData(transDv);
	}

	@Override
	public LoanDv getSelectedData() {
		return selectedData;
	}

}
