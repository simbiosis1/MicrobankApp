package org.simbiosis.microbank.model;

import static javax.persistence.GenerationType.TABLE;
import static javax.persistence.TemporalType.DATE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

@Entity
@Table(name = "MIB_LOANTRANS")
@NamedQueries({
		@NamedQuery(name = "getLoanTransByRefId", query = "select x from LoanTransaction x where x.company=:company and x.branch=:branch and x.refId=:refId"),
		@NamedQuery(name = "getLoanTransByDateCode", query = "select x from LoanTransaction x where x.loan.id=:loanId and x.date=:date and x.code=:code"),
		@NamedQuery(name = "getLoanPayment", query = "select x.loan.id,x.loan.code,sum(x.principal),sum(x.margin),sum(x.discount) from LoanTransaction x where x.direction=2 and x.loan.id=:id and x.date<=:date group by x.loan.id,x.loan.code"),
		@NamedQuery(name = "listLastPayment", query = "select x from LoanTransaction x where x.direction=2 and x.loan.id=:loanId order by x.date desc"),
		@NamedQuery(name = "listLoanPayment1", query = "select x.loan.id,x.loan.code,sum(x.principal),sum(x.margin),sum(x.discount) from LoanTransaction x where x.direction=2 and (x.loan.active=1 or (x.loan.active=0 and x.loan.closing>:date)) and x.loan.company=:company and x.date<=:date group by x.loan.id,x.loan.code order by x.loan.code"),
		@NamedQuery(name = "listLoanPayment2", query = "select x.loan.id,x.loan.code,sum(x.principal),sum(x.margin),sum(x.discount) from LoanTransaction x where x.direction=2 and (x.loan.active=1 or (x.loan.active=0 and x.loan.closing>:date)) and x.loan.branch=:branch and x.date<=:date group by x.loan.id,x.loan.code order by x.loan.code"),
		@NamedQuery(name = "getMaxLoanTransCode", query = "select max(x.code) from LoanTransaction x where x.company=:company and x.branch=:branch and x.code like :prefixCode"),
		@NamedQuery(name = "listLoanTransactionUntil", query = "select x from LoanTransaction x where x.direction=2 and x.loan.id=:id and x.date<=:date order by x.date, x.id"),
		@NamedQuery(name = "listLoanTransaction", query = "select x from LoanTransaction x where x.direction=2 and x.loan.id=:id order by x.date, x.id"),
		@NamedQuery(name = "listLoanRepaymentByRange", query = "select x from LoanTransaction x where x.direction=2 and x.loan.id=:id and x.date>=:beginDate and x.date<=:endDate order by x.date, x.id"),
		@NamedQuery(name = "listAllLoanTransactionByRange1", query = "select x from LoanTransaction x where x.company=:company and x.direction=:direction and x.date>=:beginDate and x.date<=:endDate"),
		@NamedQuery(name = "listAllLoanTransactionByRange2", query = "select x from LoanTransaction x where x.branch=:branch and x.direction=:direction and x.date>=:beginDate and x.date<=:endDate"),
		@NamedQuery(name = "getSumLoanTransaction", query = "select x.loan.id, sum(x.principal),sum(x.margin) from LoanTransaction x where x.loan.id=:loanId and x.direction=:direction and x.date<=:date group by x.loan.id"),
		@NamedQuery(name = "listSumLoanTransGroup1", query = "select x.loan.product.id, sum(x.principal),sum(x.margin), sum(x.discount) from LoanTransaction x where x.company=:company and x.direction=:direction and x.date>=:beginDate and x.date<=:endDate group by x.loan.product.id"),
		@NamedQuery(name = "listSumLoanTransGroup2", query = "select x.loan.product.id, sum(x.principal),sum(x.margin), sum(x.discount) from LoanTransaction x where x.branch=:branch and x.direction=:direction and x.date>=:beginDate and x.date<=:endDate group by x.loan.product.id"),
		@NamedQuery(name = "listLoanRevenueByProduct", query = "select x.loan.product.id,x.loan.product.code,x.loan.product.name,sum(x.margin) from LoanTransaction x where x.loan.product.profitShared=1 and x.direction=2 and x.company=:company and x.date>=:beginDate and x.date<=:endDate group by x.loan.product.id,x.loan.product.name,x.loan.product.code order by x.loan.product.code") })
public class LoanTransaction {
	@Id
	@Column(name = "LTR_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_loantrans")
	@TableGenerator(name = "gen_mib_loantrans", allocationSize = 1, pkColumnValue = "gen_mib_loantrans")
	long id;
	@Column(name = "LTR_REFID")
	long refId;
	@Column(name = "LTR_DATE")
	@Temporal(DATE)
	Date date;
	@Column(name = "LTR_CODE", length = 30)
	String code;
	@Column(name = "LTR_REFCODE", length = 30)
	String refCode;
	@Column(name = "LTR_DESCRIPTION", length = 200)
	String description;
	@Column(name = "LTR_PRINCIPAL")
	double principal;
	@Column(name = "LTR_MARGIN")
	double margin;
	@Column(name = "LTR_DISCOUNT")
	double discount;
	@Column(name = "LTR_DIRECTION")
	int direction;
	@Column(name = "LTR_TYPE")
	int type;
	@Column(name = "LTR_COMPANY")
	long company;
	@Column(name = "LTR_BRANCH")
	long branch;

	@ManyToOne
	@JoinColumn(name = "LOA_ID", referencedColumnName = "LOA_ID")
	Loan loan;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRefId() {
		return refId;
	}

	public void setRefId(long refId) {
		this.refId = refId;
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

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

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

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
}
