package org.simbiosis.microbank.model;

import static javax.persistence.GenerationType.TABLE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "MIB_REVENUE")
public class Revenue {
	@Id
	@Column(name = "REV_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_revenue")
	@TableGenerator(name = "gen_mib_revenue", allocationSize = 1, pkColumnValue = "gen_mib_revenue")
	long id;
	@Column(name = "REV_COMPANY")
	long company;
	@Column(name = "REV_BRANCH")
	long branch;
	@Column(name = "REV_MONTH")
	int month;
	@Column(name = "REV_YEAR")
	int year;
	@Column(name = "REV_PRODUCT")
	long product;
	@Column(name = "REV_TYPE")
	int type;
	@Column(name = "REV_VALUE")
	double value;

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

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public long getProduct() {
		return product;
	}

	public void setProduct(long product) {
		this.product = product;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
