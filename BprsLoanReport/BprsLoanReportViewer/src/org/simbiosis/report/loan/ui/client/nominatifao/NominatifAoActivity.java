package org.simbiosis.report.loan.ui.client.nominatifao;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.kembang.module.client.rpc.SystemService;
import org.kembang.module.client.rpc.SystemServiceAsync;
import org.kembang.module.shared.SimpleBranchDv;
import org.simbiosis.report.loan.ui.client.AppFactory;
import org.simbiosis.report.loan.ui.client.nominatifao.INominatifAo.Activity;
import org.simbiosis.report.loan.ui.client.places.NominatifAo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class NominatifAoActivity extends Activity {

	private final SystemServiceAsync srv = GWT.create(SystemService.class);

	AppFactory appFactory;
	NominatifAo place;

	public NominatifAoActivity(NominatifAo place, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		INominatifAo form = appFactory.getLoanNominatifAo();
		form.setActivity(this, appFactory.getAppStatus());
		if (appFactory.getAppStatus().isLogin()){
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
		INominatifAo form = appFactory.getLoanNominatifAo();
		form.exportReport();
	}

	void onView() {
		INominatifAo form = appFactory.getLoanNominatifAo();
		form.loadReport();
	}

	private void loadCommonDatas() {
		showLoading();
		srv.listSimpleBranch(getKey(), new AsyncCallback<List<SimpleBranchDv>>() {

			@Override
			public void onSuccess(List<SimpleBranchDv> result) {
				hideLoading();
				INominatifAo form = appFactory.getLoanNominatifAo();
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
