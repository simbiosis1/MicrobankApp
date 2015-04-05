package org.simbiosis.ui.bprs.admin.client.savingjournal;

import java.util.ArrayList;
import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.admin.client.editor.CoaListEditor;
import org.simbiosis.ui.bprs.admin.client.editor.SavingTransTypeEditor;
import org.simbiosis.ui.bprs.admin.shared.CoaDv;
import org.simbiosis.ui.bprs.common.client.savinghelper.SavingInfo;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SavingJournalEditor extends FormWidget implements ISavingJournal,
		Editor<TransactionDv> {

	Activity activity;

	private static TarikTunaiUiBinder uiBinder = GWT
			.create(TarikTunaiUiBinder.class);

	interface TarikTunaiUiBinder extends UiBinder<Widget, SavingJournalEditor> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<TransactionDv, SavingJournalEditor> {
	}

	List<CoaDv> listCoa = new ArrayList<CoaDv>();

	private Driver driver = GWT.create(Driver.class);

	@UiField
	SavingInfo saving;
	@UiField
	Label strDate;
	@UiField
	TextBox refCode;
	@UiField
	TextBox description;
	@UiField
	SavingTransTypeEditor type;
	@UiField
	TextBox strValue;
	@UiField
	CoaListEditor coa;

	public SavingJournalEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasSave(true);
		setHasBack(true);
		//
		coa.setList(listCoa);
		driver.initialize(this);
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
	public void showData(TransactionDv transactionDv) {
		driver.edit(transactionDv);
	}

	@Override
	public TransactionDv getData() {
		return driver.flush();
	}

	@Override
	public void setListCoa(List<CoaDv> listCoa) {
		this.listCoa.clear();
		this.listCoa.addAll(listCoa);
		//
		coa.setList(this.listCoa);
	}


}
