package org.simbiosis.ui.bprs.common.client.printing;

import org.simbiosis.ui.bprs.common.client.handler.ValidationHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

public class DlgPrintValidation extends Composite {

	private static DlgPrintValidationUiBinder uiBinder = GWT
			.create(DlgPrintValidationUiBinder.class);

	interface DlgPrintValidationUiBinder extends
			UiBinder<Widget, DlgPrintValidation> {
	}

	@UiField
	Button btnNo;
	@UiField
	Button btnYes;
	@UiField
	DialogBox mainPanel;

	String psName;
	String validationText;
	ValidationHandler handler;

	public DlgPrintValidation(String psName, String validationText,
			ValidationHandler handler) {
		initWidget(uiBinder.createAndBindUi(this));
		this.psName = psName;
		this.validationText = validationText;
		this.handler = handler;
	}

	public void show() {
		mainPanel.center();
	}

	@UiHandler("btnNo")
	void onBtnNoClick(ClickEvent e) {
		mainPanel.hide();
	}

	@UiHandler("btnYes")
	void onBtnYesClick(ClickEvent e) {
		processPrinting();
		mainPanel.hide();
	}

	void processPrinting() {
		handler.showLoading();
		String url = Window.Location.getProtocol() + "//"
				+ Window.Location.getHost() + "/BprsReportingService/" + psName;
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
				URL.encode(url));
		try {
			builder.setHeader("Content-Type",
					"application/x-www-form-urlencoded");
			// Request request =
			builder.sendRequest("data=" + validationText,
					new RequestCallback() {
						public void onError(Request request, Throwable exception) {
							handler.hideLoading();
							Window.alert("Error : sendRequest");
							// Couldn't connect to server (could be timeout, SOP
							// violation, etc.)
						}

						public void onResponseReceived(Request request,
								Response response) {
							handler.hideLoading();
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
