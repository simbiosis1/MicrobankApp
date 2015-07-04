package org.simbiosis.microbank.model;

import java.io.Serializable;
import java.util.Date;

public class LoanRpt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2540783523195331954L;

	LoanRptPk id;
	long customer;
	long company;
	long branch;
	String branchCode;
	String branchName;
	String code;
	String name;
	String address;
	String city;
	String postCode;
	String phone;
	String handphone;
	long product;
	String productCode;
	String productName;
	double rate;
	double iRate;
	int type;
	int month;
	int year;
	int schema;
	String contract;
	Date contractDate;
	int tenor;
	double principal;
	double margin;
	double total;
	double paidPrincipal;
	double paidMargin;
	double paidDiscount;
	double paidTotal;
	double osPrincipal;
	double osMargin;
	double osTotal;
	int quality;
	int dueOs;
	Date lastPaid;
	double dueOsPrincipal;
	double dueOsMargin;
	double dueOsTotal;
	double outstanding;
	int guaranteeType;
	double guarantee;
	String guaranteeTypeName;
	String guaranteeDescription;
	double ppap;
	long saving;
	String savingCode;
	double savingBallance;
	Date registration;
	Date begin;
	Date end;
	String biCity;
	long ao;
	String aoName;
	double fine;
	double installment;
	Date paymentDueDate;
	double currentPayment;
	double monthlyPayment;

	public LoanRptPk getId() {
		return id;
	}

	public void setId(LoanRptPk id) {
		this.id = id;
	}

	public long getCustomer() {
		return customer;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
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

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
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

	public int getSchema() {
		return schema;
	}

	public void setSchema(int schema) {
		this.schema = schema;
	}

	public double getPrincipal() {
		return principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	public double getPaidPrincipal() {
		return paidPrincipal;
	}

	public void setPaidPrincipal(double paydPrincipal) {
		this.paidPrincipal = paydPrincipal;
	}

	public double getOsPrincipal() {
		return osPrincipal;
	}

	public void setOsPrincipal(double osPrincipal) {
		this.osPrincipal = osPrincipal;
	}

	public double getMargin() {
		return margin;
	}

	public void setMargin(double margin) {
		this.margin = margin;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getPaidMargin() {
		return paidMargin;
	}

	public void setPaidMargin(double paydMargin) {
		this.paidMargin = paydMargin;
	}

	public double getPaidTotal() {
		return paidTotal;
	}

	public void setPaidTotal(double paydTotal) {
		this.paidTotal = paydTotal;
	}

	public double getOsMargin() {
		return osMargin;
	}

	public void setOsMargin(double osMargin) {
		this.osMargin = osMargin;
	}

	public double getOsTotal() {
		return osTotal;
	}

	public void setOsTotal(double osTotal) {
		this.osTotal = osTotal;
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

	public String getBiCity() {
		return biCity;
	}

	public void setBiCity(String biCity) {
		this.biCity = biCity;
	}

	public int getTenor() {
		return tenor;
	}

	public void setTenor(int tenor) {
		this.tenor = tenor;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public double getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(double guarantee) {
		this.guarantee = guarantee;
	}

	public double getPpap() {
		return ppap;
	}

	public void setPpap(double ppap) {
		this.ppap = ppap;
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

	public double getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(double outstanding) {
		this.outstanding = outstanding;
	}

	public double getSavingBallance() {
		return savingBallance;
	}

	public void setSavingBallance(double savingBallance) {
		this.savingBallance = savingBallance;
	}

	public long getAo() {
		return ao;
	}

	public void setAo(long ao) {
		this.ao = ao;
	}

	public String getAoName() {
		return aoName;
	}

	public void setAoName(String aoName) {
		this.aoName = aoName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getGuaranteeType() {
		return guaranteeType;
	}

	public void setGuaranteeType(int guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	public String getSavingCode() {
		return savingCode;
	}

	public void setSavingCode(String savingCode) {
		this.savingCode = savingCode;
	}

	public double getFine() {
		return fine;
	}

	public void setFine(double fine) {
		this.fine = fine;
	}

	public double getInstallment() {
		return installment;
	}

	public void setInstallment(double installment) {
		this.installment = installment;
	}

	public double getPaidDiscount() {
		return paidDiscount;
	}

	public void setPaidDiscount(double paidDiscount) {
		this.paidDiscount = paidDiscount;
	}

	public long getSaving() {
		return saving;
	}

	public void setSaving(long saving) {
		this.saving = saving;
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

	public double getiRate() {
		return iRate;
	}

	public void setiRate(double iRate) {
		this.iRate = iRate;
	}

	public String getGuaranteeDescription() {
		return guaranteeDescription;
	}

	public void setGuaranteeDescription(String guaranteeDescription) {
		this.guaranteeDescription = guaranteeDescription;
	}

	public String getGuaranteeTypeName() {
		return guaranteeTypeName;
	}

	public void setGuaranteeTypeName(String guaranteeTypeName) {
		this.guaranteeTypeName = guaranteeTypeName;
	}

	public Date getPaymentDueDate() {
		return paymentDueDate;
	}

	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	public double getCurrentPayment() {
		return currentPayment;
	}

	public void setCurrentPayment(double currentPayment) {
		this.currentPayment = currentPayment;
	}

	public double getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(double monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

}
