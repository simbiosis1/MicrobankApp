package org.simbiosis.ui.bprs.teller.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class VaultTransactionDv implements IsSerializable {
	Long id;
	String code;
	String refCode;
	Date date;
	String strDate;
	Integer type;
	String strType;
	Double value;
	String strValue;
	Integer direction;
	String validationText;
	String maker;
	Long teller;
	String strTeller;

	String str50L;
	String str100L;
	String str200L;
	String str500L;
	String str1000L;

	String str1000K;
	String str2000K;
	String str5000K;
	String str10000K;
	String str20000K;
	String str50000K;
	String str100000K;

	String control;

	public VaultTransactionDv() {
		id = 0L;
		type = 0;
		value = 0D;
		direction = 0;
		teller = 0L;
		//
		str50L = "0";
		str100L = "0";
		str200L = "0";
		str500L = "0";
		str1000L = "0";

		str1000K = "0";
		str2000K = "0";
		str5000K = "0";
		str10000K = "0";
		str20000K = "0";
		str50000K = "0";
		str100000K = "0";
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

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStrDate() {
		return strDate;
	}

	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
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

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public String getValidationText() {
		return validationText;
	}

	public void setValidationText(String validationText) {
		this.validationText = validationText;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public long getTeller() {
		return teller;
	}

	public void setTeller(long teller) {
		this.teller = teller;
	}

	public String getStrTeller() {
		return strTeller;
	}

	public void setStrTeller(String strTeller) {
		this.strTeller = strTeller;
	}

	public String getStr50L() {
		return str50L;
	}

	public void setStr50L(String str50l) {
		str50L = str50l;
	}

	public String getStr100L() {
		return str100L;
	}

	public void setStr100L(String str100l) {
		str100L = str100l;
	}

	public String getStr500L() {
		return str500L;
	}

	public void setStr500L(String str500l) {
		str500L = str500l;
	}

	public String getStr1000L() {
		return str1000L;
	}

	public void setStr1000L(String str1000l) {
		str1000L = str1000l;
	}

	public String getStr1000K() {
		return str1000K;
	}

	public void setStr1000K(String str1000k) {
		str1000K = str1000k;
	}

	public String getStr2000K() {
		return str2000K;
	}

	public void setStr2000K(String str2000k) {
		str2000K = str2000k;
	}

	public String getStr5000K() {
		return str5000K;
	}

	public void setStr5000K(String str5000k) {
		str5000K = str5000k;
	}

	public String getStr10000K() {
		return str10000K;
	}

	public void setStr10000K(String str10000k) {
		str10000K = str10000k;
	}

	public String getStr20000K() {
		return str20000K;
	}

	public void setStr20000K(String str20000k) {
		str20000K = str20000k;
	}

	public String getStr50000K() {
		return str50000K;
	}

	public void setStr50000K(String str50000k) {
		str50000K = str50000k;
	}

	public String getStr100000K() {
		return str100000K;
	}

	public void setStr100000K(String str100000k) {
		str100000K = str100000k;
	}

	public String getStr200L() {
		return str200L;
	}

	public void setStr200L(String str200l) {
		str200L = str200l;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setTeller(Long teller) {
		this.teller = teller;
	}

	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}
}
