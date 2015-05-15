package org.simbiosis.ui.bprs.common.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CustomerDv implements IsSerializable {

	public enum SexTypeEnum {
		PRIA, WANITA;
		public static String valueToString(int value) {
			switch (value) {
			case 1:
				return "PRIA";
			case 2:
				return "WANITA";
			default:
				return "";
			}
		}
	};

	public enum IdTypeEnum {
		KTP, PASPOR, SIM, LAIN_LAIN;
		public static String valueToString(int value) {
			switch (value) {
			case 1:
				return "KTP";
			case 2:
				return "PASPOR";
			case 3:
				return "SIM";
			case 4:
			default:
				return "LAIN-LAIN";
			}
		}
	};

	Long id;
	Integer nr;
	String code;
	Date registration;
	String name;
	String title;
	Integer sex;
	String strSex;
	String pob;
	Date dob;
	Integer idType;
	String strIdType;
	String idCode;
	String motherName;
	String spouseName;
	String address;
	String city;
	String district;
	String village;
	String area1;
	String area2;
	String postCode;
	String province;
	String phone;
	String handphone;
	String occupation;
	String officeName;
	String officeAddress;
	String officeCity;
	String income;
	Boolean taxable;
	String strTaxable;
	String taxNr;
	String descendant;
	String descAddress;
	Integer type;
	Boolean bankRel;
	String strBankRel;

	public CustomerDv() {
		id = 0L;
		sex = 1;
		idType = 1;
		type = 1;
		taxable = true;
		bankRel = false;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getStrSex() {
		return strSex;
	}

	public void setStrSex(String strSex) {
		this.strSex = strSex;
	}

	public String getPob() {
		return pob;
	}

	public void setPob(String pob) {
		this.pob = pob;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
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

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
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

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getArea1() {
		return area1;
	}

	public void setArea1(String area1) {
		this.area1 = area1;
	}

	public String getArea2() {
		return area2;
	}

	public void setArea2(String area2) {
		this.area2 = area2;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getTaxNr() {
		return taxNr;
	}

	public void setTaxNr(String taxNr) {
		this.taxNr = taxNr;
	}

	public String getDescendant() {
		return descendant;
	}

	public void setDescendant(String descendant) {
		this.descendant = descendant;
	}

	public String getDescAddress() {
		return descAddress;
	}

	public void setDescAddress(String descAddress) {
		this.descAddress = descAddress;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Boolean getTaxable() {
		return taxable;
	}

	public void setTaxable(Boolean taxable) {
		this.taxable = taxable;
	}

	public String getStrTaxable() {
		return strTaxable;
	}

	public void setStrTaxable(String strTaxable) {
		this.strTaxable = strTaxable;
	}

	public Boolean getBankRel() {
		return bankRel;
	}

	public void setBankRel(Boolean bankRel) {
		this.bankRel = bankRel;
	}

	public String getStrBankRel() {
		return strBankRel;
	}

	public void setStrBankRel(String strBankRel) {
		this.strBankRel = strBankRel;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getOfficeCity() {
		return officeCity;
	}

	public void setOfficeCity(String officeCity) {
		this.officeCity = officeCity;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

}
