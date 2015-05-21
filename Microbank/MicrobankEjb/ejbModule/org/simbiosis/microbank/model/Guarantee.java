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
@Table(name = "MIB_GUARANTEE")
@NamedQueries({
		@NamedQuery(name = "listDepositGuarantee", query = "select x from Guarantee x where x.company=:company and x.type=3 and x.number=:number"),
		@NamedQuery(name = "listGuaranteeByCode", query = "select x from Guarantee x where x.company=:company and x.code=:code"),
		@NamedQuery(name = "listGuaranteeByCompany", query = "select x from Guarantee x where x.company=:company"),
		@NamedQuery(name = "listGuaranteeByCompanyBranch", query = "select x from Guarantee x where x.company=:company and x.branch=:branch"),
		@NamedQuery(name = "getMaxGuaranteeCode", query = "select max(x.code) from Guarantee x where x.company=:company and x.branch=:branch and x.code like :prefixCode"),
		@NamedQuery(name = "getGuaranteeByRefId", query = "select x from Guarantee x where x.active=1 and x.company=:company and x.refId=:refId") })
public class Guarantee {
	@Id
	@Column(name = "GUA_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_guarantee")
	@TableGenerator(name = "gen_mib_guarantee", allocationSize = 1, pkColumnValue = "gen_mib_guarantee")
	long id;
	@Column(name = "GUA_REFID")
	long refId;
	@Column(name = "GUA_CODE", length = 30)
	String code;
	@Column(name = "GUA_REFCODE", length = 30)
	String refCode;
	@Column(name = "GUA_REGISTRATION")
	@Temporal(DATE)
	Date registration;
	@Column(name = "GUA_OWNER", length = 100)
	String owner;
	@Column(name = "GUA_ACTIVE")
	int active;
	@Column(name = "GUA_CLOSING")
	@Temporal(DATE)
	Date closing;
	@Column(name = "GUA_TYPE")
	int type;
	@Column(name = "GUA_NUMBER", length = 100)
	String number;
	@Column(name = "GUA_DESCRIPTION", length = 5000)
	String description;
	@Column(name = "GUA_BONDTYPE")
	int bondType;
	@Column(name = "GUA_BONDDATE")
	@Temporal(DATE)
	Date bondDate;
	@Column(name = "GUA_BONDNOTARIAL")
	int bondNotarial;
	@Column(name = "GUA_BONDNOTARIALNAME", length = 100)
	String bondNotarialName;
	@Column(name = "GUA_APPRDATE")
	@Temporal(DATE)
	Date appraisalDate;
	@Column(name = "GUA_APPRTYPE")
	int appraisalType;
	@Column(name = "GUA_APPRNAME", length = 100)
	String appraisalName;
	@Column(name = "GUA_APPRINTVALUE")
	double appraisalIntValue;
	@Column(name = "GUA_APPRMARKVALUE")
	double appraisalMarkValue;
	@Column(name = "GUA_APPROJKVALUE")
	double appraisalOJKValue;
	@Column(name = "GUA_COMPANY")
	long company;
	@Column(name = "GUA_BRANCH")
	long branch;

	@ManyToOne
	@JoinColumn(name = "CST_ID", referencedColumnName = "CST_ID")
	Customer customer;

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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getBondType() {
		return bondType;
	}

	public void setBondType(int bondType) {
		this.bondType = bondType;
	}

	public Date getBondDate() {
		return bondDate;
	}

	public void setBondDate(Date bondDate) {
		this.bondDate = bondDate;
	}

	public int getBondNotarial() {
		return bondNotarial;
	}

	public void setBondNotarial(int bondNotarial) {
		this.bondNotarial = bondNotarial;
	}

	public String getBondNotarialName() {
		return bondNotarialName;
	}

	public void setBondNotarialName(String bondNotarialName) {
		this.bondNotarialName = bondNotarialName;
	}

	public Date getAppraisalDate() {
		return appraisalDate;
	}

	public void setAppraisalDate(Date appraisalDate) {
		this.appraisalDate = appraisalDate;
	}

	public int getAppraisalType() {
		return appraisalType;
	}

	public void setAppraisalType(int appraisalType) {
		this.appraisalType = appraisalType;
	}

	public String getAppraisalName() {
		return appraisalName;
	}

	public void setAppraisalName(String appraisalName) {
		this.appraisalName = appraisalName;
	}

	public double getAppraisalIntValue() {
		return appraisalIntValue;
	}

	public void setAppraisalIntValue(double appraisalIntValue) {
		this.appraisalIntValue = appraisalIntValue;
	}

	public double getAppraisalMarkValue() {
		return appraisalMarkValue;
	}

	public void setAppraisalMarkValue(double appraisalMarkValue) {
		this.appraisalMarkValue = appraisalMarkValue;
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

	public Date getClosing() {
		return closing;
	}

	public void setClosing(Date closing) {
		this.closing = closing;
	}

	public double getAppraisalOJKValue() {
		return appraisalOJKValue;
	}

	public void setAppraisalOJKValue(double appraisalOJKValue) {
		this.appraisalOJKValue = appraisalOJKValue;
	}

}
