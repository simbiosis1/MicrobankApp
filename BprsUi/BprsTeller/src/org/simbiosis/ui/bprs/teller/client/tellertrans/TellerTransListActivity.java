package org.simbiosis.ui.bprs.teller.client.tellertrans;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.shared.FindTransactionDv;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;
import org.simbiosis.ui.bprs.teller.client.AppFactory;
import org.simbiosis.ui.bprs.teller.client.rpc.AppService;
import org.simbiosis.ui.bprs.teller.client.rpc.AppServiceAsync;
import org.simbiosis.ui.bprs.teller.client.tellertrans.ITellerTransList.Activity;
import org.simbiosis.ui.bprs.teller.shared.TellerDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class TellerTransListActivity extends Activity {

	private final AppServiceAsync tellerSrv = GWT.create(AppService.class);

	TellerTransListPlace myPlace;
	AppFactory appFactory;

	public TellerTransListActivity(TellerTransListPlace myPlace,
			AppFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ITellerTransList myForm = appFactory.getListTransaksiTeller();
		myForm.setActivity(this, appFactory.getAppStatus());
		if (appFactory.getAppStatus().isLogin()) {
			loadCommonData();
		}
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_VIEW:
			onView();
			break;
		case FA_EXPORTPDF:
			onExportPdf();
			break;
		default:
			break;
		}
	}

	private void onExportPdf() {
		DateTimeFormat fmt = DateTimeFormat.getFormat("dd-MM-yyyy");
		ITellerTransList myForm = appFactory.getListTransaksiTeller();
		String teller = "teller=" + myForm.getTeller();
		String date = "date=" + fmt.format(myForm.getDate());
		Window.open("/BprsReportingService/getTellerTransPdf?" + teller + "&"
				+ date, "_blank", null);
	}

	private void onView() {
		showLoading();
		ITellerTransList myForm = appFactory.getListTransaksiTeller();
		tellerSrv.listTellerTransactionByTeller(getKey(), myForm.getTeller(),
				myForm.getDate(), new AsyncCallback<List<TransactionDv>>() {

					@Override
					public void onSuccess(List<TransactionDv> result) {
						hideLoading();
						ITellerTransList myForm = appFactory
								.getListTransaksiTeller();
						FindTransactionDv dv = new FindTransactionDv();
						dv.getResultTable().clear();
						dv.getResultTable().addAll(result);
						myForm.setListTransaction(dv);
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert(caught.getMessage());
					}
				});
	}

	private void loadCommonData() {
		showLoading();
		tellerSrv.listTeller(getKey(), new AsyncCallback<List<TellerDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(List<TellerDv> result) {
				ITellerTransList myForm = appFactory.getListTransaksiTeller();
				myForm.setTellerList(result);
				hideLoading();
			}
		});
	}

}
