package org.simbiosis.ui.bprs.cs.client.depositinput;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.client.handler.GetSavingHandler;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankService;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankServiceAsync;
import org.simbiosis.ui.bprs.common.client.savinghelper.DlgGetSaving;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.DepositDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.cs.client.AppFactory;
import org.simbiosis.ui.bprs.cs.client.depositinput.IDepositInput.Activity;
import org.simbiosis.ui.bprs.cs.client.rpc.CsService;
import org.simbiosis.ui.bprs.cs.client.rpc.CsServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class DepositInputActivity extends Activity {

	private final CsServiceAsync csService = GWT.create(CsService.class);

	private final MicBankServiceAsync comSrv = GWT.create(MicBankService.class);

	DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd-MM-yyyy");

	Place myPlace;
	AppFactory appFactory;
	Activity activity;

	public DepositInputActivity(Place myPlace, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
		this.activity = this;
	}

	Activity getActivity() {
		return activity;
	}

	@Override
	public void showData(DataDv dataDv) {
		comSrv.getDeposit(dataDv.getId(), new AsyncCallback<DepositDv>() {

			@Override
			public void onSuccess(DepositDv result) {
				IDepositInput viewerForm = appFactory.getDepositViewer();
				viewerForm.showData(result);
				hideLoading();
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : getDeposit");
			}
		});
	}

	@Override
	public void newData() {
		GetSavingHandler handler = new GetSavingHandler() {

			@Override
			public void showSaving(SavingDv savingDv) {
				IDepositInput editorForm = appFactory.getDepositEditor();
				DepositDv dv = new DepositDv();
				// FIXME : harusnya tanggal ambil dari server
				dv.setRegistration(new Date());
				dv.setStrRegistration(dateFormat.format(dv.getRegistration()));
				dv.setCustomer(savingDv.getCustomer());
				savingDv.copySavingData();
				dv.setSaving(savingDv);
				editorForm.showData(dv);
				appFactory.showApplication(null, editorForm.getFormWidget());
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
		DlgGetSaving dlg = new DlgGetSaving(getKey(), false, handler);
		dlg.center();
	}

	@Override
	public void loadCommonList() {
		csService.loadCommonListDeposit(getKey(),
				new AsyncCallback<List<DataDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : loadCommonListDeposit");
					}

					@Override
					public void onSuccess(List<DataDv> result) {
						hideLoading();
						IDepositInput editorForm = appFactory
								.getDepositEditor();
						editorForm.setDepositProductList(result);
					}
				});
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_EDIT:
			onEdit();
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

	void onEdit() {
		DepositDv data = appFactory.getDepositViewer().getSelectedData();
		IDepositInput editorForm = appFactory.getDepositEditor();
		editorForm.showData(data);
		appFactory.showApplication(null, editorForm.getFormWidget());
	}

	void onSave() {
		showLoading();
		DepositDv data = appFactory.getDepositEditor().getEditedData();
		csService.saveDeposit(getKey(), data, new AsyncCallback<Long>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : saveDeposit");
			}

			@Override
			public void onSuccess(Long result) {
				showDeposit(result);
			}
		});

	}

	void showDeposit(Long id) {
		comSrv.getDeposit(id, new AsyncCallback<DepositDv>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : getDeposit");
			}

			@Override
			public void onSuccess(DepositDv result) {
				hideLoading();
				if (result != null) {
					IDepositInput viewerForm = appFactory.getDepositViewer();
					viewerForm.showData(result);
					appFactory.showApplication(null, viewerForm.getFormWidget());
					Window.alert("Data sudah disimpan");
				}
			}
		});
	}

	void onBack() {
		appFactory.getPlaceController().goTo(myPlace);
	}

}
