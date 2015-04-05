package org.simbiosis.ui.bprs.reporting.client.neracapercobaan;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.editor.BranchListBox;
import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.kembang.module.shared.SimpleBranchDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class NeracaPercobaan extends FormWidget implements INeracaPercobaan {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, NeracaPercobaan> {
	}

	@UiField
	BranchListBox branch;
	@UiField
	ListBox type;
	@UiField
	DateBox date;
	@UiField
	DockLayoutPanel reportFrame;

	public NeracaPercobaan() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		date.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd-MM-yyyy")));
		date.setValue(new Date());
		//
		setHasView(true);
		setHasExportXls(true);
		//
		type.addItem("Neraca Percobaan Harian");
		type.addItem("Neraca Percobaan Bulanan");
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

	public void createReport(String report) {
		String strBranch = "branch=" + branch.getValue().toString();
		String strType = "/BprsReportingService/";
		String strDate = "date=" + date.getTextBox().getText();
		switch (type.getSelectedIndex()) {
		case 0:
			strType += "getNeracaPercobaan" + report;
			break;
		case 1:
			strType += "getNeracaPercobaanBulanan" + report;
			break;
		default:
			break;
		}
		String url = strType + "?" + strBranch + "&" + strDate;
		Frame frame = new Frame(url);
		frame.setSize("100%", "100%");
		reportFrame.clear();
		reportFrame.add(frame);
	}

	@Override
	public void exportReport() {
		createReport("Xls");
	}

	@Override
	public void loadReport() {
		createReport("");
	}

	@Override
	public void addBranchList(List<SimpleBranchDv> branchList) {
		branch.setList(branchList);
	}

}
