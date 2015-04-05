package org.simbiosis.ui.bprs.common.client.handler;

import java.util.List;

import org.simbiosis.ui.bprs.common.shared.DataDv;

public interface FindSavingHandler {
	public abstract void showSavingList(List<DataDv> dataDv);

	public abstract void showLoading();

	public abstract void hideLoading();
}
