package org.simbiosis.ui.bprs.loan.client.loaninput;

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

public class LoanViewer extends FormWidget implements ILoanInput {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, LoanViewer> {
	}

	@UiField
	LoanFormViewer loanFormViewer;

	public LoanViewer() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasEdit(true);
		setHasBack(true);
		setHasExportPdf(true);
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
	public LoanDv getEditedData() {
		return null;
	}

	@Override
	public void setLoanProductList(List<DataDv> listSavingProduct) {
	}

	@Override
	public void setSchedule(List<LoanScheduleDv> newSchedule) {
	}

	@Override
	public void setLoanAO(List<UserDv> listAo) {
	}

	@Override
	public void setBISektor(List<String> listBISektor) {
	}

}
