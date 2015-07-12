package org.simbiosis.bprs.ui.config.client.savingproduct;

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

public class SavProductViewerMdh extends Composite implements Editor<ProductDv> {

	private static UserViewerUiBinder uiBinder = GWT
			.create(UserViewerUiBinder.class);

	interface UserViewerUiBinder extends UiBinder<Widget, SavProductViewerMdh> {
	}

	interface Driver extends SimpleBeanEditorDriver<ProductDv, SavProductViewerMdh> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label code;
	@UiField
	Label name;
	@UiField
	Label strCoa1;
	@UiField
	Label strCoa2;
	@UiField
	Label strCoa3;
	@UiField
	Label strCoa4;
	@UiField
	Label strSchema;
	@UiField
	NumberLabel<Double> sharing;
	@UiField
	NumberLabel<Double> minValue;
	@UiField
	NumberLabel<Double> minSharable;
	@UiField
	NumberLabel<Double> closeAdmin;
	@UiField
	NumberLabel<Double> monthlyAdmin;
	
	public SavProductViewerMdh() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		driver.initialize(this);
	}

	public void setUser(ProductDv user){
		driver.edit(user);
	}
}
