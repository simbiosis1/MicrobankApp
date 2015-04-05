package org.simbiosis.ui.bprs.reporting.client;

import org.kembang.module.client.mvp.KembangHistoryMapper;
import org.simbiosis.ui.bprs.reporting.client.depositnominatif.DepositNominatifPlace;
import org.simbiosis.ui.bprs.reporting.client.labarugi.LabaRugiPlace;
import org.simbiosis.ui.bprs.reporting.client.neraca.NeracaPlace;
import org.simbiosis.ui.bprs.reporting.client.neracapercobaan.NeracaPercobaanPlace;
import org.simbiosis.ui.bprs.reporting.client.places.Publikasi;
import org.simbiosis.ui.bprs.reporting.client.revenuesharing.RevenueSharingPlace;
import org.simbiosis.ui.bprs.reporting.client.savingnominatif.SavingNominatifPlace;

import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ SavingNominatifPlace.Tokenizer.class,
		DepositNominatifPlace.Tokenizer.class, NeracaPlace.Tokenizer.class,
		LabaRugiPlace.Tokenizer.class, RevenueSharingPlace.Tokenizer.class,
		NeracaPercobaanPlace.Tokenizer.class,Publikasi.Tokenizer.class })
public interface AppHistoryMapper extends KembangHistoryMapper {

}
