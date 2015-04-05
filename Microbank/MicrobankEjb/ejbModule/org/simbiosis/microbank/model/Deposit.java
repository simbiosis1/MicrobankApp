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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

@Entity
@Table(name = "MIB_DEPOSIT")
@NamedQueries({
		@NamedQuery(name = "listDepositId1", query = "select x.id from Deposit x where (x.active=1 or (x.active=0 and x.closing>:date)) and x.company=:company and x.registration<=:date order by x.code"),
		@NamedQuery(name = "listDepositId2", query = "select x.id from Deposit x where (x.active=1 or (x.active=0 and x.closing>:date)) and x.branch=:branch and x.registration<=:date order by x.code"),
		@NamedQuery(name = "listDepositByCompany", query = "select x from Deposit x where x.active=1 and x.company=:company order by x.code"),
		@NamedQuery(name = "listDepositByCompanyBranch", query = "select x from Deposit x where x.active=1 and x.company=:company and x.branch=:branch order by x.code"),
		@NamedQuery(name = "getDepositByRefId1", query = "select x from Deposit x where x.company=:company and x.refId=:refId"),
		@NamedQuery(name = "getDepositByRefId2", query = "select x from Deposit x where x.branch=:branch and x.refId=:refId"),
		@NamedQuery(name = "getDepositByCode", query = "select x from Deposit x where x.company=:company and x.code=:code")

})
public class Deposit {
	@Id
	@Column(name = "DEP_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_deposit")
	@TableGenerator(name = "gen_mib_deposit", allocationSize = 1, pkColumnValue = "gen_mib_deposit")
	long id;
	@Column(name = "DEP_REFID")
	long refId;
	@Column(name = "DEP_CODE", length = 30)
	String code;
	@Column(name = "DEP_REFCODE", length = 30)
	String refCode;
	@Column(name = "DEP_REGISTRATION")
	@Temporal(DATE)
	Date registration;
	@Column(name = "DEP_VALUE")
	double value;
	@Column(name = "DEP_RATE")
	double rate;
	@Column(name = "DEP_SPECIALRATE")
	double specialRate;
	@Column(name = "DEP_BILYET", length = 50)
	String bilyet;
	@Column(name = "DEP_ARO")
	int aro;
	@Column(name = "DEP_BEGIN")
	@Temporal(DATE)
	Date begin;
	@Column(name = "DEP_END")
	@Temporal(DATE)
	Date end;
	@Column(name = "DEP_COMPANY")
	long company;
	@Column(name = "DEP_BRANCH")
	long branch;
	@Column(name = "DEP_ACTIVE")
	int active;
	@Column(name = "DEP_ZAKAT")
	int zakat;
	@Column(name = "DEP_CLOSING")
	@Temporal(DATE)
	Date closing;

	@OneToOne
	@JoinColumn(name = "SAV_ID", referencedColumnName = "SAV_ID")
	Saving saving;

	@ManyToOne
	@JoinColumn(name = "CST_ID", referencedColumnName = "CST_ID")
	Customer customer;

	@ManyToOne
	@JoinColumn(name = "DPR_ID", referencedColumnName = "DPR_ID")
	DepositProduct product;

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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public DepositProduct getProduct() {
		return product;
	}

	public void setProduct(DepositProduct product) {
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

	public String getBilyet() {
		return bilyet;
	}

	public void setBilyet(String bilyet) {
		this.bilyet = bilyet;
	}

	public int getAro() {
		return aro;
	}

	public void setAro(int aro) {
		this.aro = aro;
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

	public Saving getSaving() {
		return saving;
	}

	public void setSaving(Saving saving) {
		this.saving = saving;
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

}
