package org.simbiosis.ui.bprs.cs.client.savingclose;

import java.util.Date;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.client.handler.GetSavingHandler;
import org.simbiosis.ui.bprs.common.client.handler.ValidationHandler;
import org.simbiosis.ui.bprs.common.client.printing.DlgPrintValidation;
import org.simbiosis.ui.bprs.common.client.savinghelper.DlgGetSaving;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.cs.client.AppFactory;
import org.simbiosis.ui.bprs.cs.client.rpc.CsService;
import org.simbiosis.ui.bprs.cs.client.rpc.CsServiceAsync;
import org.simbiosis.ui.bprs.cs.client.savingclose.ISavingClose.Activity;
import org.simbiosis.ui.bprs.cs.shared.SavingCloseDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class SavingCloseActivity extends Activity {

	private final CsServiceAsync csSrv = GWT.create(CsService.class);

	Place myPlace;
	AppFactory appFactory;
	Activity activity;

	public SavingCloseActivity(Place myPlace, AppFactory appFactory) {
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
		ISavingClose myForm = appFactory.getSavingCloseViewer();
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
		ISavingClose myForm = appFactory.getSavingCloseViewer();
		appFactory.showApplication(null, myForm.getFormWidget());
	}

	private void onSave() {
		//
		showLoading();
		ISavingClose formEditor = appFactory.getSavingCloseEditor();
		SavingCloseDv data = formEditor.getData();
		csSrv.closeSaving(data.getSaving().getId(), data.getReason(), new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				 hideLoading();
				 Window.alert("Error : " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				 hideLoading();
				 Window.alert("Rekening tabungan siap ditutup");
			}
		});
	}

	private void onNew() {
		//
		GetSavingHandler handler = new GetSavingHandler() {

			@Override
			public void showSaving(SavingDv savingDv) {
				savingDv.copySavingData();
				//
				ISavingClose myForm = appFactory.getSavingCloseEditor();
				myForm.setActivity(getActivity(), appFactory.getAppStatus());
				//
				SavingCloseDv savingClose = new SavingCloseDv();
				savingClose.setSaving(savingDv);
				DateTimeFormat dtf = DateTimeFormat.getFormat("dd-MM-yyyy");
				savingClose.setClosing(new Date());
				savingClose.setStrClosing(dtf.format(savingClose.getClosing()));
				//
				myForm.showData(savingClose);
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
		DlgGetSaving dlgGetSaving = new DlgGetSaving(getKey(), true, handler);
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
}
