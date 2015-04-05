package org.simbiosis.ui.bprs.dashboard.client;

import org.kembang.module.client.mvp.KembangActivityMapper;
import org.simbiosis.ui.bprs.dashboard.client.dashboard.DashboardActivity;
import org.simbiosis.ui.bprs.dashboard.client.loan.DashboardLoanActivity;
import org.simbiosis.ui.bprs.dashboard.client.places.Dashboard;
import org.simbiosis.ui.bprs.dashboard.client.places.DashboardTks;
import org.simbiosis.ui.bprs.dashboard.client.places.DashboardLoan;
import org.simbiosis.ui.bprs.dashboard.client.tks.DashboardTksActivity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper extends KembangActivityMapper {

	public AppActivityMapper(AppFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public Activity createActivity(Place place) {
		AppFactory clientFactory = (AppFactory) getClientFactory();
		if (place instanceof Dashboard) {
			return new DashboardActivity((Dashboard) place, clientFactory);
		} else if (place instanceof DashboardLoan) {
			return new DashboardLoanActivity((DashboardLoan) place, clientFactory);
		} else if (place instanceof DashboardTks) {
			return new DashboardTksActivity((DashboardTks) place,
					clientFactory);
		}
		return null;
	}
}
