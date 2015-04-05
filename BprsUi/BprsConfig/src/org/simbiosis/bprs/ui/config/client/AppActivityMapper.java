package org.simbiosis.bprs.ui.config.client;

import org.kembang.module.client.mvp.KembangActivityMapper;
import org.simbiosis.bprs.ui.config.client.deposit.DepProductListActivity;
import org.simbiosis.bprs.ui.config.client.deposit.DepProductListPlace;
import org.simbiosis.bprs.ui.config.client.loan.LoanProductListActivity;
import org.simbiosis.bprs.ui.config.client.loan.LoanProductListPlace;
import org.simbiosis.bprs.ui.config.client.saving.SavProductListActivity;
import org.simbiosis.bprs.ui.config.client.saving.SavProductListPlace;
import org.simbiosis.bprs.ui.config.client.teller.TellerListActivity;
import org.simbiosis.bprs.ui.config.client.teller.TellerListPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper extends KembangActivityMapper {

	public AppActivityMapper(AppFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public Activity createActivity(Place place) {
		AppFactory clientFactory = (AppFactory) getClientFactory();
		if (place instanceof TellerListPlace) {
			return new TellerListActivity((TellerListPlace) place,
					clientFactory);
		} else if (place instanceof SavProductListPlace) {
			return new SavProductListActivity((SavProductListPlace) place,
					clientFactory);
		} else if (place instanceof DepProductListPlace) {
			return new DepProductListActivity((DepProductListPlace) place,
					clientFactory);
		} else if (place instanceof LoanProductListPlace) {
			return new LoanProductListActivity((LoanProductListPlace) place,
					clientFactory);
		}
		return null;
	}

}
