package org.simbiosis.ui.bprs.security.client;

import org.kembang.module.client.mvp.KembangClientFactoryImpl;
import org.simbiosis.ui.bprs.security.client.auth.AuthList;
import org.simbiosis.ui.bprs.security.client.auth.IAuthList;

public class BprsSecurityFactoryImpl extends KembangClientFactoryImpl implements
		BprsSecurityFactory {

	static final AuthList AUTH_LIST = new AuthList();

	@Override
	public IAuthList getAuthList() {
		return AUTH_LIST;
	}

}
