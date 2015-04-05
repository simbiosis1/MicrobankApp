package org.simbiosis.microbank;

import java.io.Serializable;
import java.util.Date;

public class DepositRevSharingDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5668470452112814542L;
	long id;
	long company;
	Date calcDate;
	Date date;
	int status;
	double customerSharing;
	double zakat;
	double tax;
	long deposit;
	long saving;

	public DepositRevSharingDto() {
		super();
		id = 0;
		status = 0;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getCalcDate() {
		return calcDate;
	}

	public void setCalcDate(Date calcDate) {
		this.calcDate = calcDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getCustomerSharing() {
		return customerSharing;
	}

	public void setCustomerSharing(double customerSharing) {
		this.customerSharing = customerSharing;
	}

	public double getZakat() {
		return zakat;
	}

	public void setZakat(double zakat) {
		this.zakat = zakat;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public long getDeposit() {
		return deposit;
	}

	public void setDeposit(long deposit) {
		this.deposit = deposit;
	}

	public long getSaving() {
		return saving;
	}

	public void setSaving(long saving) {
		this.saving = saving;
	}
}
