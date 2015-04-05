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
@Table(name = "MIB_SAVINGCODE")
@NamedQueries({ @NamedQuery(name = "getSavingCodeCounter", query = "select x from SavingCode x where x.company=:company and x.prefix=:prefix") })
public class SavingCode {
	@Id
	@Column(name = "SCD_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_savingcode")
	@TableGenerator(name = "gen_mib_savingcode", allocationSize = 1, pkColumnValue = "gen_mib_savingcode")
	long id;
	@Column(name = "SCD_COMPANY")
	long company;
	@Column(name = "SCD_PREFIX", length = 30)
	String prefix;
	@Column(name = "SCD_COUNTER")
	long counter;

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

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public long getCounter() {
		return counter;
	}

	public void setCounter(long counter) {
		this.counter = counter;
	}

}
