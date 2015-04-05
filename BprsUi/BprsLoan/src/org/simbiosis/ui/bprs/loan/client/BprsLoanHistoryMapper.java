package org.simbiosis.ui.bprs.loan.client;

import org.kembang.module.client.mvp.KembangHistoryMapper;
import org.simbiosis.ui.bprs.loan.client.guarantee.GuaranteeListPlace;
import org.simbiosis.ui.bprs.loan.client.info.InfoListPlace;
import org.simbiosis.ui.bprs.loan.client.loan.LoanListPlace;
import org.simbiosis.ui.bprs.loan.client.places.Reschedule;
import org.simbiosis.ui.bprs.loan.client.simulation.SimulationPlace;

import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ LoanListPlace.Tokenizer.class, InfoListPlace.Tokenizer.class,
		Reschedule.Tokenizer.class, GuaranteeListPlace.Tokenizer.class,
		SimulationPlace.Tokenizer.class })
public interface BprsLoanHistoryMapper extends KembangHistoryMapper {

}
