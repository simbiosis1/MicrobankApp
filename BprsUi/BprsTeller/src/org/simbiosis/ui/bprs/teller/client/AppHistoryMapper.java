package org.simbiosis.ui.bprs.teller.client;

import org.kembang.module.client.mvp.KembangHistoryMapper;
import org.simbiosis.ui.bprs.teller.client.cashtrans.CashTransPlace;
import org.simbiosis.ui.bprs.teller.client.htvault.HtVaultPlace;
import org.simbiosis.ui.bprs.teller.client.places.LoanRepayment;
import org.simbiosis.ui.bprs.teller.client.places.UploadCollective;
import org.simbiosis.ui.bprs.teller.client.savingdeposit.DepositPlace;
import org.simbiosis.ui.bprs.teller.client.savingprint.SavingPrintPlace;
import org.simbiosis.ui.bprs.teller.client.savingtrans.SavingTransListPlace;
import org.simbiosis.ui.bprs.teller.client.savingwd.WithdrawalPlace;
import org.simbiosis.ui.bprs.teller.client.tellertrans.TellerTransListPlace;
import org.simbiosis.ui.bprs.teller.client.vault.VaultPlace;

import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ DepositPlace.Tokenizer.class,
		WithdrawalPlace.Tokenizer.class, CashTransPlace.Tokenizer.class,
		VaultPlace.Tokenizer.class, HtVaultPlace.Tokenizer.class,
		TellerTransListPlace.Tokenizer.class, SavingPrintPlace.Tokenizer.class,
		SavingTransListPlace.Tokenizer.class, UploadCollective.Tokenizer.class,
		LoanRepayment.Tokenizer.class })
public interface AppHistoryMapper extends KembangHistoryMapper {

}
