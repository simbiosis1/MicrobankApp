package org.simbiosis.ui.bprs.loan.client.viewer;

import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.client.customerhelper.SimpleCustomerViewer;
import org.simbiosis.ui.bprs.common.client.savinghelper.SavingInfoShort;
import org.simbiosis.ui.bprs.common.shared.GuaranteeDv;
import org.simbiosis.ui.bprs.common.shared.LoanDv;
import org.simbiosis.ui.bprs.loan.client.editor.GuaranteeListViewerTable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class LoanFormViewer extends FormWidget {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, LoanFormViewer> {
	}

	@UiField
	SimpleCustomerViewer customer;
	@UiField
	SavingInfoShort saving;
	@UiField
	LoanViewerWidget loan;
	@UiField
	GuaranteeListViewerTable guarantee;

	LoanDv selectedData;

	public LoanFormViewer() {
		initWidget(uiBinder.createAndBindUi(this));
	}

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

	public LoanDv getSelectedData() {
		return selectedData;
	}

}
