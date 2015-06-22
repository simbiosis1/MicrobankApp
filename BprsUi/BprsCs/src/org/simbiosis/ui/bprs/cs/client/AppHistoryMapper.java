package org.simbiosis.ui.bprs.cs.client;

import org.kembang.module.client.mvp.KembangHistoryMapper;
import org.simbiosis.ui.bprs.cs.client.customer.CustomerPlace;
import org.simbiosis.ui.bprs.cs.client.deposit.DepositPlace;
import org.simbiosis.ui.bprs.cs.client.places.CustomerInfo;
import org.simbiosis.ui.bprs.cs.client.places.SavingBlock;
import org.simbiosis.ui.bprs.cs.client.saving.SavingPlace;
import org.simbiosis.ui.bprs.cs.client.savingclose.SavingClosePlace;

import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ CustomerPlace.Tokenizer.class, SavingPlace.Tokenizer.class,
		DepositPlace.Tokenizer.class, SavingClosePlace.Tokenizer.class,
		SavingBlock.Tokenizer.class, CustomerInfo.Tokenizer.class })
public interface AppHistoryMapper extends KembangHistoryMapper {

}
