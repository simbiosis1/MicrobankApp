package org.simbiosis.ui.bprs.teller.client.vault;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.teller.client.editor.VaultTypeEditor;
import org.simbiosis.ui.bprs.teller.shared.VaultTransactionDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class VaultEditor extends FormWidget implements IVault {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, VaultEditor> {
	}

	@UiField
	Label strDate;
	@UiField
	VaultTypeEditor type;
	@UiField
	HorizontalPanel info;
	@UiField
	Button btnGet;

	VaultInfo vaultInfo = new VaultInfo();
	VaultInput vaultInput = new VaultInput();

	VaultTransactionDv data;

	public VaultEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		type.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				setEditor(type.getSelectedIndex());
			}
		});
		info.add(vaultInfo);
		//
		setHasSave(true);
		setHasBack(true);
		//
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
	public void showData(VaultTransactionDv transDv) {
		if (transDv.getDirection() == 2) {
			vaultInfo.showData(transDv.getCode(), transDv.getStrValue());
			data.setId(transDv.getId());
			data.setCode(transDv.getCode());
			data.setValue(transDv.getValue());
			data.setStrValue(transDv.getStrValue());
		}
	}

	@Override
	public void newData(VaultTransactionDv transDv) {
		data = transDv;
		strDate.setText(transDv.getStrDate());
	}

	@Override
	public VaultTransactionDv getData() {
		if (data.getDirection() == 1) {
			data.setStrValue(vaultInput.getValue());
		}
		return data;
	}

	@UiHandler("btnGet")
	public void onBtnGetClick(ClickEvent e) {
		activity.getReadyVault(data);
	}

	void setEditor(int posisi) {
		info.clear();
		if (posisi == 0) {
			btnGet.setVisible(true);
			info.add(vaultInfo);
			data.setDirection(2);
		} else {
			btnGet.setVisible(false);
			info.add(vaultInput);
			data.setDirection(1);
		}
	}
}
