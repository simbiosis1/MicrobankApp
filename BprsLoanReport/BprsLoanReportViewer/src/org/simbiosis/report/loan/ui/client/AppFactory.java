package org.simbiosis.report.loan.ui.client;

import org.kembang.module.client.mvp.KembangClientFactory;
import org.simbiosis.report.loan.ui.client.dropping.IDropping;
import org.simbiosis.report.loan.ui.client.nominatif.INominatif;
import org.simbiosis.report.loan.ui.client.nominatifao.INominatifAo;
import org.simbiosis.report.loan.ui.client.remedial.IRemedial;
import org.simbiosis.report.loan.ui.client.remedial1.IRemedial1;
import org.simbiosis.report.loan.ui.client.transaction.ITransaction;

public interface AppFactory extends KembangClientFactory {
	INominatif getLoanNominatif();

	INominatifAo getLoanNominatifAo();

	IRemedial getLoanRemedial();

	IRemedial1 getLoanRemedial1();

	ITransaction getTransaction();
	
	IDropping getDropping();
}
