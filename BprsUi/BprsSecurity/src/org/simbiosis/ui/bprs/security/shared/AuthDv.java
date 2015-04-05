package org.simbiosis.ui.bprs.security.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AuthDv implements IsSerializable {
	int nr;
	Long id;
	String description;
	Long authorizer;
	String strAuthorizer;
	Long user;
	String strUser;
	String strBranch;

	public AuthDv() {
		nr = 0;
		id = 0L;
		authorizer = 0L;
		user = 0L;
	}

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getAuthorizer() {
		return authorizer;
	}

	public void setAuthorizer(Long authorizer) {
		this.authorizer = authorizer;
	}

	public String getStrAuthorizer() {
		return strAuthorizer;
	}

	public void setStrAuthorizer(String strAuthorizer) {
		this.strAuthorizer = strAuthorizer;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public String getStrUser() {
		return strUser;
	}

	public void setStrUser(String strUser) {
		this.strUser = strUser;
	}

	public String getStrBranch() {
		return strBranch;
	}

	public void setStrBranch(String strBranch) {
		this.strBranch = strBranch;
	}
}
