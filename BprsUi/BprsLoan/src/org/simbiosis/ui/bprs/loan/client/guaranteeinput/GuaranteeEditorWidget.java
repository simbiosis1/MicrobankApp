package org.simbiosis.ui.bprs.loan.client.guaranteeinput;

import java.util.ArrayList;
import java.util.List;

import org.kembang.editor.client.DoubleTextBox;
import org.simbiosis.ui.bprs.common.shared.GuaranteeDv;
import org.simbiosis.ui.bprs.loan.client.editor.TypeListEditor;
import org.simbiosis.ui.bprs.loan.client.guaranteeinput.IGuaranteeInput.Activity;
import org.simbiosis.ui.bprs.loan.shared.TypeDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class GuaranteeEditorWidget extends Composite implements
		Editor<GuaranteeDv> {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, GuaranteeEditorWidget> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<GuaranteeDv, GuaranteeEditorWidget> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	TextBox code;
	@UiField
	DateLabel registration;
	@UiField
	TypeListEditor type;
	@UiField
	TextBox number;
	@UiField
	TextBox description;
	@UiField
	TextBox ownerName;
	@UiField
	DoubleTextBox appraisalIntValue;
	@UiField
	DoubleTextBox appraisalMarkValue;

	String[] widthsText = { "28px", "100px", "150px", "150px", "150px" };
	String[] footerText = { "", "Total", "0", "0", "0" };
	NumberFormat numberFormat = NumberFormat.getFormat("####,###.00");

	List<TypeDv> guaranteeTypes = new ArrayList<TypeDv>();

	public GuaranteeEditorWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		driver.initialize(this);
		type.setList(this.guaranteeTypes);
	}

	public void showData(GuaranteeDv loanDv) {
		//
		driver.edit(loanDv);
	}

	public GuaranteeDv getData() {
		return driver.flush();
	}

	public void setGuaranteeTypes(List<TypeDv> typeList) {
		this.guaranteeTypes.clear();
		this.guaranteeTypes.addAll(typeList);
		type.setList(this.guaranteeTypes);
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

}
