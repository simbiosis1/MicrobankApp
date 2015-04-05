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
@Table(name = "MIB_DEPOSITBILYET")
@NamedQueries({
		@NamedQuery(name = "listBilyetByCompany", query = "select x from DepositBilyet x where x.company=:company"),
		@NamedQuery(name = "listBilyetByCompanyBranch", query = "select x from DepositBilyet x where x.company=:company and x.branch=:branch") })
public class DepositBilyet {
	@Id
	@Column(name = "BIL_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_depositbilyet")
	@TableGenerator(name = "gen_mib_depositbilyet", allocationSize = 1, pkColumnValue = "gen_mib_depositbilyet")
	long id;
	@Column(name = "BIL_CODE")
	String code;
	@Column(name = "BIL_AVAILABLE")
	int available;
	@Column(name = "BIL_COMPANY")
	long company;
	@Column(name = "BIL_BRANCH")
	long branch;

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

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
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

}
