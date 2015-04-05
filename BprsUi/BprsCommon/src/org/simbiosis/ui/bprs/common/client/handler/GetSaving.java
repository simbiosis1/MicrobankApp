package org.simbiosis.ui.bprs.common.client.handler;

import org.simbiosis.ui.bprs.common.client.rpc.MicBankService;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankServiceAsync;
import org.simbiosis.ui.bprs.common.shared.SavingDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GetSaving {
	private final MicBankServiceAsync koperasiService = GWT
			.create(MicBankService.class);

	GetSavingHandler handler;

	public GetSaving(GetSavingHandler handler) {
		this.handler = handler;
	}

	public void go(Long id) {
		koperasiService.getSaving(id, new AsyncCallback<SavingDv>() {

			@Override
			public void onFailure(Throwable caught) {
				handler.hideLoading();
				Window.alert("Error : getSaving");
			}

			@Override
			public void onSuccess(SavingDv savingDv) {
				handler.hideLoading();
				handler.showSaving(savingDv);
			}
		});
	}
}
