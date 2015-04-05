package org.simbiosis.ui.bprs.common.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DepositDv implements IsSerializable {
	Long id;
	Integer nr;
	String code;
	String bilyet;
	Date registration;
	String strRegistration;
	CustomerDv customer = new CustomerDv();
	Long product;
	String strProduct;
	Double rate;
	String strRate;
	Double specialRate;
	String strSpecialRate;
	Boolean aro;
	String strAro;
	Boolean zakat;
	String strZakat;
	Double value;
	String strValue;

	SavingDv saving;

	String name;
	String strSex;
	String strIdType;
	String idCode;
	String address;
	String city;
	String postCode;
	String province;

	public DepositDv() {
		id = 0L;
		product = 0L;
		aro = true;
		specialRate = 0D;
		strSpecialRate = "0";
	}

	public void copyDepositData() {
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

	public String getBilyet() {
		return bilyet;
	}

	public void setBilyet(String bilyet) {
		this.bilyet = bilyet;
	}

	public Date getRegistration() {
		return registration;
	}

	public void setRegistration(Date registration) {
		this.registration = registration;
	}

	public String getStrRegistration() {
		return strRegistration;
	}

	public void setStrRegistration(String strRegistration) {
		this.strRegistration = strRegistration;
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

	public Boolean getAro() {
		return aro;
	}

	public void setAro(Boolean aro) {
		this.aro = aro;
	}

	public String getStrAro() {
		return strAro;
	}

	public void setStrAro(String strAro) {
		this.strAro = strAro;
	}

	public SavingDv getSaving() {
		return saving;
	}

	public void setSaving(SavingDv saving) {
		this.saving = saving;
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

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public Boolean getZakat() {
		return zakat;
	}

	public void setZakat(Boolean zakat) {
		this.zakat = zakat;
	}

	public String getStrZakat() {
		return strZakat;
	}

	public void setStrZakat(String strZakat) {
		this.strZakat = strZakat;
	}

	public Double getSpecialRate() {
		return specialRate;
	}

	public void setSpecialRate(Double specialRate) {
		this.specialRate = specialRate;
	}

	public String getStrSpecialRate() {
		return strSpecialRate;
	}

	public void setStrSpecialRate(String strSpecialRate) {
		this.strSpecialRate = strSpecialRate;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getStrRate() {
		return strRate;
	}

	public void setStrRate(String strRate) {
		this.strRate = strRate;
	}

}
