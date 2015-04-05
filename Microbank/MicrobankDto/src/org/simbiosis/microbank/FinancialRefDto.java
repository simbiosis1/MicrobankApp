package org.simbiosis.microbank;

import java.io.Serializable;
import java.util.Date;

public class FinancialRefDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2538959895453223083L;
	long id;
	Date pos;
	int order;
	String number;
	String group;
	String description;
	String code;
	double value;
	long company;
	long branch;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getPos() {
		return pos;
	}

	public void setPos(Date pos) {
		this.pos = pos;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
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

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
}
