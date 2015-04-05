package org.simbiosis.ui.bprs.common.client.handler;

import java.util.Date;
import java.util.List;

import org.simbiosis.ui.bprs.common.client.rpc.MicBankService;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankServiceAsync;
import org.simbiosis.ui.bprs.common.shared.DataDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FindCustomer {

	private final MicBankServiceAsync koperasiService = GWT
			.create(MicBankService.class);

	FindCustomerHandler cariHandler;
	String key;

	public FindCustomer(String key, FindCustomerHandler cariHandler) {
		this.cariHandler = cariHandler;
		this.key = key;
	}

	public void go(Boolean hasName, Boolean hasDob, String name, Date dob) {
		koperasiService.findCustomer(key, hasName, hasDob, name, dob,
				new AsyncCallback<List<DataDv>>() {

					@Override
					public void onSuccess(List<DataDv> result) {
						cariHandler.hideLoading();
						cariHandler.showCustomerList(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						cariHandler.hideLoading();
						Window.alert("Error : findCustomer");
					}
				});
	}
}
