package org.simbiosis.ui.bprs.loan.client.simulation;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.shared.LoanScheduleDv;
import org.simbiosis.ui.bprs.loan.client.BprsLoanFactory;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanService;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanServiceAsync;
import org.simbiosis.ui.bprs.loan.client.simulation.ISimulation.Activity;
import org.simbiosis.ui.bprs.loan.shared.LoanScheduleGenDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class SimulationActivity extends Activity {

	private final LoanServiceAsync loanSrv = GWT.create(LoanService.class);

	SimulationPlace myPlace;
	BprsLoanFactory appFactory;

	String reportService = "/BprsLoanReportService";

	public SimulationActivity(SimulationPlace myPlace,
			BprsLoanFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ISimulation myForm = appFactory.getSimulation();
		myForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_EXPORTPDF:
			onExportPdf();
			break;
		default:
			break;
		}
	}

	private void onExportPdf() {
		ISimulation myForm = appFactory.getSimulation();
		LoanScheduleGenDv dv = myForm.getData();
		Window.open(
				reportService + "/getLoanSimulationPdf?principal="
						+ dv.getPrincipal() + "&rate=" + dv.getRate()
						+ "&tenor=" + dv.getTenor() + "&type="
						+ dv.getScheduleType() + "&name=" + dv.getName(),
				"_blank", null);
	}

	@Override
	public void generate(LoanScheduleGenDv data) {
		showLoading();
		loanSrv.createLoanSchedule(data.getPrincipal(), data.getTenor(),
				data.getRate(), new Date(), data.getScheduleType(),
				new AsyncCallback<List<LoanScheduleDv>>() {

					@Override
					public void onSuccess(List<LoanScheduleDv> result) {
						hideLoading();
						//
						ISimulation myForm = appFactory.getSimulation();
						LoanScheduleGenDv data = myForm.getData();
						data.getSchedules().clear();
						data.setSchedules(result);
						myForm.setData(data);
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : createLoanSchedule");
					}
				});
	}

}
