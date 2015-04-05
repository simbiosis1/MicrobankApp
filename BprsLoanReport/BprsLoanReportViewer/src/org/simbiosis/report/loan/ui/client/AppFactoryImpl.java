package org.simbiosis.report.loan.ui.client;

import org.kembang.module.client.mvp.KembangClientFactoryImpl;
import org.simbiosis.report.loan.ui.client.dropping.Dropping;
import org.simbiosis.report.loan.ui.client.dropping.IDropping;
import org.simbiosis.report.loan.ui.client.nominatif.INominatif;
import org.simbiosis.report.loan.ui.client.nominatif.Nominatif;
import org.simbiosis.report.loan.ui.client.nominatifao.INominatifAo;
import org.simbiosis.report.loan.ui.client.nominatifao.NominatifAo;
import org.simbiosis.report.loan.ui.client.remedial.IRemedial;
import org.simbiosis.report.loan.ui.client.remedial.Remedial;
import org.simbiosis.report.loan.ui.client.remedial1.IRemedial1;
import org.simbiosis.report.loan.ui.client.remedial1.Remedial1;
import org.simbiosis.report.loan.ui.client.transaction.ITransaction;
import org.simbiosis.report.loan.ui.client.transaction.Transaction;

public class AppFactoryImpl extends KembangClientFactoryImpl implements
		AppFactory {

	static final Nominatif LOAN_NOMINATIF = new Nominatif();
	static final NominatifAo LOAN_NOMINATIFAO = new NominatifAo();
	static final Remedial LOAN_REMEDIAL = new Remedial();
	static final Remedial1 LOAN_REMEDIAL1 = new Remedial1();
	static final Transaction TRANSACTION = new Transaction();
	static final Dropping DROPPING = new Dropping();

	@Override
	public INominatif getLoanNominatif() {
		return LOAN_NOMINATIF;
	}

	@Override
	public IRemedial getLoanRemedial() {
		return LOAN_REMEDIAL;
	}

	@Override
	public IRemedial1 getLoanRemedial1() {
		return LOAN_REMEDIAL1;
	}

	@Override
	public INominatifAo getLoanNominatifAo() {
		return LOAN_NOMINATIFAO;
	}

	@Override
	public ITransaction getTransaction() {
		return TRANSACTION;
	}

	@Override
	public IDropping getDropping() {
		return DROPPING;
	}

}
