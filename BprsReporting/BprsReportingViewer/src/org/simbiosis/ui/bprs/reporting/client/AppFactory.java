package org.simbiosis.ui.bprs.reporting.client;

import org.kembang.module.client.mvp.KembangClientFactory;
import org.simbiosis.ui.bprs.reporting.client.depositnominatif.IDepositNominatif;
import org.simbiosis.ui.bprs.reporting.client.labarugi.ILabaRugi;
import org.simbiosis.ui.bprs.reporting.client.neraca.INeraca;
import org.simbiosis.ui.bprs.reporting.client.neracapercobaan.INeracaPercobaan;
import org.simbiosis.ui.bprs.reporting.client.publikasi.IPublikasi;
import org.simbiosis.ui.bprs.reporting.client.revenuesharing.IRevenueSharing;
import org.simbiosis.ui.bprs.reporting.client.savingnominatif.ISavingNominatif;

public interface AppFactory extends KembangClientFactory {
	ISavingNominatif getSavingNominatif();

	IDepositNominatif getDepositNominatif();

	INeraca getNeraca();

	ILabaRugi getLabaRugi();

	IRevenueSharing getRevenueSharing();

	INeracaPercobaan getNeracaPercobaan();
	
	IPublikasi getPublikasi();

}
