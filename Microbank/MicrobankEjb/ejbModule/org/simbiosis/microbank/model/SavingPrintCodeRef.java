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
@Table(name = "MIB_SAVINGPRINTCODEREF")
@NamedQueries({ @NamedQuery(name = "listPrintCode", query = "select x from SavingPrintCodeRef x where x.company=:company order by x.type") })
public class SavingPrintCodeRef {
	@Id
	@Column(name = "SPC_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_savingprintcoderef")
	@TableGenerator(name = "gen_mib_savingprintcoderef", allocationSize = 1, pkColumnValue = "gen_mib_savingprintcoderef")
	long id;
	@Column(name = "SPC_COMPANY")
	Long company;
	@Column(name = "SPC_TYPE")
	Integer type;
	@Column(name = "SPC_CODE", length = 10)
	String code;

	public SavingPrintCodeRef() {
		company = 0L;
		type = 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
