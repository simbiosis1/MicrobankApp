package org.simbiosis.bprs.ui.config.client.deposit;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.kembang.module.shared.SimpleBranchDv;
import org.simbiosis.bprs.ui.config.client.AppFactory;
import org.simbiosis.bprs.ui.config.client.deposit.IDepProduct.Activity;
import org.simbiosis.bprs.ui.config.client.rpc.AppService;
import org.simbiosis.bprs.ui.config.client.rpc.AppServiceAsync;
import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.ProductDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class DepProductListActivity extends Activity {

	private final AppServiceAsync wmsService = GWT.create(AppService.class);

	Place myPlace;
	AppFactory appFactory;

	public DepProductListActivity(Place myPlace, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IDepProduct myForm = appFactory.getDepProduct();
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
		IDepProduct myForm = appFactory.getDepProduct();
		myForm.newProduct();
	}

	private void onReload() {
		loadDepProduct();
		IDepProduct myForm = appFactory.getDepProduct();
		myForm.clearViewer();
	}

	private void onSave() {

		IDepProduct myForm = appFactory.getDepProduct();
		ProductDv dv = myForm.getProduct();
		showLoading();
		wmsService.saveDepProduct(getKey(), dv, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				hideLoading();
				Window.alert("Data sudah disimpan");
				onReload();
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : saveDepProduct");
			}
		});
	}

	private void onBack() {
		IDepProduct myForm = appFactory.getDepProduct();
		myForm.viewSelected();
	}

	private void onEdit() {
		IDepProduct myForm = appFactory.getDepProduct();
		myForm.editSelected();
	}

	private void loadCommonData() {
		showLoading();
		wmsService.listCoaForTransaction(getKey(),
				new AsyncCallback<List<CoaDv>>() {

					@Override
					public void onSuccess(List<CoaDv> result) {
						IDepProduct myForm = appFactory.getDepProduct();
						myForm.setCoa(result);
						loadTerm();
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listCoa1");
					}
				});
	}

	private void loadTerm() {
		wmsService.listTerm(getKey(),
				new AsyncCallback<List<SimpleBranchDv>>() {

					@Override
					public void onSuccess(List<SimpleBranchDv> result) {
						IDepProduct myForm = appFactory.getDepProduct();
						myForm.setTerm(result);
						loadDepProduct();
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listTerm");
					}
				});
	}

	private void loadDepProduct() {
		wmsService.listDepProduct(getKey(),
				new AsyncCallback<List<ProductDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listDepProduct");
					}

					@Override
					public void onSuccess(List<ProductDv> result) {
						IDepProduct myForm = appFactory.getDepProduct();
						myForm.setProduct(result);
						hideLoading();
					}
				});
	}
}
