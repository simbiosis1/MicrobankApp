package org.simbiosis.ui.bprs.loan.shared;

import java.util.ArrayList;
import java.util.List;

import org.simbiosis.ui.bprs.common.shared.TransactionDv;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InfoLoanDv implements IsSerializable {
	Double osPrincipal;
	Double osMargin;
	Double osTotal;
	Integer quality;
	Integer osDueCount;
	Double osDueValue;

	List<TransactionDv> loanPayments = new ArrayList<TransactionDv>();

	public InfoLoanDv() {
		osPrincipal = 0D;
		osMargin = 0D;
		osTotal = 0D;
		quality = 1;
		osDueCount = 0;
		osDueValue = 0D;
	}

	public Double getOsPrincipal() {
		return osPrincipal;
	}

	public void setOsPrincipal(Double osPrincipal) {
		this.osPrincipal = osPrincipal;
	}

	public Double getOsMargin() {
		return osMargin;
	}

	public void setOsMargin(Double osMargin) {
		this.osMargin = osMargin;
	}

	public Double getOsTotal() {
		return osTotal;
	}

	public void setOsTotal(Double osTotal) {
		this.osTotal = osTotal;
	}

	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public Integer getOsDueCount() {
		return osDueCount;
	}

	public void setOsDueCount(Integer osDueCount) {
		this.osDueCount = osDueCount;
	}

	public Double getOsDueValue() {
		return osDueValue;
	}

	public void setOsDueValue(Double osDueValue) {
		this.osDueValue = osDueValue;
	}

	public List<TransactionDv> getLoanPayments() {
		return loanPayments;
	}

	public void setLoanPayments(List<TransactionDv> loanPayments) {
		this.loanPayments = loanPayments;
	}

}
