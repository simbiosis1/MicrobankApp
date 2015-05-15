package org.simbiosis.ui.bprs.loan.client.viewer;

import org.kembang.grid.client.FlexTableHelper;
import org.simbiosis.ui.bprs.common.shared.LoanDv;
import org.simbiosis.ui.bprs.common.shared.LoanScheduleDv;
import org.simbiosis.ui.bprs.loan.client.editor.LoanScheduleViewerTable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.Widget;

public class LoanViewerWidget extends Composite implements Editor<LoanDv> {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, LoanViewerWidget> {
	}

	interface Driver extends SimpleBeanEditorDriver<LoanDv, LoanViewerWidget> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label code;
	@UiField
	DateLabel registration;
	@UiField
	DateLabel contractDate;
	@UiField
	Label contract;
	@UiField
	Label strAo;
	@UiField
	Label aoHistory;
	@UiField
	Label strProduct;
	@UiField
	NumberLabel<Double> principal;
	@UiField
	NumberLabel<Double> rate;
	@UiField
	NumberLabel<Integer> tenor;
	@UiField
	NumberLabel<Double> admin;
	@UiField
	NumberLabel<Double> fine;
	@UiField
	Label purpose;
	@UiField
	Label biSektor;
	@UiField
	LoanScheduleViewerTable schedules;
	@UiField
	FlexTable schedFooter;

	String[] widthsText = { "28px", "100px", "150px", "150px", "150px" };
	String[] footerText = { "", "Total", "0", "0", "0" };
	NumberFormat numberFormat = NumberFormat.getFormat("####,###.00");

	public LoanViewerWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		// format table hasil pencarian
		FlexTableHelper.setupHeader(schedFooter, 5, footerText, widthsText);
		//
		driver.initialize(this);
	}

	public void showData(LoanDv loanDv) {
		// Hitung angka
		double principal = 0;
		double margin = 0;
		double total = 0;
		for (LoanScheduleDv sched : loanDv.getSchedules()) {
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
		driver.edit(loanDv);
	}
}
