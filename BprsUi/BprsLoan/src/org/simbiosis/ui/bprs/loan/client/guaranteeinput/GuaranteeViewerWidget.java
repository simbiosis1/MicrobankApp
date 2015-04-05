package org.simbiosis.ui.bprs.loan.client.guaranteeinput;

import java.util.List;

import org.simbiosis.ui.bprs.common.shared.GuaranteeDv;
import org.simbiosis.ui.bprs.loan.client.guaranteeinput.IGuaranteeInput.Activity;
import org.simbiosis.ui.bprs.loan.shared.TypeDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class GuaranteeViewerWidget extends Composite implements
		Editor<GuaranteeDv> {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, GuaranteeViewerWidget> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<GuaranteeDv, GuaranteeViewerWidget> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label code;
	@UiField
	Label strRegistration;
	@UiField
	Label strType;
	@UiField
	Label number;
	@UiField
	Label description;
	@UiField
	Label ownerName;
	@UiField
	Label strAppraisalIntValue;
	@UiField
	Label strAppraisalMarkValue;
	

	String[] widthsText = { "28px", "100px", "150px", "150px", "150px" };
	String[] footerText = { "", "Total", "0", "0", "0" };
	NumberFormat numberFormat = NumberFormat.getFormat("####,###.00");

	public GuaranteeViewerWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		driver.initialize(this);
	}

	public void showData(GuaranteeDv loanDv) {
		//
		driver.edit(loanDv);
	}

	public GuaranteeDv getData() {
		return driver.flush();
	}

	public void setGuaranteeTypes(List<TypeDv> typeList) {
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

}
