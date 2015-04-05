package org.simbiosis.ui.bprs.dashboard.client.tks;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.dashboard.client.AppFactory;
import org.simbiosis.ui.bprs.dashboard.client.places.DashboardTks;
import org.simbiosis.ui.bprs.dashboard.client.tks.IDashboardTks.Activity;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class DashboardTksActivity extends Activity {
	// private final ReportSrvAsync srv = GWT.create(ReportSrv.class);

	AppFactory appFactory;
	DashboardTks place;

	public DashboardTksActivity(DashboardTks place, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IDashboardTks form = appFactory.getDashboardTks();
		form.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, form.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_VIEW:
			onView();
			break;
		case FA_EXPORTXLS:
			onExport();
			break;
		default:
			break;
		}
	}

	private void onExport() {
		IDashboardTks form = appFactory.getDashboardTks();
		form.exportReport();
	}

	private void onView() {
		IDashboardTks form = appFactory.getDashboardTks();
		form.loadReport();
	}

}
