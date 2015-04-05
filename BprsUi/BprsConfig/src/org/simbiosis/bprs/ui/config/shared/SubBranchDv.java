package org.simbiosis.bprs.ui.config.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SubBranchDv implements IsSerializable {
	Long id;
	Integer nr;
	String code;
	String address;
	String name;
	Long branch;
	String strBranch;
	Long coa1;
	Long coa2;
	String strCoa1;
	String strCoa2;

	public SubBranchDv() {
		nr = 0;
		id = 0L;
		branch = 0L;
		coa1 = 0L;
		coa2 = 0L;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getCoa1() {
		return coa1;
	}

	public void setCoa1(Long coa1) {
		this.coa1 = coa1;
	}

	public Long getCoa2() {
		return coa2;
	}

	public void setCoa2(Long coa2) {
		this.coa2 = coa2;
	}

	public String getStrCoa1() {
		return strCoa1;
	}

	public void setStrCoa1(String strCoa1) {
		this.strCoa1 = strCoa1;
	}

	public String getStrCoa2() {
		return strCoa2;
	}

	public void setStrCoa2(String strCoa2) {
		this.strCoa2 = strCoa2;
	}

	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public Long getBranch() {
		return branch;
	}

	public void setBranch(Long branch) {
		this.branch = branch;
	}

	public String getStrBranch() {
		return strBranch;
	}

	public void setStrBranch(String strBranch) {
		this.strBranch = strBranch;
	}

}
