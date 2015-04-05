package org.simbiosis.ui.bprs.loan.client;

import org.kembang.module.client.mvp.KembangEntryPoint;

import com.google.gwt.core.client.GWT;

public class BprsLoanEntryPoint extends KembangEntryPoint {

	public BprsLoanEntryPoint() {
		super();
	}

	@Override
	public void initComponent() {
		BprsLoanFactory clientFactory = GWT.create(BprsLoanFactory.class);
		BprsLoanHistoryMapper historyMapper = GWT
				.create(BprsLoanHistoryMapper.class);
		setHistoryMapper(historyMapper);
		setClientFactory(clientFactory);
		setActivityMapper(new BprsLoanActivityMapper(clientFactory));
	}

}
