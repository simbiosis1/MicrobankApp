package org.simbiosis.ui.bprs.admin.client.savingjournal;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.admin.client.BprsAdminFactory;
import org.simbiosis.ui.bprs.admin.client.rpc.AppService;
import org.simbiosis.ui.bprs.admin.client.rpc.AppServiceAsync;
import org.simbiosis.ui.bprs.admin.client.savingjournal.ISavingJournal.Activity;
import org.simbiosis.ui.bprs.admin.shared.CoaDv;
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

public class SavingJournalActivity extends Activity {

	private final AppServiceAsync botService = GWT.create(AppService.class);

	Place myPlace;
	BprsAdminFactory appFactory;
	Activity activity;

	public SavingJournalActivity(Place myPlace, BprsAdminFactory appFactory) {
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
		ISavingJournal myForm = appFactory.getSavingJournalViewer();
		myForm.setActivity(this, appFactory.getAppStatus());
		if (appFactory.getAppStatus().isLogin()) {
			loadCoa();
		}
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
		ISavingJournal myForm = appFactory.getSavingJournalViewer();
		appFactory.showApplication(null, myForm.getFormWidget());
	}

	private void onSave() {
		//
		showLoading();
		ISavingJournal formEditor = appFactory.getSavingJournalEditor();
		TransactionDv data = formEditor.getData();
		botService.saveSavingJournalTrans(getKey(), data,
				new AsyncCallback<TransactionDv>() {

					@Override
					public void onSuccess(TransactionDv result) {
						hideLoading();
						// Window.alert("Transaksi sudah disimpan");
						ISavingJournal myForm = appFactory
								.getSavingJournalViewer();
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
				ISavingJournal myForm = appFactory.getSavingJournalEditor();
				//
				TransactionDv dv = new TransactionDv();
				dv.setDate(new Date());
				dv.setDirection(1);
				dv.setType(1);
				savingDv.copySavingData();
				dv.setSaving(savingDv);
				//
				myForm.showData(dv);
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
		DlgGetSaving dlgGetSaving = new DlgGetSaving(getKey(), false, handler);
		dlgGetSaving.center();
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

	private void loadCoa() {
		showLoading();
		botService.listCoaForTransaction(getKey(),
				new AsyncCallback<List<CoaDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listCoaByType");
					}

					@Override
					public void onSuccess(List<CoaDv> result) {
						hideLoading();
						ISavingJournal myForm = appFactory
								.getSavingJournalEditor();
						myForm.setActivity(getActivity(),
								appFactory.getAppStatus());
						myForm.setListCoa(result);
					}
				});
	}

}
