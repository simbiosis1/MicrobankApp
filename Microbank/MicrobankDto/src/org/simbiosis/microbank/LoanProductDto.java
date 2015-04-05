package org.simbiosis.microbank;

import java.io.Serializable;

public class LoanProductDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6556771736604416099L;
	long id;
	long refId;
	String code;
	String name;
	long company;
	int schema;
	long coa1;
	long coa2;
	long coa3;
	long coa4;
	long coa5;
	long coa6;
	int active;
	int profitShared;

	public LoanProductDto() {
		super();
		id = 0;
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

	public int getSchema() {
		return schema;
	}

	public void setSchema(int schema) {
		this.schema = schema;
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

	public long getCoa4() {
		return coa4;
	}

	public void setCoa4(long coa4) {
		this.coa4 = coa4;
	}

	public long getCoa5() {
		return coa5;
	}

	public void setCoa5(long coa5) {
		this.coa5 = coa5;
	}

	public long getCoa6() {
		return coa6;
	}

	public void setCoa6(long coa6) {
		this.coa6 = coa6;
	}

	public int getProfitShared() {
		return profitShared;
	}

	public void setProfitShared(int profitShared) {
		this.profitShared = profitShared;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

}
