package org.simbiosis.ui.bprs.reporting.client;

import org.kembang.module.client.mvp.KembangActivityMapper;
import org.simbiosis.ui.bprs.reporting.client.depositnominatif.DepositNominatifActivity;
import org.simbiosis.ui.bprs.reporting.client.depositnominatif.DepositNominatifPlace;
import org.simbiosis.ui.bprs.reporting.client.labarugi.LabaRugiActivity;
import org.simbiosis.ui.bprs.reporting.client.labarugi.LabaRugiPlace;
import org.simbiosis.ui.bprs.reporting.client.neraca.NeracaActivity;
import org.simbiosis.ui.bprs.reporting.client.neraca.NeracaPlace;
import org.simbiosis.ui.bprs.reporting.client.neracapercobaan.NeracaPercobaanActivity;
import org.simbiosis.ui.bprs.reporting.client.neracapercobaan.NeracaPercobaanPlace;
import org.simbiosis.ui.bprs.reporting.client.places.Publikasi;
import org.simbiosis.ui.bprs.reporting.client.publikasi.PublikasiActivity;
import org.simbiosis.ui.bprs.reporting.client.revenuesharing.RevenueSharingActivity;
import org.simbiosis.ui.bprs.reporting.client.revenuesharing.RevenueSharingPlace;
import org.simbiosis.ui.bprs.reporting.client.savingnominatif.SavingNominatifActivity;
import org.simbiosis.ui.bprs.reporting.client.savingnominatif.SavingNominatifPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper extends KembangActivityMapper {

	public AppActivityMapper(AppFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public Activity createActivity(Place place) {
		AppFactory clientFactory = (AppFactory) getClientFactory();
		if (place instanceof SavingNominatifPlace) {
			return new SavingNominatifActivity((SavingNominatifPlace) place,
					clientFactory);
		} else if (place instanceof DepositNominatifPlace) {
			return new DepositNominatifActivity((DepositNominatifPlace) place,
					clientFactory);
		} else if (place instanceof NeracaPlace) {
			return new NeracaActivity((NeracaPlace) place, clientFactory);
		} else if (place instanceof RevenueSharingPlace) {
			return new RevenueSharingActivity((RevenueSharingPlace) place,
					clientFactory);
		} else if (place instanceof LabaRugiPlace) {
			return new LabaRugiActivity((LabaRugiPlace) place, clientFactory);
		} else if (place instanceof NeracaPercobaanPlace) {
			return new NeracaPercobaanActivity((NeracaPercobaanPlace) place,
					clientFactory);
		} else if (place instanceof Publikasi) {
			return new PublikasiActivity((Publikasi) place, clientFactory);
		}
		return null;
	}
}
