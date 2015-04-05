package org.simbiosis.ui.bprs.security.client;

import org.kembang.module.client.mvp.KembangEntryPoint;

import com.google.gwt.core.client.GWT;

public class BprsSecurityEntryPoint extends KembangEntryPoint {

	public BprsSecurityEntryPoint() {
		super();
	}

	@Override
	public void initComponent() {
		BprsSecurityFactory clientFactory = GWT.create(BprsSecurityFactory.class);
		BprsSecurityHistoryMapper historyMapper = GWT
				.create(BprsSecurityHistoryMapper.class);
		setHistoryMapper(historyMapper);
		setClientFactory(clientFactory);
		setActivityMapper(new BprsSecurityActivityMapper(clientFactory));
	}

}
