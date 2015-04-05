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
@Table(name = "MIB_DEPOSITTRANS")
@NamedQueries({
		@NamedQuery(name = "getMaxDepositTransCode", query = "select max(x.code) from DepositTransaction x where x.company=:company and x.branch=:branch and x.code like :prefixCode"),
		@NamedQuery(name = "listDepositTransaction", query = "select x from DepositTransaction x where x.deposit.id=:id and x.date<=:date order by x.date, x.id") })
public class DepositTransaction {
	@Id
	@Column(name = "DTR_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_deposittrans")
	@TableGenerator(name = "gen_mib_deposittrans", allocationSize = 1, pkColumnValue = "gen_mib_deposittrans")
	long id;
	@Column(name = "DTR_REFID")
	long refId;
	@Column(name = "DTR_DATE")
	@Temporal(DATE)
	Date date;
	@Column(name = "DTR_CODE", length = 30)
	String code;
	@Column(name = "DTR_REFCODE", length = 30)
	String refCode;
	@Column(name = "DTR_DESCRIPTION", length = 200)
	String description;
	@Column(name = "DTR_VALUE")
	double value;
	@Column(name = "DTR_DIRECTION")
	int direction;
	@Column(name = "DTR_COMPANY")
	long company;
	@Column(name = "DTR_BRANCH")
	long branch;

	@ManyToOne
	@JoinColumn(name = "DEP_ID", referencedColumnName = "DEP_ID")
	Deposit deposit;

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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
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

	public Deposit getDeposit() {
		return deposit;
	}

	public void setDeposit(Deposit deposit) {
		this.deposit = deposit;
	}
}
