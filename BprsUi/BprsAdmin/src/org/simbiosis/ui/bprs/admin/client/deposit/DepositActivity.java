package org.simbiosis.ui.bprs.admin.client.deposit;

import java.util.Date;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.admin.client.AppFactory;
import org.simbiosis.ui.bprs.admin.client.deposit.IDeposit.Activity;
import org.simbiosis.ui.bprs.admin.client.rpc.AppService;
import org.simbiosis.ui.bprs.admin.client.rpc.AppServiceAsync;
import org.simbiosis.ui.bprs.common.client.deposithelper.DlgGetDeposit;
import org.simbiosis.ui.bprs.common.client.handler.GetDepositHandler;
import org.simbiosis.ui.bprs.common.client.handler.ValidationHandler;
import org.simbiosis.ui.bprs.common.client.printing.DlgPrintValidation;
import org.simbiosis.ui.bprs.common.shared.DepositDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class DepositActivity extends Activity {

	private final AppServiceAsync srv = GWT.create(AppService.class);

	Place myPlace;
	AppFactory appFactory;
	Activity activity;

	public DepositActivity(Place myPlace, AppFactory appFactory) {
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
		IDeposit myForm = appFactory.getDepositoViewer();
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
		IDeposit myForm = appFactory.getDepositoViewer();
		appFactory.showApplication(null, myForm.getFormWidget());
	}

	private void onSave() {
		showLoading();
		IDeposit formEditor = appFactory.getDepositoEditor();
		TransactionDv data = formEditor.getData();
		srv.saveDepositTrans(getKey(), data,
				new AsyncCallback<TransactionDv>() {

					@Override
					public void onSuccess(TransactionDv result) {
						hideLoading();
						IDeposit myForm = appFactory.getDepositoViewer();
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
		GetDepositHandler handler = new GetDepositHandler() {

			@Override
			public void showDeposit(DepositDv depositDv) {
				IDeposit myForm = appFactory.getDepositoEditor();
				myForm.setActivity(getActivity(), appFactory.getAppStatus());
				//
				TransactionDv transDv = new TransactionDv();
				transDv.setDate(new Date());
				transDv.setType(1);
				transDv.setDirection(1);
				transDv.setValue(depositDv.getValue());
				depositDv.copyDepositData();
				transDv.setDeposit(depositDv);
				SavingDv savingDv = depositDv.getSaving();
				savingDv.copySavingData();
				transDv.setSaving(savingDv);
				//
				myForm.showData(transDv);
				appFactory.showApplication(null, myForm.getFormWidget());
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
		DlgGetDeposit dlgGetDeposit = new DlgGetDeposit(getKey(), handler);
		dlgGetDeposit.center();
	}

	private void printValidation(String validationText) {
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
