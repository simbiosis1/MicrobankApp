package org.simbiosis.ui.bprs.loan.client.loan;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.loan.client.BprsLoanFactory;
import org.simbiosis.ui.bprs.loan.client.loan.ILoanList.Activity;
import org.simbiosis.ui.bprs.loan.client.loaninput.ILoanInput;
import org.simbiosis.ui.bprs.loan.client.loaninput.LoanInputActivity;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanService;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class LoanListActivity extends Activity {

	private final LoanServiceAsync loanSrv = GWT.create(LoanService.class);

	LoanListPlace myPlace;
	BprsLoanFactory appFactory;

	LoanInputActivity inputActivity;

	public LoanListActivity(LoanListPlace myPlace, BprsLoanFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
	}

	private LoanInputActivity getInputActivity() {
		if (inputActivity == null) {
			inputActivity = new LoanInputActivity(new LoanListPlace(""),
					appFactory);
		}
		return inputActivity;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ILoanList myForm = appFactory.getLoanList();
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
		DataDv selectedData = appFactory.getLoanList().getSelectedData();
		ILoanInput viewerForm = appFactory.getLoanViewer();
		inputActivity.showData(selectedData);
		appFactory.showApplication(null, viewerForm.getFormWidget());
	}

	void onSearch() {
		showLoading();
		ILoanList myForm = appFactory.getLoanList();
		loanSrv.findLoan(getKey(), myForm.isHasCode(), myForm.isHasName(),
				myForm.isHasDob(), myForm.getCode(), myForm.getName(),
				myForm.getDob(), new AsyncCallback<List<DataDv>>() {

					@Override
					public void onSuccess(List<DataDv> result) {
						CariDataDv dataPencarian = new CariDataDv();
						dataPencarian.getResultTable().addAll(result);
						ILoanList myForm = appFactory.getLoanList();
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
		ILoanInput viewerForm = appFactory.getLoanViewer();
		viewerForm.setActivity(getInputActivity(), appFactory.getAppStatus());
		ILoanInput editorForm = appFactory.getLoanEditor();
		editorForm.setActivity(getInputActivity(), appFactory.getAppStatus());
	}

}
