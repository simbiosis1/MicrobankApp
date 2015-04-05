package org.simbiosis.microbank.model;

import static javax.persistence.GenerationType.TABLE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "MIB_REVSHARING")
@NamedQueries({ @NamedQuery(name = "listRevenueSharing", query = "select x from RevenueSharing x where x.company=:company and x.month=:month and x.year=:year and x.id>:id order by x.id") })
public class RevenueSharing {
	@Id
	@Column(name = "RVS_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_revsharing")
	@TableGenerator(name = "gen_mib_revsharing", allocationSize = 1, pkColumnValue = "gen_mib_revsharing")
	long id;
	@Column(name = "RVS_COMPANY")
	long company;
	@Column(name = "RVS_BRANCH")
	long branch;
	@Column(name = "RVS_MONTH")
	int month;
	@Column(name = "RVS_YEAR")
	int year;
	@Column(name = "RVS_ACCOUNT")
	long account;
	@Column(name = "RVS_TYPE")
	int type;
	@Column(name = "RVS_SAVING")
	long saving;
	@Column(name = "RVS_STARTVALUE")
	double startValue;
	@Column(name = "RVS_ENDVALUE")
	double endValue;
	@Column(name = "RVS_AVERAGEVALUE")
	double averageValue;
	@Column(name = "RVS_SHARING")
	double sharing;
	@Column(name = "RVS_TOTALSHARING")
	double totalSharing;
	@Column(name = "RVS_CUSTOMERSHARING")
	double customerSharing;
	@Column(name = "RVS_TAX")
	double tax;
	@Column(name = "RVS_ZAKAT")
	double zakat;

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
}
