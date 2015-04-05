package org.simbiosis.ui.bprs.security.client.auth;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.security.client.editor.AuthListTable;
import org.simbiosis.ui.bprs.security.shared.AuthListDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class AuthList extends FormWidget implements IAuthList,
		Editor<AuthListDv> {

	Activity activity;

	@UiField
	Button btnApprove;
	@UiField
	AuthListTable auths;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, AuthList> {
	}

	interface Driver extends SimpleBeanEditorDriver<AuthListDv, AuthList> {
	}

	private Driver driver = GWT.create(Driver.class);

	public AuthList() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasReload(true);
		//
		driver.initialize(this);
		driver.edit(new AuthListDv());
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

	@UiHandler("btnApprove")
	void onBtnApproveClick(ClickEvent e) {
		activity.authorize(auths.getSelectedData());
	}

	@Override
	public void setData(AuthListDv data) {
		driver.edit(data);
	}
}
