package org.simbiosis.ui.bprs.dashboard.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DashboardViewer implements EntryPoint {
	AppEntryPoint appEntryPoint = new AppEntryPoint();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		//
		Resources.INSTANCE.css().ensureInjected();
		//
		appEntryPoint.start();
	}
}
