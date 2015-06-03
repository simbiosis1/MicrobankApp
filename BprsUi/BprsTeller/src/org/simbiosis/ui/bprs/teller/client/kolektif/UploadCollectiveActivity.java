package org.simbiosis.ui.bprs.teller.client.kolektif;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.client.handler.ValidationHandler;
import org.simbiosis.ui.bprs.common.client.printing.DlgPrintValidation;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;
import org.simbiosis.ui.bprs.teller.client.AppFactory;
import org.simbiosis.ui.bprs.teller.client.htvault.IHtVault;
import org.simbiosis.ui.bprs.teller.client.kolektif.IUploadCollective.Activity;
import org.simbiosis.ui.bprs.teller.client.rpc.AppService;
import org.simbiosis.ui.bprs.teller.client.rpc.AppServiceAsync;
import org.simbiosis.ui.bprs.teller.shared.TellerDv;
import org.simbiosis.ui.bprs.teller.shared.UploadCollectiveDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class UploadCollectiveActivity extends Activity {

	private final AppServiceAsync tellerSrv = GWT.create(AppService.class);

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
			loadTellers();
		}
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
	}

	private void loadTellers() {
		showLoading();
		tellerSrv.listTeller(getKey(), new AsyncCallback<List<TellerDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listTeller");
			}

			@Override
			public void onSuccess(List<TellerDv> result) {
				hideLoading();
				IUploadCollective myForm = appFactory.getUploadCollective();
				myForm.setTellers(result);
			}
		});
	}

	@Override
	public void confirmTransfer() {
		showLoading();
		IUploadCollective myForm = appFactory.getUploadCollective();
		// botSrv.listConfirmTransfer(getKey(), myForm.getSrcData(),
		// new AsyncCallback<List<TransferCollectiveDv>>() {
		//
		// @Override
		// public void onFailure(Throwable caught) {
		// hideLoading();
		// Window.alert("Error : listConfirmTransfer");
		// }
		//
		// @Override
		// public void onSuccess(List<TransferCollectiveDv> result) {
		// hideLoading();
		// IUploadCollective myForm = appFactory
		// .getUploadCollective();
		// myForm.confirmTransfer(result);
		// }
		// });
	}

	@Override
	public void executeTransfer() {
		showLoading();
		IUploadCollective myForm = appFactory.getUploadCollective();
		// botSrv.executeCollectiveTransfer(getKey(), myForm.getTeller(),
		// myForm.getTransferData(), new AsyncCallback<Void>() {
		//
		// @Override
		// public void onSuccess(Void result) {
		// hideLoading();
		// Window.alert("Transfer berhasil");
		// }
		//
		// @Override
		// public void onFailure(Throwable caught) {
		// hideLoading();
		// Window.alert("Error : executeCollectiveTransfer");
		// }
		// });
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
