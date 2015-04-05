package org.simbiosis.report.loan.ui.client.remedial1;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.kembang.module.client.rpc.SystemService;
import org.kembang.module.client.rpc.SystemServiceAsync;
import org.kembang.module.shared.SimpleBranchDv;
import org.kembang.module.shared.UserDv;
import org.simbiosis.report.loan.ui.client.AppFactory;
import org.simbiosis.report.loan.ui.client.places.Remedial1;
import org.simbiosis.report.loan.ui.client.remedial1.IRemedial1.Activity;
import org.simbiosis.report.loan.ui.client.rpc.ReportSrv;
import org.simbiosis.report.loan.ui.client.rpc.ReportSrvAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class Remedial1Activity extends Activity {

	private final ReportSrvAsync srv = GWT.create(ReportSrv.class);
	private final SystemServiceAsync systemSrv = GWT
			.create(SystemService.class);

	AppFactory appFactory;
	Remedial1 place;

	public Remedial1Activity(Remedial1 place, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IRemedial1 form = appFactory.getLoanRemedial1();
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
		IRemedial1 form = appFactory.getLoanRemedial1();
		form.exportReport();
	}

	void onView() {
		IRemedial1 form = appFactory.getLoanRemedial1();
		form.loadReport();
	}

	private void loadCommonDatas() {
		showLoading();
		systemSrv.listSimpleBranch(getKey(),
				new AsyncCallback<List<SimpleBranchDv>>() {

					@Override
					public void onSuccess(List<SimpleBranchDv> result) {
						hideLoading();
						IRemedial1 form = appFactory.getLoanRemedial1();
						form.addBranchList(result);
						//
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : loadBranch");
					}
				});
	}

	@Override
	public void loadAo(long branch) {
		showLoading();
		srv.listAo(getKey(), branch, new AsyncCallback<List<UserDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : Load AO");
			}

			@Override
			public void onSuccess(List<UserDv> result) {
				hideLoading();
				IRemedial1 loanRemedial = appFactory.getLoanRemedial1();
				loanRemedial.addAoList(result);
			}
		});
	}

}
