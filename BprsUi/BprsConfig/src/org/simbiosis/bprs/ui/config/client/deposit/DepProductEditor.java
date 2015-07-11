package org.simbiosis.bprs.ui.config.client.deposit;

import java.util.List;

import org.kembang.editor.client.DoubleTextBox;
import org.kembang.module.shared.SimpleBranchDv;
import org.simbiosis.bprs.ui.config.client.editor.CoaListEditor;
import org.simbiosis.bprs.ui.config.client.editor.TermListBox;
import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.ProductDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class DepProductEditor extends Composite implements Editor<ProductDv> {

	private static UserViewerUiBinder uiBinder = GWT
			.create(UserViewerUiBinder.class);

	interface UserViewerUiBinder extends UiBinder<Widget, DepProductEditor> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<ProductDv, DepProductEditor> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	TextBox code;
	@UiField
	TextBox name;
	@UiField
	CoaListEditor coa1;
	@UiField
	CoaListEditor coa2;
	@UiField
	CoaListEditor coa3;
	@UiField
	DoubleTextBox sharing;
	@UiField
	TermListBox term;

	public DepProductEditor(List<CoaDv> coaList, List<SimpleBranchDv> termList) {
		initWidget(uiBinder.createAndBindUi(this));
		//
		coa1.setList(coaList);
		coa2.setList(coaList);
		coa3.setList(coaList);
		term.setList(termList);
		//
		driver.initialize(this);
	}

	public void setData(ProductDv user) {
		driver.edit(user);
	}

	public ProductDv getData() {
		return driver.flush();
	}
}
