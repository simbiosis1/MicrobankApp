package org.simbiosis.ui.bprs.loan.client.infoviewer;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.LoanDv;
import org.simbiosis.ui.bprs.loan.client.BprsLoanFactory;
import org.simbiosis.ui.bprs.loan.client.info.InfoListPlace;
import org.simbiosis.ui.bprs.loan.client.infoviewer.IInfoViewer.Activity;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanService;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanServiceAsync;
import org.simbiosis.ui.bprs.loan.shared.InfoLoanDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class InfoViewerActivity extends Activity {

	private final LoanServiceAsync koperasiService = GWT
			.create(LoanService.class);

	InfoListPlace myPlace;
	BprsLoanFactory appFactory;

	String reportService = "/BprsLoanReportService";

	InfoViewerActivity inputActivity;

	public InfoViewerActivity(InfoListPlace myPlace, BprsLoanFactory appFactory) {
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
		case FA_BACK:
			onBack();
			break;
		case FA_EXPORTPDF:
			onExportPdf();
			break;
		default:
			break;
		}
	}

	private void onExportPdf() {
		IInfoViewer myForm = appFactory.getInfoViewer();
		LoanDv dv = myForm.getSelectedData();
		Window.open(reportService + "/getLoanPaidExtPdf?loan=" + dv.getId(),
				"_blank", null);
	}

	void onBack() {
		appFactory.getPlaceController().goTo(myPlace);
	}

	public void showData(DataDv dataDv) {
		koperasiService.getLoan(dataDv.getId(), new AsyncCallback<LoanDv>() {

			@Override
			public void onSuccess(LoanDv result) {
				IInfoViewer viewerForm = appFactory.getInfoViewer();
				viewerForm.showLoan(result);
				loadPayment(result.getId());
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : getSimpanan");
			}
		});
	}

	private void loadPayment(long id) {
		koperasiService.getPaymentInfo(id, new AsyncCallback<InfoLoanDv>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : findFinancing");
			}

			@Override
			public void onSuccess(InfoLoanDv result) {
				IInfoViewer viewerForm = appFactory.getInfoViewer();
				viewerForm.showPayment(result);
				hideLoading();
			}
		});
	}

}
