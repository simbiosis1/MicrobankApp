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
@Table(name = "MIB_TELLERTRANS")
@NamedQueries({
		@NamedQuery(name = "getTellerBallance", query = "select x.direction, sum(x.value) from TellerTransaction x where x.teller.id=:teller and x.date=:date group by x.direction order by x.direction"),
		@NamedQuery(name = "getMaxTellerTransCode", query = "select max(x.code) from TellerTransaction x where x.company=:company and x.branch=:branch and x.code like :prefixCode"),
		@NamedQuery(name = "listTellerTransaction1", query = "select x from TellerTransaction x where x.company=:company and x.date>=:startDate and x.date<=:endDate order by x.date, x.timestamp"),
		@NamedQuery(name = "listTellerTransaction2", query = "select x from TellerTransaction x where x.branch=:branch and x.date>=:startDate and x.date<=:endDate order by x.date, x.timestamp"),
		@NamedQuery(name = "listTellerTransactionByTeller", query = "select x from TellerTransaction x where x.teller.id=:tellerId and x.date=:date order by x.date, x.timestamp"),
		@NamedQuery(name = "listTellerTransactionByCompany", query = "select x from TellerTransaction x where x.teller.company=:company and x.date=:date order by x.date, x.timestamp"),
		@NamedQuery(name = "getTellerTransactionByRefId", query = "select x from TellerTransaction x where x.company=:company and x.branch=:branch and x.refId=:refId") })
public class TellerTransaction {
	@Id
	@Column(name = "TTR_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_tellertrans")
	@TableGenerator(name = "gen_mib_tellertrans", allocationSize = 1, pkColumnValue = "gen_mib_tellertrans")
	long id;
	@Column(name = "TTR_REFID")
	long refId;
	@Column(name = "TTR_DATE")
	@Temporal(DATE)
	Date date;
	@Column(name = "TTR_TIMESTAMP")
	Date timestamp;
	@Column(name = "TTR_CODE", length = 30)
	String code;
	@Column(name = "TTR_REFCODE", length = 30)
	String refCode;
	@Column(name = "TTR_DESCRIPTION", length = 200)
	String description;
	@Column(name = "TTR_VALUE")
	double value;
	@Column(name = "TTR_DIRECTION")
	int direction;
	@Column(name = "TTR_TYPE")
	int type;
	@Column(name = "TTR_ACCOUNTID")
	long accountId;
	@Column(name = "TTR_TRANSID")
	long transId;
	@Column(name = "TTR_COMPANY")
	long company;
	@Column(name = "TTR_BRANCH")
	long branch;
	@Column(name = "TTR_SUBBRANCH")
	long subBranch;
	@ManyToOne
	@JoinColumn(name = "TEL_ID", referencedColumnName = "TEL_ID")
	Teller teller;

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

	public long getRefId() {
		return refId;
	}

	public void setRefId(long refId) {
		this.refId = refId;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getTransId() {
		return transId;
	}

	public void setTransId(long transId) {
		this.transId = transId;
	}

	public Teller getTeller() {
		return teller;
	}

	public void setTeller(Teller teller) {
		this.teller = teller;
	}

	public long getSubBranch() {
		return subBranch;
	}

	public void setSubBranch(long subBranch) {
		this.subBranch = subBranch;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
