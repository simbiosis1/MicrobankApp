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
@Table(name = "MIB_TELLERTRANSCODE")
@NamedQueries({ @NamedQuery(name = "getTellerTransCodeCounter", query = "select x from TellerTransactionCode x where x.company=:company and x.prefix=:prefix") })
public class TellerTransactionCode {
	@Id
	@Column(name = "TTC_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_tellertranscode")
	@TableGenerator(name = "gen_mib_tellertranscode", allocationSize = 1, pkColumnValue = "gen_mib_tellertranscode")
	long id;
	@Column(name = "TTC_COMPANY")
	long company;
	@Column(name = "TTC_PREFIX", length = 30)
	String prefix;
	@Column(name = "TTC_COUNTER")
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
