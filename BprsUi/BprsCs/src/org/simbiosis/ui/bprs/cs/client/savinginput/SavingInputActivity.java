package org.simbiosis.ui.bprs.cs.client.savinginput;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.client.customerhelper.DlgFindCustomer;
import org.simbiosis.ui.bprs.common.client.handler.GetCustomerHandler;
import org.simbiosis.ui.bprs.common.client.handler.GetSaving;
import org.simbiosis.ui.bprs.common.client.handler.GetSavingHandler;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankService;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankServiceAsync;
import org.simbiosis.ui.bprs.common.shared.CustomerDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.cs.client.AppFactory;
import org.simbiosis.ui.bprs.cs.client.rpc.CsService;
import org.simbiosis.ui.bprs.cs.client.rpc.CsServiceAsync;
import org.simbiosis.ui.bprs.cs.client.savinginput.ISavingInput.Activity;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class SavingInputActivity extends Activity {

	DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd-MM-yyyy");

	private final CsServiceAsync csService = GWT.create(CsService.class);
	private final MicBankServiceAsync micSrv = GWT.create(MicBankService.class);

	Place myPlace;
	AppFactory appFactory;
	SavingInputActivity activity;

	public SavingInputActivity(Place myPlace, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
		this.activity = this;
	}

	Activity getActivity() {
		return activity;
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
		SavingDv data = appFactory.getSavingViewer().getSelectedData();
		ISavingInput editorForm = appFactory.getSavingEditor();
		editorForm.showData(data);
		appFactory.showApplication(null, editorForm.getFormWidget());
	}

	void onSave() {
		showLoading();
		SavingDv data = appFactory.getSavingEditor().getEditedData();
		csService.saveSaving(getKey(), data, new AsyncCallback<Long>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : saveSaving");
			}

			@Override
			public void onSuccess(Long result) {
				showData(result);
			}
		});
	}

	private void showData(Long id) {
		micSrv.getSaving(id, new AsyncCallback<SavingDv>() {

			@Override
			public void onSuccess(SavingDv result) {
				hideLoading();
				if (result != null) {
					ISavingInput viewerForm = appFactory.getSavingViewer();
					viewerForm.showData(result);
					appFactory.showApplication(null, viewerForm.getFormWidget());
					Window.alert("Data sudah disimpan");
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : getSaving");
			}
		});
	}

	void onBack() {
		appFactory.getPlaceController().goTo(myPlace);
	}

	@Override
	public void showData(DataDv selectedData) {
		GetSavingHandler handler = new GetSavingHandler() {

			@Override
			public void showSaving(SavingDv savingDv) {
				ISavingInput viewerForm = appFactory.getSavingViewer();
				viewerForm.showData(savingDv);
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

		GetSaving getSaving = new GetSaving(handler);
		getSaving.go(selectedData.getId());
	}

	@Override
	public void newData() {
		String key = Cookies.getCookie(appFactory.getAppStatus()
				.getCookiesName());
		GetCustomerHandler handler = new GetCustomerHandler() {

			@Override
			public void showLoading() {
				activity.showLoading();
			}

			@Override
			public void showSaving(SavingDv savingDv) {
				if (savingDv == null) {
					newSaving(true, null);
				} else {
					newSaving(false, savingDv.getCustomer());
				}
			}

			@Override
			public void hideLoading() {
				activity.hideLoading();
			}
		};
		DlgFindCustomer cariAnggota = new DlgFindCustomer(key, true, handler);
		cariAnggota.center();
	}

	void newSaving(boolean newCustomer, CustomerDv customerDv) {
		ISavingInput editorForm = appFactory.getSavingEditor();
		SavingDv saving = new SavingDv();
		saving.setRegistration(new Date());
		saving.setStrRegistration(dateFormat.format(saving.getRegistration()));
		if (newCustomer) {
			saving.setNewCustomer(newCustomer);
		} else {
			saving.setCustomer(customerDv);
		}
		editorForm.newData(saving);
		appFactory.showApplication(null, editorForm.getFormWidget());
	}

	@Override
	public void loadCommonList() {
		String key = Cookies.getCookie(appFactory.getAppStatus()
				.getCookiesName());
		csService.loadCommonListSaving(key, new AsyncCallback<List<DataDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : loadCommonListSaving");
			}

			@Override
			public void onSuccess(List<DataDv> result) {
				hideLoading();
				ISavingInput editorForm = appFactory.getSavingEditor();
				editorForm.setSavingProductList(result);
			}
		});
	}
	
	@Override
	public void loadCommonListProvinsi() {
		String key = Cookies.getCookie(appFactory.getAppStatus()
				.getCookiesName());
		csService.loadCommonListProvinsi(key, new AsyncCallback<List<DataDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : loadCommonListSavingProvinsi");
			}

			@Override
			public void onSuccess(List<DataDv> result) {
				hideLoading();
				ISavingInput editorForm = appFactory.getSavingEditor();
				editorForm.setSavingProvinsiList(result);
			}
		});
	}

	@Override
	public void loadCommonListCity() {
		String key = Cookies.getCookie(appFactory.getAppStatus()
				.getCookiesName());
		csService.loadCommonListCity(key, new AsyncCallback<List<DataDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : loadCommonListSavingProvinsi");
			}

			@Override
			public void onSuccess(List<DataDv> result) {
				hideLoading();
				ISavingInput editorForm = appFactory.getSavingEditor();
				editorForm.setSavingCityList(result);
			}
		});
	}

	@Override
	public void loadCommonListJenisPekerjaan() {
		String key = Cookies.getCookie(appFactory.getAppStatus()
				.getCookiesName());
		csService.loadCommonListJenisPekerjaan(key, new AsyncCallback<List<DataDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : loadCommonListSavingJenisPekerjaan");
			}

			@Override
			public void onSuccess(List<DataDv> result) {
				hideLoading();
				ISavingInput editorForm = appFactory.getSavingEditor();
				editorForm.setSavingPekerjaanList(result);
			}
		});
	}
}
