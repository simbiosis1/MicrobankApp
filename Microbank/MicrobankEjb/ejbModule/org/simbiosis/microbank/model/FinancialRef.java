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
@Table(name = "MIB_FINANCIALREF")
@NamedQueries({ @NamedQuery(name = "listFinancialRef", query = "select x from FinancialRef x where x.type=:type and x.scheme=:scheme order by x.order") })
public class FinancialRef {
	@Id
	@Column(name = "FRF_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_financialref")
	@TableGenerator(name = "gen_mib_financialref", allocationSize = 1, pkColumnValue = "gen_mib_financialref")
	long id;
	@Column(name = "FRF_SCHEME")
	int scheme;
	@Column(name = "FRF_TYPE")
	int type;
	@Column(name = "FRF_ORDER")
	int order;
	@Column(name = "FRF_NUMBER", length = 30)
	String number;
	@Column(name = "FRF_DESCRIPTION", length = 100)
	String description;
	@Column(name = "FRF_CODE", length = 30)
	String code;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getScheme() {
		return scheme;
	}

	public void setScheme(int scheme) {
		this.scheme = scheme;
	}

}
