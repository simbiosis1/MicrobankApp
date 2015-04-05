package org.simbiosis.bprs.ui.config.client.teller;

import java.util.ArrayList;
import java.util.List;

import org.kembang.module.shared.SimpleBranchDv;
import org.kembang.module.shared.UserDv;
import org.simbiosis.bprs.ui.config.client.editor.BranchListBox;
import org.simbiosis.bprs.ui.config.client.editor.CoaListEditor;
import org.simbiosis.bprs.ui.config.client.editor.SubBranchListBox;
import org.simbiosis.bprs.ui.config.client.editor.UserListBox;
import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.SubBranchDv;
import org.simbiosis.bprs.ui.config.shared.TellerDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TellerEditor extends Composite implements Editor<TellerDv> {

	private static UserViewerUiBinder uiBinder = GWT
			.create(UserViewerUiBinder.class);

	interface UserViewerUiBinder extends UiBinder<Widget, TellerEditor> {
	}

	interface Driver extends SimpleBeanEditorDriver<TellerDv, TellerEditor> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	TextBox code;
	@UiField
	CoaListEditor coa;
	@UiField
	UserListBox user;
	@UiField
	BranchListBox branch;
	@UiField
	SubBranchListBox subBranch;

	//List<SimpleBranchDv> branchList = null;
	List<SubBranchDv> subBranchList = null;
	List<SubBranchDv> activeSubBranchList = new ArrayList<SubBranchDv>();

	public TellerEditor(List<UserDv> userList, List<CoaDv> coaList,
			List<SimpleBranchDv> bList, List<SubBranchDv> sbList) {
		initWidget(uiBinder.createAndBindUi(this));
		//
		coa.setList(coaList);
		user.setList(userList);
		//branchList = bList;
		branch.setList(bList);
		subBranchList = sbList;
		//
		setupSubBranch(bList.get(0).getId());
		//
		branch.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				setupSubBranch(branch.getValue());
			}
		});
		//
		driver.initialize(this);
	}

	private void setupSubBranch(long branchNow) {
		subBranch.clear();
		activeSubBranchList.clear();
		for (SubBranchDv sb : subBranchList) {
			if ((sb.getBranch() == branchNow) || (branchNow == 0L)) {
				activeSubBranchList.add(sb);
			}
		}
		subBranch.setList(activeSubBranchList);
	}

	public void setData(TellerDv teller) {
		setupSubBranch(teller.getBranch());
		driver.edit(teller);
	}

	public TellerDv getData() {
		return driver.flush();
	}
}
