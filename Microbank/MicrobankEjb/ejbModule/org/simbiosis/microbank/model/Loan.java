package org.simbiosis.microbank.model;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.GenerationType.TABLE;
import static javax.persistence.TemporalType.DATE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

@Entity
@Table(name = "MIB_LOAN")
@NamedQueries({
		@NamedQuery(name = "listLoanBySchema", query = "select x from Loan x where x.company=:company and x.product.schema=:schema and x.active=1 order by x.code"),
		@NamedQuery(name = "listLoanDropping1", query = "select x from Loan x where x.company=:company and x.contractDate>=:beginDate and x.contractDate<=:endDate order by x.code"),
		@NamedQuery(name = "listLoanDropping2", query = "select x from Loan x where x.branch=:branch and x.contractDate>=:beginDate and x.contractDate<=:endDate order by x.code"),
		@NamedQuery(name = "listLoanInfo1", query = "select x from Loan x where (x.active=1 or (x.active=0 and x.closing>:date)) and x.contractDate<=:date and x.company=:company order by x.code"),
		@NamedQuery(name = "listLoanInfo2", query = "select x from Loan x where (x.active=1 or (x.active=0 and x.closing>:date)) and x.contractDate<=:date and x.branch=:branch order by x.code"),
		@NamedQuery(name = "listLoanCifNR1", query = "select x.customer from Loan x where x.active=1 and x.company=:company and x.customer.otherCode is null and x.contractDate<=:date order by x.code"),
		@NamedQuery(name = "listLoanCifNR2", query = "select x.customer from Loan x where x.active=1 and x.branch=:branch and x.customer.otherCode is null and x.contractDate<=:date order by x.code"),
		@NamedQuery(name = "listLoanByCompany", query = "select x from Loan x where x.company=:company order by x.code"),
		@NamedQuery(name = "listLoanByCompanyBranch", query = "select x from Loan x where x.company=:company and x.branch=:branch order by x.code"),
		@NamedQuery(name = "listLoanIdByCompany", query = "select x.id from Loan x where (x.active=:active or (x.active=0 and x.closing>:date)) and x.company=:company and x.contractDate<=:date order by x.code"),
		@NamedQuery(name = "listLoanIdByCompanyBranch", query = "select x.id from Loan x where (x.active=:active or (x.active=0 and x.closing>:date)) and x.company=:company and x.branch=:branch and x.contractDate<=:date order by x.code"),
		@NamedQuery(name = "getMaxLoanCode", query = "select max(x.code) from Loan x where x.company=:company and x.branch=:branch and x.code like :prefixCode"),
		@NamedQuery(name = "getLoanByRefId", query = "select x from Loan x where x.company=:company and x.branch=:branch and x.refId=:refId"),
		@NamedQuery(name = "getLoanByCode1", query = "select x from Loan x where x.company=:company and x.code=:code"),
		@NamedQuery(name = "getLoanByCode2", query = "select x from Loan x where x.company=:company and x.code=:code and x.active=:active"),
		@NamedQuery(name = "listAoLoanByCompany", query = "select distinct (x.ao) from Loan x where x.company=:company and x.active=:active"),
		@NamedQuery(name = "listAoLoanByCompanyBranch", query = "select distinct (x.ao) from Loan x where x.company=:company and x.branch=:branch and x.active=:active") })
