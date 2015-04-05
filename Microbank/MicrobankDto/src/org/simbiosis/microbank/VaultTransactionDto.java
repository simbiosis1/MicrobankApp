package org.simbiosis.microbank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VaultTransactionDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2777735472914525918L;

	long id;
	Date date;
	String code;
	String refCode;
	double value;
	int direction;
	int status;
	long company;
	long branch;
	long subBranch;
	long teller;
	List<VaultTransactionItemDto> items = new ArrayList<VaultTransactionItemDto>();

	public VaultTransactionDto() {
		id = 0;
		status = 0;
		teller = 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
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

	public long getSubBranch() {
		return subBranch;
	}

	public void setSubBranch(long subBranch) {
		this.subBranch = subBranch;
	}

	public List<VaultTransactionItemDto> getItems() {
		return items;
	}

	public void setItems(List<VaultTransactionItemDto> items) {
		this.items = items;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getTeller() {
		return teller;
	}

	public void setTeller(long teller) {
		this.teller = teller;
	}
}
