package org.simbiosis.bprs.ui.config.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TellerDv implements IsSerializable {

	Integer nr;
	Long id;
	String code;
	String name;
	Long user;
	Long coa;
	Long company;
	Long branch;
	Long subBranch;
	String strUser;
	String strCoa;
	String strBranch;
	String strSubBranch;

	public TellerDv() {
		id = 0L;
		user = 0L;
		coa = 0L;
		branch = 0L;
		subBranch = 0L;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public Long getCoa() {
		return coa;
	}

	public void setCoa(Long coa) {
		this.coa = coa;
	}

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

	public Long getBranch() {
		return branch;
	}

	public void setBranch(Long branch) {
		this.branch = branch;
	}

	public Long getSubBranch() {
		return subBranch;
	}

	public void setSubBranch(Long subBranch) {
		this.subBranch = subBranch;
	}

	public String getStrUser() {
		return strUser;
	}

	public void setStrUser(String strUser) {
		this.strUser = strUser;
	}

	public String getStrCoa() {
		return strCoa;
	}

	public void setStrCoa(String strCoa) {
		this.strCoa = strCoa;
	}

	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public String getStrBranch() {
		return strBranch;
	}

	public void setStrBranch(String strBranch) {
		this.strBranch = strBranch;
	}

	public String getStrSubBranch() {
		return strSubBranch;
	}

	public void setStrSubBranch(String strSubBranch) {
		this.strSubBranch = strSubBranch;
	}

}
