package org.simbiosis.ui.bprs.common.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SavingDv implements IsSerializable {
	Long id;
	Integer nr;
	String code;
	Date registration;
	Long product;
	String strProduct;
	//
	Boolean newCustomer;
	CustomerDv customer = new CustomerDv();
	//
	String name;
	String strSex;
	String strIdType;
	String idCode;
	String address;
	String city;
	String postCode;
	String province;
	Boolean zakat;
	String strZakat;

	Double minValue;

	Date closing;
	String reason;

	public SavingDv() {
		super();
		id = 0L;
		nr=0;
		newCustomer = false;
		product = 0L;
		zakat = true;
		minValue = 0D;
	}

	public void copySavingData() {
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
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

	public Boolean getNewCustomer() {
		return newCustomer;
	}

	public void setNewCustomer(Boolean newCustomer) {
		this.newCustomer = newCustomer;
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

	public Boolean getZakat() {
		return zakat;
	}

	public void setZakat(Boolean zakat) {
		this.zakat = zakat;
	}

	public Date getClosing() {
		return closing;
	}

	public void setClosing(Date closing) {
		this.closing = closing;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStrZakat() {
		return strZakat;
	}

	public void setStrZakat(String strZakat) {
		this.strZakat = strZakat;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}
}
