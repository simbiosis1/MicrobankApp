package org.simbiosis.report.loan.ui.client.remedial;

import java.util.ArrayList;
import java.util.List;

import org.kembang.module.client.editor.BranchListBox;
import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.kembang.module.shared.SimpleBranchDv;
import org.kembang.module.shared.UserDv;
import org.simbiosis.report.loan.ui.client.editor.UserListBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class Remedial extends FormWidget implements IRemedial {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, Remedial> {
	}

	@UiField
	BranchListBox branch;
	@UiField
	UserListBox ao;
	@UiField
	DockLayoutPanel reportFrame;
	@UiField
	ListBox all;

	String reportService = "/BprsLoanReportService";

	List<UserDv> aoList = new ArrayList<UserDv>();

	public Remedial() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasView(true);
		setHasExportXls(true);
		//
		all.addItem("Semua", "1");
		all.addItem("Yang belum bayar", "0");
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
		String strBranch = "branch=" + branch.getValue();
		String strAo = "&ao=" + ao.getValue();
		String strAll = "&all=" + all.getValue(all.getSelectedIndex());
		String url = reportService + "/" + report + "?" + strBranch + strAo
				+ strAll;
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
		createReport("getLoanRemedialXls", true);
	}

	@Override
	public void loadReport() {
		createReport("getLoanRemedial", false);
	}

	@Override
	public void addBranchList(List<SimpleBranchDv> branchList) {
		branch.setList(branchList);
		activity.loadAo(branch.getValue());
	}

	@Override
	public void addAoList(List<UserDv> aoList) {
		this.aoList.clear();
		this.aoList.addAll(aoList);
		ao.setList(this.aoList);
	}

	@UiHandler("branch")
	void onChange(ChangeEvent event) {
		activity.loadAo(branch.getValue());
	}

}
