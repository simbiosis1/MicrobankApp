package org.simbiosis.ui.bprs.loan.client.guaranteeinput;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.client.customerhelper.DlgFindCustomer;
import org.simbiosis.ui.bprs.common.client.handler.GetCustomerHandler;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.GuaranteeDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.common.shared.ValidationDv;
import org.simbiosis.ui.bprs.loan.client.BprsLoanFactory;
import org.simbiosis.ui.bprs.loan.client.guaranteeinput.IGuaranteeInput.Activity;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanService;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanServiceAsync;
import org.simbiosis.ui.bprs.loan.shared.TypeDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class GuaranteeInputActivity extends Activity {
	private final LoanServiceAsync loanSvc = GWT.create(LoanService.class);

	DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd-MM-yyyy");

	Place myPlace;
	BprsLoanFactory appFactory;
	Activity activity;

	public GuaranteeInputActivity(Place myPlace, BprsLoanFactory appFactory) {
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
		loanSvc.getGuarantee(dataDv.getId(), new AsyncCallback<GuaranteeDv>() {

			@Override
			public void onSuccess(GuaranteeDv result) {
				IGuaranteeInput viewerForm = appFactory.getGuaranteeViewer();
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
	public void newData() {
		GetCustomerHandler handler = new GetCustomerHandler() {

			@Override
			public void showSaving(SavingDv savingDv) {
				IGuaranteeInput editorForm = appFactory.getGuaranteeEditor();
				GuaranteeDv loanDv = new GuaranteeDv();
				loanDv.setRegistration(new Date());
				loanDv.setStrRegistration(dateFormat.format(loanDv
						.getRegistration()));
				loanDv.setCustomer(savingDv.getCustomer());
				editorForm.showData(loanDv);
				appFactory.showApplication(null, editorForm.getFormWidget());
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
		DlgFindCustomer dlgFindCustomer = new DlgFindCustomer(getKey(), false,
				handler);
		dlgFindCustomer.show();
	}

	@Override
	public void loadCommonList() {
		showLoading();
		loanSvc.loadCommonListGuarantee(getKey(),
				new AsyncCallback<List<TypeDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : saveFinancing");
					}

					@Override
					public void onSuccess(List<TypeDv> result) {
						IGuaranteeInput editorForm = appFactory
								.getGuaranteeEditor();
						editorForm.setGuaranteeTypes(result);
						hideLoading();
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
		case FA_BACK:
			onBack();
			break;
		default:
			break;
		}
	}

	void onEdit() {
		GuaranteeDv data = appFactory.getGuaranteeViewer().getSelectedData();
		IGuaranteeInput editorForm = appFactory.getGuaranteeEditor();
		editorForm.showData(data);
		appFactory.showApplication(null, editorForm.getFormWidget());
	}

	void onSave() {
		showLoading();
		GuaranteeDv data = appFactory.getGuaranteeEditor().getEditedData();
		loanSvc.validateGuarantee(getKey(), data,
				new AsyncCallback<ValidationDv>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : validateGuarantee");
					}

					@Override
					public void onSuccess(ValidationDv result) {
						if (result.getError() == 0) {
							GuaranteeDv data = appFactory.getGuaranteeEditor().getEditedData();
							saveGuarantee(data);
						} else {
							hideLoading();
							Window.alert("Error : " + result.getErrorMessage());
						}
					}
				});

	}

	private void onBack() {
		appFactory.getPlaceController().goTo(myPlace);
	}

	private void saveGuarantee(GuaranteeDv dv) {
		loanSvc.saveGuarantee(getKey(), dv, new AsyncCallback<GuaranteeDv>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : saveFinancing");
			}

			@Override
			public void onSuccess(GuaranteeDv result) {
				hideLoading();
				if (result != null) {
					IGuaranteeInput viewerForm = appFactory
							.getGuaranteeViewer();
					viewerForm.showData(result);
					appFactory.showApplication(null, viewerForm.getFormWidget());
					Window.alert("Data sudah disimpan");
				}
			}
		});
	}
}
