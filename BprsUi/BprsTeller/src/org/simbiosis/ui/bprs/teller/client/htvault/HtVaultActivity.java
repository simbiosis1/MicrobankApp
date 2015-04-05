package org.simbiosis.ui.bprs.teller.client.htvault;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.client.handler.ValidationHandler;
import org.simbiosis.ui.bprs.common.client.printing.DlgPrintValidation;
import org.simbiosis.ui.bprs.teller.client.AppFactory;
import org.simbiosis.ui.bprs.teller.client.htvault.IHtVault.Activity;
import org.simbiosis.ui.bprs.teller.client.rpc.AppService;
import org.simbiosis.ui.bprs.teller.client.rpc.AppServiceAsync;
import org.simbiosis.ui.bprs.teller.shared.TellerDv;
import org.simbiosis.ui.bprs.teller.shared.VaultTransactionDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class HtVaultActivity extends Activity {

	private final AppServiceAsync tellerSrv = GWT.create(AppService.class);

	Place myPlace;
	AppFactory appFactory;
	Activity activity;

	public HtVaultActivity(Place myPlace, AppFactory appFactory) {
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
		IHtVault myForm = appFactory.getHtVaultViewer();
		myForm.setActivity(this, appFactory.getAppStatus());
		IHtVault myFormEditor = appFactory.getHtVaultEditor();
		myFormEditor.setActivity(this, appFactory.getAppStatus());
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
		IHtVault myForm = appFactory.getHtVaultViewer();
		appFactory.showApplication(null, myForm.getFormWidget());
	}

	private void onSave() {
		//
		showLoading();
		IHtVault formEditor = appFactory.getHtVaultEditor();
		VaultTransactionDv data = formEditor.getData();
		if (data.getType() == 1) {
			tellerSrv.saveVaultTrans(getKey(), data,
					new AsyncCallback<VaultTransactionDv>() {

						@Override
						public void onSuccess(VaultTransactionDv result) {
							hideLoading();
							//Window.alert("Transaksi sudah disimpan");
							IHtVault myForm = appFactory.getHtVaultViewer();
							myForm.showData(result);
							appFactory.showApplication(null,
									myForm.getFormWidget());
							printValidation(result.getValidationText());
						}

						@Override
						public void onFailure(Throwable caught) {
							hideLoading();
							Window.alert("Error : saveVaultTrans");
						}
					});
		} else {
			tellerSrv.approveVaultTrans(getKey(), data,
					new AsyncCallback<VaultTransactionDv>() {

						@Override
						public void onFailure(Throwable caught) {
							hideLoading();
							Window.alert("Error : " + caught.getMessage());
						}

						@Override
						public void onSuccess(VaultTransactionDv result) {
							hideLoading();
							//Window.alert("Transaksi sudah disimpan");
							IHtVault myForm = appFactory.getHtVaultViewer();
							myForm.showData(result);
							appFactory.showApplication(null,
									myForm.getFormWidget());
							printValidation(result.getValidationText());
						}
					});
		}
	}

	private void onNew() {
		//
		IHtVault myForm = appFactory.getHtVaultEditor();
		//
		VaultTransactionDv transactionDv = new VaultTransactionDv();
		transactionDv.setDate(new Date());
		transactionDv.setType(1);
		transactionDv.setDirection(2);
		DateTimeFormat format = DateTimeFormat.getFormat("dd-MM-yyyy");
		transactionDv.setStrDate(format.format(transactionDv.getDate()));
		//
		myForm.showData(transactionDv);
		appFactory.showApplication(null, myForm.getFormWidget());
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
							Window.alert("Tidak ada transaksi khasanah/vault dari teller");
						}
						IHtVault myForm = appFactory.getHtVaultEditor();
						myForm.showData(result);
						appFactory.showApplication(null, myForm.getFormWidget());
					}
				});
	}

	@Override
	public void listAvailableTeller() {
		showLoading();
		//
		tellerSrv.listTeller(getKey(), new AsyncCallback<List<TellerDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listTeller");
			}

			@Override
			public void onSuccess(List<TellerDv> result) {
				hideLoading();
				IHtVault myForm = appFactory.getHtVaultEditor();
				myForm.setTellers(result);
			}
		});
	}

	
}
