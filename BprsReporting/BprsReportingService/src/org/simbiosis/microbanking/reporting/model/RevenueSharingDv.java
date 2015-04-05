package org.simbiosis.microbanking.reporting.model;

public class RevenueSharingDv {
	int nr;
	String product;
	String code;
	String name;
	int type;
	Double tax;
	Double avgBallance;
	Double revValue;
	Double equivalent;

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Double getAvgBallance() {
		return avgBallance;
	}

	public void setAvgBallance(Double avgBallance) {
		this.avgBallance = avgBallance;
	}

	public Double getRevValue() {
		return revValue;
	}

	public void setRevValue(Double revValue) {
		this.revValue = revValue;
	}

	public Double getEquivalent() {
		return equivalent;
	}

	public void setEquivalent(Double equivalent) {
		this.equivalent = equivalent;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

}
