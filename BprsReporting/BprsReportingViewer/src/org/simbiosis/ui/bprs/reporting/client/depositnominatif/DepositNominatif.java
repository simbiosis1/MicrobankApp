package org.simbiosis.ui.bprs.reporting.client.depositnominatif;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.editor.BranchListBox;
import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.kembang.module.shared.SimpleBranchDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class DepositNominatif extends FormWidget implements IDepositNominatif {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, DepositNominatif> {
	}

	@UiField
	BranchListBox branch;
	@UiField
	DateBox date;
	@UiField
	DockLayoutPanel reportFrame;

	public DepositNominatif() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasView(true);
		setHasExportXls(true);
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

	private void createReport(String report, boolean download) {
		String strBranch = "branch=" + branch.getValue().toString();
		String strDate = "date=" + date.getTextBox().getText();
		String strProduct = "product=0";
		String url = "/BprsReportingService/" + report + "?" + strBranch + "&"
				+ strDate + "&" + strProduct;
		Frame frame = null;
  		if (download) {
			frame = Frame
					.wrap(Document.get().getElementById("__downloadFrame"));
			frame.setUrl(url);
		} else {
			frame = new Frame(url);
			frame.setSize("100%", "100%");
			reportFrame.clear();
			reportFrame.add(frame);
		}
	}

	@Override
	public void exportReport() {
		createReport("getDepositNominatifXls", true);
	}

	@Override
	public void loadReport() {
		createReport("getDepositNominatifSummary", false);
	}

	@Override
	public void addBranchList(List<SimpleBranchDv> branchList) {
		branch.setList(branchList);
	}
}
