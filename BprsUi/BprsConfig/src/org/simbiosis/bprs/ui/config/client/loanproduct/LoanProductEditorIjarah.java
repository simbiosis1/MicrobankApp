package org.simbiosis.bprs.ui.config.client.loanproduct;

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

public class LoanProductEditorIjarah extends Composite implements
		Editor<ProductDv> {

	private static UserViewerUiBinder uiBinder = GWT
			.create(UserViewerUiBinder.class);

	interface UserViewerUiBinder extends UiBinder<Widget, LoanProductEditorIjarah> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<ProductDv, LoanProductEditorIjarah> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	TextBox name;
	@UiField
	CoaListEditor coa1;
	@UiField
	CoaListEditor coa2;
	@UiField
	CoaListEditor coa3;
	@UiField
	CoaListEditor coa4;
	@UiField
	TextBox code;
	@UiField
	CheckBox hasShare;

	public LoanProductEditorIjarah(List<CoaDv> coas) {
		initWidget(uiBinder.createAndBindUi(this));
		//
		coa1.setList(coas);
		coa2.setList(coas);
		coa3.setList(coas);
		coa4.setList(coas);
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
