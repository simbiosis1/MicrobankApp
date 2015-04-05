package org.simbiosis.crm;

import java.io.Serializable;

public class AccountOfficerDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2763585578922813257L;
	long id;
	long company;
	long branch;
	String name;
	long userId;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
