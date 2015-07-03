package org.simbiosis.ui.bprs.dashboard.client.loanmonitor;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.kembang.module.client.rpc.SystemService;
import org.kembang.module.client.rpc.SystemServiceAsync;
import org.kembang.module.shared.SimpleBranchDv;
import org.simbiosis.ui.bprs.dashboard.client.AppFactory;
import org.simbiosis.ui.bprs.dashboard.client.loanmonitor.ILoanMonitor.Activity;
import org.simbiosis.ui.bprs.dashboard.client.places.LoanMonitor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class LoanMonitorActivity extends Activity {

	private final SystemServiceAsync srv = GWT.create(SystemService.class);

	AppFactory appFactory;
	LoanMonitor place;

	public LoanMonitorActivity(LoanMonitor place, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ILoanMonitor form = appFactory.getLoanMonitor();
		form.setActivity(this, appFactory.getAppStatus());
		if (appFactory.getAppStatus().isLogin()) {
			loadCommonDatas();
		}
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

	void onExport() {
		ILoanMonitor form = appFactory.getLoanMonitor();
		form.exportReport();
	}

	void onView() {
		ILoanMonitor form = appFactory.getLoanMonitor();
		form.loadReport();
	}

	private void loadCommonDatas() {
		showLoading();
		srv.listSimpleBranch(getKey(),
				new AsyncCallback<List<SimpleBranchDv>>() {

					@Override
					public void onSuccess(List<SimpleBranchDv> result) {
						hideLoading();
						ILoanMonitor form = appFactory.getLoanMonitor();
						form.addBranchList(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : loadBranch");
					}
				});
	}

}
