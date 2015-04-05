package org.simbiosis.ui.bprs.reporting.client;

import org.kembang.module.client.mvp.KembangClientFactoryImpl;
import org.simbiosis.ui.bprs.reporting.client.depositnominatif.DepositNominatif;
import org.simbiosis.ui.bprs.reporting.client.depositnominatif.IDepositNominatif;
import org.simbiosis.ui.bprs.reporting.client.labarugi.ILabaRugi;
import org.simbiosis.ui.bprs.reporting.client.labarugi.LabaRugi;
import org.simbiosis.ui.bprs.reporting.client.neraca.INeraca;
import org.simbiosis.ui.bprs.reporting.client.neraca.Neraca;
import org.simbiosis.ui.bprs.reporting.client.neracapercobaan.INeracaPercobaan;
import org.simbiosis.ui.bprs.reporting.client.neracapercobaan.NeracaPercobaan;
import org.simbiosis.ui.bprs.reporting.client.publikasi.IPublikasi;
import org.simbiosis.ui.bprs.reporting.client.publikasi.Publikasi;
import org.simbiosis.ui.bprs.reporting.client.revenuesharing.IRevenueSharing;
import org.simbiosis.ui.bprs.reporting.client.revenuesharing.RevenueSharing;
import org.simbiosis.ui.bprs.reporting.client.savingnominatif.ISavingNominatif;
import org.simbiosis.ui.bprs.reporting.client.savingnominatif.SavingNominatif;

public class AppFactoryImpl extends KembangClientFactoryImpl implements
		AppFactory {

	static final SavingNominatif SAVING_NOMINATIF = new SavingNominatif();
	static final DepositNominatif DEPOSIT_NOMINATIF = new DepositNominatif();
	static final Neraca NERACA = new Neraca();
	static final LabaRugi LABA_RUGI = new LabaRugi();
	static final RevenueSharing REVENUE_SHARING = new RevenueSharing();
	static final NeracaPercobaan NERACA_PERCOBAAN = new NeracaPercobaan();
	static final Publikasi PUBLIKASI = new Publikasi();

	@Override
	public ISavingNominatif getSavingNominatif() {
		return SAVING_NOMINATIF;
	}

	@Override
	public IDepositNominatif getDepositNominatif() {
		return DEPOSIT_NOMINATIF;
	}

	@Override
	public INeraca getNeraca() {
		return NERACA;
	}

	@Override
	public ILabaRugi getLabaRugi() {
		return LABA_RUGI;
	}

	@Override
	public IRevenueSharing getRevenueSharing() {
		return REVENUE_SHARING;
	}

	@Override
	public INeracaPercobaan getNeracaPercobaan() {
		return NERACA_PERCOBAAN;
	}

	@Override
	public IPublikasi getPublikasi() {
		return PUBLIKASI;
	}

}
