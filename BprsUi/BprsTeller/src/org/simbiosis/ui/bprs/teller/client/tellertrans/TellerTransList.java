package org.simbiosis.ui.bprs.teller.client.tellertrans;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.FindTransactionDv;
import org.simbiosis.ui.bprs.teller.client.editor.TellerListEditor;
import org.simbiosis.ui.bprs.teller.shared.TellerDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class TellerTransList extends FormWidget implements ITellerTransList {

	Activity activity;

	private static ListTransaksiTellerUiBinder uiBinder = GWT
			.create(ListTransaksiTellerUiBinder.class);

	interface ListTransaksiTellerUiBinder extends
			UiBinder<Widget, TellerTransList> {
	}

	@UiField
	TellerTransListTableWidget searchEditor;
	@UiField
	DateBox transactionDate;
	@UiField
	TellerListEditor tellers;

	public TellerTransList() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		transactionDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd-MM-yyyy")));
		transactionDate.setValue(new Date());
		// format filter
		setHasView(true);
		setHasExportPdf(true);
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
	public void setListTransaction(FindTransactionDv findTransactionDv) {
		searchEditor.showData(findTransactionDv);
	}

	@Override
	public Date getDate() {
		return transactionDate.getValue();
	}

	@Override
	public Long getTeller() {
		return tellers.getValue();
	}

	@Override
	public void setTellerList(List<TellerDv> tellerList) {
		tellers.setList(tellerList);
	}

}
