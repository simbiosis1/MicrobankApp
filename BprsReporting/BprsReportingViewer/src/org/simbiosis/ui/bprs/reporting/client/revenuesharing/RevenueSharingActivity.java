package org.simbiosis.ui.bprs.reporting.client.revenuesharing;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.reporting.client.AppFactory;
import org.simbiosis.ui.bprs.reporting.client.revenuesharing.IRevenueSharing.Activity;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class RevenueSharingActivity extends Activity {

	// private final ReportSrvAsync srv = GWT.create(ReportSrv.class);

	AppFactory appFactory;
	RevenueSharingPlace place;

	public RevenueSharingActivity(RevenueSharingPlace place,
			AppFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IRevenueSharing form = appFactory.getRevenueSharing();
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

	void onView() {
		IRevenueSharing form = appFactory.getRevenueSharing();
		form.loadReport();
	}

	void onExport() {
		IRevenueSharing form = appFactory.getRevenueSharing();
		form.exportReport();
	}

	public void loadCommonDatas() {
		IRevenueSharing form = appFactory.getRevenueSharing();
		form.addPeriods("Juni 2012", "6;2012");
		form.addPeriods("Juli 2012", "7;2012");
		form.addPeriods("Agustus 2012", "8;2012");
		form.addPeriods("September 2012", "9;2012");
		form.addPeriods("Oktober 2012", "10;2012");
		form.addPeriods("Nopember 2012", "11;2012");
		form.addPeriods("Desember 2012", "12;2012");
		form.addPeriods("Januari 2013", "1;2013");
		form.addPeriods("Februari 2013", "2;2013");
		form.addPeriods("Maret 2013", "3;2013");
		form.addPeriods("April 2013", "4;2013");
		form.addPeriods("Mei 2013", "5;2013");
		form.addPeriods("Juni 2013", "6;2013");
		form.addPeriods("Juli 2013", "7;2013");
		form.addPeriods("Agustus 2013", "8;2013");
		form.addPeriods("September 2013", "9;2013");
		form.addPeriods("Oktober 2013", "10;2013");
		form.addPeriods("Nopember 2013", "11;2013");
		form.addPeriods("Desember 2013", "12;2013");
		form.addPeriods("Januari 2014", "01;2014");
		form.addPeriods("Februari 2014", "02;2014");
		form.addPeriods("Maret 2014", "03;2014");
		form.addPeriods("April 2014", "04;2014");
		form.addPeriods("Mei 2014", "05;2014");
		form.addPeriods("Juni 2014", "06;2014");
		form.addPeriods("Juli 2014", "07;2014");
		form.addPeriods("Agustus 2014", "08;2014");
		form.addPeriods("September 2014", "09;2014");
		form.addPeriods("Oktober 2014", "10;2014");
		form.addPeriods("Nopember 2014", "11;2014");
		form.addPeriods("Desember 2014", "12;2014");
		form.addPeriods("Januari 2015", "01;2015");
		form.addPeriods("Februari 2015", "02;2015");
		form.addPeriods("Maret 2015", "03;2015");
		form.addPeriods("April 2015", "04;2015");
		// showLoading();
		// srv.loadBranch(getKey(), new AsyncCallback<List<BranchDv>>() {
		//
		// @Override
		// public void onSuccess(List<BranchDv> result) {
		// hideLoading();
		// IRevenueSharing form = appFactory.getRevenueSharing();
		// form.addBranchList(result);
		// }
		//
		// @Override
		// public void onFailure(Throwable caught) {
		// hideLoading();
		// Window.alert("Error : loadBranch");
		// }
		// });
	}

}
