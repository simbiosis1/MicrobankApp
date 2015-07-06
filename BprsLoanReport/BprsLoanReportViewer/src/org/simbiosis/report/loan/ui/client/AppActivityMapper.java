package org.simbiosis.report.loan.ui.client;

import org.kembang.module.client.mvp.KembangActivityMapper;
import org.simbiosis.report.loan.ui.client.dropping.DroppingActivity;
import org.simbiosis.report.loan.ui.client.nominatif.NominatifActivity;
import org.simbiosis.report.loan.ui.client.nominatifao.NominatifAoActivity;
import org.simbiosis.report.loan.ui.client.places.Dropping;
import org.simbiosis.report.loan.ui.client.places.Nominatif;
import org.simbiosis.report.loan.ui.client.places.NominatifAo;
import org.simbiosis.report.loan.ui.client.places.Remedial;
import org.simbiosis.report.loan.ui.client.places.Remedial1;
import org.simbiosis.report.loan.ui.client.places.Remedial12;
import org.simbiosis.report.loan.ui.client.places.Remedial34;
import org.simbiosis.report.loan.ui.client.places.Transaction;
import org.simbiosis.report.loan.ui.client.remedial.RemedialActivity;
import org.simbiosis.report.loan.ui.client.remedial1.Remedial1Activity;
import org.simbiosis.report.loan.ui.client.remedial12.Remedial12Activity;
import org.simbiosis.report.loan.ui.client.remedial34.Remedial34Activity;
import org.simbiosis.report.loan.ui.client.transaction.TransactionActivity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper extends KembangActivityMapper {

	public AppActivityMapper(AppFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public Activity createActivity(Place place) {
		AppFactory clientFactory = (AppFactory) getClientFactory();
		if (place instanceof Nominatif) {
			return new NominatifActivity((Nominatif) place, clientFactory);
		} else if (place instanceof NominatifAo) {
			return new NominatifAoActivity((NominatifAo) place, clientFactory);
		} else if (place instanceof Remedial) {
			return new RemedialActivity((Remedial) place, clientFactory);
		} else if (place instanceof Remedial1) {
			return new Remedial1Activity((Remedial1) place, clientFactory);
		} else if (place instanceof Remedial12) {
			return new Remedial12Activity((Remedial12) place, clientFactory);
		} else if (place instanceof Remedial34) {
			return new Remedial34Activity((Remedial34) place, clientFactory);
		} else if (place instanceof Transaction) {
			return new TransactionActivity((Transaction) place, clientFactory);
		} else if (place instanceof Dropping) {
			return new DroppingActivity((Dropping) place, clientFactory);
		}
		return null;
	}
}
