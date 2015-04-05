package org.simbiosis.ui.bprs.cs.client.savingblock;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.client.handler.GetSavingHandler;
import org.simbiosis.ui.bprs.common.client.savinghelper.DlgGetSaving;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.cs.client.AppFactory;
import org.simbiosis.ui.bprs.cs.client.places.SavingBlock;
import org.simbiosis.ui.bprs.cs.client.rpc.CsService;
import org.simbiosis.ui.bprs.cs.client.rpc.CsServiceAsync;
import org.simbiosis.ui.bprs.cs.client.savingblock.ISavingBlockEditor.Activity;
import org.simbiosis.ui.bprs.cs.shared.SavingBlockirDataDv;
import org.simbiosis.ui.bprs.cs.shared.SavingBlockirDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class SavingBlockActivity extends Activity {

	private final CsServiceAsync csService = GWT.create(CsService.class);

	SavingBlock myPlace;
	AppFactory appFactory;
	SavingBlockActivity activity;

	public SavingBlockActivity(SavingBlock myPlace, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
		this.activity = this;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ISavingBlockEditor myForm = appFactory.getSavingBlockEditor();
		myForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_SEARCH:
			onSearch();
			break;
		case FA_SAVE:
			onSave();
			break;
		default:
			break;
		}
	}

	private void onSave() {
		ISavingBlockEditor myForm = appFactory.getSavingBlockEditor();
		SavingBlockirDataDv data = myForm.getData();
		showLoading();
		csService.saveBlockir(data, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				hideLoading();
				Window.alert("Data blokir sudah disimpan");
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : saveBlockir");
			}
		});

	}

	private void onSearch() {
		GetSavingHandler handler = new GetSavingHandler() {

			@Override
			public void showSaving(SavingDv savingDv) {
				savingDv.copySavingData();
				SavingBlockirDataDv data = new SavingBlockirDataDv();
				data.setSaving(savingDv);
				searchBlockir(data);
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

	private void searchBlockir(final SavingBlockirDataDv data) {
		csService.listBlockir(data.getSaving().getId(),
				new AsyncCallback<List<SavingBlockirDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listBlockir");
					}

					@Override
					public void onSuccess(List<SavingBlockirDv> result) {
						ISavingBlockEditor myForm = appFactory
								.getSavingBlockEditor();
						data.setBlockir(result);
						myForm.showData(data);
						myForm.enableButtons(true);
					}
				});
	}

}
