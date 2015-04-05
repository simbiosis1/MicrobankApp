package org.simbiosis.microbank;

import java.io.Serializable;
import java.util.Date;

public class FindCustomerDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	long company;
	long branch;
	boolean withName;
	boolean withDob;
	String name;
	Date dob;

	public FindCustomerDto() {
		branch = 0;
		withName = false;
		withDob = false;
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

	public boolean isWithName() {
		return withName;
	}

	public void setWithName(boolean withName) {
		this.withName = withName;
	}

	public boolean isWithDob() {
		return withDob;
	}

	public void setWithDob(boolean withDob) {
		this.withDob = withDob;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

}
