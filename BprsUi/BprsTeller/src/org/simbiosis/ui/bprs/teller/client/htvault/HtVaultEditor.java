package org.simbiosis.ui.bprs.teller.client.htvault;

import java.util.ArrayList;
import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.teller.client.editor.TellerListEditor;
import org.simbiosis.ui.bprs.teller.client.editor.VaultTypeEditor;
import org.simbiosis.ui.bprs.teller.shared.TellerDv;
import org.simbiosis.ui.bprs.teller.shared.VaultTransactionDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class HtVaultEditor extends FormWidget implements IHtVault,
		Editor<VaultTransactionDv> {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, HtVaultEditor> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<VaultTransactionDv, HtVaultEditor> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	VaultTypeEditor type;
	@UiField
	TellerListEditor teller;
	@UiField
	Label strDate;
	@UiField
	Label code;
	@UiField
	Label strValue;
	@UiField
	Button btnGet;

	@UiField
	TextBox str50L;
	@UiField
	TextBox str100L;
	@UiField
	TextBox str200L;
	@UiField
	TextBox str500L;
	@UiField
	TextBox str1000L;
	@UiField
	TextBox str1000K;
	@UiField
	TextBox str2000K;
	@UiField
	TextBox str5000K;
	@UiField
	TextBox str10000K;
	@UiField
	TextBox str20000K;
	@UiField
	TextBox str50000K;
	@UiField
	TextBox str100000K;

	@UiField
	Label control;

	List<TellerDv> tellerList = new ArrayList<TellerDv>();
	boolean loaded = false;

	public HtVaultEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		type.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				setEditor(type.getSelectedIndex());
			}
		});
		btnGet.setVisible(false);
		//
		setHasSave(true);
		setHasBack(true);
		//
		teller.setList(tellerList);
		driver.initialize(this);
		//
		KeyUpHandler editValuesHandler = new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				onValueEdit();
			}
		};
		str50L.addKeyUpHandler(editValuesHandler);
		str100L.addKeyUpHandler(editValuesHandler);
		str200L.addKeyUpHandler(editValuesHandler);
		str500L.addKeyUpHandler(editValuesHandler);
		str1000L.addKeyUpHandler(editValuesHandler);
		str1000K.addKeyUpHandler(editValuesHandler);
		str2000K.addKeyUpHandler(editValuesHandler);
		str5000K.addKeyUpHandler(editValuesHandler);
		str10000K.addKeyUpHandler(editValuesHandler);
		str20000K.addKeyUpHandler(editValuesHandler);
		str50000K.addKeyUpHandler(editValuesHandler);
		str100000K.addKeyUpHandler(editValuesHandler);
	}

	void onValueEdit() {
		int value = 50 * Integer.parseInt(str50L.getText()) + 100
				* Integer.parseInt(str100L.getText()) + 200
				* Integer.parseInt(str200L.getText()) + 500
				* Integer.parseInt(str500L.getText()) + 1000
				* Integer.parseInt(str1000L.getText()) + 1000
				* Integer.parseInt(str1000K.getText()) + 2000
				* Integer.parseInt(str2000K.getText()) + 5000
				* Integer.parseInt(str5000K.getText()) + 10000
				* Integer.parseInt(str10000K.getText()) + 20000
				* Integer.parseInt(str20000K.getText()) + 50000
				* Integer.parseInt(str50000K.getText()) + 100000
				* Integer.parseInt(str100000K.getText());
		NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		control.setText(nf.format(value));
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
		if (appStatus.isLogin() && !loaded) {
			activity.listAvailableTeller();
			loaded = true;
		}
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void showData(VaultTransactionDv transactionDv) {
		driver.edit(transactionDv);
	}

	@Override
	public VaultTransactionDv getData() {
		VaultTransactionDv result = driver.flush();
		result.setDirection((result.getType() == 1) ? 2 : 1);
		return result;
	}

	@UiHandler("btnGet")
	public void onBtnGetClick(ClickEvent e) {
		activity.getReadyVault(getData());
	}

	void setEditor(int posisi) {
		if (posisi == 0) {
			btnGet.setVisible(false);
		} else {
			btnGet.setVisible(true);
		}
	}

	@Override
	public void setTellers(List<TellerDv> tellers) {
		tellerList.addAll(tellers);
		teller.setList(tellerList);
	}

}
