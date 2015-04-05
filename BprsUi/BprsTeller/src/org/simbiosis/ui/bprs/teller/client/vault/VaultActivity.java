package org.simbiosis.ui.bprs.teller.client.vault;

import java.util.Date;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.client.handler.ValidationHandler;
import org.simbiosis.ui.bprs.common.client.printing.DlgPrintValidation;
import org.simbiosis.ui.bprs.teller.client.AppFactory;
import org.simbiosis.ui.bprs.teller.client.rpc.AppService;
import org.simbiosis.ui.bprs.teller.client.rpc.AppServiceAsync;
import org.simbiosis.ui.bprs.teller.client.vault.IVault.Activity;
import org.simbiosis.ui.bprs.teller.shared.VaultTransactionDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class VaultActivity extends Activity {

	private final AppServiceAsync tellerSrv = GWT
			.create(AppService.class);

	Place myPlace;
	AppFactory appFactory;
	Activity activity;

	public VaultActivity(Place myPlace, AppFactory appFactory) {
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
		IVault myForm = appFactory.getVaultViewer();
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
		IVault myForm = appFactory.getVaultViewer();
		appFactory.showApplication(null, myForm.getFormWidget());
	}

	private void onSave() {
		//
		showLoading();
		IVault formEditor = appFactory.getVaultEditor();
		VaultTransactionDv data = formEditor.getData();
		if (data.getDirection() == 1) {
			saveVaultTrans(data);
		} else {
			approveVaultTrans(data);
		}
	}

	private void onNew() {
		//
		IVault myForm = appFactory.getVaultEditor();
		myForm.setActivity(getActivity(), appFactory.getAppStatus());
		//
		VaultTransactionDv transactionDv = new VaultTransactionDv();
		transactionDv.setDate(new Date());
		transactionDv.setType(1);
		transactionDv.setDirection(2);
		DateTimeFormat format = DateTimeFormat.getFormat("dd-MM-yyyy");
		transactionDv.setStrDate(format.format(transactionDv.getDate()));
		//
		myForm.newData(transactionDv);
		appFactory.showApplication(null, myForm.getFormWidget());
	}

	void printValidation(String validationText) {
		ValidationHandler handler = new ValidationHandler() {

			@Override
			public void showLoading() {
				showLoading();
			}

			@Override
			public void hideLoading() {
				hideLoading();
			}
		};
		DlgPrintValidation printValidation = new DlgPrintValidation(
				"getCashSavingTransValidation", validationText, handler);
		printValidation.show();
	}

	void saveVaultTrans(VaultTransactionDv data) {
		tellerSrv.saveVaultTrans(getKey(), data,
				new AsyncCallback<VaultTransactionDv>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : " + caught.getMessage());
					}

					@Override
					public void onSuccess(VaultTransactionDv result) {
						hideLoading();
						Window.alert("Transaksi sudah disimpan");
						IVault myForm = appFactory.getVaultViewer();
						myForm.showData(result);
						appFactory.showApplication(null, myForm.getFormWidget());
					}
				});
	}

	void approveVaultTrans(VaultTransactionDv data) {
		tellerSrv.approveVaultTrans(getKey(), data,
				new AsyncCallback<VaultTransactionDv>() {

					@Override
					public void onSuccess(VaultTransactionDv result) {
						hideLoading();
						Window.alert("Transaksi sudah disimpan");
						IVault myForm = appFactory.getVaultViewer();
						myForm.showData(result);
						appFactory.showApplication(null, myForm.getFormWidget());
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : approveVaultTrans");
					}
				});
	}

	@Override
	public void getReadyVault(VaultTransactionDv transDv) {
		showLoading();
		//
		tellerSrv.getAvailableVaultTrans(getKey(), transDv,
				new AsyncCallback<VaultTransactionDv>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : getAvailableVaultTrans");
					}

					@Override
					public void onSuccess(VaultTransactionDv result) {
						hideLoading();
						if ((result.getCode() != null)
								&& result.getCode().equalsIgnoreCase("---")
								&& result.getValue() == 0) {
							Window.alert("Tidak ada transaksi khasanah/vault untuk anda");
						}
						IVault myForm = appFactory.getVaultEditor();
						myForm.showData(result);
						appFactory.showApplication(null, myForm.getFormWidget());
					}
				});
	}

}
