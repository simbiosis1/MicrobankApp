package org.simbiosis.ui.bprs.loan.client.viewer;

import org.kembang.grid.client.FlexTableHelper;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;
import org.simbiosis.ui.bprs.loan.client.editor.PaymentViewerTable;
import org.simbiosis.ui.bprs.loan.shared.InfoLoanDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LoanPaymentViewer extends Composite implements Editor<InfoLoanDv> {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, LoanPaymentViewer> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<InfoLoanDv, LoanPaymentViewer> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label osPrincipal;
	@UiField
	Label osMargin;
	@UiField
	Label osTotal;
	@UiField
	Label quality;
	//@UiField
	//Label osDueValue;
	//@UiField
	//Label osDueCount;
	@UiField
	PaymentViewerTable loanPayments;
	@UiField
	FlexTable schedFooter;

	String[] widthsText = { "28px", "100px", "150px", "150px", "150px" };
	String[] footerText = { "", "Total", "0", "0", "0" };
	NumberFormat numberFormat = NumberFormat.getFormat("####,###.00");

	public LoanPaymentViewer() {
		initWidget(uiBinder.createAndBindUi(this));
		// format table hasil pencarian
		FlexTableHelper.setupHeader(schedFooter, 5, footerText, widthsText);
		//
		driver.initialize(this);
	}

	public void showData(InfoLoanDv data) {
		// Hitung angka
		double principal = 0;
		double margin = 0;
		double total = 0;
		for (TransactionDv sched : data.getLoanPayments()) {
			principal += sched.getPrincipal();
			margin += sched.getMargin();
			total += sched.getTotal();
		}
		schedFooter.getCellFormatter().setHorizontalAlignment(0, 2,
				HasHorizontalAlignment.ALIGN_RIGHT);
		FlexTableHelper.setTextFooter(schedFooter, 2,
				numberFormat.format(principal));
		schedFooter.getCellFormatter().setHorizontalAlignment(0, 3,
				HasHorizontalAlignment.ALIGN_RIGHT);
		FlexTableHelper.setTextFooter(schedFooter, 3,
				numberFormat.format(margin));
		schedFooter.getCellFormatter().setHorizontalAlignment(0, 4,
				HasHorizontalAlignment.ALIGN_RIGHT);
		FlexTableHelper.setTextFooter(schedFooter, 4,
				numberFormat.format(total));
		//
		driver.edit(data);
	}
}
