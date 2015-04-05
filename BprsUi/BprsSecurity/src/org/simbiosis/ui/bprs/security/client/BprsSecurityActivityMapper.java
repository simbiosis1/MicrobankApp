package org.simbiosis.ui.bprs.security.client;

import org.kembang.module.client.mvp.KembangActivityMapper;
import org.simbiosis.ui.bprs.security.client.auth.AuthListActivity;
import org.simbiosis.ui.bprs.security.client.auth.AuthListPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class BprsSecurityActivityMapper extends KembangActivityMapper {

	public BprsSecurityActivityMapper(BprsSecurityFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public Activity createActivity(Place place) {
		BprsSecurityFactory clientFactory = (BprsSecurityFactory) getClientFactory();
		if (place instanceof AuthListPlace) {
			return new AuthListActivity((AuthListPlace) place, clientFactory);
		}
		return null;
	}

}
