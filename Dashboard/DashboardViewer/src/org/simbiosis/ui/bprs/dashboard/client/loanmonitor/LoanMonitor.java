package org.simbiosis.ui.bprs.dashboard.client.loanmonitor;

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

public class LoanMonitor extends FormWidget implements ILoanMonitor {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, LoanMonitor> {
	}

	@UiField
	BranchListBox branch;
	@UiField
	DateBox date;
	@UiField
	DockLayoutPanel reportFrame;
	@UiField
	ListBox type;

	public LoanMonitor() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasView(true);
		setHasExportXls(true);
		// init format batasTanggal
		date.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd-MM-yyyy")));
		date.setValue(new Date());
		//
		type.addItem("Pencairan");
		type.addItem("Angsuran");
		type.addItem("NPF");
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
		String strBranch = "branch=" + branch.getValue().toString();
		String strDate = "date=" + date.getTextBox().getText();
		Frame frame = new Frame(
				"/DashboardService/getDashboardLoanDroppingXls?" + strBranch
						+ "&" + strDate);
		frame.setSize("100%", "100%");
		reportFrame.clear();
		reportFrame.add(frame);
	}

	@Override
	public void loadReport() {
		String strBranch = "branch=" + branch.getValue().toString();
		String strDate = "date=" + date.getTextBox().getText();
		String strType = "";
		switch (type.getSelectedIndex()) {
		case 0:
			strType = "/DashboardService/getDashboardLoanDropping?";
			break;
		case 1:
			strType = "/DashboardService/getLoanAngsuran?";
			break;
		case 2:
			strType = "/DashboardService/getNPF?";
			break;
		default:
			break;
		}
		String url = strType + strBranch + "&" + strDate;
		Frame frame = new Frame(url);
		frame.setSize("100%", "100%");
		reportFrame.clear();
		reportFrame.add(frame);
	}

	@Override
	public void addBranchList(List<SimpleBranchDv> branchList) {
		branch.setList(branchList);
	}

}
