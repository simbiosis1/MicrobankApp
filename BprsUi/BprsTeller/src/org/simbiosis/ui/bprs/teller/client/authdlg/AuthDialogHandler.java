package org.simbiosis.ui.bprs.teller.client.authdlg;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;

public abstract class AuthDialogHandler {
	Label labelStatus;
	Button btnExecute;

	public Label getLabelStatus() {
		return labelStatus;
	}

	public void setLabelStatus(Label labelStatus) {
		this.labelStatus = labelStatus;
	}

	public Button getBtnExecute() {
		return btnExecute;
	}

	public void setBtnExecute(Button btnExecute) {
		this.btnExecute = btnExecute;
	}

	public abstract void showLoading();

	public abstract void hideLoading();

	public abstract void confirm();

	public abstract void execute();
}
