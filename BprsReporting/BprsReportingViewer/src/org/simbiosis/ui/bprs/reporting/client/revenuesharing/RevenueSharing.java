package org.simbiosis.ui.bprs.reporting.client.revenuesharing;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class RevenueSharing extends FormWidget implements IRevenueSharing {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, RevenueSharing> {
	}

	@UiField
	ListBox period;
	@UiField
	DockLayoutPanel reportFrame;

	public RevenueSharing() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasView(true);
		setHasExportXls(true);
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
	public void loadReport() {
		String value = period.getValue(period.getSelectedIndex());
		String[] values = value.split(";");
		String strValues = "month=" + values[0] + "&year=" + values[1];
		String url = "/BprsReportingService/getRevenueSharing?" + strValues;
		Frame frame = new Frame(url);
		frame.setSize("100%", "100%");
		reportFrame.clear();
		reportFrame.add(frame);
	}

	@Override
	public void exportReport() {
		String value = period.getValue(period.getSelectedIndex());
		String[] values = value.split(";");
		String strValues = "month=" + values[0] + "&year=" + values[1];
		String url = "/BprsReportingService/getRevenueSharingXls?" + strValues;
		Frame frame = Frame.wrap(Document.get().getElementById(
				"__downloadFrame"));
		frame.setUrl(url);
	}

	@Override
	public void addPeriods(String text, String value) {
		period.addItem(text, value);
	}

}
