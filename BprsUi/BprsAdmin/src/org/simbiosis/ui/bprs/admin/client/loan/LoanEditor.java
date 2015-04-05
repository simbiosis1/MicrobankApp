package org.simbiosis.ui.bprs.admin.client.loan;

import java.util.ArrayList;
import java.util.List;

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
import com.google.gwt.user.client.ui.Label;
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
	Label strDate;
	@UiField
	TextBox refCode;
	@UiField
	TextBox strValue;
	@UiField
	TextBox strPrincipal;
	@UiField
	TextBox strMargin;
	@UiField
	TextBox strDiscount;
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
		strPrincipal.setVisible(false);
		strMargin.setVisible(false);
		strDiscount.setVisible(false);
		//
		type.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				String principal = "";
				String margin = "";
				String total = "";
				if (type.getValue() == 2) {
					strPrincipal.setReadOnly(false);
					strDiscount.setVisible(false);
					principal = repayment.get(0).getStrPrincipal();
					margin = repayment.get(0).getStrMargin();
					total = repayment.get(0).getStrTotal();
				} else if (type.getValue() == 3) {
					strPrincipal.setReadOnly(true);
					strDiscount.setVisible(true);
					principal = repayment.get(1).getStrPrincipal();
					margin = repayment.get(1).getStrMargin();
					total = repayment.get(1).getStrTotal();
				}
				strPrincipal.setText(principal);
				strMargin.setText(margin);
				strValue.setText(total);
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
			strDiscount.setVisible(false);
			if (loan.isDropped()) {
				// Tentukan tampilan untuk pilihan transaksi
				type.removeItem(0);
				//
				strPrincipal.setVisible(true);
				strMargin.setVisible(true);
			} else {
				// Tentukan tampilan untuk pilihan transaksi
				type.removeItem(2);
				type.removeItem(1);
				// Tentukan jumlah
				strValue.setEnabled(false);
				transactionDv.setStrValue(transactionDv.getLoan()
						.getStrPrincipal());
				transactionDv.setStrMargin(transactionDv.getLoan()
						.getStrMargin());
				//
				strPrincipal.setVisible(false);
				strMargin.setVisible(false);
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
