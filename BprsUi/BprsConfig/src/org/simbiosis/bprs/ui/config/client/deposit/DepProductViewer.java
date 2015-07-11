package org.simbiosis.bprs.ui.config.client.deposit;

import org.simbiosis.bprs.ui.config.shared.ProductDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.Widget;

public class DepProductViewer extends Composite implements Editor<ProductDv> {

	private static UserViewerUiBinder uiBinder = GWT
			.create(UserViewerUiBinder.class);

	interface UserViewerUiBinder extends UiBinder<Widget, DepProductViewer> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<ProductDv, DepProductViewer> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label name;
	@UiField
	Label strCoa1;
	@UiField
	Label strCoa2;
	@UiField
	Label strCoa3;
	@UiField
	NumberLabel<Double> sharing;
	@UiField
	Label strTerm;
	@UiField
	Label code;

	public DepProductViewer() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		driver.initialize(this);
	}

	public void setUser(ProductDv user) {
		driver.edit(user);
	}
}
