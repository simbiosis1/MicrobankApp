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
@Table(name = "MIB_SAVINGTRANS")
@NamedQueries({
		@NamedQuery(name = "listSavingBallance1", query = "select x.saving.id, x.saving.code, x.saving.customer.name, x.saving.customer.city, x.saving.product.id, x.direction, sum(x.value) from SavingTransaction x where (x.saving.active=1 or (x.saving.active=0 and x.saving.closing>:date)) and x.saving.registration<=:date and x.saving.company=:company and x.date<=:date group by x.saving.id, x.saving.code, x.saving.customer.name, x.saving.customer.city, x.saving.product.id, x.direction order by x.saving.id, x.direction"),
		@NamedQuery(name = "listSavingBallance2", query = "select x.saving.id, x.saving.code, x.saving.customer.name, x.saving.customer.city, x.saving.product.id, x.direction, sum(x.value) from SavingTransaction x where (x.saving.active=1 or (x.saving.active=0 and x.saving.closing>:date)) and x.saving.registration<=:date and x.saving.company=:company and x.saving.branch=:branch and x.date<=:date group by x.saving.id, x.saving.code, x.saving.customer.name, x.saving.customer.city, x.saving.product.id, x.direction order by x.saving.id, x.direction"),
		@NamedQuery(name = "getTotalSavingTransBeforeDate", query = "select x.direction, sum(x.value) from SavingTransaction x where x.saving.id=:savingId and x.date<:date group by x.direction order by x.direction"),
		@NamedQuery(name = "getTotalSavingTransBeforeTransId", query = "select x.direction, sum(x.value) from SavingTransaction x where x.saving.id=:savingId and x.id<=:transId group by x.direction order by x.direction"),
		@NamedQuery(name = "getTotalSavingTransUntil", query = "select x.direction,sum(x.value) from SavingTransaction x where x.saving.id=:savingId and x.date<=:date group by x.direction order by x.direction"),
		@NamedQuery(name = "getTotalSavingTransDay", query = "select sum(x.value) from SavingTransaction x where x.saving.id=:savingId and x.direction=:direction and x.date=:date"),
		@NamedQuery(name = "listSavingIdTransacting", query = "select x.saving.id from SavingTransaction x where x.company=:company and x.date=:date"),
		@NamedQuery(name = "getMaxSavingTransCode", query = "select max(x.code) from SavingTransaction x where x.company=:company and x.branch=:branch and x.code like :prefixCode"),
		@NamedQuery(name = "listSavingTransUntil", query = "select x from SavingTransaction x where x.saving.id=:id and x.date<=:date order by x.date, x.timestamp, x.direction"),
		@NamedQuery(name = "listSavingTransWithoutNuc", query = "select x from SavingTransaction x where x.saving.id=:id and x.id>:transId and x.nuc=0 order by x.date, x.timestamp, x.direction"),
		@NamedQuery(name = "listSavingTransFrom", query = "select x from SavingTransaction x where x.saving.id=:id and x.date>=:date order by x.date, x.timestamp, x.direction"),
		@NamedQuery(name = "listSavingTransRange", query = "select x from SavingTransaction x where x.saving.id=:id and x.date>=:beginDate and x.date<=:endDate order by x.date, x.timestamp, x.direction"),
		@NamedQuery(name = "listSavingTrans", query = "select x from SavingTransaction x where x.company=:company and x.date>=:beginDate and x.date<=:endDate"),
		@NamedQuery(name = "getSavingTransByRefId", query = "select x from SavingTransaction x where x.refId=:refId and x.company=:company and x.branch=:branch")})
public class SavingTransaction {
	@Id
	@Column(name = "STR_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_savingtrans")
	@TableGenerator(name = "gen_mib_savingtrans", allocationSize = 1, pkColumnValue = "gen_mib_savingtrans")
	long id;
	@Column(name = "STR_REFID")
	long refId;
	@Column(name = "STR_DATE")
	@Temporal(DATE)
	Date date;
	@Column(name = "STR_TIMESTAMP")
	Date timestamp;
	@Column(name = "STR_CODE", length = 30)
	String code;
	@Column(name = "STR_REFCODE", length = 30)
	String refCode;
	@Column(name = "STR_DESCRIPTION", length = 200)
	String description;
	@Column(name = "STR_VALUE")
	double value;
	@Column(name = "STR_DIRECTION")
	int direction;
	@Column(name = "STR_TYPE")
	int type;
	@Column(name = "STR_COMPANY")
	long company;
	@Column(name = "STR_BRANCH")
	long branch;
	@Column(name = "STR_NUC")
	int nuc;

	@ManyToOne
	@JoinColumn(name = "SAV_ID", referencedColumnName = "SAV_ID")
	Saving saving;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Saving getSaving() {
		return saving;
	}

	public void setSaving(Saving saving) {
		this.saving = saving;
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getNuc() {
		return nuc;
	}

	public void setNuc(int nuc) {
		this.nuc = nuc;
	}
}
