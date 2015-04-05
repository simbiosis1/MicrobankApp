package org.simbiosis.ui.bprs.loan.client.guarantee;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.loan.client.BprsLoanFactory;
import org.simbiosis.ui.bprs.loan.client.guarantee.IGuaranteeList.Activity;
import org.simbiosis.ui.bprs.loan.client.guaranteeinput.GuaranteeInputActivity;
import org.simbiosis.ui.bprs.loan.client.guaranteeinput.IGuaranteeInput;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanService;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class GuaranteeListActivity extends Activity {

	private final LoanServiceAsync appSrv = GWT.create(LoanService.class);

	GuaranteeListPlace myPlace;
	BprsLoanFactory appFactory;

	GuaranteeInputActivity inputActivity;

	public GuaranteeListActivity(GuaranteeListPlace myPlace,
			BprsLoanFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
	}

	private GuaranteeInputActivity getInputActivity() {
		if (inputActivity == null) {
			inputActivity = new GuaranteeInputActivity(new GuaranteeListPlace(
					""), appFactory);
		}
		return inputActivity;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IGuaranteeList myForm = appFactory.getGuaranteeList();
		myForm.setActivity(this, appFactory.getAppStatus());
		if (appFactory.getAppStatus().isLogin()) {
			initViewerEditor();
		}
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_NEW:
			onNew();
			break;
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

	void onNew() {
		getInputActivity().newData();
	}

	void onView() {
		showLoading();
		DataDv selectedData = appFactory.getGuaranteeList().getSelectedData();
		IGuaranteeInput viewerForm = appFactory.getGuaranteeViewer();
		inputActivity.showData(selectedData);
		appFactory.showApplication(null, viewerForm.getFormWidget());
	}

	void onSearch() {
		showLoading();
		IGuaranteeList myForm = appFactory.getGuaranteeList();
		appSrv.findGuarantee(getKey(), myForm.isHasCode(), myForm.isHasName(),
				myForm.isHasDob(), myForm.getCode(), myForm.getName(),
				myForm.getDob(), new AsyncCallback<List<DataDv>>() {

					@Override
					public void onSuccess(List<DataDv> result) {
						CariDataDv dataPencarian = new CariDataDv();
						dataPencarian.getResultTable().addAll(result);
						IGuaranteeList myForm = appFactory.getGuaranteeList();
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

	private void initViewerEditor() {
		IGuaranteeInput viewerForm = appFactory.getGuaranteeViewer();
		viewerForm.setActivity(getInputActivity(), appFactory.getAppStatus());
		IGuaranteeInput editorForm = appFactory.getGuaranteeEditor();
		editorForm.setActivity(getInputActivity(), appFactory.getAppStatus());
	}

}
