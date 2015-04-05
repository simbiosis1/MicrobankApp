package org.simbiosis.microbank;

import java.io.Serializable;
import java.util.Date;

public class LoanScheduleDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6561864986933386942L;
	long id;
	Date date;
	double principal;
	double margin;
	double total;
	long loan;

	public LoanScheduleDto() {
		id = 0;
	}

	public LoanScheduleDto(Date date, double principal, double margin,
			double total) {
		super();
		id = 0;
		this.date = date;
		this.principal = principal;
		this.margin = margin;
		this.total = total;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public long getLoan() {
		return loan;
	}

	public void setLoan(long loan) {
		this.loan = loan;
	}
}
