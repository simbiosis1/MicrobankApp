package org.simbiosis.bprs.ui.config.client.teller;

import java.util.ArrayList;
import java.util.List;

import org.kembang.grid.client.GridSelectionHandler;
import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.kembang.module.shared.SimpleBranchDv;
import org.kembang.module.shared.UserDv;
import org.simbiosis.bprs.ui.config.client.editor.TellersTable;
import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.SubBranchDv;
import org.simbiosis.bprs.ui.config.shared.TellerDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TellerList extends FormWidget implements ITeller {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, TellerList> {
	}

	List<UserDv> listUser = new ArrayList<UserDv>();
	List<CoaDv> listCoa = new ArrayList<CoaDv>();
	List<TellerDv> listTeller = new ArrayList<TellerDv>();
	List<SimpleBranchDv> listBranch = new ArrayList<SimpleBranchDv>();
	List<SubBranchDv> listSubBranch = new ArrayList<SubBranchDv>();

	TellerViewer tellerViewer = new TellerViewer();
	TellerEditor tellerEditor;
	Boolean isEditor = false;

	@UiField
	TellersTable tellers;
	@UiField
	HorizontalPanel tellerForm;

	public TellerList() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasNew(true);
		setHasEdit(true);
		setHasSave(false);
		setHasReload(true);
		setHasBack(false);
		//
		tellers.setSelectionHandler(new GridSelectionHandler() {

			@Override
			public void onSelection() {
				onSelectionHandler();
			}
		});
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
	public void setUsers(List<UserDv> users) {
		listUser.clear();
		listUser.addAll(users);
	}

	@Override
	public void setBranches(List<SimpleBranchDv> branches) {
		listBranch.clear();
		listBranch.addAll(branches);
	}

	@Override
	public void setSubBranches(List<SubBranchDv> subBranches) {
		listSubBranch.clear();
		listSubBranch.addAll(subBranches);
	}

	@Override
	public void setCoa(List<CoaDv> coa) {
		listCoa.clear();
		listCoa.addAll(coa);
		tellerEditor = new TellerEditor(listUser, listCoa, listBranch,
				listSubBranch);
	}

	@Override
	public void setTellers(List<TellerDv> tellers) {
		listTeller.clear();
		listTeller.addAll(tellers);
		this.tellers.clear();
		for (TellerDv user : tellers) {
			this.tellers.addRow(user);
		}
	}

	void onSelectionHandler() {
		setViewerData(tellers.getSelectedData());
	}

	private void setViewerData(TellerDv user) {
		tellerForm.clear();
		tellerForm.add(tellerViewer);
		tellerViewer.setUser(user);
		showBack(false);
		showSave(false);
		isEditor = false;
	}

	private void setEditorData(TellerDv user) {
		tellerForm.clear();
		tellerForm.add(tellerEditor);
		tellerEditor.setData(user);
		showBack(true);
		showSave(true);
		isEditor = true;
	}

	@Override
	public void editSelected() {
		setEditorData(tellers.getSelectedData());
	}

	@Override
	public void newUser() {
		setEditorData(new TellerDv());
	}

	@Override
	public void viewSelected() {
		setViewerData(tellers.getSelectedData());
	}

	@Override
	public void clearViewer() {
		setViewerData(new TellerDv());
	}

	@Override
	public TellerDv getUser() {
		if (isEditor) {
			return tellerEditor.getData();
		} else {
			return tellers.getSelectedData();
		}
	}

}
