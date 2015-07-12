package org.simbiosis.microbank;

import java.io.Serializable;

public class SavingProductDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1500796648788517913L;
	long id;
	long refId;
	String code;
	String name;
	long company;
	int schema;
	double sharing;
	int hasShare;
	long coa1;
	long coa2;
	long coa3;
	long coa4;
	double minValue;
	double minSharable;
	double closeAdmin;
	double monthlyAdmin;

	public SavingProductDto() {
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

	public double getSharing() {
		return sharing;
	}

	public void setSharing(double sharing) {
		this.sharing = sharing;
	}

	public int getHasShare() {
		return hasShare;
	}

	public void setHasShare(int hasShare) {
		this.hasShare = hasShare;
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

	public int getSchema() {
		return schema;
	}

	public void setSchema(int schema) {
		this.schema = schema;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public long getCoa4() {
		return coa4;
	}

	public void setCoa4(long coa4) {
		this.coa4 = coa4;
	}

	public double getCloseAdmin() {
		return closeAdmin;
	}

	public void setCloseAdmin(double closeAdmin) {
		this.closeAdmin = closeAdmin;
	}

	public double getMinSharable() {
		return minSharable;
	}

	public void setMinSharable(double minSharable) {
		this.minSharable = minSharable;
	}

	public double getMonthlyAdmin() {
		return monthlyAdmin;
	}

	public void setMonthlyAdmin(double monthlyAdmin) {
		this.monthlyAdmin = monthlyAdmin;
	}

}
