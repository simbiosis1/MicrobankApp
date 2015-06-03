package org.simbiosis.ui.bprs.admin.client;

import org.kembang.module.client.mvp.KembangActivityMapper;
import org.simbiosis.ui.bprs.admin.client.deposit.DepositActivity;
import org.simbiosis.ui.bprs.admin.client.deposit.DepositPlace;
import org.simbiosis.ui.bprs.admin.client.loan.LoanActivity;
import org.simbiosis.ui.bprs.admin.client.loan.LoanPlace;
import org.simbiosis.ui.bprs.admin.client.places.UploadCollective;
import org.simbiosis.ui.bprs.admin.client.savingjournal.SavingJournalActivity;
import org.simbiosis.ui.bprs.admin.client.savingjournal.SavingJournalPlace;
import org.simbiosis.ui.bprs.admin.client.transfer.TransferActivity;
import org.simbiosis.ui.bprs.admin.client.transfer.TransferPlace;
import org.simbiosis.ui.bprs.admin.client.uploadcollective.UploadCollectiveActivity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper extends KembangActivityMapper {

	public AppActivityMapper(AppFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public Activity createActivity(Place place) {
		AppFactory clientFactory = (AppFactory) getClientFactory();
		if (place instanceof LoanPlace) {
			return new LoanActivity((LoanPlace) place,
					clientFactory);
		} else if (place instanceof DepositPlace) {
			return new DepositActivity((DepositPlace) place, clientFactory);
		} else if (place instanceof SavingJournalPlace) {
			return new SavingJournalActivity((SavingJournalPlace) place,
					clientFactory);
		} else if (place instanceof TransferPlace) {
			return new TransferActivity((TransferPlace) place, clientFactory);
		} else if (place instanceof UploadCollective) {
			return new UploadCollectiveActivity((UploadCollective) place, clientFactory);
		}
		return null;
	}

}
