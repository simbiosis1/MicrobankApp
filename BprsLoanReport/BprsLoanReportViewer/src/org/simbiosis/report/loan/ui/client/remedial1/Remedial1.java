package org.simbiosis.report.loan.ui.client.remedial1;

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
import com.google.gwt.user.client.ui.Widget;

public class Remedial1 extends FormWidget implements IRemedial1 {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, Remedial1> {
	}

	@UiField
	BranchListBox branch;
	@UiField
	UserListBox ao;
	@UiField
	DockLayoutPanel reportFrame;

	String reportService = "/BprsLoanReportService";

	@UiHandler("branch")
	void onChange(ChangeEvent event) {
		activity.loadAo(branch.getValue());
	}

	List<UserDv> aoList = new ArrayList<UserDv>();

	public Remedial1() {
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

	private void createReport(String report, boolean download) {
		String strBranch = "branch=" + branch.getValue();
		String strAo = "&ao=" + ao.getValue();
		String url = reportService + "/" + report + "?" + strBranch + strAo;
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

}
