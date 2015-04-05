package org.simbiosis.ui.bprs.dashboard.client;

import org.kembang.module.client.mvp.KembangClientFactoryImpl;
import org.simbiosis.ui.bprs.dashboard.client.dashboard.Dashboard;
import org.simbiosis.ui.bprs.dashboard.client.dashboard.IDashboard;
import org.simbiosis.ui.bprs.dashboard.client.loan.IDashboardLoan;
import org.simbiosis.ui.bprs.dashboard.client.loan.DashboardLoan;
import org.simbiosis.ui.bprs.dashboard.client.tks.DashboardTks;
import org.simbiosis.ui.bprs.dashboard.client.tks.IDashboardTks;

public class AppFactoryImpl extends KembangClientFactoryImpl implements
		AppFactory {

	static final Dashboard DASHBOARD = new Dashboard();
	static final DashboardLoan LOAN_DROPPING = new DashboardLoan();
	static final DashboardTks DASHBOARD_TKS = new DashboardTks();

	@Override
	public IDashboard getDashboard() {
		return DASHBOARD;
	}

	@Override
	public IDashboardLoan getLoanDropping() {
		return LOAN_DROPPING;
	}

	@Override
	public IDashboardTks getDashboardTks() {
		return DASHBOARD_TKS;
	}

}
