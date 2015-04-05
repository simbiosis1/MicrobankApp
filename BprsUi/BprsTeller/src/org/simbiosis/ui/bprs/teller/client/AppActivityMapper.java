package org.simbiosis.ui.bprs.teller.client;

import org.kembang.module.client.mvp.KembangActivityMapper;
import org.simbiosis.ui.bprs.teller.client.cashtrans.CashTransActivity;
import org.simbiosis.ui.bprs.teller.client.cashtrans.CashTransPlace;
import org.simbiosis.ui.bprs.teller.client.htvault.HtVaultActivity;
import org.simbiosis.ui.bprs.teller.client.htvault.HtVaultPlace;
import org.simbiosis.ui.bprs.teller.client.savingdeposit.DepositActivity;
import org.simbiosis.ui.bprs.teller.client.savingdeposit.DepositPlace;
import org.simbiosis.ui.bprs.teller.client.savingprint.SavingPrintActivity;
import org.simbiosis.ui.bprs.teller.client.savingprint.SavingPrintPlace;
import org.simbiosis.ui.bprs.teller.client.savingtrans.SavingTransListActivity;
import org.simbiosis.ui.bprs.teller.client.savingtrans.SavingTransListPlace;
import org.simbiosis.ui.bprs.teller.client.savingwd.WithdrawalActivity;
import org.simbiosis.ui.bprs.teller.client.savingwd.WithdrawalPlace;
import org.simbiosis.ui.bprs.teller.client.tellertrans.TellerTransListActivity;
import org.simbiosis.ui.bprs.teller.client.tellertrans.TellerTransListPlace;
import org.simbiosis.ui.bprs.teller.client.vault.VaultActivity;
import org.simbiosis.ui.bprs.teller.client.vault.VaultPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper extends KembangActivityMapper {

	public AppActivityMapper(AppFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public Activity createActivity(Place place) {
		AppFactory clientFactory = (AppFactory) getClientFactory();
		if (place instanceof DepositPlace) {
			return new DepositActivity((DepositPlace) place,
					clientFactory);
		} else if (place instanceof WithdrawalPlace) {
			return new WithdrawalActivity((WithdrawalPlace) place,
					clientFactory);
		} else if (place instanceof CashTransPlace) {
			return new CashTransActivity((CashTransPlace) place, clientFactory);
		} else if (place instanceof VaultPlace) {
			return new VaultActivity((VaultPlace) place, clientFactory);
		} else if (place instanceof HtVaultPlace) {
			return new HtVaultActivity((HtVaultPlace) place, clientFactory);
		} else if (place instanceof TellerTransListPlace) {
			return new TellerTransListActivity(
					(TellerTransListPlace) place, clientFactory);
		} else if (place instanceof SavingPrintPlace) {
			return new SavingPrintActivity((SavingPrintPlace) place,
					clientFactory);
		} else if (place instanceof SavingTransListPlace) {
			return new SavingTransListActivity((SavingTransListPlace) place,
					clientFactory);
		}
		return null;
	}

}
