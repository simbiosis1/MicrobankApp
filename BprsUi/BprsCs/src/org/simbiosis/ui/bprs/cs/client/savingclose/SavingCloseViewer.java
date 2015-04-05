package org.simbiosis.ui.bprs.cs.client.savingclose;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.client.savinghelper.SavingInfo;
import org.simbiosis.ui.bprs.cs.shared.SavingCloseDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SavingCloseViewer extends FormWidget implements
		Editor<SavingCloseDv>, ISavingClose {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, SavingCloseViewer> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<SavingCloseDv, SavingCloseViewer> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	SavingInfo saving;
	@UiField
	Label strClosing;
	@UiField
	Label reason;

	public SavingCloseViewer() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasNew(true);
		//
		driver.initialize(this);
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
		//
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void showData(SavingCloseDv savingDv) {
		driver.edit(savingDv);
	}

	@Override
	public SavingCloseDv getData() {
		return driver.flush();
	}

}
