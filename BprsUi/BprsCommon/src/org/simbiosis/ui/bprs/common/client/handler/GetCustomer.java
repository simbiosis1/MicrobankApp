package org.simbiosis.ui.bprs.common.client.handler;

import org.simbiosis.ui.bprs.common.client.rpc.MicBankService;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankServiceAsync;
import org.simbiosis.ui.bprs.common.shared.CustomerDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GetCustomer {
	private final MicBankServiceAsync koperasiService = GWT
			.create(MicBankService.class);

	GetCustomerHandler handler;

	public GetCustomer(GetCustomerHandler handler) {
		this.handler = handler;
	}

	public void go(Long id) {
		koperasiService.getCustomer(id, new AsyncCallback<CustomerDv>() {

			@Override
			public void onFailure(Throwable caught) {
				handler.hideLoading();
				Window.alert("Error : getSaving");
			}

			@Override
			public void onSuccess(CustomerDv customerDv) {
				handler.hideLoading();
				SavingDv savingDv = new SavingDv();
				savingDv.setCustomer(customerDv);
				handler.showSaving(savingDv);
			}
		});
	}
}
