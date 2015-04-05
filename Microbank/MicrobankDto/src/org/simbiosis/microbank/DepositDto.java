package org.simbiosis.microbank;

import java.io.Serializable;
import java.util.Date;

public class DepositDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4591716738908157677L;
	long id;
	long refId;
	boolean hasCode;
	String code;
	String refCode;
	String bilyet;
	Date registration;
	double value;
	double rate;
	double specialRate;
	Date begin;
	Date end;
	int aro;
	long customer;
	long company;
	long branch;
	long product;
	long saving;
	int active;
	int zakat;
	Date closing;

	public DepositDto() {
		id = 0;
		refId = 0;
		saving = 0;
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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getSpecialRate() {
		return specialRate;
	}

	public void setSpecialRate(double specialRate) {
		this.specialRate = specialRate;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public int getAro() {
		return aro;
	}

	public void setAro(int aro) {
		this.aro = aro;
	}

	public long getSaving() {
		return saving;
	}

	public void setSaving(long saving) {
		this.saving = saving;
	}

	public String getBilyet() {
		return bilyet;
	}

	public void setBilyet(String bilyet) {
		this.bilyet = bilyet;
	}

	public int getZakat() {
		return zakat;
	}

	public void setZakat(int zakat) {
		this.zakat = zakat;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public boolean isHasCode() {
		return hasCode;
	}

	public void setHasCode(boolean hasCode) {
		this.hasCode = hasCode;
	}

}
