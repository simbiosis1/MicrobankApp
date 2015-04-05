package org.simbiosis.ui.bprs.loan.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BprsLoan implements EntryPoint {
	BprsLoanEntryPoint appEntryPoint = new BprsLoanEntryPoint();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		appEntryPoint.start();
	}
}
