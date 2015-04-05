package org.simbiosis.microbank;

import java.io.Serializable;
import java.util.Date;

public class LoanQualityDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1805901743603991608L;
	long id;
	int value;
	int dueOs;
	Date lastPaid;
	double osPrincipal;
	double osMargin;
	int duration;

	public LoanQualityDto() {
		id = 0;
		value = 1;
	}

	public LoanQualityDto(long id) {
		this.id = id;
		value = 1;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getDueOs() {
		return dueOs;
	}

	public void setDueOs(int dueOs) {
		this.dueOs = dueOs;
	}

	public Date getLastPaid() {
		return lastPaid;
	}

	public void setLastPaid(Date lastPaid) {
		this.lastPaid = lastPaid;
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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
