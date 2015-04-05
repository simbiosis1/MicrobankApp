package org.simbiosis.ui.bprs.security.client.rpc;

import java.util.List;

import org.simbiosis.ui.bprs.security.shared.AuthDv;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SecServiceAsync {

	void listAuth(String key, AsyncCallback<List<AuthDv>> callback);

	void authorize(String key, Long id, AsyncCallback<Void> callback);

}
