package org.simbiosis.ui.bprs.cs.client.customerinfo;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.cs.client.AppFactory;
import org.simbiosis.ui.bprs.cs.client.customerinfo.ICustomerInfoData.Activity;
import org.simbiosis.ui.bprs.cs.client.places.CustomerInfo;
import org.simbiosis.ui.bprs.cs.client.rpc.CsService;
import org.simbiosis.ui.bprs.cs.client.rpc.CsServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class CustomerInfoDataActivity extends Activity {

	CustomerInfo myPlace;
	AppFactory appFactory;
	Activity activity;

	private final CsServiceAsync csService = GWT.create(CsService.class);

	public CustomerInfoDataActivity(CustomerInfo myPlace, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
		this.activity = this;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_BACK:
			onBack();
			break;
		default:
			break;
		}
	}

	void onBack() {
		appFactory.getPlaceController().goTo(myPlace);
	}

	@Override
	public void showData(DataDv dataDv) {
	}

}
