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
@Table(name = "MIB_TELLER")
@NamedQueries({
		@NamedQuery(name = "listTellerByCompany", query = "select x from Teller x where x.company=:company"),
		@NamedQuery(name = "listTellerByBranch", query = "select x from Teller x where x.branch=:branch"),
		@NamedQuery(name = "listTellerBySubBranch", query = "select x from Teller x where x.subBranch=:subBranch"),
		@NamedQuery(name = "getTellerByUser", query = "select x from Teller x where x.user=:user") })
public class Teller {
	@Id
	@Column(name = "TEL_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_teller")
	@TableGenerator(name = "gen_mib_teller", allocationSize = 1, pkColumnValue = "gen_mib_teller")
	long id;
	@Column(name = "TEL_CODE", length = 30)
	String code;
	@Column(name = "TEL_USER")
	long user;
	@Column(name = "TEL_COA")
	long coa;
	@Column(name = "TEL_COMPANY")
	long company;
	@Column(name = "TEL_BRANCH")
	long branch;
	@Column(name = "TEL_SUBBRANCH")
	long subBranch;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getUser() {
		return user;
	}

	public void setUser(long user) {
		this.user = user;
	}

	public long getCoa() {
		return coa;
	}

	public void setCoa(long coa) {
		this.coa = coa;
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

	public long getCompany() {
		return company;
	}

	public void setCompany(long company) {
		this.company = company;
	}
}
