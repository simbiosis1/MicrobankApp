package org.simbiosis.bp.dto;

import java.io.Serializable;

public class ValidationDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3287828836131922804L;
	int error;
	int auth;
	String errMessage = new String();
	String authMessage = new String();

	public ValidationDto() {
		error = 0;
		auth = 0;
	}

	public int getError() {
		return error;
	}

	public int getAuth() {
		return auth;
	}

	public String getErrorMessage() {
		return errMessage;
	}

	public String getAuthMessage() {
		return authMessage;
	}

	public void addErrorMessage(String message) {
		error++;
		if (!errMessage.isEmpty()) {
			errMessage += ";";
		}
		errMessage += message;
	}

	public void addAuthMessage(String message) {
		auth++;
		if (!authMessage.isEmpty()) {
			authMessage += ";";
		}
		authMessage += message;
	}
}
