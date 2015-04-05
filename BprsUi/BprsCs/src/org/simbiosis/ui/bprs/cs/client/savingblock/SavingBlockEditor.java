package org.simbiosis.ui.bprs.cs.client.savingblock;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.client.savinghelper.SavingInfo;
import org.simbiosis.ui.bprs.cs.client.editor.SavingBlockirTable;
import org.simbiosis.ui.bprs.cs.shared.SavingBlockirDataDv;
import org.simbiosis.ui.bprs.cs.shared.SavingBlockirDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class SavingBlockEditor extends FormWidget implements
		ISavingBlockEditor, Editor<SavingBlockirDataDv> {

	Activity activity;

	private static SavingBlockUiBinder uiBinder = GWT
			.create(SavingBlockUiBinder.class);

	interface SavingBlockUiBinder extends UiBinder<Widget, SavingBlockEditor> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<SavingBlockirDataDv, SavingBlockEditor> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	SavingInfo saving;
	@UiField
	SavingBlockirTable blockir;
	@UiField
	Button add;
	@UiField
	Button remove;

	public SavingBlockEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		enableButtons(false);
		//
		setHasSearch(true);
		setHasSave(true);
		//
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
	public void showData(SavingBlockirDataDv data) {
		driver.edit(data);
	}

	@Override
	public void enableButtons(Boolean status) {
		add.setVisible(status);
		remove.setVisible(status);
	}

	@UiHandler("add")
	public void onAdd(ClickEvent event) {
		blockir.addRow(new SavingBlockirDv());
	}

	@UiHandler("remove")
	public void onRemove(ClickEvent event) {
		blockir.removeSelected();
	}

	@Override
	public SavingBlockirDataDv getData() {
		return driver.flush();
	}

}
