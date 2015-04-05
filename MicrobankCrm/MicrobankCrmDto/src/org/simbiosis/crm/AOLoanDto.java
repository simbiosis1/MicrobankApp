package org.simbiosis.crm;

import java.io.Serializable;

public class AOLoanDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3338275819304829746L;
	long company;
	long branch;
	long ao;
	long loan;
	String aoName;
	String loanCode;
	String loanName;

	public long getCompany() {
		return company;
	}

	public void setCompany(long company) {
		this.company = company;
	}

	public long getBranch() {
		return branch;
	}

	public void setBranch(long branch) {
		this.branch = branch;
	}

	public long getAo() {
		return ao;
	}

	public void setAo(long ao) {
		this.ao = ao;
	}

	public long getLoan() {
		return loan;
	}

	public void setLoan(long loan) {
		this.loan = loan;
	}

	public String getAoName() {
		return aoName;
	}

	public void setAoName(String aoName) {
		this.aoName = aoName;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

}
