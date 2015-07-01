package org.simbiosis.bprs.ui.config.client.savingproduct;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.bprs.ui.config.client.AppFactory;
import org.simbiosis.bprs.ui.config.client.rpc.AppService;
import org.simbiosis.bprs.ui.config.client.rpc.AppServiceAsync;
import org.simbiosis.bprs.ui.config.client.savingproduct.ISavProduct.Activity;
import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.ProductDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class SavProductListActivity extends Activity {

	private final AppServiceAsync wmsService = GWT.create(AppService.class);

	Place myPlace;
	AppFactory appFactory;

	public SavProductListActivity(Place myPlace,
			AppFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ISavProduct myForm = appFactory.getSavProduct();
		myForm.setActivity(this, appFactory.getAppStatus());
		if (appFactory.getAppStatus().isLogin()) {
			loadCommonData();
		}
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_NEW:
			onNew();
			break;
		case FA_EDIT:
			onEdit();
			break;
		case FA_SAVE:
			onSave();
			break;
		case FA_RELOAD:
			onReload();
			break;
		case FA_BACK:
			onBack();
			break;
		default:
			break;
		}
	}

	private void onNew() {
		ISavProduct myForm = appFactory.getSavProduct();
		myForm.newProduct();
	}

	private void onReload() {
		loadUsers();
		ISavProduct myForm = appFactory.getSavProduct();
		myForm.clearViewer();
	}

	private void onSave() {
	
		ISavProduct myForm = appFactory.getSavProduct();
		ProductDv dv = myForm.getProduct();
	
			showLoading();
			wmsService.saveSavingProduct(getKey(), dv, new AsyncCallback<Void>() {

				@Override
				public void onSuccess(Void result) {
					hideLoading();
					Window.alert("Data sudah disimpan");
					onReload();
				}

				@Override
				public void onFailure(Throwable caught) {
					hideLoading();
					Window.alert("Error : saveProduct");
				}
			});
	}

	private void onBack() {
		ISavProduct myForm = appFactory.getSavProduct();
		myForm.viewSelected();
	}

	private void onEdit() {
		ISavProduct myForm = appFactory.getSavProduct();
		myForm.editSelected();
	}

	public void loadCommonData() {
		showLoading();
		wmsService.listCoaForTransaction(getKey(), new AsyncCallback<List<CoaDv>>() {

			@Override
			public void onSuccess(List<CoaDv> result) {
				ISavProduct myForm = appFactory.getSavProduct();
				myForm.setCoa(result);
				loadUsers();
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listCoa1");
			}
		});
	}

	private void loadUsers() {
		wmsService.listSavingProduct(getKey(), new AsyncCallback<List<ProductDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listProduct");
			}

			@Override
			public void onSuccess(List<ProductDv> result) {
				ISavProduct myForm = appFactory.getSavProduct();
				myForm.setProducts(result);
				hideLoading();
			}
		});
	}
}
