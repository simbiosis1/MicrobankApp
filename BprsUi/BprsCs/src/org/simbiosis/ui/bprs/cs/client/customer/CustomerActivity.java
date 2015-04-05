package org.simbiosis.ui.bprs.cs.client.customer;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.client.handler.FindCustomer;
import org.simbiosis.ui.bprs.common.client.handler.FindCustomerHandler;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.cs.client.AppFactory;
import org.simbiosis.ui.bprs.cs.client.customer.ICustomer.Activity;
import org.simbiosis.ui.bprs.cs.client.customerinput.CustomerInputActivity;
import org.simbiosis.ui.bprs.cs.client.customerinput.ICustomerInput;
import org.simbiosis.ui.bprs.cs.client.rpc.CsService;
import org.simbiosis.ui.bprs.cs.client.rpc.CsServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class CustomerActivity extends Activity {

	CustomerPlace myPlace;
	AppFactory appFactory;
	CustomerInputActivity inputActivity;
	Activity activity;

	private final CsServiceAsync csService = GWT.create(CsService.class);

	public CustomerActivity(CustomerPlace myPlace, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
		this.activity = this;
	}

	CustomerInputActivity getInputActivity() {
		if (inputActivity == null) {
			inputActivity = new CustomerInputActivity(new CustomerPlace(""),
					appFactory);
		}
		return inputActivity;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ICustomer myForm = appFactory.getListCustomer();
		myForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, myForm.getFormWidget());
		loadCommonListProvinsi();
		loadCommonListCity();
		loadCommonListJenisPekerjaan();
	}

	

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_VIEW:
			onView();
			break;
		case FA_SEARCH:
			onSearch();
			break;
		default:
			break;
		}
	}

	void onView() {
		showLoading();
		DataDv selectedData = appFactory.getListCustomer().getSelectedData();
		ICustomerInput viewerForm = appFactory.getCustomerViewer();
		viewerForm.setActivity(getInputActivity(), appFactory.getAppStatus());
		inputActivity.showData(selectedData);
		appFactory.showApplication(null, viewerForm.getFormWidget());
	}

	void onSearch() {
		showLoading();
		ICustomer myForm = appFactory.getListCustomer();
		myForm.searchData();
	}

	@Override
	public void searchData(Boolean hasName, Boolean hasDob, String name,
			Date dob) {
		FindCustomerHandler handler = new FindCustomerHandler() {

			@Override
			public void showLoading() {
				activity.showLoading();
			}

			@Override
			public void showCustomerList(List<DataDv> result) {
				CariDataDv dataPencarian = new CariDataDv();
				dataPencarian.getResultTable().addAll(result);
				ICustomer myForm = appFactory.getListCustomer();
				myForm.showData(dataPencarian);
				hideLoading();
			}

			@Override
			public void hideLoading() {
				activity.hideLoading();
			}
		};
		FindCustomer findCustomer = new FindCustomer(getKey(), handler);
		findCustomer.go(hasName, hasDob, name, dob);
	}

	public void loadCommonListProvinsi() {
		String key = Cookies.getCookie(appFactory.getAppStatus()
				.getCookiesName());
		csService.loadCommonListProvinsi(key,
				new AsyncCallback<List<DataDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : loadCommonListSavingProvinsi");
					}

					@Override
					public void onSuccess(List<DataDv> result) {
						hideLoading();
						ICustomerInput editorForm = appFactory
								.getCustomerEditor();
						editorForm.setSavingProvinsiList(result);
					}
				});
	}
	
	private void loadCommonListCity() {
		String key = Cookies.getCookie(appFactory.getAppStatus()
				.getCookiesName());
		csService.loadCommonListCity(key,
				new AsyncCallback<List<DataDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : Load Common List City");
					}

					@Override
					public void onSuccess(List<DataDv> result) {
						hideLoading();
						ICustomerInput editorForm = appFactory
								.getCustomerEditor();
						editorForm.setSavingCityList(result);
					}
				});
		
	}
	

	private void loadCommonListJenisPekerjaan() {
		String key = Cookies.getCookie(appFactory.getAppStatus()
				.getCookiesName());
		csService.loadCommonListJenisPekerjaan(key,
				new AsyncCallback<List<DataDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : Load Common List Jenis Pekerjaan");
					}

					@Override
					public void onSuccess(List<DataDv> result) {
						hideLoading();
						ICustomerInput editorForm = appFactory
								.getCustomerEditor();
						editorForm.setSavingJenisPekerjaanList(result);
					}
				});
		
	}

}
