package org.simbiosis.ui.bprs.loan.shared;

import java.util.ArrayList;
import java.util.List;

import org.simbiosis.ui.bprs.common.shared.TransactionDv;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InfoLoanDv implements IsSerializable {
	String osPrincipal;
	String osMargin;
	String osTotal;
	String quality;
	String osDueCount;
	String osDueValue;

	List<TransactionDv> loanPayments = new ArrayList<TransactionDv>();

	public InfoLoanDv() {
	}

	public String getOsPrincipal() {
		return osPrincipal;
	}

	public void setOsPrincipal(String osPrincipal) {
		this.osPrincipal = osPrincipal;
	}

	public String getOsMargin() {
		return osMargin;
	}

	public void setOsMargin(String osMargin) {
		this.osMargin = osMargin;
	}

	public String getOsTotal() {
		return osTotal;
	}

	public void setOsTotal(String osTotal) {
		this.osTotal = osTotal;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getOsDueCount() {
		return osDueCount;
	}

	public void setOsDueCount(String osDueCount) {
		this.osDueCount = osDueCount;
	}

	public String getOsDueValue() {
		return osDueValue;
	}

	public void setOsDueValue(String osDueValue) {
		this.osDueValue = osDueValue;
	}

	public List<TransactionDv> getLoanPayments() {
		return loanPayments;
	}

	public void setLoanPayments(List<TransactionDv> loanPayments) {
		this.loanPayments = loanPayments;
	}

}
