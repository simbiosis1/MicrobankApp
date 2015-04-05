package org.simbiosis.ui.bprs.common.client.handler;

import org.simbiosis.ui.bprs.common.shared.DepositDv;

public interface GetDepositHandler {
	public abstract void showDeposit(DepositDv depositDv);

	public abstract void showLoading();

	public abstract void hideLoading();
}
