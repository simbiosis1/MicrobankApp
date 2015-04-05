package org.simbiosis.microbank;

import java.io.Serializable;
import java.util.Date;

public class FindDepositDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7068114539354758687L;
	long company;
	long branch;
	boolean withCode;
	boolean withName;
	boolean withDob;
	String code;
	String name;
	Date dob;
	int active;

	public FindDepositDto() {
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

	public boolean isWithCode() {
		return withCode;
	}

	public void setWithCode(boolean withCode) {
		this.withCode = withCode;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

}
