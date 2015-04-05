package org.simbiosis.ui.bprs.teller.client.savingdeposit;

import java.util.Date;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.client.handler.GetSavingHandler;
import org.simbiosis.ui.bprs.common.client.handler.ValidationHandler;
import org.simbiosis.ui.bprs.common.client.printing.DlgPrintValidation;
import org.simbiosis.ui.bprs.common.client.savinghelper.DlgGetSaving;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;
import org.simbiosis.ui.bprs.teller.client.AppFactory;
import org.simbiosis.ui.bprs.teller.client.rpc.AppService;
import org.simbiosis.ui.bprs.teller.client.rpc.AppServiceAsync;
import org.simbiosis.ui.bprs.teller.client.savingdeposit.IDeposit.Activity;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class DepositActivity extends Activity {

	private final AppServiceAsync tellerSrv = GWT
			.create(AppService.class);

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
		IDeposit myForm = appFactory.getSetorTunaiViewer();
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
		IDeposit myForm = appFactory.getSetorTunaiViewer();
		appFactory.showApplication(null, myForm.getFormWidget());
	}

	private void onSave() {
		//
		showLoading();
		IDeposit formEditor = appFactory.getSetorTunaiEditor();
		TransactionDv data = formEditor.getData();
		tellerSrv.saveSavingTrans(getKey(), data,
				new AsyncCallback<TransactionDv>() {

					@Override
					public void onSuccess(TransactionDv result) {
						hideLoading();
						IDeposit myForm = appFactory.getSetorTunaiViewer();
						myForm.showData(result);
						appFactory.showApplication(null, myForm.getFormWidget());
						printValidation(result.getValidationText());
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
		GetSavingHandler handler = new GetSavingHandler() {

			@Override
			public void showSaving(SavingDv savingDv) {
				IDeposit myForm = appFactory.getSetorTunaiEditor();
				myForm.setActivity(getActivity(), appFactory.getAppStatus());
				//
				TransactionDv transactionDv = new TransactionDv();
				transactionDv.setDate(new Date());
				transactionDv.setDirection(1);
				DateTimeFormat format = DateTimeFormat.getFormat("dd-MM-yyyy");
				transactionDv
						.setStrDate(format.format(transactionDv.getDate()));
				savingDv.copySavingData();
				transactionDv.setSaving(savingDv);
				//
				myForm.showData(transactionDv);
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
		DlgGetSaving dlgCariSimpanan = new DlgGetSaving(getKey(), true, handler);
		dlgCariSimpanan.center();
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
