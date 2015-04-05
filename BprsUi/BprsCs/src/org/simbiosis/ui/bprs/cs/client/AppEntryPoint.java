package org.simbiosis.ui.bprs.cs.client;

import org.kembang.module.client.mvp.KembangEntryPoint;

import com.google.gwt.core.client.GWT;

public class AppEntryPoint extends KembangEntryPoint {

	public AppEntryPoint() {
		super();
	}

	@Override
	public void initComponent() {
		AppFactory clientFactory = GWT.create(AppFactory.class);
		AppHistoryMapper historyMapper = GWT.create(AppHistoryMapper.class);
		setHistoryMapper(historyMapper);
		setClientFactory(clientFactory);
		setActivityMapper(new AppActivityMapper(clientFactory));
	}

}
