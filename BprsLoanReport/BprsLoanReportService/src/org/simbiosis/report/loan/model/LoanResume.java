package org.simbiosis.report.loan.model;

public class LoanResume {
	long product;
	String productName;
	double principal;
	double margin;
	double total;
	double osPrincipal;
	double osMargin;
	double osTotal;
	double ppap;
	String link;

	public long getProduct() {
		return product;
	}

	public void setProduct(long product) {
		this.product = product;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrincipal() {
		return principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
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

	public double getOsPrincipal() {
		return osPrincipal;
	}

	public void setOsPrincipal(double osPrincipal) {
		this.osPrincipal = osPrincipal;
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

	public double getPpap() {
		return ppap;
	}

	public void setPpap(double ppap) {
		this.ppap = ppap;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
