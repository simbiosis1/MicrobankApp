package org.simbiosis.ui.bprs.teller.client.savingtrans;

import java.util.Date;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.client.savinghelper.SavingInfo;
import org.simbiosis.ui.bprs.common.shared.FindTransactionDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class SavingTransList extends FormWidget implements ISavingTransList {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, SavingTransList> {
	}

	@UiField
	CetakTabunganTable searchEditor;
	@UiField
	DateBox beginDate;
	@UiField
	DateBox endDate;
	@UiField
	SavingInfo savingDv;

	public SavingTransList() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		beginDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd-MM-yyyy")));
		beginDate.setValue(new Date());
		endDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd-MM-yyyy")));
		endDate.setValue(new Date());
		// format filter
		setHasSearch(true);
		setHasReload(true);
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
	public Date getBeginDate() {
		return beginDate.getValue();
	}

	@Override
	public Date getEndDate() {
		return endDate.getValue();
	}

	@Override
	public void setSaving(SavingDv savingDv) {
		this.savingDv.showData(savingDv);
	}

	@Override
	public SavingDv getSaving() {
		return savingDv.getData();
	}

}
