package org.simbiosis.ui.bprs.teller.client.authdlg;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class AuthDialog extends DialogBox {

	private static AuthDialogUiBinder uiBinder = GWT
			.create(AuthDialogUiBinder.class);

	interface AuthDialogUiBinder extends UiBinder<Widget, AuthDialog> {
	}

	@UiField
	Label labelStatus;
	@UiField
	Button btnConfirm;
	@UiField
	Button btnExecute;

	AuthDialogHandler handler;

	public AuthDialog(AuthDialogHandler handler) {
		setWidget(uiBinder.createAndBindUi(this));
		setText("Tunggu otorisasi");
		//
		setGlassEnabled(true);
		//
		btnExecute.setEnabled(false);
		//
		this.handler = handler;
		this.handler.setLabelStatus(labelStatus);
		this.handler.setBtnExecute(btnExecute);
	}

	@UiHandler("btnConfirm")
	void onBtnConfirmClick(ClickEvent e) {
		handler.confirm();
	}

	@UiHandler("btnExecute")
	void onBtnExecuteClick(ClickEvent e) {
		handler.execute();
		hide();
	}

}
