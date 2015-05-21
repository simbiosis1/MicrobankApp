package org.simbiosis.ui.bprs.admin.client.transfer;

import java.util.Date;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.admin.client.BprsAdminFactory;
import org.simbiosis.ui.bprs.admin.client.rpc.AppService;
import org.simbiosis.ui.bprs.admin.client.rpc.AppServiceAsync;
import org.simbiosis.ui.bprs.admin.client.transfer.ITransfer.Activity;
import org.simbiosis.ui.bprs.common.client.handler.GetSavingHandler;
import org.simbiosis.ui.bprs.common.client.handler.ValidationHandler;
import org.simbiosis.ui.bprs.common.client.printing.DlgPrintValidation;
import org.simbiosis.ui.bprs.common.client.savinghelper.DlgGetSaving;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class TransferActivity extends Activity {

	private final AppServiceAsync botSrv = GWT.create(AppService.class);

	Place myPlace;
	BprsAdminFactory appFactory;
	Activity activity;

	final TransactionDv transDv = new TransactionDv();

	public TransferActivity(Place myPlace, BprsAdminFactory appFactory) {
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
		//
		ITransfer myForm = appFactory.getTransferViewer();
		myForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, myForm.getFormWidget());
		//
		myForm = appFactory.getTransferEditor();
		myForm.setActivity(getActivity(), appFactory.getAppStatus());
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
		ITransfer myForm = appFactory.getTransferViewer();
		appFactory.showApplication(null, myForm.getFormWidget());
	}

	private void onSave() {
		//
		showLoading();
		ITransfer formEditor = appFactory.getTransferEditor();
		TransactionDv data = formEditor.getData();
		botSrv.saveTransferSaving(getKey(), data,
				new AsyncCallback<TransactionDv>() {

					@Override
					public void onSuccess(TransactionDv result) {
						hideLoading();
						// Window.alert("Transaksi sudah disimpan");
						ITransfer myForm = appFactory.getTransferViewer();
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

	private void findDestSaving() {
		GetSavingHandler destHandler = new GetSavingHandler() {

			@Override
			public void showSaving(SavingDv savingDv) {
				//
				savingDv.copySavingData();
				transDv.setSavingDest(savingDv);
				//
				ITransfer myForm = appFactory.getTransferEditor();
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
		DlgGetSaving dlgSavingDest = new DlgGetSaving(getKey(), false,
				destHandler);
		dlgSavingDest.center();
	}

	private void onNew() {
		transDv.setDate(new Date());
		transDv.setDirection(1);
		//
		GetSavingHandler srcHandler = new GetSavingHandler() {

			@Override
			public void showSaving(SavingDv savingDv) {
				//
				savingDv.copySavingData();
				transDv.setSaving(savingDv);
				//
				findDestSaving();
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
		//
		DlgGetSaving dlgSavingSrc = new DlgGetSaving(getKey(), false,
				srcHandler);
		dlgSavingSrc.center();
		//
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
