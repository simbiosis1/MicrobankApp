package org.simbiosis.ui.bprs.loan.client.reschedule;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.loan.client.BprsLoanFactory;
import org.simbiosis.ui.bprs.loan.client.places.Reschedule;
import org.simbiosis.ui.bprs.loan.client.reschedule.IRescheduleList.Activity;
import org.simbiosis.ui.bprs.loan.client.rescheduleinput.IRescheduleInput;
import org.simbiosis.ui.bprs.loan.client.rescheduleinput.RescheduleInputActivity;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanService;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class RescheduleListActivity extends Activity {

	private final LoanServiceAsync koperasiService = GWT
			.create(LoanService.class);

	Reschedule myPlace;
	BprsLoanFactory appFactory;

	RescheduleInputActivity inputActivity;

	public RescheduleListActivity(Reschedule myPlace, BprsLoanFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
	}

	private RescheduleInputActivity getRescheduleActivity() {
		if (inputActivity == null) {
			inputActivity = new RescheduleInputActivity(new Reschedule(""),
					appFactory);
		}
		return inputActivity;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IRescheduleList myForm = appFactory.getRescheduleList();
		myForm.setActivity(this, appFactory.getAppStatus());
		if (appFactory.getAppStatus().isLogin()) {
			initViewerEditor();
		}
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
		DataDv selectedData = appFactory.getRescheduleList().getSelectedData();
		IRescheduleInput viewerForm = appFactory.getRescheduleViewer();
		inputActivity.showData(selectedData);
		appFactory.showApplication(null, viewerForm.getFormWidget());
	}

	void onSearch() {
		showLoading();
		IRescheduleList myForm = appFactory.getRescheduleList();
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
						IRescheduleList myForm = appFactory.getRescheduleList();
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
		IRescheduleInput viewerForm = appFactory.getRescheduleViewer();
		viewerForm.setActivity(getRescheduleActivity(),
				appFactory.getAppStatus());
		IRescheduleInput editorForm = appFactory.getRescheduleEditor();
		editorForm.setActivity(getRescheduleActivity(),
				appFactory.getAppStatus());
	}
}
