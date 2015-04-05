package org.simbiosis.dashboard.reporting.model;

import java.io.Serializable;
import java.util.Date;

public class LoanDroppingDv implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5587252536677460594L;
	String product;
	String month;
	double dropping;
	double margin;
	Date date;
	String code;
	String name;
	double discount;
	double total;
	double angsuran;
	

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getDropping() {
		return dropping;
	}

	public void setDropping(double dropping) {
		this.dropping = dropping;
	}

	public double getMargin() {
		return margin;
	}

	public void setMargin(double margin) {
		this.margin = margin;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getAngsuran() {
		return angsuran;
	}

	public void setAngsuran(double angsuran) {
		this.angsuran = angsuran;
	}
}
