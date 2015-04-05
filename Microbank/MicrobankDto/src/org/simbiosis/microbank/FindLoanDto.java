package org.simbiosis.microbank;

import java.io.Serializable;

public class FindLoanDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4545421498895145758L;
	long company;
	long branch;
	boolean withCode;
	boolean withName;
	String code;
	String name;
	int active;

	public FindLoanDto() {
		company = 0;
		branch = 0;
		withName = false;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isWithName() {
		return withName;
	}

	public void setWithName(boolean withName) {
		this.withName = withName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}
}
