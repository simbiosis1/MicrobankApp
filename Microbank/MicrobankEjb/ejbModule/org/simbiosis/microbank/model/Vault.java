package org.simbiosis.microbank.model;

import static javax.persistence.GenerationType.TABLE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "MIB_VAULT")
@NamedQueries({
		@NamedQuery(name = "listVaultByBranch", query = "select x from Vault x where x.company=:company and x.branch=:branch"),
		@NamedQuery(name = "getVaultValue", query = "select x from Vault x where x.company=:company and x.branch=:branch and x.value=:value and x.type=:type") })
public class Vault {
	@Id
	@Column(name = "VAU_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_vault")
	@TableGenerator(name = "gen_mib_vault", allocationSize = 1, pkColumnValue = "gen_mib_vault")
	long id;
	@Column(name = "VAU_COMPANY")
	long company;
	@Column(name = "VAU_BRANCH")
	long branch;
	@Column(name = "VAU_SUBBRANCH")
	long subBranch;
	@Column(name = "VAU_TYPE")
	int type;
	@Column(name = "VAU_VALUE")
	double value;
	@Column(name = "VAU_AMOUNT")
	double amount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public long getSubBranch() {
		return subBranch;
	}

	public void setSubBranch(long subBranch) {
		this.subBranch = subBranch;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
