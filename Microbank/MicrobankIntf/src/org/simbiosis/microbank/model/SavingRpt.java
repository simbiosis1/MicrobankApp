package org.simbiosis.microbank.model;

import java.io.Serializable;
import java.util.Date;

public class SavingRpt implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4744165332412505033L;
	SavingRptPk id;
	//Date pos;
	//long refId;
	long company;
	long branch;
	String branchCode;
	String branchName;
	String code;
	String name;
	long product;
	String productCode;
	String productName;
	int schema;
	double rate;
	double iRate;
	int type;
	int month;
	int year;
	Date begin;
	Date end;
	double valPrev;
	double valAfter;
	double transDebet;
	double transCredit;
	String biCity;
	String city;

	public SavingRptPk getId() {
		return id;
	}

	public void setId(SavingRptPk id) {
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

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
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

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getiRate() {
		return iRate;
	}

	public void setiRate(double iRate) {
		this.iRate = iRate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public double getValPrev() {
		return valPrev;
	}

	public void setValPrev(double valPrev) {
		this.valPrev = valPrev;
	}

	public double getValAfter() {
		return valAfter;
	}

	public void setValAfter(double valAfter) {
		this.valAfter = valAfter;
	}

	public double getTransDebet() {
		return transDebet;
	}

	public void setTransDebet(double transDebet) {
		this.transDebet = transDebet;
	}

	public double getTransCredit() {
		return transCredit;
	}

	public void setTransCredit(double transCredit) {
		this.transCredit = transCredit;
	}

	public String getBiCity() {
		return biCity;
	}

	public void setBiCity(String biCity) {
		this.biCity = biCity;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
