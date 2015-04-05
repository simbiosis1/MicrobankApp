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
@Table(name = "MIB_LOANSCHEDULE")
@NamedQueries({
		@NamedQuery(name = "payLoanScheduleBulk", query = "update LoanSchedule x set x.paid=1 where x.loan.id=:idLoan and x.id<=:maxSched"),
		@NamedQuery(name = "resetLoanSchedule", query = "update LoanSchedule x set x.paid=0 where x.loan in (select y from Loan y where y.company=:company)"),
		@NamedQuery(name = "listLoanScheduleNotPaidUntil", query = "select x from LoanSchedule x where x.loan.id=:id and x.date<=:date order by x.date"),
		@NamedQuery(name = "listLoanScheduleUntil", query = "select x from LoanSchedule x where x.loan.id=:id and x.date<=:date order by x.date"),
		@NamedQuery(name = "minLoanSchedule", query = "select min(x.date) from LoanSchedule x where x.loan.id=:id"),
		@NamedQuery(name = "maxLoanSchedule", query = "select max(x.date) from LoanSchedule x where x.loan.id=:id"),
		@NamedQuery(name = "listLoanSchedule", query = "select x from LoanSchedule x where x.loan.id=:id order by x.date"),
		@NamedQuery(name = "listLoanBill", query = "select x from LoanSchedule x where x.loan.company=:company and x.loan.active=1 and x.paid<>1 and x.date<=:date order by x.loan.code, x.date"),
		@NamedQuery(name = "listLoanScheduleNotPaid", query = "select x from LoanSchedule x where x.loan.id=:id and x.paid<>1 and x.date<=:date order by x.date"),
		@NamedQuery(name = "listLoanScheduleNotPaid2", query = "select x from LoanSchedule x where x.loan.id=:id and x.paid<>1 order by x.date"),
		@NamedQuery(name = "deleteLoanSchedules", query = "delete from LoanSchedule x where x.loan.id=:loanId"),
		@NamedQuery(name = "listRepaymentByRange", query = "select x from LoanSchedule x where x.loan.id=:loanId and x.paid=0 and x.date<=:endDate"),
		@NamedQuery(name = "listLoanSchedulesByRange", query = "select x from LoanSchedule x where x.loan.active=1 and x.loan.company=:company and x.date>=:beginDate and x.date<=:endDate") })
public class LoanSchedule {
	@Id
	@Column(name = "LSC_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_loanschedule")
	@TableGenerator(name = "gen_mib_loanschedule", allocationSize = 1, pkColumnValue = "gen_mib_loanschedule")
	long id;
	@Column(name = "LSC_DATE")
	@Temporal(DATE)
	Date date;
	@Column(name = "LSC_PRINCIPAL")
	double principal;
	@Column(name = "LSC_MARGIN")
	double margin;
	@Column(name = "LSC_TOTAL")
	double total;
	@Column(name = "LSC_PAID")
	int paid;

	@ManyToOne
	@JoinColumn(name = "LOA_ID", referencedColumnName = "LOA_ID")
	Loan loan;

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

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public int getPaid() {
		return paid;
	}

	public void setPaid(int paid) {
		this.paid = paid;
	}
}
