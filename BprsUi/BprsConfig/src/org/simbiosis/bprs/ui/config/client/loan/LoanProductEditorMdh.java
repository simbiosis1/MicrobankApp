package org.simbiosis.bprs.ui.config.client.loan;

import java.util.List;

import org.simbiosis.bprs.ui.config.client.editor.CoaListEditor;
import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.ProductDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoanProductEditorMdh extends Composite implements
		Editor<ProductDv> {

	private static UserViewerUiBinder uiBinder = GWT
			.create(UserViewerUiBinder.class);

	interface UserViewerUiBinder extends UiBinder<Widget, LoanProductEditorMdh> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<ProductDv, LoanProductEditorMdh> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	TextBox name;
	@UiField
	CoaListEditor coa1;
	@UiField
	CoaListEditor coa3;
	@UiField
	TextBox code;
	@UiField
	CheckBox hasShare;

	public LoanProductEditorMdh(List<CoaDv> coas) {
		initWidget(uiBinder.createAndBindUi(this));
		//
		coa1.setList(coas);
		coa3.setList(coas);
		//
		driver.initialize(this);
		driver.edit(new ProductDv());
	}

	public void setProduct(ProductDv product) {
		driver.edit(product);
	}

	public ProductDv getProduct() {
		return driver.flush();
	}
}
