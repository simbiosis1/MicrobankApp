package org.simbiosis.ui.bprs.security.client;

import org.kembang.module.client.mvp.KembangClientFactory;
import org.simbiosis.ui.bprs.security.client.auth.IAuthList;

public interface BprsSecurityFactory extends KembangClientFactory {
	IAuthList getAuthList();
}
