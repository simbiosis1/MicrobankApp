package org.simbiosis.ui.bprs.reporting.client.neraca;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.kembang.module.client.rpc.SystemService;
import org.kembang.module.client.rpc.SystemServiceAsync;
import org.kembang.module.shared.SimpleBranchDv;
import org.simbiosis.ui.bprs.reporting.client.AppFactory;
import org.simbiosis.ui.bprs.reporting.client.neraca.INeraca.Activity;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class NeracaActivity extends Activity {

	private final SystemServiceAsync srv = GWT.create(SystemService.class);

	AppFactory appFactory;
	NeracaPlace place;

	public NeracaActivity(NeracaPlace place, AppFactory clientFactory) {
		setMainFactory(clientFactory);
		this.place = place;
		this.appFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		INeraca form = appFactory.getNeraca();
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
		INeraca form = appFactory.getNeraca();
		form.exportReport();
	}

	void onView() {
		INeraca form = appFactory.getNeraca();
		form.loadReport();
	}

	private void loadCommonDatas() {
		showLoading();
		srv.listSimpleBranch(getKey(), new AsyncCallback<List<SimpleBranchDv>>() {

			@Override
			public void onSuccess(List<SimpleBranchDv> result) {
				hideLoading();
				INeraca form = appFactory.getNeraca();
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
