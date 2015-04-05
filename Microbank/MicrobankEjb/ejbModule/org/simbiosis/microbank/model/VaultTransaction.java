package org.simbiosis.microbank.model;

import static javax.persistence.GenerationType.TABLE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;

@Entity
@Table(name = "MIB_VAULTTRANS")
@NamedQueries({ 
	@NamedQuery(name = "getMaxVaultTransCode", query = "select max(x.code) from VaultTransaction x where x.company=:company and x.branch=:branch and x.code like :prefixCode"),
	@NamedQuery(name = "getVaultTransByTeller", query = "select x from VaultTransaction x where x.status=0 and x.company=:company and x.branch=:branch and x.teller.id=:tellerId and x.date=:date") })
public class VaultTransaction {
	@Id
	@Column(name = "VAT_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_vaulttrans")
	@TableGenerator(name = "gen_mib_vaulttrans", allocationSize = 1, pkColumnValue = "gen_mib_vaulttrans")
	long id;
	@Column(name = "VAT_DATE")
	@Temporal(DATE)
	Date date;
	@Column(name = "VAT_CODE", length = 30)
	String code;
	@Column(name = "VAT_REFCODE", length = 30)
	String refCode;
	@Column(name = "VAT_VALUE")
	double value;
	@Column(name = "VAT_DIRECTION")
	int direction;
	@Column(name = "VAT_STATUS")
	int status;
	@Column(name = "VAT_COMPANY")
	long company;
	@Column(name = "VAT_BRANCH")
	long branch;
	@Column(name = "VAT_SUBBRANCH")
	long subBranch;

	@OneToMany(mappedBy = "vaultTransaction", cascade = ALL)
	List<VaultTransactionItem> items = new ArrayList<VaultTransactionItem>();

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public List<VaultTransactionItem> getItems() {
		return items;
	}

	public void setItems(List<VaultTransactionItem> items) {
		this.items = items;
	}

	public long getSubBranch() {
		return subBranch;
	}

	public void setSubBranch(long subBranch) {
		this.subBranch = subBranch;
	}

	public Teller getTeller() {
		return teller;
	}

	public void setTeller(Teller teller) {
		this.teller = teller;
	}

}
