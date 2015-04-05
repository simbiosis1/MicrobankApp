package org.simbiosis.microbank;

import java.io.Serializable;

public class RevenueSharingDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 243675474387786235L;
	long id;
	long company;
	long branch;
	int month;
	int year;
	long account;
	int type;
	String code;
	long customer;
	String name;
	String product;
	double sharing;
	int hasShare;
	long saving;
	double startValue;
	double endValue;
	double averageValue;
	double totalSharing;
	double customerSharing;
	double tax;
	double zakat;

	public RevenueSharingDto() {
		super();
		id = 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public long getAccount() {
		return account;
	}

	public void setAccount(long account) {
		this.account = account;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getSaving() {
		return saving;
	}

	public void setSaving(long saving) {
		this.saving = saving;
	}

	public double getStartValue() {
		return startValue;
	}

	public void setStartValue(double startValue) {
		this.startValue = startValue;
	}

	public double getEndValue() {
		return endValue;
	}

	public void setEndValue(double endValue) {
		this.endValue = endValue;
	}

	public double getAverageValue() {
		return averageValue;
	}

	public void setAverageValue(double averageValue) {
		this.averageValue = averageValue;
	}

	public double getSharing() {
		return sharing;
	}

	public void setSharing(double sharing) {
		this.sharing = sharing;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getZakat() {
		return zakat;
	}

	public void setZakat(double zakat) {
		this.zakat = zakat;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getCustomer() {
		return customer;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public double getTotalSharing() {
		return totalSharing;
	}

	public void setTotalSharing(double totalSharing) {
		this.totalSharing = totalSharing;
	}

	public double getCustomerSharing() {
		return customerSharing;
	}

	public void setCustomerSharing(double customerSharing) {
		this.customerSharing = customerSharing;
	}

	public int getHasShare() {
		return hasShare;
	}

	public void setHasShare(int hasShare) {
		this.hasShare = hasShare;
	}
}
