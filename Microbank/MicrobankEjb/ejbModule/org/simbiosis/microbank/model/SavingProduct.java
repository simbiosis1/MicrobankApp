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
@Table(name = "MIB_SAVINGPRODUCT")
@NamedQueries({
		@NamedQuery(name = "listSavingProduct", query = "select x from SavingProduct x where x.company=:company"),
		@NamedQuery(name = "getSavingProductByRefId", query = "select x from SavingProduct x where x.company=:company and x.refId=:refId") })
public class SavingProduct {
	@Id
	@Column(name = "SPR_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_savingproduct")
	@TableGenerator(name = "gen_mib_savingproduct", allocationSize = 1, pkColumnValue = "gen_mib_savingproduct")
	long id;
	@Column(name = "SPR_REFID")
	long refId;
	@Column(name = "SPR_CODE", length = 30)
	String code;
	@Column(name = "SPR_NAME", length = 50)
	String name;
	@Column(name = "SPR_COMPANY")
	long company;
	@Column(name = "SPR_SCHEMA")
	int schema;
	@Column(name = "SPR_SHARING")
	double sharing;
	@Column(name = "SPR_HASSHARE")
	int hasShare;
	@Column(name = "SPR_COA1")
	long coa1;
	@Column(name = "SPR_COA2")
	long coa2;
	@Column(name = "SPR_COA3")
	long coa3;
	@Column(name = "SPR_COA4")
	long coa4;
	@Column(name = "SPR_MINVALUE")
	double minValue;
	@Column(name = "SPR_CLOSEADMIN")
	double closeAdmin;
	@Column(name = "SPR_ACTIVE")
	int active;

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

	public double getSharing() {
		return sharing;
	}

	public void setSharing(double sharing) {
		this.sharing = sharing;
	}

	public int getHasShare() {
		return hasShare;
	}

	public void setHasShare(int hasShare) {
		this.hasShare = hasShare;
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

	public int getSchema() {
		return schema;
	}

	public void setSchema(int schema) {
		this.schema = schema;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public long getCoa4() {
		return coa4;
	}

	public void setCoa4(long coa4) {
		this.coa4 = coa4;
	}

	public double getCloseAdmin() {
		return closeAdmin;
	}

	public void setCloseAdmin(double closeAdmin) {
		this.closeAdmin = closeAdmin;
	}

}
