package org.simbiosis.microbank;

import java.io.Serializable;
import java.util.Date;

public class DepositInformationDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2262223627498095129L;
	long id;
	long company;
	long branch;
	String strBranch;
	String code;
	long customer;
	String name;
	String city;
	long product;
	String productCode;
	String productName;
	Date registration;
	Date begin;
	Date end;
	double value;
	long saving;
	double sharing;
	long coa1;
	long coa2;
	long coa3;
	int zakat;

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

	public String getStrBranch() {
		return strBranch;
	}

	public void setStrBranch(String strBranch) {
		this.strBranch = strBranch;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getCustomer() {
		return customer;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getProduct() {
		return product;
	}

	public void setProduct(long product) {
		this.product = product;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public Date getRegistration() {
		return registration;
	}

	public void setRegistration(Date registration) {
		this.registration = registration;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public long getSaving() {
		return saving;
	}

	public void setSaving(long saving) {
		this.saving = saving;
	}

	public int getZakat() {
		return zakat;
	}

	public void setZakat(int zakat) {
		this.zakat = zakat;
	}
}
