package org.simbiosis.ui.bprs.teller.client.savingprint;

import java.util.Date;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.client.savinghelper.SavingInfo;
import org.simbiosis.ui.bprs.common.shared.SavingDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class SavingPrint extends FormWidget implements ISavingPrint {

	Activity activity;

	private static ListTransaksiTellerUiBinder uiBinder = GWT
			.create(ListTransaksiTellerUiBinder.class);

	interface ListTransaksiTellerUiBinder extends
			UiBinder<Widget, SavingPrint> {
	}

	@UiField
	Button btnPrintMaster;
	@UiField
	Button btnPrintBook;
	@UiField
	DateBox date;
	@UiField
	TextBox nuc;
	@UiField
	SavingInfo savingDv;

	public SavingPrint() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		date.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd-MM-yyyy")));
		date.setValue(new Date());
		//
		nuc.setText("0");
		// format filter
		setHasSearch(true);
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void setSaving(SavingDv savingDv) {
		this.savingDv.showData(savingDv);
	}

	@UiHandler("btnPrintMaster")
	public void onBtnPrintMaster(ClickEvent e) {
		activity.printMaster(savingDv.getData());
	}

	@UiHandler("btnPrintBook")
	public void onBtnPrintBook(ClickEvent e) {
		activity.printBook(savingDv.getData(), date.getValue(), nuc.getText());
	}
}
