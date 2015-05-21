package org.simbiosis.ui.bprs.admin.client.loan;

import java.util.ArrayList;
import java.util.List;

import org.kembang.editor.client.DoubleTextBox;
import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.admin.client.editor.LoanTransTypeEditor;
import org.simbiosis.ui.bprs.common.client.loanhelper.LoanInfo;
import org.simbiosis.ui.bprs.common.client.savinghelper.SavingInfo;
import org.simbiosis.ui.bprs.common.shared.LoanDv;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoanEditor extends FormWidget implements ILoan,
		Editor<TransactionDv> {

	Activity activity;

	private static TarikTunaiUiBinder uiBinder = GWT
			.create(TarikTunaiUiBinder.class);

	interface TarikTunaiUiBinder extends UiBinder<Widget, LoanEditor> {
	}

	interface Driver extends SimpleBeanEditorDriver<TransactionDv, LoanEditor> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	LoanInfo loan;
	@UiField
	SavingInfo saving;
	@UiField
	DateLabel date;
	@UiField
	TextBox refCode;
	@UiField
	DoubleTextBox value;
	@UiField
	DoubleTextBox principal;
	@UiField
	DoubleTextBox margin;
	@UiField
	DoubleTextBox discount;
	@UiField
	LoanTransTypeEditor type;

	TransactionDv transactionDv;
	List<TransactionDv> repayment = new ArrayList<TransactionDv>();

	public LoanEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasSave(true);
		setHasBack(true);
		//
		driver.initialize(this);
		//
		principal.setVisible(false);
		margin.setVisible(false);
		discount.setVisible(false);
		//
		type.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				Double dprincipal = 0D;
				Double dmargin = 0D;
				Double dtotal = 0D;
				if (type.getValue() == 2) {
					principal.setReadOnly(false);
					discount.setVisible(false);
					dprincipal = repayment.get(0).getPrincipal();
					dmargin = repayment.get(0).getMargin();
					dtotal = repayment.get(0).getTotal();
				} else if (type.getValue() == 3) {
					principal.setReadOnly(true);
					discount.setVisible(true);
					dprincipal = repayment.get(1).getPrincipal();
					dmargin = repayment.get(1).getMargin();
					dtotal = repayment.get(1).getTotal();
				}
				principal.setValue(dprincipal);
				margin.setValue(dmargin);
				value.setValue(dtotal);
			}
		});
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
		//
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void showData(TransactionDv transactionDv) {
		this.transactionDv = transactionDv;
		type.clear();
		type.addAll();
		if (transactionDv.getLoan().getId() != 0) {
			LoanDv loan = transactionDv.getLoan();
			discount.setVisible(false);
			if (loan.isDropped()) {
				// Tentukan tampilan untuk pilihan transaksi
				type.removeItem(0);
				//
				principal.setVisible(true);
				margin.setVisible(true);
			} else {
				// Tentukan tampilan untuk pilihan transaksi
				type.removeItem(2);
				type.removeItem(1);
				// Tentukan jumlah
				value.setEnabled(false);
				transactionDv.setValue(transactionDv.getLoan().getPrincipal());
				transactionDv.setMargin(transactionDv.getLoan().getMargin());
				//
				principal.setVisible(false);
				margin.setVisible(false);
			}
		}
		//
		driver.edit(transactionDv);
	}

	@Override
	public TransactionDv getData() {
		return driver.flush();
	}

	@Override
	public void setRepayment(List<TransactionDv> repayment) {
		this.repayment.addAll(repayment);
	}

}
