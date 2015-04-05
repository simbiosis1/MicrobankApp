package org.simbiosis.ui.bprs.common.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LoanScheduleDv implements IsSerializable {
	Integer nr;
	Long id;
	Date date;
	Double principal;
	Double margin;
	Double total;
	Long loan;

	public LoanScheduleDv() {
		super();
		id = 0L;
		principal = 0D;
		margin = 0D;
		total = 0D;
		loan = 0L;
	}

	public LoanScheduleDv(Date date, double principal, double margin,
			double total) {
		id = 0L;
		this.date = date;
		this.principal = principal;
		this.margin = margin;
		this.total = total;
		loan = 0L;
	}

	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}

	public Double getMargin() {
		return margin;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Long getLoan() {
		return loan;
	}

	public void setLoan(Long loan) {
		this.loan = loan;
	}

}
