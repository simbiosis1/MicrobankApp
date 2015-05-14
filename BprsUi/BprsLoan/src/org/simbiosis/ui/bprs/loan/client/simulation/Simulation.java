package org.simbiosis.ui.bprs.loan.client.simulation;

import org.kembang.editor.client.DoubleTextBox;
import org.kembang.editor.client.IntegerTextBox;
import org.kembang.grid.client.FlexTableHelper;
import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.LoanScheduleDv;
import org.simbiosis.ui.bprs.loan.client.editor.LoanScheduleType;
import org.simbiosis.ui.bprs.loan.client.editor.LoanScheduleViewerTable;
import org.simbiosis.ui.bprs.loan.shared.LoanScheduleGenDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class Simulation extends FormWidget implements ISimulation,
		Editor<LoanScheduleGenDv> {
	Activity activity;

	private static SimulationUiBinder uiBinder = GWT
			.create(SimulationUiBinder.class);

	interface SimulationUiBinder extends UiBinder<Widget, Simulation> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<LoanScheduleGenDv, Simulation> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	TextBox name;
	@UiField
	DoubleTextBox principal;
	@UiField
	IntegerTextBox tenor;
	@UiField
	DoubleTextBox rate;
	@UiField
	LoanScheduleViewerTable schedules;
	@UiField
	Button btnGenerate;
	@UiField
	FlexTable schedFooter;
	@UiField
	LoanScheduleType scheduleType;

	String[] widthsText = { "28px", "100px", "150px", "150px", "150px" };
	String[] footerText = { "", "Total", "0", "0", "0" };
	NumberFormat numberFormat = NumberFormat.getFormat("####,###.00");

	public Simulation() {
		initWidget(uiBinder.createAndBindUi(this));
		setHasExportPdf(true);
		//
		FlexTableHelper.setupHeader(schedFooter, 5, footerText, widthsText);
		//
		driver.initialize(this);
		driver.edit(new LoanScheduleGenDv());
		initViewer(0, 0, 0);
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

	private void initViewer(double principal, double margin, double total) {
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
	}

	@Override
	public void setData(LoanScheduleGenDv data) {
		// Hitung angka
		double principal = 0;
		double margin = 0;
		double total = 0;
		for (LoanScheduleDv sched : data.getSchedules()) {
			principal += sched.getPrincipal();
			margin += sched.getMargin();
			total += sched.getTotal();
		}
		initViewer(principal, margin, total);
		//
		driver.edit(data);
	}

	@UiHandler("btnGenerate")
	void onBtnGenerateClick(ClickEvent e) {
		activity.generate(driver.flush());
	}

	@Override
	public LoanScheduleGenDv getData() {
		return driver.flush();
	}
}
