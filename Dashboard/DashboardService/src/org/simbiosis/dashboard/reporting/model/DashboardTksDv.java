package org.simbiosis.dashboard.reporting.model;

import java.io.Serializable;

public class DashboardTksDv implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3172102562823097808L;
	String month;
	String cabang;
	String item;
	Double itemValue;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getCabang() {
		return cabang;
	}

	public void setCabang(String cabang) {
		this.cabang = cabang;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Double getItemValue() {
		return itemValue;
	}

	public void setItemValue(Double itemValue) {
		this.itemValue = itemValue;
	}

}
