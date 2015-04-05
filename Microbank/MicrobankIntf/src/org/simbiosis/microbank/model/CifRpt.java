package org.simbiosis.microbank.model;

import java.io.Serializable;

public class CifRpt implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6262726704542275624L;
	long id;
	long refId;
	String code;
	String name;
	int idType;
	String idCode;
	String taxNr;
	String biCity;

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

	public int getIdType() {
		return idType;
	}

	public void setIdType(int idType) {
		this.idType = idType;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getTaxNr() {
		return taxNr;
	}

	public void setTaxNr(String taxNr) {
		this.taxNr = taxNr;
	}

	public String getBiCity() {
		return biCity;
	}

	public void setBiCity(String biCity) {
		this.biCity = biCity;
	}

}
