package org.simbiosis.microbank;

import java.io.Serializable;
import java.util.Date;

public class SavingDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 628316776044564640L;
	long id;
	long refId;
	String code;
	String refCode;
	Date registration;
	int confidential;
	double rate;
	double specialRate;
	long customer;
	long company;
	long branch;
	long product;
	int active;
	int zakat;
	Date closing;
	String closingReason;
	boolean hasCode;

	public SavingDto() {
		id = 0;
		refId = 0;
		hasCode = false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRefId() {
		return refId;
	}

	public void setRefId(long refId) {
		this.refId = refId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public Date getRegistration() {
		return registration;
	}

	public void setRegistration(Date registration) {
		this.registration = registration;
	}

	public long getCustomer() {
		return customer;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
	}

	public long getCompany() {
		return company;
	}

	public void setCompany(long company) {
		this.company = company;
	}

	public long getBranch() {
		return branch;
	}

	public void setBranch(long branch) {
		this.branch = branch;
	}

	public long getProduct() {
		return product;
	}

	public void setProduct(long product) {
		this.product = product;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Date getClosing() {
		return closing;
	}

	public void setClosing(Date closing) {
		this.closing = closing;
	}

	public String getClosingReason() {
		return closingReason;
	}

	public void setClosingReason(String closingReason) {
		this.closingReason = closingReason;
	}

	public double getSpecialRate() {
		return specialRate;
	}

	public void setSpecialRate(double specialRate) {
		this.specialRate = specialRate;
	}

	public int getZakat() {
		return zakat;
	}

	public void setZakat(int zakat) {
		this.zakat = zakat;
	}

	public int getConfidential() {
		return confidential;
	}

	public void setConfidential(int confidential) {
		this.confidential = confidential;
	}

	public boolean isHasCode() {
		return hasCode;
	}

	public void setHasCode(boolean hasCode) {
		this.hasCode = hasCode;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

}
