package org.simbiosis.ui.bprs.common.client.handler;

import java.util.Date;
import java.util.List;

import org.simbiosis.ui.bprs.common.client.rpc.MicBankService;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankServiceAsync;
import org.simbiosis.ui.bprs.common.shared.DataDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FindSaving {

	private final MicBankServiceAsync koperasiService = GWT
			.create(MicBankService.class);

	FindSavingHandler cariHandler;
	String key;
	Boolean tellerTransaction;

	public FindSaving(String key, Boolean tellerTransaction,
			FindSavingHandler cariHandler) {
		this.cariHandler = cariHandler;
		this.key = key;
		this.tellerTransaction = tellerTransaction;
	}

	public void go(Boolean hasCode, Boolean hasName,
			Boolean hasDob, String code, String name, Date dob) {
		koperasiService.findSaving(key, tellerTransaction, hasCode, hasName,
				hasDob, code, name, dob, new AsyncCallback<List<DataDv>>() {

					@Override
					public void onSuccess(List<DataDv> result) {
						cariHandler.hideLoading();
						cariHandler.showSavingList(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						cariHandler.hideLoading();
						Window.alert("Error : findCustomer");
					}
				});
	}
}
