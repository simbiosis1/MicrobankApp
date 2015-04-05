package org.simbiosis.bprs.ui.config.client.teller;

import org.simbiosis.bprs.ui.config.shared.TellerDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class TellerViewer extends Composite implements Editor<TellerDv> {

	private static UserViewerUiBinder uiBinder = GWT
			.create(UserViewerUiBinder.class);

	interface UserViewerUiBinder extends UiBinder<Widget, TellerViewer> {
	}

	interface Driver extends SimpleBeanEditorDriver<TellerDv, TellerViewer> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label code;
	@UiField
	Label strUser;
	@UiField
	Label strCoa;
	@UiField
	Label strBranch;
	@UiField
	Label strSubBranch;

	public TellerViewer() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		driver.initialize(this);
	}

	public void setUser(TellerDv user) {
		driver.edit(user);
	}
}
