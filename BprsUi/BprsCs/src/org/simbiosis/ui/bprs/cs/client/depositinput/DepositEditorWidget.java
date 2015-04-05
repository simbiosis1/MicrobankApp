package org.simbiosis.ui.bprs.cs.client.depositinput;

import java.util.ArrayList;
import java.util.List;

import org.simbiosis.ui.bprs.common.client.editor.ProductListEditor;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.DepositDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class DepositEditorWidget extends Composite implements Editor<DepositDv> {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, DepositEditorWidget> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<DepositDv, DepositEditorWidget> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	TextBox code;
	@UiField
	Label strRegistration;
	@UiField
	ProductListEditor product;
	@UiField
	TextBox bilyet;
	@UiField
	TextBox strValue;
	@UiField
	Label strRate;
	@UiField
	TextBox strSpecialRate;
	@UiField
	CheckBox aro;
	@UiField
	CheckBox zakat;

	List<DataDv> productList = new ArrayList<DataDv>();

	public DepositEditorWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		product.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				strRate.setText(productList.get(product.getSelectedIndex())
						.getStrRate());
			}
		});
		//
		driver.initialize(this);
		product.setList(productList);
	}

	public void showData(DepositDv depositDv) {
		driver.edit(depositDv);
		if (depositDv.getId() == 0L) {
			strRate.setText(productList.get(0).getStrRate());
		}
	}

	public DepositDv getData() {
		return driver.flush();
	}

	public void setDepositProductList(List<DataDv> productList) {
		this.productList.clear();
		this.productList.addAll(productList);
		product.setList(this.productList);
	}

}
