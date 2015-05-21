package org.simbiosis.ui.bprs.common.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LoanDv implements IsSerializable {
	Long id;
	Integer nr;
	String code;
	Date registration;
	CustomerDv customer = new CustomerDv();
	Long product;
	String strProduct;
	Double principal;
	Double margin;
	Double rate;
	Integer tenor;
	String contract;
	Date contractDate;
	boolean dropped;
	int scheduleType;
	String strScheduleType;
	Long ao;
	String strAo;
	String aoHistory;
	Double admin;
	Double fine;
	String purpose;
	String biSektor;
	List<LoanScheduleDv> schedules = new ArrayList<LoanScheduleDv>();
	List<GuaranteeDv> guarantees = new ArrayList<GuaranteeDv>();

	SavingDv saving;

	String name;
	String strSex;
	String strIdType;
	String idCode;
	String address;
	String city;
	String postCode;
	String province;

	public LoanDv() {
		super();
		id = 0L;
		product = 0L;
		principal = 0D;
		rate = 0D;
		tenor = 0;
		ao = 0L;
		scheduleType = 1;
		admin = 0D;
		fine = 0D;
	}

	public void copyLoanData() {
		name = customer.getName();
		strSex = customer.getStrSex();
		strIdType = customer.getStrIdType();
		idCode = customer.getIdCode();
		address = customer.getAddress();
		city = customer.getCity();
		postCode = customer.getPostCode();
		province = customer.getProvince();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getRegistration() {
		return registration;
	}

	public void setRegistration(Date registration) {
		this.registration = registration;
	}

	public CustomerDv getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDv customer) {
		this.customer = customer;
	}

	public Long getProduct() {
		return product;
	}

	public void setProduct(Long product) {
		this.product = product;
	}

	public String getStrProduct() {
		return strProduct;
	}

	public void setStrProduct(String strProduct) {
		this.strProduct = strProduct;
	}

	public List<LoanScheduleDv> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<LoanScheduleDv> schedules) {
		this.schedules = schedules;
	}

	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStrSex() {
		return strSex;
	}

	public void setStrSex(String strSex) {
		this.strSex = strSex;
	}

	public String getStrIdType() {
		return strIdType;
	}

	public void setStrIdType(String strIdType) {
		this.strIdType = strIdType;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public SavingDv getSaving() {
		return saving;
	}

	public void setSaving(SavingDv saving) {
		this.saving = saving;
	}

	public boolean isDropped() {
		return dropped;
	}

	public void setDropped(boolean dropped) {
		this.dropped = dropped;
	}

	public Double getMargin() {
		return margin;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	public List<GuaranteeDv> getGuarantees() {
		return guarantees;
	}

	public void setGuarantees(List<GuaranteeDv> guarantees) {
		this.guarantees = guarantees;
	}

	public int getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(int scheduleType) {
		this.scheduleType = scheduleType;
	}

	public String getStrScheduleType() {
		return strScheduleType;
	}

	public void setStrScheduleType(String strScheduleType) {
		this.strScheduleType = strScheduleType;
	}

	public Long getAo() {
		return ao;
	}

	public void setAo(Long ao) {
		this.ao = ao;
	}

	public String getStrAo() {
		return strAo;
	}

	public void setStrAo(String strAo) {
		this.strAo = strAo;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getBiSektor() {
		return biSektor;
	}

	public void setBiSektor(String biSektor) {
		this.biSektor = biSektor;
	}

	public String getAoHistory() {
		return aoHistory;
	}

	public void setAoHistory(String aoHistory) {
		this.aoHistory = aoHistory;
	}

	public Double getAdmin() {
		return admin;
	}

	public void setAdmin(Double admin) {
		this.admin = admin;
	}

	public Double getFine() {
		return fine;
	}

	public void setFine(Double fine) {
		this.fine = fine;
	}

	public Integer getTenor() {
		return tenor;
	}

	public void setTenor(Integer tenor) {
		this.tenor = tenor;
	}
}
