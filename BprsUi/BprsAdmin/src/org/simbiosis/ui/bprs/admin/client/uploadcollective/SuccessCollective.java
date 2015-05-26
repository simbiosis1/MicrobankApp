package org.simbiosis.ui.bprs.admin.client.uploadcollective;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SuccessCollective extends Composite {

	private static MyUiBinder uiBinder = GWT
			.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, SuccessCollective> {
	}

	public SuccessCollective() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
