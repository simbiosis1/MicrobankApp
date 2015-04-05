package org.simbiosis.bprs.ui.config.client.loan;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.bprs.ui.config.client.AppFactory;
import org.simbiosis.bprs.ui.config.client.loan.ILoanProduct.Activity;
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

public class LoanProductListActivity extends Activity {

	private final AppServiceAsync wmsService = GWT.create(AppService.class);

	Place myPlace;
	AppFactory appFactory;

	public LoanProductListActivity(Place myPlace, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ILoanProduct myForm = appFactory.getLoanProduct();
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
		ILoanProduct myForm = appFactory.getLoanProduct();
		myForm.newProduct();
	}

	private void onReload() {
		showLoading();
		loadProduct();
	}

	private void onSave() {
		ILoanProduct myForm = appFactory.getLoanProduct();
		ProductDv dv = myForm.getProduct();
		showLoading();
		wmsService.saveLoanProduct(getKey(), dv, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				hideLoading();
				Window.alert("Data sudah disimpan");
				ILoanProduct myForm = appFactory.getLoanProduct();
				myForm.clearViewer();
				onReload();
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : saveLoanProduct");
			}
		});
	}

	private void onBack() {
		ILoanProduct myForm = appFactory.getLoanProduct();
		myForm.viewSelected();
	}

	private void onEdit() {
		ILoanProduct myForm = appFactory.getLoanProduct();
		myForm.editSelected();
	}

	private void loadCommonData() {
		showLoading();
		wmsService.listCoaForTransaction(getKey(),
				new AsyncCallback<List<CoaDv>>() {

					@Override
					public void onSuccess(List<CoaDv> result) {
						ILoanProduct myForm = appFactory.getLoanProduct();
						myForm.setCoa(result);
						//loadProduct();
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listCoa1");
					}
				});
		loadProduct();
	}

	private void loadProduct() {
		wmsService.listLoanProduct(getKey(),
				new AsyncCallback<List<ProductDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listProduct");
					}

					@Override
					public void onSuccess(List<ProductDv> result) {
						ILoanProduct myForm = appFactory.getLoanProduct();
						myForm.setProducts(result);
						myForm.clearViewer();
						hideLoading();
					}
				});
	}
}
