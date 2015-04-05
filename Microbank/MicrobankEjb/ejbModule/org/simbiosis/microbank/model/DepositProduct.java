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
@Table(name = "MIB_DEPOSITPRODUCT")
@NamedQueries({
		@NamedQuery(name = "listDepositProduct", query = "select x from DepositProduct x where x.company=:company"),
		@NamedQuery(name = "getDepositProductByRefId", query = "select x from DepositProduct x where x.company=:company and x.refId=:refId") })
public class DepositProduct {
	@Id
	@Column(name = "DPR_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_depositproduct")
	@TableGenerator(name = "gen_mib_depositproduct", allocationSize = 1, pkColumnValue = "gen_mib_depositproduct")
	long id;
	@Column(name = "DPR_REFID")
	long refId;
	@Column(name = "DPR_CODE", length = 30)
	String code;
	@Column(name = "DPR_NAME", length = 50)
	String name;
	@Column(name = "DPR_TERM")
	int term;
	@Column(name = "DPR_SHARING")
	double sharing;
	@Column(name = "DPR_COMPANY")
	long company;
	@Column(name = "DPR_ACTIVE")
	int active;
	@Column(name = "DPR_COA1")
	long coa1;
	@Column(name = "DPR_COA2")
	long coa2;
	@Column(name = "DPR_COA3")
	long coa3;

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

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSharing() {
		return sharing;
	}

	public void setSharing(double sharing) {
		this.sharing = sharing;
	}

	public long getCompany() {
		return company;
	}

	public void setCompany(long company) {
		this.company = company;
	}

	public long getCoa1() {
		return coa1;
	}

	public void setCoa1(long coa1) {
		this.coa1 = coa1;
	}

	public long getCoa2() {
		return coa2;
	}

	public void setCoa2(long coa2) {
		this.coa2 = coa2;
	}

	public long getCoa3() {
		return coa3;
	}

	public void setCoa3(long coa3) {
		this.coa3 = coa3;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}
}
