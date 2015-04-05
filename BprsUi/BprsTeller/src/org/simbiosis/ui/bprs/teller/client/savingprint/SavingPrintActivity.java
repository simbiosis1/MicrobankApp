package org.simbiosis.ui.bprs.teller.client.savingprint;

import java.util.Date;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.client.handler.GetSavingHandler;
import org.simbiosis.ui.bprs.common.client.printing.Print;
import org.simbiosis.ui.bprs.common.client.savinghelper.DlgGetSaving;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.teller.client.AppFactory;
import org.simbiosis.ui.bprs.teller.client.savingprint.ISavingPrint.Activity;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class SavingPrintActivity extends Activity {

	SavingPrintPlace myPlace;
	AppFactory appFactory;
	Activity activity;

	public SavingPrintActivity(SavingPrintPlace myPlace, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
		this.activity = this;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ISavingPrint myForm = appFactory.getCetakTabungan();
		myForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_SEARCH:
			onSearch();
			break;
		default:
			break;
		}
	}

	private void onSearch() {
		GetSavingHandler handler = new GetSavingHandler() {

			@Override
			public void showSaving(SavingDv savingDv) {
				ISavingPrint myForm = appFactory.getCetakTabungan();
				savingDv.copySavingData();
				myForm.setSaving(savingDv);
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
		DlgGetSaving dlgCariSimpanan = new DlgGetSaving(getKey(), true, handler);
		dlgCariSimpanan.center();
	}

	@Override
	public void printMaster(SavingDv saving) {
		DateTimeFormat fmt = DateTimeFormat.getFormat("dd/MM/yyyy");
		String data = saving.getCode() + "<>" + saving.getStrProduct() + "<>"
				+ saving.getName() + "<>" + saving.getIdCode() + "<>"
				+ saving.getAddress() + "<>" + saving.getCity() + " "
				+ saving.getPostCode() + "<>" + fmt.format(new Date()) + "<>"
				+ saving.getCustomer().getCode();
		loadPrintServer("getSavingMaster", data);
	}

	@Override
	public void printBook(SavingDv saving, Date date, String nuc) {
		DateTimeFormat fmt = DateTimeFormat.getFormat("dd/MM/yyyy");
		loadPrintServer("getSavingBook",
				"" + saving.getId() + "<>" + fmt.format(date) + "<>" + nuc);
	}

	public void loadPrintServer(String psName, String data) {
		showLoading();
		String url = Window.Location.getProtocol() + "//"
				+ Window.Location.getHost() + "/BprsReportingService/" + psName;
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
				URL.encode(url));
		try {
			builder.setHeader("Content-Type",
					"application/x-www-form-urlencoded");
			// Request request =
			builder.sendRequest("data=" + data, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					activity.hideLoading();
					Window.alert("Error : sendRequest");
					// Couldn't connect to server (could be timeout, SOP
					// violation, etc.)
				}

				public void onResponseReceived(Request request,
						Response response) {
					activity.hideLoading();
					if (200 == response.getStatusCode()) {
						Print.it(response.getText());
					} else {
						Window.alert("Error response : "
								+ response.getStatusText());
					}
				}
			});
		} catch (RequestException e) {
			Window.alert("Error : couldn't connect to server");
		}
	}

}
