package org.simbiosis.microbank;

import java.io.Serializable;

public class LoanCodeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6576857663273233795L;
	long id;
	String code;
	int available;
	long company;
	long branch;

	public LoanCodeDto() {
		id = 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
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
}
