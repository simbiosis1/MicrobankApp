package org.simbiosis.ui.bprs.common.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ValidationDv implements IsSerializable {
	int error;
	int auth;
	String errMessage = new String();
	String authMessage = new String();

	public ValidationDv() {
		error = 0;
		auth = 0;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	public String getErrorMessage() {
		return errMessage;
	}

	public void setErrorMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public String getAuthMessage() {
		return authMessage;
	}

	public void setAuthMessage(String authMessage) {
		this.authMessage = authMessage;
	}


}
