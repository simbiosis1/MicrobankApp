package org.simbiosis.ui.bprs.cs.client;

import org.kembang.module.client.mvp.KembangActivityMapper;
import org.simbiosis.ui.bprs.cs.client.customer.CustomerActivity;
import org.simbiosis.ui.bprs.cs.client.customer.CustomerPlace;
import org.simbiosis.ui.bprs.cs.client.deposit.DepositActivity;
import org.simbiosis.ui.bprs.cs.client.deposit.DepositPlace;
import org.simbiosis.ui.bprs.cs.client.places.SavingBlock;
import org.simbiosis.ui.bprs.cs.client.saving.SavingActivity;
import org.simbiosis.ui.bprs.cs.client.saving.SavingPlace;
import org.simbiosis.ui.bprs.cs.client.savingblock.SavingBlockActivity;
import org.simbiosis.ui.bprs.cs.client.savingclose.SavingCloseActivity;
import org.simbiosis.ui.bprs.cs.client.savingclose.SavingClosePlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper extends KembangActivityMapper {

	public AppActivityMapper(AppFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public Activity createActivity(Place place) {
		AppFactory clientFactory = (AppFactory) getClientFactory();
		if (place instanceof CustomerPlace) {
			return new CustomerActivity((CustomerPlace) place, clientFactory);
		} else if (place instanceof SavingPlace) {
			return new SavingActivity((SavingPlace) place, clientFactory);
		} else if (place instanceof DepositPlace) {
			return new DepositActivity((DepositPlace) place, clientFactory);
		} else if (place instanceof SavingClosePlace) {
			return new SavingCloseActivity((SavingClosePlace) place,
					clientFactory);
		} else if (place instanceof SavingBlock) {
			return new SavingBlockActivity((SavingBlock) place,
					clientFactory);
		}
		return null;
	}

}
