package org.simbiosis.ui.bprs.reporting.client.savingnominatif;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.kembang.module.client.rpc.SystemService;
import org.kembang.module.client.rpc.SystemServiceAsync;
import org.kembang.module.shared.SimpleBranchDv;
import org.simbiosis.ui.bprs.reporting.client.AppFactory;
import org.simbiosis.ui.bprs.reporting.client.savingnominatif.ISavingNominatif.Activity;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class SavingNominatifActivity extends Activity {
	private final SystemServiceAsync systemSrv = GWT
			.create(SystemService.class);

	AppFactory appFactory;
	SavingNominatifPlace place;

	public SavingNominatifActivity(SavingNominatifPlace place,
			AppFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ISavingNominatif form = appFactory.getSavingNominatif();
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
		ISavingNominatif form = appFactory.getSavingNominatif();
		form.exportReport();
	}

	void onView() {
		ISavingNominatif form = appFactory.getSavingNominatif();
		form.loadReport();
	}

	private void loadCommonDatas() {
		showLoading();
		systemSrv.listSimpleBranch(getKey(), new AsyncCallback<List<SimpleBranchDv>>() {

			@Override
			public void onSuccess(List<SimpleBranchDv> result) {
				hideLoading();
				ISavingNominatif form = appFactory.getSavingNominatif();
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
