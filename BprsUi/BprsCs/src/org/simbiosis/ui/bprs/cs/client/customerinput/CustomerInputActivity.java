package org.simbiosis.ui.bprs.cs.client.customerinput;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankService;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankServiceAsync;
import org.simbiosis.ui.bprs.common.shared.CustomerDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.cs.client.AppFactory;
import org.simbiosis.ui.bprs.cs.client.customerinput.ICustomerInput.Activity;
import org.simbiosis.ui.bprs.cs.client.rpc.CsService;
import org.simbiosis.ui.bprs.cs.client.rpc.CsServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class CustomerInputActivity extends Activity {

	private final CsServiceAsync csService = GWT.create(CsService.class);
	private final MicBankServiceAsync comSrv = GWT.create(MicBankService.class);

	Place place;
	AppFactory appFactory;
	Activity activity;

	public CustomerInputActivity(Place myPlace, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.place = myPlace;
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
		CustomerDv data = appFactory.getCustomerViewer().getSelectedData();
		ICustomerInput form = appFactory.getCustomerEditor();
		form.setActivity(this, appFactory.getAppStatus());
		form.showData(data);
		appFactory.showApplication(null, form.getFormWidget());
	}

	void onSave() {
		showLoading();
		CustomerDv data = appFactory.getCustomerEditor().getEditedData();
		csService.saveCustomer(getKey(), data, new AsyncCallback<Long>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : saveCustomer");
			}

			@Override
			public void onSuccess(Long result) {
				showData(result);
			}
		});
	}

	private void showData(Long id) {
		comSrv.getCustomer(id, new AsyncCallback<CustomerDv>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : getCustomer");
			}

			@Override
			public void onSuccess(CustomerDv result) {
				hideLoading();
				ICustomerInput form = appFactory.getCustomerViewer();
				form.setActivity(getActivity(), appFactory.getAppStatus());
				form.showData(result);
				appFactory.showApplication(null, form.getFormWidget());
				Window.alert("Data sudah disimpan");
			}
		});
	}

	void onBack() {
		appFactory.getPlaceController().goTo(place);
	}

	@Override
	public void showData(DataDv data) {
		showLoading();
		comSrv.getCustomer(data.getId(), new AsyncCallback<CustomerDv>() {

			@Override
			public void onSuccess(CustomerDv result) {
				hideLoading();
				ICustomerInput form = appFactory.getCustomerViewer();
				form.showData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : getCustomer");
			}
		});
	}

}
