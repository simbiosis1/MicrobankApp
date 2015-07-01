package org.simbiosis.bprs.ui.config.client;

import org.kembang.module.client.mvp.KembangHistoryMapper;
import org.simbiosis.bprs.ui.config.client.deposit.DepProductListPlace;
import org.simbiosis.bprs.ui.config.client.loan.LoanProductListPlace;
import org.simbiosis.bprs.ui.config.client.places.GlConfig;
import org.simbiosis.bprs.ui.config.client.savingproduct.SavProductListPlace;
import org.simbiosis.bprs.ui.config.client.teller.TellerListPlace;

import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ TellerListPlace.Tokenizer.class,
		SavProductListPlace.Tokenizer.class,
		DepProductListPlace.Tokenizer.class,
		LoanProductListPlace.Tokenizer.class, GlConfig.Tokenizer.class })
public interface AppHistoryMapper extends KembangHistoryMapper {

}
