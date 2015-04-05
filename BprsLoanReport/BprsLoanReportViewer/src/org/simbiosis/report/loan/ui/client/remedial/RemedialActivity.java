package org.simbiosis.report.loan.ui.client.remedial;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.kembang.module.client.rpc.SystemService;
import org.kembang.module.client.rpc.SystemServiceAsync;
import org.kembang.module.shared.SimpleBranchDv;
import org.kembang.module.shared.UserDv;
import org.simbiosis.report.loan.ui.client.AppFactory;
import org.simbiosis.report.loan.ui.client.places.Remedial;
import org.simbiosis.report.loan.ui.client.remedial.IRemedial.Activity;
import org.simbiosis.report.loan.ui.client.rpc.ReportSrv;
import org.simbiosis.report.loan.ui.client.rpc.ReportSrvAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class RemedialActivity extends Activity {

	private final ReportSrvAsync srv = GWT.create(ReportSrv.class);
	private final SystemServiceAsync systemSrv = GWT
			.create(SystemService.class);

	AppFactory appFactory;
	Remedial place;

	public RemedialActivity(Remedial place, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IRemedial form = appFactory.getLoanRemedial();
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
		IRemedial form = appFactory.getLoanRemedial();
		form.exportReport();
	}

	void onView() {
		IRemedial form = appFactory.getLoanRemedial();
		form.loadReport();
	}

	private void loadCommonDatas() {
		showLoading();
		systemSrv.listSimpleBranch(getKey(),
				new AsyncCallback<List<SimpleBranchDv>>() {

					@Override
					public void onSuccess(List<SimpleBranchDv> result) {
						hideLoading();
						IRemedial form = appFactory.getLoanRemedial();
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
				IRemedial loanRemedial = appFactory.getLoanRemedial();
				loanRemedial.addAoList(result);
			}
		});
	}

}
