package org.simbiosis.microbank;

import java.io.Serializable;

public class VaultDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 994313531745445992L;
	long id;
	long company;
	long branch;
	long subBranch;
	double type;
	double value;
	double amount;

	public VaultDto() {
		id = 0;
	}

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

	public long getSubBranch() {
		return subBranch;
	}

	public void setSubBranch(long subBranch) {
		this.subBranch = subBranch;
	}

	public double getValue() {
		return value;
	}

	public double getType() {
		return type;
	}

	public void setType(double type) {
		this.type = type;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
