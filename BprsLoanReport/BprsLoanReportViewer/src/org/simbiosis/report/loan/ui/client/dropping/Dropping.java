package org.simbiosis.report.loan.ui.client.dropping;

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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class Dropping extends FormWidget implements IDropping {

	Activity activity;
	private static TransactionUiBinder uiBinder = GWT
			.create(TransactionUiBinder.class);

	interface TransactionUiBinder extends UiBinder<Widget, Dropping> {
	}

	@UiField
	BranchListBox branch;
	@UiField
	DateBox beginDate;
	@UiField
	DateBox endDate;
	@UiField
	DockLayoutPanel reportFrame;

	String reportService = "/BprsLoanReportService";

	DateTimeFormat dtf = DateTimeFormat.getFormat("dd-MM-yyyy");

	public Dropping() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasView(true);
		setHasExportXls(true);
		//
		// init format batasTanggal
		beginDate.setFormat(new DateBox.DefaultFormat(dtf));
		beginDate.setValue(new Date());
		endDate.setFormat(new DateBox.DefaultFormat(dtf));
		endDate.setValue(new Date());

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
	public void setListBranch(List<SimpleBranchDv> branches) {
		branch.setList(branches);
	}

	private void createReport(String report, boolean download) {
		String strBranch = "branch=" + branch.getValue().toString();
		String strBeginDate = "beginDate=" + beginDate.getTextBox().getText();
		String strEndDate = "endDate=" + endDate.getTextBox().getText();
		String url = reportService + "/" + report + "?" + strBranch + "&"
				+ strBeginDate + "&" + strEndDate;
		Frame frame = null;
		if (download) {
			Window.open(url, "_blank", null);
		} else {
			frame = new Frame(url);
			frame.setSize("100%", "100%");
			reportFrame.clear();
			reportFrame.add(frame);
		}
	}

	@Override
	public void exportReport() {
		createReport("getLoanDroppingXls", true);
	}

	@Override
	public void loadReport() {
		createReport("getLoanDropping", false);
	}

}
