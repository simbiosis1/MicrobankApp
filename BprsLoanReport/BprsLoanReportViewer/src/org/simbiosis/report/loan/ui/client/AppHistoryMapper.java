package org.simbiosis.report.loan.ui.client;

import org.kembang.module.client.mvp.KembangHistoryMapper;
import org.simbiosis.report.loan.ui.client.places.Dropping;
import org.simbiosis.report.loan.ui.client.places.Nominatif;
import org.simbiosis.report.loan.ui.client.places.NominatifAo;
import org.simbiosis.report.loan.ui.client.places.Remedial;
import org.simbiosis.report.loan.ui.client.places.Remedial1;
import org.simbiosis.report.loan.ui.client.places.Remedial12;
import org.simbiosis.report.loan.ui.client.places.Remedial34;
import org.simbiosis.report.loan.ui.client.places.Transaction;

import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ Nominatif.Tokenizer.class, NominatifAo.Tokenizer.class,
		Remedial.Tokenizer.class, Remedial1.Tokenizer.class,
		Remedial12.Tokenizer.class, Remedial34.Tokenizer.class,
		Transaction.Tokenizer.class, Dropping.Tokenizer.class })
public interface AppHistoryMapper extends KembangHistoryMapper {

}
