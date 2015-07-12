package org.simbiosis.bprs.ui.config.client;

import org.kembang.module.client.mvp.KembangHistoryMapper;
import org.simbiosis.bprs.ui.config.client.places.DepositProduct;
import org.simbiosis.bprs.ui.config.client.places.GlConfig;
import org.simbiosis.bprs.ui.config.client.places.LoanProduct;
import org.simbiosis.bprs.ui.config.client.places.SavingProduct;
import org.simbiosis.bprs.ui.config.client.places.TellerConfig;

import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ TellerConfig.Tokenizer.class,
		SavingProduct.Tokenizer.class,
		DepositProduct.Tokenizer.class,
		LoanProduct.Tokenizer.class, GlConfig.Tokenizer.class })
public interface AppHistoryMapper extends KembangHistoryMapper {

}
