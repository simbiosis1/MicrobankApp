package org.simbiosis.ui.bprs.cs.client.savinginput;

import java.util.ArrayList;
import java.util.List;

import org.simbiosis.ui.bprs.common.client.editor.ProductListEditor;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SavingEditorWidget extends Composite implements Editor<SavingDv> {

	private static SimpananEditorUiBinder uiBinder = GWT
			.create(SimpananEditorUiBinder.class);

	interface SimpananEditorUiBinder extends
			UiBinder<Widget, SavingEditorWidget> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<SavingDv, SavingEditorWidget> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	TextBox code;
	@UiField
	DateLabel registration;
	@UiField
	ProductListEditor product;
	@UiField
	CheckBox zakat;

	List<DataDv> savingProductList = new ArrayList<DataDv>();

	public SavingEditorWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		driver.initialize(this);
		product.setList(savingProductList);
	}

	public void showData(SavingDv tabunganDv) {
		driver.edit(tabunganDv);
	}

	public SavingDv getData() {
		return driver.flush();
	}

	public void setSavingProductList(List<DataDv> savingProductList) {
		this.savingProductList.clear();
		this.savingProductList.addAll(savingProductList);
		product.setList(this.savingProductList);
	}
}
