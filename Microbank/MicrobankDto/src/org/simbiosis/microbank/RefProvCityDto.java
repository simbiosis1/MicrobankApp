package org.simbiosis.microbank;

import java.io.Serializable;

public class RefProvCityDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 602956383749290656L;

	
	long id;
	
	String name;
	
	long company;
	
	long branch;
	
	long level;
	

	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}
	
	
}
