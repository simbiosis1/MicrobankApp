package org.simbiosis.dashboard.reporting.model;

import java.io.Serializable;

public class NPFDv implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4766796818152401727L;

	double totalPrincipal;
	double totalPrincipalNett;
	double totalPrincipalGross;
	double totalNet;
	double totalGross;
	String productName;
	String column;

	public NPFDv() {
		totalPrincipal = 0;
		totalPrincipalNett = 0;
		totalPrincipalGross = 0;
	}

	public void addPrincipal(double principal) {
		totalPrincipal += principal;
	}

	public void addPrincipalNett(double principalNett) {
		totalPrincipalNett += principalNett;
	}

	public void addPrincipalGross(double principalGross) {
		totalPrincipalGross += principalGross;
	}

	public double getTotalPrincipal() {
		return totalPrincipal;
	}

	public void setTotalPrincipal(double totalPrincipal) {
		this.totalPrincipal = totalPrincipal;
	}

	public double getTotalPrincipalNett() {
		return totalPrincipalNett;
	}

	public void setTotalPrincipalNett(double totalPrincipalNett) {
		this.totalPrincipalNett = totalPrincipalNett;
	}

	public double getTotalPrincipalGross() {
		return totalPrincipalGross;
	}

	public void setTotalPrincipalGross(double totalPrincipalGross) {
		this.totalPrincipalGross = totalPrincipalGross;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public double getTotalNet() {
		return totalNet;
	}

	public void setTotalNet(double totalNet) {
		this.totalNet = totalNet;
	}

	public double getTotalGross() {
		return totalGross;
	}

	public void setTotalGross(double totalGross) {
		this.totalGross = totalGross;
	}

	public void calculate() {
		totalNet = totalPrincipalNett * 100 / totalPrincipal;
		totalGross = totalPrincipalGross * 100 / totalPrincipal;
	}
}
