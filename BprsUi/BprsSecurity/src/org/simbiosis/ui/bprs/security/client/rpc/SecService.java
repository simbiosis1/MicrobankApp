package org.simbiosis.ui.bprs.security.client.rpc;

import java.util.List;

import org.simbiosis.ui.bprs.security.shared.AuthDv;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("security")
public interface SecService extends RemoteService {
	List<AuthDv> listAuth(String key);

	void authorize(String key, Long id);

}
