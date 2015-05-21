package org.simbiosis.ui.bprs.teller.client.savingwd;

import java.util.Date;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.client.handler.GetSavingHandler;
import org.simbiosis.ui.bprs.common.client.handler.ValidationHandler;
import org.simbiosis.ui.bprs.common.client.printing.DlgPrintValidation;
import org.simbiosis.ui.bprs.common.client.savinghelper.DlgGetSaving;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;
import org.simbiosis.ui.bprs.teller.client.AppFactory;
import org.simbiosis.ui.bprs.teller.client.authdlg.AuthDialog;
import org.simbiosis.ui.bprs.teller.client.authdlg.AuthDialogHandler;
import org.simbiosis.ui.bprs.teller.client.rpc.AppService;
import org.simbiosis.ui.bprs.teller.client.rpc.AppServiceAsync;
import org.simbiosis.ui.bprs.teller.client.savingwd.IWithdrawal.Activity;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class WithdrawalActivity extends Activity {

	private final AppServiceAsync tellerSrv = GWT.create(AppService.class);

	Place myPlace;
	AppFactory appFactory;
	Activity activity;

	public WithdrawalActivity(Place myPlace, AppFactory appFactory) {
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
		IWithdrawal myForm = appFactory.getTarikTunaiViewer();
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
		IWithdrawal myForm = appFactory.getTarikTunaiViewer();
		appFactory.showApplication(null, myForm.getFormWidget());
	}

	private void onSave() {
		//
		showLoading();
		IWithdrawal formEditor = appFactory.getTarikTunaiEditor();
		TransactionDv data = formEditor.getData();
		tellerSrv.saveSavingTrans(getKey(), data,
				new AsyncCallback<TransactionDv>() {

					@Override
					public void onSuccess(TransactionDv result) {
						hideLoading();
						IWithdrawal myForm = appFactory.getTarikTunaiViewer();
						myForm.showData(result);
						appFactory.showApplication(null, myForm.getFormWidget());
						printValidation(result.getValidationText());
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						onSaveError(caught.getMessage());
					}
				});
	}

	private void onSaveError(String message) {
		if (message.substring(0, 4).equalsIgnoreCase("AUTH")) {
			AuthDialog dlg = new AuthDialog(new AuthDialogHandler() {

				@Override
				public void showLoading() {
					getActivity().showLoading();
				}

				@Override
				public void hideLoading() {
					getActivity().hideLoading();
				}

				@Override
				public void execute() {
					onSave();
				}

				@Override
				public void confirm() {
					showLoading();
					tellerSrv.hasTellerApproval(getKey(),
							new AsyncCallback<Boolean>() {

								@Override
								public void onSuccess(Boolean result) {
									hideLoading();
									if (result) {
										getLabelStatus().setText(
												"Otorisasi sudah siap");
										getBtnExecute().setEnabled(true);
									} else {
										getLabelStatus().setText(
												"Belum otorisasi");
										getBtnExecute().setEnabled(false);
									}
								}

								@Override
								public void onFailure(Throwable caught) {
									hideLoading();
									Window.alert("Error : hasTellerApproval");
								}
							});
				}
			});
			dlg.center();
		} else {
			Window.alert("Error : " + message);
		}
	}

	private void onNew() {
		//
		GetSavingHandler handler = new GetSavingHandler() {

			@Override
			public void showSaving(SavingDv savingDv) {
				IWithdrawal myForm = appFactory.getTarikTunaiEditor();
				myForm.setActivity(getActivity(), appFactory.getAppStatus());
				//
				TransactionDv transactionDv = new TransactionDv();
				transactionDv.setDate(new Date());
				transactionDv.setDirection(2);
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
