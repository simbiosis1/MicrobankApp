package org.simbiosis.microbank.model;

import static javax.persistence.GenerationType.TABLE;
import static javax.persistence.TemporalType.DATE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "MIB_DEPOSITREVSHARING")
@NamedQueries({
	@NamedQuery(name = "listDepositRevSharing", query = "select x from DepositRevSharing x where x.status=0 and x.company=:company and x.date<=:date") })
public class DepositRevSharing {
	@Id
	@Column(name = "DRS_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_depositrevsharing")
	@TableGenerator(name = "gen_mib_depositrevsharing", allocationSize = 1, pkColumnValue = "gen_mib_depositrevsharing")
	long id;
	@Column(name = "DRS_COMPANY")
	long company;
	@Column(name = "DRS_DATE")
	@Temporal(DATE)
	Date date;
	@Column(name = "DRS_STATUS")
	int status;
	@Column(name = "DRS_CUSTOMERSHARING")
	double customerSharing;
	@Column(name = "DRS_ZAKAT")
	double zakat;
	@Column(name = "DRS_TAX")
	double tax;
	
	@ManyToOne
	@JoinColumn(name = "DEP_ID", referencedColumnName = "DEP_ID")
	Deposit deposit;
	
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

	public Deposit getDeposit() {
		return deposit;
	}

	public void setDeposit(Deposit deposit) {
		this.deposit = deposit;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
}
