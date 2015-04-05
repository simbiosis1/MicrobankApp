package org.simbiosis.ui.bprs.security.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BprsSecurity implements EntryPoint {
	BprsSecurityEntryPoint appEntryPoint = new BprsSecurityEntryPoint();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		appEntryPoint.start();
	}
}
