package org.simbiosis.ui.bprs.common.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GuaranteeDv implements IsSerializable {
	Integer nr;
	Long id;
	String code;
	Date registration;
	CustomerDv customer = new CustomerDv();
	Long company;
	Long branch;
	String ownerName;
	String number;
	String description;
	Double appraisalIntValue;
	Double appraisalMarkValue;
	Double appraisalTaxValue;
	Integer type;
	String strType;
	Integer active;

	public GuaranteeDv() {
		nr = 0;
		id = 0L;
		company = 0L;
		branch = 0L;
		type = 0;
		active = 1;
		appraisalIntValue = 0D;
		appraisalMarkValue = 0D;
		appraisalTaxValue = 0D;
	}

	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

	public Long getBranch() {
		return branch;
	}

	public void setBranch(Long branch) {
		this.branch = branch;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getRegistration() {
		return registration;
	}

	public void setRegistration(Date registration) {
		this.registration = registration;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CustomerDv getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDv customer) {
		this.customer = customer;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Double getAppraisalIntValue() {
		return appraisalIntValue;
	}

	public void setAppraisalIntValue(Double appraisalIntValue) {
		this.appraisalIntValue = appraisalIntValue;
	}

	public Double getAppraisalMarkValue() {
		return appraisalMarkValue;
	}

	public void setAppraisalMarkValue(Double appraisalMarkValue) {
		this.appraisalMarkValue = appraisalMarkValue;
	}

	public Double getAppraisalTaxValue() {
		return appraisalTaxValue;
	}

	public void setAppraisalTaxValue(Double appraisalTaxValue) {
		this.appraisalTaxValue = appraisalTaxValue;
	}

}
