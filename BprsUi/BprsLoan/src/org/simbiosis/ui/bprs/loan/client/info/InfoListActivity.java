package org.simbiosis.ui.bprs.loan.client.info;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.loan.client.BprsLoanFactory;
import org.simbiosis.ui.bprs.loan.client.info.IInfoList.Activity;
import org.simbiosis.ui.bprs.loan.client.infoviewer.IInfoViewer;
import org.simbiosis.ui.bprs.loan.client.infoviewer.InfoViewerActivity;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanService;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class InfoListActivity extends Activity {

	private final LoanServiceAsync koperasiService = GWT
			.create(LoanService.class);

	InfoListPlace myPlace;
	BprsLoanFactory appFactory;

	InfoViewerActivity viewerActivity;

	public InfoListActivity(InfoListPlace myPlace, BprsLoanFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
	}

	private InfoViewerActivity getInfoActivity() {
		if (viewerActivity == null) {
			viewerActivity = new InfoViewerActivity(new InfoListPlace(""),
					appFactory);
		}
		return viewerActivity;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IInfoList myForm = appFactory.getInfoList();
		myForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_VIEW:
			onView();
			break;
		case FA_SEARCH:
			onSearch();
			break;
		default:
			break;
		}
	}

	void onView() {
		showLoading();
		DataDv selectedData = appFactory.getInfoList().getSelectedData();
		IInfoViewer viewerForm = appFactory.getInfoViewer();
		viewerActivity.showData(selectedData);
		appFactory.showApplication(null, viewerForm.getFormWidget());
	}

	void onSearch() {
		showLoading();
		IInfoList myForm = appFactory.getInfoList();
		myForm.searchData();
	}

	@Override
	public void searchData(Boolean hasCode, Boolean hasName, Boolean hasDob,
			String code, String name, Date dob) {
		koperasiService.findLoan(getKey(), hasCode, hasName, hasDob, code,
				name, dob, new AsyncCallback<List<DataDv>>() {

					@Override
					public void onSuccess(List<DataDv> result) {
						CariDataDv dataPencarian = new CariDataDv();
						dataPencarian.getResultTable().addAll(result);
						IInfoList myForm = appFactory.getInfoList();
						myForm.showData(dataPencarian);
						hideLoading();
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : findFinancing");
					}
				});
	}

	@Override
	public void initViewerEditor() {
		IInfoViewer viewerForm = appFactory.getInfoViewer();
		viewerForm.setActivity(getInfoActivity(), appFactory.getAppStatus());
	}

}