public class Loan {
	@Id
	@Column(name = "LOA_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_loan")
	@TableGenerator(name = "gen_mib_loan", allocationSize = 1, pkColumnValue = "gen_mib_loan")
	long id;
	@Column(name = "LOA_REFID")
	long refId;
	@Column(name = "LOA_CODE", length = 30)
	String code;
	@Column(name = "LOA_REFCODE", length = 30)
	String refCode;
	@Column(name = "LOA_REGISTRATION")
	@Temporal(DATE)
	Date registration;
	@Column(name = "LOA_PRINCIPAL")
	double principal;
	@Column(name = "LOA_TENOR")
	double tenor;
	@Column(name = "LOA_RATE")
	double rate;
	@Column(name = "LOA_MARGIN")
	double margin;
	@Column(name = "LOA_CONTRACT", length = 50)
	String contract;
	@Column(name = "LOA_CONTRACTDATE")
	@Temporal(DATE)
	Date contractDate;
	@Column(name = "LOA_DROPPED")
	int dropped;
	@Column(name = "LOA_COMPANY")
	long company;
	@Column(name = "LOA_BRANCH")
	long branch;
	@Column(name = "LOA_PURPOSE", length = 200)
	String purpose;
	@Column(name = "LOA_BISEKTOR", length = 200)
	String biSektor;

	@Column(name = "LOA_ACTIVE")
	int active;
	@Column(name = "LOA_CLOSING")
	@Temporal(DATE)
	Date closing;
	@Column(name = "LOA_AO")
	long ao;
	@Column(name = "LOA_AOHISTORY", length = 400)
	String aoHistory;

	@Column(name = "LOA_RESCHEDULE")
	int reschedule;
	@Column(name = "LOA_ADMIN")
	double admin;
	@Column(name = "LOA_FINE")
	double fine;

	@OneToOne
	@JoinColumn(name = "SAV_ID", referencedColumnName = "SAV_ID")
	Saving saving;

	@ManyToOne
	@JoinColumn(name = "CST_ID", referencedColumnName = "CST_ID")
	Customer customer;

	@ManyToOne
	@JoinColumn(name = "LPR_ID", referencedColumnName = "LPR_ID")
	LoanProduct product;

	@OneToMany(mappedBy = "loan", cascade = { PERSIST, MERGE })
	List<LoanSchedule> schedules = new ArrayList<LoanSchedule>();

	@ManyToMany
	@JoinTable(name = "MIB_LOANGUARANTEE", joinColumns = @JoinColumn(name = "LOA_ID", referencedColumnName = "LOA_ID"), inverseJoinColumns = @JoinColumn(name = "GUA_ID", referencedColumnName = "GUA_ID"))
	List<Guarantee> guarantees = new ArrayList<Guarantee>();

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

	public double getPrincipal() {
		return principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	public double getTenor() {
		return tenor;
	}

	public void setTenor(double tenor) {
		this.tenor = tenor;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
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

	public LoanProduct getProduct() {
		return product;
	}

	public void setProduct(LoanProduct product) {
		this.product = product;
	}

	public List<LoanSchedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<LoanSchedule> schedules) {
		this.schedules = schedules;
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

	public Saving getSaving() {
		return saving;
	}

	public void setSaving(Saving saving) {
		this.saving = saving;
	}

	public double getMargin() {
		return margin;
	}

	public void setMargin(double margin) {
		this.margin = margin;
	}

	public int getDropped() {
		return dropped;
	}

	public void setDropped(int dropped) {
		this.dropped = dropped;
	}

	public List<Guarantee> getGuarantees() {
		return guarantees;
	}

	public void setGuarantees(List<Guarantee> guarantees) {
		this.guarantees = guarantees;
	}

	public long getAo() {
		return ao;
	}

	public void setAo(long ao) {
		this.ao = ao;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getBiSektor() {
		return biSektor;
	}

	public void setBiSektor(String biSektor) {
		this.biSektor = biSektor;
	}

	public int getReschedule() {
		return reschedule;
	}

	public void setReschedule(int reschedule) {
		this.reschedule = reschedule;
	}

	public double getAdmin() {
		return admin;
	}

	public void setAdmin(double admin) {
		this.admin = admin;
	}

	public double getFine() {
		return fine;
	}

	public void setFine(double fine) {
		this.fine = fine;
	}

	public String getAoHistory() {
		return aoHistory;
	}

	public void setAoHistory(String aoHistory) {
		this.aoHistory = aoHistory;
	}

}
