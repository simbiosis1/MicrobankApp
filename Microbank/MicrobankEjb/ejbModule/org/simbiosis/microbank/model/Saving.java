package org.simbiosis.microbank.model;

import static javax.persistence.GenerationType.TABLE;
import static javax.persistence.TemporalType.DATE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

@Entity
@Table(name = "MIB_SAVING")
@NamedQueries({
		@NamedQuery(name = "listSavingIdByCompany", query = "select x.id from Saving x where (x.active=:active or (x.active=0 and x.closing>:date)) and x.company=:company and x.registration<=:date order by x.code"),
		@NamedQuery(name = "listSavingIdByBranch", query = "select x.id from Saving x where (x.active=:active or (x.active=0 and x.closing>:date)) and x.branch=:branch and x.registration<=:date order by x.code"),
		@NamedQuery(name = "listSavingByCompany", query = "select x from Saving x where x.active=1 and x.company=:company order by x.code"),
		@NamedQuery(name = "listSavingByCompanyBranch", query = "select x from Saving x where x.active=1 and x.company=:company and x.branch=:branch order by x.code"),
		@NamedQuery(name = "listSavingByCustomer", query = "select x from Saving x where x.active=1 and x.company=:company and x.customer.id=:idCustomer order by x.code"),
		@NamedQuery(name = "getMaxSavingCode", query = "select max(x.code) from Saving x where x.company=:company and x.branch=:branch and x.code like :prefixCode"),
		@NamedQuery(name = "getSavingByCode", query = "select x from Saving x where x.company=:company and x.code=:code"),
		@NamedQuery(name = "getAllSavingByRefId", query = "select x from Saving x where x.company=:company and x.refId=:refId"),
		@NamedQuery(name = "getSavingByRefId", query = "select x from Saving x where x.company=:company and x.branch=:branch and x.refId=:refId") })
public class Saving {
	@Id
	@Column(name = "SAV_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_saving")
	@TableGenerator(name = "gen_mib_saving", allocationSize = 1, pkColumnValue = "gen_mib_saving")
	long id;
	@Column(name = "SAV_REFID")
	long refId;
	@Column(name = "SAV_CODE", length = 30)
	String code;
	@Column(name = "SAV_REFCODE", length = 30)
	String refCode;
	@Column(name = "SAV_REGISTRATION")
	@Temporal(DATE)
	Date registration;
	@Column(name = "SAV_CONFIDENTIAL")
	int confidential;
	@Column(name = "SAV_SPECIALRATE")
	double specialRate;
	@Column(name = "SAV_COMPANY")
	long company;
	@Column(name = "SAV_BRANCH")
	long branch;
	@Column(name = "SAV_ACTIVE")
	int active;
	@Column(name = "SAV_ZAKAT")
	int zakat;
	@Column(name = "SAV_ONCLOSE")
	int onClose;
	@Column(name = "SAV_CLOSING")
	@Temporal(DATE)
	Date closing;
	@Column(name = "SAV_CLOSINGREASON", length = 150)
	String closingReason;

	@ManyToOne
	@JoinColumn(name = "CST_ID", referencedColumnName = "CST_ID")
	Customer customer;

	@ManyToOne
	@JoinColumn(name = "SPR_ID", referencedColumnName = "SPR_ID")
	SavingProduct product;

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

	public double getSpecialRate() {
		return specialRate;
	}

	public void setSpecialRate(double specialRate) {
		this.specialRate = specialRate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
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

	public SavingProduct getProduct() {
		return product;
	}

	public void setProduct(SavingProduct product) {
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

	public int getZakat() {
		return zakat;
	}

	public void setZakat(int zakat) {
		this.zakat = zakat;
	}

	public int getOnClose() {
		return onClose;
	}

	public void setOnClose(int onClose) {
		this.onClose = onClose;
	}

	public int getConfidential() {
		return confidential;
	}

	public void setConfidential(int confidential) {
		this.confidential = confidential;
	}
}
