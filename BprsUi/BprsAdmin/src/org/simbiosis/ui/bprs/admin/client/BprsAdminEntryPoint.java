package org.simbiosis.ui.bprs.admin.client;

import org.kembang.module.client.mvp.KembangEntryPoint;

import com.google.gwt.core.client.GWT;

public class BprsAdminEntryPoint extends KembangEntryPoint {

	public BprsAdminEntryPoint() {
		super();
	}

	@Override
	public void initComponent() {
		BprsAdminFactory clientFactory = GWT.create(BprsAdminFactory.class);
		BprsAdminHistoryMapper historyMapper = GWT
				.create(BprsAdminHistoryMapper.class);
		setHistoryMapper(historyMapper);
		setClientFactory(clientFactory);
		setActivityMapper(new BprsAdminActivityMapper(clientFactory));
	}

}
