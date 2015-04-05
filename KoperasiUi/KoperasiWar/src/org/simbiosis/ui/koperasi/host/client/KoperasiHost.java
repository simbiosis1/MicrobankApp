package org.simbiosis.ui.koperasi.host.client;

import org.kembang.host.client.mvp.MainFramePresenter;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class KoperasiHost implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		//
		Resources.INSTANCE.css().ensureInjected();
		//
		MainFramePresenter app = new MainFramePresenter(RootLayoutPanel.get());
		app.onModuleLoad();
	}
}
