package org.simbiosis.ui.bprs.dashboard.client;

import org.kembang.module.client.mvp.KembangClientFactory;
import org.simbiosis.ui.bprs.dashboard.client.dashboard.IDashboard;
import org.simbiosis.ui.bprs.dashboard.client.loan.IDashboardLoan;
import org.simbiosis.ui.bprs.dashboard.client.loanmonitor.ILoanMonitor;
import org.simbiosis.ui.bprs.dashboard.client.tks.IDashboardTks;

public interface AppFactory extends KembangClientFactory {

	IDashboard getDashboard();

	IDashboardTks getDashboardTks();

	IDashboardLoan getLoanDropping();

	ILoanMonitor getLoanMonitor();

}
