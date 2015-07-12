package org.simbiosis.bprs.ui.config.client;

import org.kembang.module.client.mvp.KembangActivityMapper;
import org.simbiosis.bprs.ui.config.client.depositproduct.DepProductListActivity;
import org.simbiosis.bprs.ui.config.client.gl.GlConfigActivity;
import org.simbiosis.bprs.ui.config.client.loanproduct.LoanProductListActivity;
import org.simbiosis.bprs.ui.config.client.places.DepositProduct;
import org.simbiosis.bprs.ui.config.client.places.GlConfig;
import org.simbiosis.bprs.ui.config.client.places.LoanProduct;
import org.simbiosis.bprs.ui.config.client.places.SavingProduct;
import org.simbiosis.bprs.ui.config.client.places.TellerConfig;
import org.simbiosis.bprs.ui.config.client.savingproduct.SavProductListActivity;
import org.simbiosis.bprs.ui.config.client.teller.TellerListActivity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper extends KembangActivityMapper {

	public AppActivityMapper(AppFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public Activity createActivity(Place place) {
		AppFactory clientFactory = (AppFactory) getClientFactory();
		if (place instanceof TellerConfig) {
			return new TellerListActivity((TellerConfig) place,
					clientFactory);
		} else if (place instanceof SavingProduct) {
			return new SavProductListActivity((SavingProduct) place,
					clientFactory);
		} else if (place instanceof DepositProduct) {
			return new DepProductListActivity((DepositProduct) place,
					clientFactory);
		} else if (place instanceof LoanProduct) {
			return new LoanProductListActivity((LoanProduct) place,
					clientFactory);
		} else if (place instanceof GlConfig) {
			return new GlConfigActivity((GlConfig) place, clientFactory);
		}
		return null;
	}

}
