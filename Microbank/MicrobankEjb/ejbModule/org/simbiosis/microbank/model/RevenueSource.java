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
@Table(name = "MIB_REVSOURCE")
@NamedQueries({ @NamedQuery(name = "listRevenueByCoa", query = "select x from RevenueSource x where x.company=:company and x.active=1 order by x.id") })
public class RevenueSource {
	@Id
	@Column(name = "RSR_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_revsource")
	@TableGenerator(name = "gen_mib_revsource", allocationSize = 1, pkColumnValue = "gen_mib_revsource")
	long id;
	@Column(name = "RSR_COMPANY")
	long company;
	@Column(name = "RSR_COA")
	long coa;
	@Column(name = "RSR_ACTIVE")
	int active;

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

	public long getCoa() {
		return coa;
	}

	public void setCoa(long coa) {
		this.coa = coa;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

}
