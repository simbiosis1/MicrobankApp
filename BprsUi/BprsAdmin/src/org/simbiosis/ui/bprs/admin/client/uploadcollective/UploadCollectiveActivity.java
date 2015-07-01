package org.simbiosis.ui.bprs.admin.client.uploadcollective;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.admin.client.AppFactory;
import org.simbiosis.ui.bprs.admin.client.rpc.AppService;
import org.simbiosis.ui.bprs.admin.client.rpc.AppServiceAsync;
import org.simbiosis.ui.bprs.admin.client.uploadcollective.IUploadCollective.Activity;
import org.simbiosis.ui.bprs.admin.shared.CoaDv;
import org.simbiosis.ui.bprs.admin.shared.TransferCollectiveDv;
import org.simbiosis.ui.bprs.common.client.handler.ValidationHandler;
import org.simbiosis.ui.bprs.common.client.printing.DlgPrintValidation;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class UploadCollectiveActivity extends Activity {

	private final AppServiceAsync botSrv = GWT.create(AppService.class);

	Place myPlace;
	AppFactory appFactory;
	Activity activity;

	final TransactionDv transDv = new TransactionDv();

	public UploadCollectiveActivity(Place myPlace, AppFactory appFactory) {
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
		IUploadCollective myForm = appFactory.getUploadCollective();
		myForm.setActivity(this, appFactory.getAppStatus());
		if (appFactory.getAppStatus().isLogin()) {
			loadCoa();
		}
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
	}

	private void loadCoa() {
		showLoading();
		botSrv.listCoaForTransaction(getKey(),
				new AsyncCallback<List<CoaDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listCoaByType");
					}

					@Override
					public void onSuccess(List<CoaDv> result) {
						hideLoading();
						IUploadCollective myForm = appFactory
								.getUploadCollective();
						myForm.setCoa(result);
					}
				});
	}

	@Override
	public void confirmGaji() {
		showLoading();
		IUploadCollective myForm = appFactory.getUploadCollective();
		botSrv.listConfirmTransfer(getKey(), myForm.getSrcData(),
				new AsyncCallback<List<TransferCollectiveDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listConfirmTransfer");
					}

					@Override
					public void onSuccess(List<TransferCollectiveDv> result) {
						hideLoading();
						IUploadCollective myForm = appFactory
								.getUploadCollective();
						myForm.confirmTransfer(result);
					}
				});
	}

	@Override
	public void executeTransfer() {
		showLoading();
		IUploadCollective myForm = appFactory.getUploadCollective();
		botSrv.executeCollectiveTransfer(getKey(), myForm.getCoa(),
				myForm.getData(), new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						hideLoading();
						Window.alert("Transfer berhasil");
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : executeCollectiveTransfer");
					}
				});
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
