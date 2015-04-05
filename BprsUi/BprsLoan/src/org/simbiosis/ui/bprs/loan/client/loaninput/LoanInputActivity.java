package org.simbiosis.ui.bprs.loan.client.loaninput;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.client.handler.GetSavingHandler;
import org.simbiosis.ui.bprs.common.client.savinghelper.DlgGetSaving;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.LoanDv;
import org.simbiosis.ui.bprs.common.shared.LoanScheduleDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.loan.client.BprsLoanFactory;
import org.simbiosis.ui.bprs.loan.client.loaninput.ILoanInput.Activity;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanService;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanServiceAsync;
import org.simbiosis.ui.bprs.loan.shared.LoanScheduleGenDv;
import org.simbiosis.ui.bprs.loan.shared.UserDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class LoanInputActivity extends Activity {
	private final LoanServiceAsync loanSrv = GWT.create(LoanService.class);

	DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd-MM-yyyy");
	String reportService = "/BprsLoanReportService";

	Place myPlace;
	BprsLoanFactory appFactory;
	Activity activity;

	LoanDv activeLoan;

	public LoanInputActivity(Place myPlace, BprsLoanFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
		this.activity = this;
	}

	Activity getActivity() {
		return activity;
	}

	@Override
	public void showData(DataDv dataDv) {
		loanSrv.getLoan(dataDv.getId(), new AsyncCallback<LoanDv>() {

			@Override
			public void onSuccess(LoanDv result) {
				ILoanInput viewerForm = appFactory.getLoanViewer();
				viewerForm.showData(result);
				hideLoading();
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : getSimpanan");
			}
		});
	}

	@Override
	public void newData() {
		showLoading();
		// load data pembiayaan baru...
		loanSrv.newLoan(new AsyncCallback<LoanDv>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : newLoan");
			}

			@Override
			public void onSuccess(LoanDv result) {
				hideLoading();
				activeLoan = result;
				GetSavingHandler handler = new GetSavingHandler() {

					@Override
					public void showSaving(SavingDv savingDv) {
						ILoanInput editorForm = appFactory.getLoanEditor();
						activeLoan.setCustomer(savingDv.getCustomer());
						activeLoan.setSaving(savingDv);
						editorForm.showData(activeLoan);
						appFactory.showApplication(null,
								editorForm.getFormWidget());
					}

					@Override
					public void showLoading() {
						activity.showLoading();
					}

					@Override
					public void hideLoading() {
						activity.hideLoading();
					}
				};
				DlgGetSaving dlgGetSaving = new DlgGetSaving(getKey(), false,
						handler);
				dlgGetSaving.center();
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
				Window.alert("Error : loadCommonListFinancing");
			}

			@Override
			public void onSuccess(List<DataDv> result) {
				ILoanInput editorForm = appFactory.getLoanEditor();
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
				Window.alert("Error : loadBISektor");
			}

			@Override
			public void onSuccess(List<String> result) {
				ILoanInput editorForm = appFactory.getLoanEditor();
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
				ILoanInput editorForm = appFactory.getLoanEditor();
				editorForm.setLoanAO(result);
			}
		});
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_EDIT:
			onEdit();
			break;
		case FA_SAVE:
			onSave();
			break;
		case FA_EXPORTPDF:
			onExportPdf();
			break;
		case FA_BACK:
			onBack();
			break;
		default:
			break;
		}
	}

	private void onExportPdf() {
		ILoanInput myForm = appFactory.getLoanViewer();
		LoanDv dv = myForm.getSelectedData();
		Window.open(reportService + "/getLoanPaymentExtPdf?loan="
				+ dv.getId(), "_blank", null);
	}

	void onEdit() {
		LoanDv data = appFactory.getLoanViewer().getSelectedData();
		ILoanInput editorForm = appFactory.getLoanEditor();
		editorForm.showData(data);
		appFactory.showApplication(null, editorForm.getFormWidget());
	}

	void onSave() {
		showLoading();
		LoanDv data = appFactory.getLoanEditor().getEditedData();
		loanSrv.saveLoan(getKey(), data, new AsyncCallback<LoanDv>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : saveFinancing");
			}

			@Override
			public void onSuccess(LoanDv result) {
				hideLoading();
				if (result != null) {
					ILoanInput viewerForm = appFactory.getLoanViewer();
					viewerForm.showData(result);
					appFactory.showApplication(null, viewerForm.getFormWidget());
					Window.alert("Data sudah disimpan");
				}
			}
		});
	}

	void onBack() {
		appFactory.getPlaceController().goTo(myPlace);
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
						ILoanInput viewerForm = appFactory.getLoanEditor();
						viewerForm.setSchedule(result);
					}
				});
	}

}
