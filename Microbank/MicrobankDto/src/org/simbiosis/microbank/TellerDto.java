package org.simbiosis.microbank;

import java.io.Serializable;

public class TellerDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8417402629927438232L;
	long id;
	String code;
	String name;
	long user;
	long coa;
	long company;
	long branch;
	long subBranch;

	public TellerDto() {
		id = 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUser() {
		return user;
	}

	public void setUser(long user) {
		this.user = user;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public long getCoa() {
		return coa;
	}

	public void setCoa(long coa) {
		this.coa = coa;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
