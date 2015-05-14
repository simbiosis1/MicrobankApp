package org.simbiosis.ui.bprs.loan.client.loaninput;

import java.util.ArrayList;
import java.util.List;

import org.kembang.editor.client.DoubleTextBox;
import org.kembang.editor.client.IntegerTextBox;
import org.kembang.grid.client.FlexTableHelper;
import org.simbiosis.ui.bprs.common.client.editor.ProductListEditor;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.LoanDv;
import org.simbiosis.ui.bprs.common.shared.LoanScheduleDv;
import org.simbiosis.ui.bprs.loan.client.editor.LoanScheduleEditorTable;
import org.simbiosis.ui.bprs.loan.client.editor.LoanScheduleType;
import org.simbiosis.ui.bprs.loan.client.editor.UserListBox;
import org.simbiosis.ui.bprs.loan.client.handler.ScheduleHandler;
import org.simbiosis.ui.bprs.loan.shared.LoanScheduleGenDv;
import org.simbiosis.ui.bprs.loan.shared.UserDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class LoanEditorWidget extends Composite implements Editor<LoanDv> {

	private static PinjamanEditorUiBinder uiBinder = GWT
			.create(PinjamanEditorUiBinder.class);

	interface PinjamanEditorUiBinder extends UiBinder<Widget, LoanEditorWidget> {
	}

	interface Driver extends SimpleBeanEditorDriver<LoanDv, LoanEditorWidget> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	TextBox code;
	@UiField
	Label strRegistration;
	@UiField
	ProductListEditor product;
	@UiField
	DateBox contractDate;
	@UiField
	TextBox contract;
	@UiField
	TextBox purpose;
	@UiField
	ListBox biSektor;
	@UiField
	DoubleTextBox principal;
	@UiField
	DoubleTextBox rate;
	@UiField
	IntegerTextBox tenor;
	@UiField
	LoanScheduleEditorTable schedules;
	@UiField
	Button btnGenerate;
	@UiField
	Button btnAdd;
	@UiField
	Button btnRemove;
	@UiField
	FlexTable schedFooter;
	@UiField
	LoanScheduleType scheduleType;
	@UiField
	UserListBox ao;
	@UiField
	TextBox aoHistory;
	@UiField
	TextBox strAdmin;
	@UiField
	DoubleTextBox fine;

	String[] widthsText = { "28px", "100px", "150px", "150px", "150px" };
	String[] footerText = { "", "Total", "0", "0", "0" };
	NumberFormat numberFormat = NumberFormat.getFormat("####,###.00");
	DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("dd-MM-yyyy");

	List<DataDv> loanProductList = new ArrayList<DataDv>();
	List<UserDv> listAO = new ArrayList<UserDv>();

	ScheduleHandler scheduleHandler;

	public LoanEditorWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		// format table hasil pencarian
		FlexTableHelper.setupHeader(schedFooter, 5, footerText, widthsText);
		contractDate.setFormat(new DateBox.DefaultFormat(dateTimeFormat));
		//
		product.setList(loanProductList);
		ao.setList(listAO);
		driver.initialize(this);
	}

	public void setScheduleHandler(ScheduleHandler scheduleHandler) {
		this.scheduleHandler = scheduleHandler;
	}

	void showFooter(List<LoanScheduleDv> schedules) {
		// Hitung angka
		double principal = 0;
		double margin = 0;
		double total = 0;
		for (LoanScheduleDv sched : schedules) {
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
	}

	public void showData(LoanDv loanDv) {
		showFooter(loanDv.getSchedules());
		//
		driver.edit(loanDv);
	}

	public LoanDv getData() {
		LoanDv result = driver.flush();
		result.setBiSektor(biSektor.getItemText(biSektor.getSelectedIndex()));
		return result;
	}

	public void setLoanProductList(List<DataDv> loanProductList) {
		this.loanProductList.clear();
		this.loanProductList.addAll(loanProductList);
		product.setList(this.loanProductList);
	}

	public void setLoanAO(List<UserDv> listAo) {
		this.listAO.clear();
		this.listAO.addAll(listAo);
		ao.setList(listAO);
	}

	public void setBISektor(List<String> listBISektor) {
		for (String type : listBISektor) {
			biSektor.addItem(type, type);
		}
	}

	public void setSchedules(List<LoanScheduleDv> listSchedule) {
		schedules.clear();
		schedules.setValue(listSchedule);
		showFooter(listSchedule);
	}

	@UiHandler("btnGenerate")
	void onGenerate(ClickEvent event) {
		LoanScheduleGenDv data = new LoanScheduleGenDv();
		data.setPrincipal(principal.getValue());
		data.setTenor(tenor.getValue());
		data.setRate(rate.getValue());
		data.setScheduleType(scheduleType.getValue());
		scheduleHandler.generate(data);
	}

	@UiHandler("btnAdd")
	void onAdd(ClickEvent event) {
	}

	@UiHandler("btnRemove")
	void onRemove(ClickEvent event) {
	}
}
