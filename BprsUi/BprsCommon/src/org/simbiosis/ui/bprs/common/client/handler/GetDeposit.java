package org.simbiosis.ui.bprs.common.client.handler;

import org.simbiosis.ui.bprs.common.client.rpc.MicBankService;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankServiceAsync;
import org.simbiosis.ui.bprs.common.shared.DepositDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GetDeposit {
	private final MicBankServiceAsync koperasiService = GWT
			.create(MicBankService.class);

	GetDepositHandler handler;
	public GetDeposit(GetDepositHandler handler) {
		this.handler = handler;
	}

	public void go(Long id) {
		koperasiService.getDeposit(id, new AsyncCallback<DepositDv>() {

			@Override
			public void onFailure(Throwable caught) {
				handler.hideLoading();
				Window.alert("Error : getSaving");
			}

			@Override
			public void onSuccess(DepositDv savingDv) {
				handler.hideLoading();
				handler.showDeposit(savingDv);
			}
		});
	}
}
