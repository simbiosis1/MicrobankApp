package org.simbiosis.microbank.model;

import static javax.persistence.GenerationType.TABLE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "MIB_CUSTOMERREP")
public class CustomerRep {
	@Id
	@Column(name = "CSR_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_customerrep")
	@TableGenerator(name = "gen_mib_customerrep", allocationSize = 1, pkColumnValue = "gen_mib_customerrep")
	long id;
	@Column(name = "CSR_COMPANY")
	long company;
	@Column(name = "CSR_BRANCH")
	long branch;
	@OneToOne
	@JoinColumn(name = "CST_ID", referencedColumnName = "CST_ID")
	Customer customer;

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

	public long getBranch() {
		return branch;
	}

	public void setBranch(long branch) {
		this.branch = branch;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
