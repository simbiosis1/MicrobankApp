package org.simbiosis.microbanking.reporting.model;

import java.io.Serializable;

public class SummaryDv implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4928566673041310960L;

	int nr;
	long productId;
	double value;
	String product;
	String link;

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
