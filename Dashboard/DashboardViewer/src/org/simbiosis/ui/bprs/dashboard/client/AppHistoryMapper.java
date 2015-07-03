package org.simbiosis.ui.bprs.dashboard.client;

import org.kembang.module.client.mvp.KembangHistoryMapper;
import org.simbiosis.ui.bprs.dashboard.client.places.Dashboard;
import org.simbiosis.ui.bprs.dashboard.client.places.DashboardLoan;
import org.simbiosis.ui.bprs.dashboard.client.places.DashboardTks;
import org.simbiosis.ui.bprs.dashboard.client.places.LoanMonitor;

import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ Dashboard.Tokenizer.class, DashboardLoan.Tokenizer.class,
		DashboardTks.Tokenizer.class, LoanMonitor.Tokenizer.class })
public interface AppHistoryMapper extends KembangHistoryMapper {

}
