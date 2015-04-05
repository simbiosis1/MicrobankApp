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
@Table(name = "MIB_LOANCODE")
@NamedQueries({ @NamedQuery(name = "getLoanCodeCounter", query = "select x from LoanCode x where x.company=:company and x.prefix=:prefix") })
public class LoanCode {
	@Id
	@Column(name = "LCD_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_loancode")
	@TableGenerator(name = "gen_mib_loancode", allocationSize = 1, pkColumnValue = "gen_mib_loancode")
	long id;
	@Column(name = "LCD_COMPANY")
	long company;
	@Column(name = "LCD_PREFIX", length = 30)
	String prefix;
	@Column(name = "LCD_COUNTER")
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
