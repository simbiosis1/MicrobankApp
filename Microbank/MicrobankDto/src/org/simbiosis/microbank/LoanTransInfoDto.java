package org.simbiosis.microbank;

import java.io.Serializable;
import java.util.Date;

public class LoanTransInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1678375835192526409L;
	long id;
	long customer;
	long company;
	long branch;
	long product;
	String productCode;
	String productName;
	int schema;
	String code;
	String name;
	String address;
	String city;
	String postCode;
	String phone;
	String handphone;
	String contract;
	Date contractDate;
	Date begin;
	Date end;
	double principal;
	double margin;
	double paidPrincipal;
	double paidMargin;
	double paidDiscount;
	double osPrincipal;
	double osMargin;
	int quality;
	int dueOs;
	Date lastPaid;
	double dueOsPrincipal;
	double dueOsMargin;
	double dueOsTotal;

	// long saving;
	// long ao;
	// String aoName;
	// double fine;

	public LoanTransInfoDto() {
		id = 0;
		principal = 0;
		margin = 0;
		paidPrincipal = 0;
		paidMargin = 0;
		paidDiscount = 0;
		osPrincipal = 0;
		osMargin = 0;
		quality = 1;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHandphone() {
		return handphone;
	}

	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public long getCustomer() {
		return customer;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
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

	public int getSchema() {
		return schema;
	}

	public void setSchema(int schema) {
		this.schema = schema;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public double getPrincipal() {
		return principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	public double getMargin() {
		return margin;
	}

	public void setMargin(double margin) {
		this.margin = margin;
	}

	public double getPaidPrincipal() {
		return paidPrincipal;
	}

	public void setPaidPrincipal(double paidPrincipal) {
		this.paidPrincipal = paidPrincipal;
	}

	public double getPaidMargin() {
		return paidMargin;
	}

	public void setPaidMargin(double paidMargin) {
		this.paidMargin = paidMargin;
	}

	public double getPaidDiscount() {
		return paidDiscount;
	}

	public void setPaidDiscount(double paidDiscount) {
		this.paidDiscount = paidDiscount;
	}

	public double getOsPrincipal() {
		return osPrincipal;
	}

	public void setOsPrincipal(double osPrincipal) {
		this.osPrincipal = osPrincipal;
	}

	public double getOsMargin() {
		return osMargin;
	}

	public void setOsMargin(double osMargin) {
		this.osMargin = osMargin;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public int getDueOs() {
		return dueOs;
	}

	public void setDueOs(int dueOs) {
		this.dueOs = dueOs;
	}

	public Date getLastPaid() {
		return lastPaid;
	}

	public void setLastPaid(Date lastPaid) {
		this.lastPaid = lastPaid;
	}

	public double getDueOsPrincipal() {
		return dueOsPrincipal;
	}

	public void setDueOsPrincipal(double dueOsPrincipal) {
		this.dueOsPrincipal = dueOsPrincipal;
	}

	public double getDueOsMargin() {
		return dueOsMargin;
	}

	public void setDueOsMargin(double dueOsMargin) {
		this.dueOsMargin = dueOsMargin;
	}

	public double getDueOsTotal() {
		return dueOsTotal;
	}

	public void setDueOsTotal(double dueOsTotal) {
		this.dueOsTotal = dueOsTotal;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

}
