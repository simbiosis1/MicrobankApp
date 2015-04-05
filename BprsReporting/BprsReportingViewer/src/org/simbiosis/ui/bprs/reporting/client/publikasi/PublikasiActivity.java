package org.simbiosis.ui.bprs.reporting.client.publikasi;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.kembang.module.client.rpc.SystemService;
import org.kembang.module.client.rpc.SystemServiceAsync;
import org.kembang.module.shared.SimpleBranchDv;
import org.simbiosis.ui.bprs.reporting.client.AppFactory;
import org.simbiosis.ui.bprs.reporting.client.places.Publikasi;
import org.simbiosis.ui.bprs.reporting.client.publikasi.IPublikasi.Activity;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class PublikasiActivity extends Activity {

	private final SystemServiceAsync srv = GWT.create(SystemService.class);

	AppFactory appFactory;
	Publikasi place;

	public PublikasiActivity(Publikasi place, AppFactory clientFactory) {
		setMainFactory(clientFactory);
		this.place = place;
		this.appFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IPublikasi form = appFactory.getPublikasi();
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
		IPublikasi form = appFactory.getPublikasi();
		form.exportReport();
	}

	void onView() {
		IPublikasi form = appFactory.getPublikasi();
		form.loadReport();
	}

	private void loadCommonDatas() {
		showLoading();
		srv.listSimpleBranch(getKey(), new AsyncCallback<List<SimpleBranchDv>>() {

			@Override
			public void onSuccess(List<SimpleBranchDv> result) {
				hideLoading();
				IPublikasi form = appFactory.getPublikasi();
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
