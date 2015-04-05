package org.simbiosis.ui.bprs.common.client.handler;

import org.simbiosis.ui.bprs.common.shared.SavingDv;

public interface GetSavingHandler {
	public abstract void showSaving(SavingDv savingDv);

	public abstract void showLoading();

	public abstract void hideLoading();
}
