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

@Entity
@Table(name = "MIB_CUSTOMER")
@NamedQueries({
		@NamedQuery(name = "listCifByDin", query = "select x from Customer x where x.company=:company and x.name=:name and x.otherCode=:otherCode"),
		@NamedQuery(name = "listCif", query = "select x from Customer x where x.company=:company and x.name=:name"),
		@NamedQuery(name = "listCustomerByCompany", query = "select x from Customer x where x.company=:company"),
		@NamedQuery(name = "listCustomerByCompanyBranch", query = "select x from Customer x where x.company=:company and x.branch=:branch"),
		@NamedQuery(name = "getMaxCustomerCode", query = "select max(x.code) from Customer x where x.company=:company and x.branch=:branch and x.code like :prefixCode"),
		@NamedQuery(name = "getAllCustomerByRefId", query = "select x from Customer x where x.company=:company and x.refId=:refId"),
		@NamedQuery(name = "getCustomerByRefId", query = "select x from Customer x where x.company=:company and x.branch=:branch and x.refId=:refId"),
		@NamedQuery(name = "listCustomerByRefIdRange", query = "select x from Customer x where x.company=:company and x.branch<>:branch and x.refId>:refIdMin and x.refId<=:refIdMax"),
		@NamedQuery(name = "listCustomerByRefIdMax", query = "select x from Customer x where x.company=:company and x.branch<>:branch and x.refId<=:refId"),
		@NamedQuery(name = "listCustomerByRefIdMin", query = "select x from Customer x where x.company=:company and x.branch<>:branch and x.refId>:refId") })
public class Customer {
	@Id
	@Column(name = "CST_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_customer")
	@TableGenerator(name = "gen_mib_customer", allocationSize = 1, pkColumnValue = "gen_mib_customer")
	long id;
	@Column(name = "CST_REFID")
	long refId;
	@Column(name = "CST_CODE", length = 30)
	String code;
	@Column(name = "CST_REFCODE", length = 30)
	String refCode;
	@Column(name = "CST_REGISTRATION")
	@Temporal(DATE)
	Date registration;
	@Column(name = "CST_NAME", length = 100)
	String name;
	@Column(name = "CST_ALIAS", length = 100)
	String alias;
	@Column(name = "CST_TITLE", length = 100)
	String title;
	@Column(name = "CST_SEX")
	int sex;
	@Column(name = "CST_POB", length = 100)
	String pob;
	@Column(name = "CST_DOB")
	@Temporal(DATE)
	Date dob;
	@Column(name = "CST_IDTYPE")
	int idType;
	@Column(name = "CST_IDCODE", length = 100)
	String idCode;
	@Column(name = "CST_MOTHERNAME", length = 100)
	String motherName;
	@Column(name = "CST_SPOUSENAME", length = 100)
	String spouseName;
	@Column(name = "CST_ADDRESS", length = 150)
	String address;
	@Column(name = "CST_DISTRICT", length = 150)
	String district;
	@Column(name = "CST_VILLAGE", length = 150)
	String village;
	@Column(name = "CST_AREA1", length = 150)
	String area1;
	@Column(name = "CST_AREA2", length = 150)
	String area2;
	@Column(name = "CST_CITY", length = 100)
	String city;
	@Column(name = "CST_POSTCODE", length = 30)
	String postCode;
	@Column(name = "CST_PROVINCE", length = 100)
	String province;
	@Column(name = "CST_PHONE", length = 100)
	String phone;
	@Column(name = "CST_HANDPHONE", length = 100)
	String handphone;
	@Column(name = "CST_DESCENDANT", length = 100)
	String descendant;
	@Column(name = "CST_DESCADDRESS", length = 200)
	String descAddress;
	@Column(name = "CST_TYPE")
	int type;
	@Column(name = "CST_OCCUPATION", length = 100)
	String occupation;
	@Column(name = "CST_OFFICENAME", length = 100)
	String officeName;
	@Column(name = "CST_OFFICEADDRESS", length = 100)
	String officeAddress;
	@Column(name = "CST_OFFICECITY", length = 100)
	String officeCity;
	@Column(name = "CST_INCOME", length = 100)
	String income;
	@Column(name = "CST_TAXABLE")
	int taxable;
	@Column(name = "CST_TAXNR", length = 100)
	String taxNr;
	@Column(name = "CST_COMPANY")
	long company;
	@Column(name = "CST_BRANCH")
	long branch;
	@Column(name = "CST_OTHERSTATUS")
	int otherStatus;
	@Column(name = "CST_OTHERCODE", length = 100)
	String otherCode;
	@Column(name = "CST_BANKREL")
	int bankRel;

	public Customer() {
		id = 0;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getPob() {
		return pob;
	}

	public void setPob(String pob) {
		this.pob = pob;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
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

	public int getIdType() {
		return idType;
	}

	public void setIdType(int idType) {
		this.idType = idType;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHandphone() {
		return handphone;
	}

	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getArea1() {
		return area1;
	}

	public void setArea1(String area1) {
		this.area1 = area1;
	}

	public String getArea2() {
		return area2;
	}

	public void setArea2(String area2) {
		this.area2 = area2;
	}

	public String getDescendant() {
		return descendant;
	}

	public void setDescendant(String descendant) {
		this.descendant = descendant;
	}

	public String getDescAddress() {
		return descAddress;
	}

	public void setDescAddress(String descAddress) {
		this.descAddress = descAddress;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getTaxNr() {
		return taxNr;
	}

	public void setTaxNr(String taxNr) {
		this.taxNr = taxNr;
	}

	public String getOtherCode() {
		return otherCode;
	}

	public void setOtherCode(String otherCode) {
		this.otherCode = otherCode;
	}

	public int getBankRel() {
		return bankRel;
	}

	public void setBankRel(int bankRel) {
		this.bankRel = bankRel;
	}

	public int getOtherStatus() {
		return otherStatus;
	}

	public void setOtherStatus(int otherStatus) {
		this.otherStatus = otherStatus;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public int getTaxable() {
		return taxable;
	}

	public void setTaxable(int taxable) {
		this.taxable = taxable;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getOfficeCity() {
		return officeCity;
	}

	public void setOfficeCity(String officeCity) {
		this.officeCity = officeCity;
	}

}
