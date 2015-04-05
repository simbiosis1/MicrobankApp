package org.simbiosis.microbank;

import java.io.Serializable;

public class DepositProductDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8813175195691396969L;
	long id;
	long refId;
	String code;
	String name;
	int term;
	long company;
	double sharing;
	long coa1;
	long coa2;
	long coa3;

	public DepositProductDto() {
		id = 0;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
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

	public double getSharing() {
		return sharing;
	}

	public void setSharing(double sharing) {
		this.sharing = sharing;
	}

	public long getCoa1() {
		return coa1;
	}

	public void setCoa1(long coa1) {
		this.coa1 = coa1;
	}

	public long getCoa2() {
		return coa2;
	}

	public void setCoa2(long coa2) {
		this.coa2 = coa2;
	}

	public long getCoa3() {
		return coa3;
	}

	public void setCoa3(long coa3) {
		this.coa3 = coa3;
	}

}
