package org.simbiosis.ui.bprs.security.shared;

import java.util.ArrayList;
import java.util.List;

public class AuthListDv {
	List<AuthDv> auths = new ArrayList<AuthDv>();

	public AuthListDv() {
	}

	public AuthListDv(List<AuthDv> result) {
		auths.addAll(result);
	}

	public List<AuthDv> getAuths() {
		return auths;
	}

	public void setAuths(List<AuthDv> auths) {
		this.auths = auths;
	}

}
