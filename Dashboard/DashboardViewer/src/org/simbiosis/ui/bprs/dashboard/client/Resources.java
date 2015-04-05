package org.simbiosis.ui.bprs.dashboard.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface Resources extends ClientBundle {

	public static final Resources INSTANCE = GWT.create(Resources.class);

	@Source("Dashboard.css")
	@CssResource.NotStrict
	CssResource css();

}
