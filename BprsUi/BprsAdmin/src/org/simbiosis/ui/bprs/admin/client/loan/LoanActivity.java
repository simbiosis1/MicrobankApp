package org.simbiosis.ui.bprs.admin.client.loan;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.admin.client.AppFactory;
import org.simbiosis.ui.bprs.admin.client.loan.ILoan.Activity;
import org.simbiosis.ui.bprs.admin.client.rpc.AppService;
import org.simbiosis.ui.bprs.admin.client.rpc.AppServiceAsync;
import org.simbiosis.ui.bprs.common.client.handler.GetLoanHandler;
import org.simbiosis.ui.bprs.common.client.handler.ValidationHandler;
import org.simbiosis.ui.bprs.common.client.loanhelper.DlgGetLoan;
import org.simbiosis.ui.bprs.common.client.printing.DlgPrintValidation;
import org.simbiosis.ui.bprs.common.shared.LoanDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class LoanActivity extends Activity {

	private final AppServiceAsync koperasiService = GWT
			.create(AppService.class);

	Place myPlace;
	AppFactory appFactory;
	Activity activity;

	public LoanActivity(Place myPlace, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
		this.activity = this;
	}

	public Activity getActivity() {
		return activity;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ILoan myForm = appFactory.getPembiayaanViewer();
		myForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_NEW:
			onNew();
			break;
		case FA_SAVE:
			onSave();
			break;
		case FA_BACK:
			onBack();
			break;
		default:
			break;
		}
	}

	private void onBack() {
		ILoan myForm = appFactory.getPembiayaanViewer();
		appFactory.showApplication(null, myForm.getFormWidget());
	}

	private void onSave() {
		//
		showLoading();
		//
		ILoan formEditor = appFactory.getPembiayaanEditor();
		TransactionDv data = formEditor.getData();
		koperasiService.saveLoanTrans(getKey(), data,
				new AsyncCallback<TransactionDv>() {

					@Override
					public void onSuccess(TransactionDv result) {
						hideLoading();
						ILoan myForm = appFactory.getPembiayaanViewer();
						myForm.showData(result);
						appFactory.showApplication(null, myForm.getFormWidget());
						printValidation(result.getValidationText());
						// Window.alert("Transaksi sudah disimpan");
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : " + caught.getMessage());
					}
				});
	}

	private void onNew() {
		//
		GetLoanHandler handler = new GetLoanHandler() {

			@Override
			public void showLoan(LoanDv loanDv) {
				//
				final TransactionDv transDv = new TransactionDv();
				transDv.setDate(new Date());
				transDv.setType(1);
				transDv.setDirection(1);
				loanDv.copyLoanData();
				transDv.setLoan(loanDv);
				// transDv.setPrincipal(loanDv.getPrincipal());
				// transDv.setStrPrincipal(loanDv.getStrPrincipal());
				// transDv.setMargin(loanDv.getMargin());
				// transDv.setStrMargin(loanDv.getStrMargin());
				SavingDv savingDv = loanDv.getSaving();
				savingDv.copySavingData();
				transDv.setSaving(savingDv);
				//
				// Load data angsuran
				//
				koperasiService.getRepaymentValue(loanDv.getId(),
						new AsyncCallback<List<TransactionDv>>() {

							@Override
							public void onSuccess(List<TransactionDv> result) {
								ILoan myForm = appFactory.getPembiayaanEditor();
								myForm.setActivity(getActivity(),
										appFactory.getAppStatus());
								//
								myForm.setRepayment(result);
								//
								TransactionDv repayment = result.get(0);
								transDv.setPrincipal(repayment.getPrincipal());
								transDv.setMargin(repayment.getMargin());
								transDv.setTotal(repayment.getTotal());
								transDv.setValue(transDv.getTotal());
								transDv.setDiscount(0D);
								myForm.showData(transDv);
								appFactory.showApplication(null,
										myForm.getFormWidget());
								//
								hideLoading();
							}

							@Override
							public void onFailure(Throwable caught) {
								hideLoading();
								Window.alert("Error : " + caught.getMessage());
							}
						});
			}

			@Override
			public void showLoading() {
				activity.showLoading();
			}

			@Override
			public void hideLoading() {
				activity.hideLoading();
			}
		};
		DlgGetLoan dlgGetLoan = new DlgGetLoan(getKey(), handler);
		dlgGetLoan.center();
	}

	void printValidation(String validationText) {
		ValidationHandler handler = new ValidationHandler() {

			@Override
			public void showLoading() {
				activity.showLoading();
			}

			@Override
			public void hideLoading() {
				activity.hideLoading();
			}
		};
		DlgPrintValidation printValidation = new DlgPrintValidation(
				"getCashSavingTransValidation", validationText, handler);
		printValidation.show();
	}
}
