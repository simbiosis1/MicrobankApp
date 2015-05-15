package org.simbiosis.microbank;

import java.io.Serializable;
import java.util.Date;

public class GuaranteeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2965403657974943796L;

	long id;
	long refId;
	String code;
	String refCode;
	Date registration;
	String owner;
	int active;
	Date closing;
	int type;
	String number;
	String description;
	int bondType;
	Date bondDate;
	int bondNotarial;
	String bondNotarialName;
	Date appraisalDate;
	int appraisalType;
	String appraisalName;
	double appraisalIntValue;
	double appraisalMarkValue;
	double appraisalOJKValue;
	long customer;
	long company;
	long branch;

	public GuaranteeDto() {
		id = 0;
		refId = 0;
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

	public long getCustomer() {
		return customer;
	}

	public void setCustomer(long customer) {
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
