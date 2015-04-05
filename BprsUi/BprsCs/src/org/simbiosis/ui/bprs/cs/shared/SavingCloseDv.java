package org.simbiosis.ui.bprs.cs.shared;

import java.util.Date;

import org.simbiosis.ui.bprs.common.shared.SavingDv;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SavingCloseDv implements IsSerializable {
	SavingDv saving;
	String strClosing;
	Date closing;
	String reason;

	public SavingDv getSaving() {
		return saving;
	}

	public void setSaving(SavingDv saving) {
		this.saving = saving;
	}

	public String getStrClosing() {
		return strClosing;
	}

	public void setStrClosing(String strClosing) {
		this.strClosing = strClosing;
	}

	public Date getClosing() {
		return closing;
	}

	public void setClosing(Date closing) {
		this.closing = closing;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
