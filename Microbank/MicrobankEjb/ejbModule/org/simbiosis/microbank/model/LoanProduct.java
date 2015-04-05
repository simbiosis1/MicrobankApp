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
@Table(name = "MIB_LOANPRODUCT")
@NamedQueries({
		@NamedQuery(name = "listLoanProduct", query = "select x from LoanProduct x where x.company=:company"),
		@NamedQuery(name = "getLoanProductByRefId", query = "select x from LoanProduct x where x.company=:company and x.refId=:refId") })
public class LoanProduct {
	@Id
	@Column(name = "LPR_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_loanproduct")
	@TableGenerator(name = "gen_mib_loanproduct", allocationSize = 1, pkColumnValue = "gen_mib_loanproduct")
	long id;
	@Column(name = "LPR_REFID")
	long refId;
	@Column(name = "LPR_CODE", length = 30)
	String code;
	@Column(name = "LPR_NAME", length = 50)
	String name;
	@Column(name = "LPR_COMPANY")
	long company;
	@Column(name = "LPR_SCHEMA")
	int schema;
	@Column(name = "LPR_COA1")
	long coa1;
	@Column(name = "LPR_COA2")
	long coa2;
	@Column(name = "LPR_COA3")
	long coa3;
	@Column(name = "LPR_COA4")
	long coa4;
	@Column(name = "LPR_COA5")
	long coa5;
	@Column(name = "LPR_COA6")
	long coa6;
	@Column(name = "LPR_ACTIVE")
	int active;
	@Column(name = "LPR_PROFITSHARED")
	int profitShared;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCompany() {
		return company;
	}

	public void setCompany(long company) {
		this.company = company;
	}

	public long getRefId() {
		return refId;
	}

	public void setRefId(long refId) {
		this.refId = refId;
	}

	public int getSchema() {
		return schema;
	}

	public void setSchema(int schema) {
		this.schema = schema;
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

	public long getCoa4() {
		return coa4;
	}

	public void setCoa4(long coa4) {
		this.coa4 = coa4;
	}

	public long getCoa5() {
		return coa5;
	}

	public void setCoa5(long coa5) {
		this.coa5 = coa5;
	}

	public long getCoa6() {
		return coa6;
	}

	public void setCoa6(long coa6) {
		this.coa6 = coa6;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getProfitShared() {
		return profitShared;
	}

	public void setProfitShared(int profitShared) {
		this.profitShared = profitShared;
	}

}
