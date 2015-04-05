package org.simbiosis.ui.bprs.cs.client.deposit;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.cs.client.AppFactory;
import org.simbiosis.ui.bprs.cs.client.deposit.IDeposit.Activity;
import org.simbiosis.ui.bprs.cs.client.depositinput.DepositInputActivity;
import org.simbiosis.ui.bprs.cs.client.depositinput.IDepositInput;
import org.simbiosis.ui.bprs.cs.client.rpc.CsService;
import org.simbiosis.ui.bprs.cs.client.rpc.CsServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class DepositActivity extends Activity {

	private final CsServiceAsync csService = GWT.create(CsService.class);

	DepositPlace myPlace;
	AppFactory appFactory;

	DepositInputActivity inputActivity;

	public DepositActivity(DepositPlace myPlace, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
	}

	private DepositInputActivity getInputActivity() {
		if (inputActivity == null) {
			inputActivity = new DepositInputActivity(new DepositPlace(""),
					appFactory);
		}
		return inputActivity;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IDeposit myForm = appFactory.getListDeposit();
		myForm.setActivity(this, appFactory.getAppStatus());
		if (appFactory.getAppStatus().isLogin()) {
			initViewerEditor();
		}

		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void searchData(Boolean hasCode, Boolean hasName, Boolean hasDob,
			String code, String name, Date dob) {
		csService.findDeposit(getKey(), hasCode, hasName, hasDob, code, name,
				dob, new AsyncCallback<List<DataDv>>() {

					@Override
					public void onSuccess(List<DataDv> result) {
						CariDataDv dataPencarian = new CariDataDv();
						dataPencarian.getResultTable().addAll(result);
						IDeposit myForm = appFactory.getListDeposit();
						myForm.showData(dataPencarian);
						hideLoading();
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : findDeposit");
					}
				});
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_NEW:
			onNew();
			break;
		case FA_SEARCH:
			onSearch();
			break;
		case FA_VIEW:
			onView();
			break;
		default:
			break;
		}
	}

	private void onNew() {
		getInputActivity().newData();
	}

	private void onView() {
		showLoading();
		DataDv selectedData = appFactory.getListDeposit().getSelectedData();
		IDepositInput viewerForm = appFactory.getDepositViewer();
		inputActivity.showData(selectedData);
		appFactory.showApplication(null, viewerForm.getFormWidget());
	}

	private void onSearch() {
		showLoading();
		IDeposit myForm = appFactory.getListDeposit();
		myForm.searchData();
	}

	private void initViewerEditor() {
		IDepositInput viewerForm = appFactory.getDepositViewer();
		viewerForm.setActivity(getInputActivity(), appFactory.getAppStatus());
		IDepositInput editorForm = appFactory.getDepositEditor();
		editorForm.setActivity(getInputActivity(), appFactory.getAppStatus());
	}

}
