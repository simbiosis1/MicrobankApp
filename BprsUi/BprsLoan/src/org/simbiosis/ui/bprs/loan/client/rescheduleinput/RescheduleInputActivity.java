package org.simbiosis.ui.bprs.loan.client.rescheduleinput;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.LoanDv;
import org.simbiosis.ui.bprs.common.shared.LoanScheduleDv;
import org.simbiosis.ui.bprs.loan.client.BprsLoanFactory;
import org.simbiosis.ui.bprs.loan.client.places.Reschedule;
import org.simbiosis.ui.bprs.loan.client.rescheduleinput.IRescheduleInput.Activity;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanService;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanServiceAsync;
import org.simbiosis.ui.bprs.loan.shared.LoanScheduleGenDv;
import org.simbiosis.ui.bprs.loan.shared.UserDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class RescheduleInputActivity extends Activity {
	private final LoanServiceAsync loanSrv = GWT.create(LoanService.class);

	Reschedule myPlace;
	BprsLoanFactory appFactory;

	public RescheduleInputActivity(Reschedule myPlace,
			BprsLoanFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_NEW:
			onNew();
			break;
		case FA_SAVE:
			onSave();
			break;
		case FA_BACK:
			appFactory.getPlaceController().goTo(myPlace);
			break;
		default:
			break;
		}
	}

	private void onSave() {
		showLoading();
		LoanDv data = appFactory.getRescheduleEditor().getEditedData();
		loanSrv.saveLoanReschedule(getKey(), data, new AsyncCallback<LoanDv>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : saveFinancing");
			}

			@Override
			public void onSuccess(LoanDv result) {
				hideLoading();
				if (result != null) {
					IRescheduleInput viewerForm = appFactory
							.getRescheduleViewer();
					viewerForm.showData(result);
					appFactory.showApplication(null, viewerForm.getFormWidget());
					Window.alert("Data sudah disimpan");
				}
			}
		});
	}

	private void onNew() {
		showLoading();
		LoanDv result = appFactory.getRescheduleViewer().getSelectedData();
		loanSrv.newReschedule(result.getId(), new AsyncCallback<LoanDv>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : newReschedule");
			}

			@Override
			public void onSuccess(LoanDv result) {
				hideLoading();
				//
				IRescheduleInput editorForm = appFactory.getRescheduleEditor();
				editorForm.showData(result);
				appFactory.showApplication(null, editorForm.getFormWidget());
			}
		});
	}

	@Override
	public void showData(DataDv dataDv) {
		loanSrv.getLoan(dataDv.getId(), new AsyncCallback<LoanDv>() {

			@Override
			public void onSuccess(LoanDv result) {
				IRescheduleInput viewerForm = appFactory.getRescheduleViewer();
				viewerForm.showData(result);
				hideLoading();
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : getLoan");
			}
		});
	}

	@Override
	public void loadCommonList() {
		//
		loanSrv.loadCommonListLoan(getKey(), new AsyncCallback<List<DataDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : loadCommonListLoan");
			}

			@Override
			public void onSuccess(List<DataDv> result) {
				IRescheduleInput editorForm = appFactory.getRescheduleEditor();
				editorForm.setLoanProductList(result);
				loadBISektor();
			}
		});
	}

	private void loadBISektor() {
		loanSrv.listBISektor(new AsyncCallback<List<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listBISektor");
			}

			@Override
			public void onSuccess(List<String> result) {
				IRescheduleInput editorForm = appFactory.getRescheduleEditor();
				editorForm.setBISektor(result);
				loadAO();
			}
		});
	}

	private void loadAO() {
		loanSrv.listUsers(getKey(), new AsyncCallback<List<UserDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : loadAo");
			}

			@Override
			public void onSuccess(List<UserDv> result) {
				hideLoading();
				IRescheduleInput editorForm = appFactory.getRescheduleEditor();
				editorForm.setLoanAO(result);
			}
		});
	}

	@Override
	public void generate(LoanScheduleGenDv data) {
		showLoading();
		loanSrv.createLoanSchedule(data.getStrPrincipal(), data.getStrTenor(),
				data.getStrRate(), new Date(), data.getScheduleType(),
				new AsyncCallback<List<LoanScheduleDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : createLoanSchedule");
					}

					@Override
					public void onSuccess(List<LoanScheduleDv> result) {
						hideLoading();
						IRescheduleInput viewerForm = appFactory
								.getRescheduleEditor();
						viewerForm.setSchedule(result);
					}
				});
	}

}
