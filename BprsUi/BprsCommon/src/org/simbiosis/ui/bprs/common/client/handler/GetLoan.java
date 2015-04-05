package org.simbiosis.ui.bprs.common.client.handler;

import org.simbiosis.ui.bprs.common.client.rpc.MicBankService;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankServiceAsync;
import org.simbiosis.ui.bprs.common.shared.LoanDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GetLoan {
	private final MicBankServiceAsync koperasiService = GWT
			.create(MicBankService.class);

	GetLoanHandler handler;

	public GetLoan(GetLoanHandler handler) {
		this.handler = handler;
	}

	public void go(Long id) {
		koperasiService.getLoan(id, new AsyncCallback<LoanDv>() {

			@Override
			public void onFailure(Throwable caught) {
				handler.hideLoading();
				Window.alert("Error : getSaving");
			}

			@Override
			public void onSuccess(LoanDv savingDv) {
				handler.hideLoading();
				handler.showLoan(savingDv);
			}
		});
	}
}
