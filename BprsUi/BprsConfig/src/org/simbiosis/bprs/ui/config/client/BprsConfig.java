package org.simbiosis.bprs.ui.config.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BprsConfig implements EntryPoint {
	AppEntryPoint appEntryPoint = new AppEntryPoint();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		appEntryPoint.start();
	}
}
