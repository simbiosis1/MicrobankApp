package org.simbiosis.ui.bprs.dashboard.client.tks;

import java.util.Date;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class DashboardTks extends FormWidget implements IDashboardTks {
	Activity activity;
	private static DashboardTksUiBinder uiBinder = GWT
			.create(DashboardTksUiBinder.class);

	interface DashboardTksUiBinder extends UiBinder<Widget, DashboardTks> {
	}

	@UiField
	DateBox date;
	@UiField
	DockLayoutPanel reportFrame;

	public DashboardTks() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasView(true);
		// init format batasTanggal
		date.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd-MM-yyyy")));
		date.setValue(new Date());

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
	public void exportReport() {
		String strDate = "date=" + date.getTextBox().getText();
		Frame frame = new Frame("/DashboardService/getDashboardTksXls?"
				+ strDate);
		frame.setSize("100%", "100%");
		reportFrame.clear();
		reportFrame.add(frame);
	}

	@Override
	public void loadReport() {
		String strDate = "date=" + date.getTextBox().getText();
		Frame frame = new Frame("/DashboardService/getDashboardTks?" + strDate);
		frame.setSize("100%", "100%");
		reportFrame.clear();
		reportFrame.add(frame);
	}

}
