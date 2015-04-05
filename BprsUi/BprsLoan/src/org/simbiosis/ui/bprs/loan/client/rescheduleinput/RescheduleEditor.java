package org.simbiosis.ui.bprs.loan.client.rescheduleinput;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.client.customerhelper.SimpleCustomerViewer;
import org.simbiosis.ui.bprs.common.client.savinghelper.SavingInfoShort;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.GuaranteeDv;
import org.simbiosis.ui.bprs.common.shared.LoanDv;
import org.simbiosis.ui.bprs.common.shared.LoanScheduleDv;
import org.simbiosis.ui.bprs.loan.client.editor.GuaranteeListEditorTable;
import org.simbiosis.ui.bprs.loan.client.handler.ScheduleHandler;
import org.simbiosis.ui.bprs.loan.client.loaninput.LoanEditorWidget;
import org.simbiosis.ui.bprs.loan.shared.LoanScheduleGenDv;
import org.simbiosis.ui.bprs.loan.shared.UserDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class RescheduleEditor extends FormWidget implements IRescheduleInput {

	Activity activity;

	private static RescheduleEditorUiBinder uiBinder = GWT
			.create(RescheduleEditorUiBinder.class);

	interface RescheduleEditorUiBinder extends
			UiBinder<Widget, RescheduleEditor> {
	}

	@UiField
	SimpleCustomerViewer customer;
	@UiField
	SavingInfoShort saving;
	@UiField
	LoanEditorWidget loan;
	@UiField
	GuaranteeListEditorTable guarantee;

	LoanDv selectedData;

	public RescheduleEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasSave(true);
		setHasBack(true);
	}

	public Activity getActivity() {
		return activity;
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
		if (appStatus.isLogin()) {
			activity.loadCommonList();
			//
			loan.setScheduleHandler(new ScheduleHandler() {

				@Override
				public void generate(LoanScheduleGenDv data) {
					getActivity().generate(data);
				}
			});
		}
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void showData(LoanDv loanDv) {
		selectedData = loanDv;
		customer.showData(selectedData.getCustomer());
		saving.showData(selectedData.getSaving());
		guarantee.clear();
		for (GuaranteeDv gDv : selectedData.getGuarantees()) {
			guarantee.addRow(gDv);
		}
		//
		loan.showData(selectedData);
	}

	@Override
	public LoanDv getSelectedData() {
		return loan.getData();
	}

	@Override
	public void setLoanProductList(List<DataDv> financingProductList) {
		loan.setLoanProductList(financingProductList);
	}

	@Override
	public void setSchedule(List<LoanScheduleDv> newSchedule) {
		loan.setSchedules(newSchedule);
	}

	@Override
	public void setLoanAO(List<UserDv> listAo) {
		loan.setLoanAO(listAo);
	}

	@Override
	public void setBISektor(List<String> listBISektor) {
		loan.setBISektor(listBISektor);
	}

	@Override
	public LoanDv getEditedData() {
		LoanDv loanDv = loan.getData();
		loanDv.setCustomer(selectedData.getCustomer());
		return loanDv;
	}

}
