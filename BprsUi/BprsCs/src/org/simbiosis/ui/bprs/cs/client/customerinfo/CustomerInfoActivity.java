package org.simbiosis.ui.bprs.cs.client.customerinfo;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.client.handler.FindCustomer;
import org.simbiosis.ui.bprs.common.client.handler.FindCustomerHandler;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.cs.client.AppFactory;
import org.simbiosis.ui.bprs.cs.client.customerinfo.ICustomerInfo.Activity;
import org.simbiosis.ui.bprs.cs.client.places.CustomerInfo;
import org.simbiosis.ui.bprs.cs.client.rpc.CsService;
import org.simbiosis.ui.bprs.cs.client.rpc.CsServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class CustomerInfoActivity extends Activity {

	CustomerInfo myPlace;
	AppFactory appFactory;
	CustomerInfoDataActivity dataActivity;
	Activity activity;

	private final CsServiceAsync csService = GWT.create(CsService.class);

	public CustomerInfoActivity(CustomerInfo myPlace, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
		this.activity = this;
	}

	CustomerInfoDataActivity getDataActivity() {
		if (dataActivity == null) {
			dataActivity = new CustomerInfoDataActivity(new CustomerInfo(""),
					appFactory);
		}
		return dataActivity;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ICustomerInfo myForm = appFactory.getCustomerInfo();
		myForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, myForm.getFormWidget());
		// loadCommonListProvinsi();
		// loadCommonListCity();
		// loadCommonListJenisPekerjaan();
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
		DataDv selectedData = appFactory.getCustomerInfo().getSelectedData();
		ICustomerInfoData viewer = appFactory.getCustomerInfoData();
		viewer.setActivity(getDataActivity(), appFactory.getAppStatus());
		dataActivity.showData(selectedData);
		appFactory.showApplication(null, viewer.getFormWidget());
	}

	void onSearch() {
		showLoading();
		ICustomerInfo myForm = appFactory.getCustomerInfo();
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
				ICustomerInfo myForm = appFactory.getCustomerInfo();
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

}
