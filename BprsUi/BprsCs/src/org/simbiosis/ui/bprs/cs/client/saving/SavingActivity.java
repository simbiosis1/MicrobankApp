package org.simbiosis.ui.bprs.cs.client.saving;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.client.handler.FindSaving;
import org.simbiosis.ui.bprs.common.client.handler.FindSavingHandler;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.cs.client.AppFactory;
import org.simbiosis.ui.bprs.cs.client.saving.ISaving.Activity;
import org.simbiosis.ui.bprs.cs.client.savinginput.ISavingInput;
import org.simbiosis.ui.bprs.cs.client.savinginput.SavingInputActivity;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class SavingActivity extends Activity {

	SavingActivity activity;
	SavingPlace myPlace;
	AppFactory appFactory;
	SavingInputActivity inputActivity;

	public SavingActivity(SavingPlace myPlace, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
		activity = this;
	}

	private SavingInputActivity getInputActivity() {
		if (inputActivity == null) {
			inputActivity = new SavingInputActivity(new SavingPlace(""),
					appFactory);
		}
		return inputActivity;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ISaving myForm = appFactory.getListSaving();
		myForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_NEW:
			onNew();
			break;
		case FA_VIEW:
			onView();
			break;
		case FA_SEARCH:
			onSearch();
			break;
		default:
			break;
		}
	}

	void onNew() {
		getInputActivity().newData();
	}

	void onView() {
		showLoading();
		DataDv selectedData = appFactory.getListSaving().getSelectedData();
		ISavingInput viewerForm = appFactory.getSavingViewer();
		inputActivity.showData(selectedData);
		appFactory.showApplication(null, viewerForm.getFormWidget());
	}

	void onSearch() {
		showLoading();
		ISaving myForm = appFactory.getListSaving();
		myForm.searchData();
	}

	@Override
	public void searchData(Boolean hasCode, String code, Boolean hasName,
			Boolean hasDob, String name, Date dob) {
		FindSavingHandler handler = new FindSavingHandler() {

			@Override
			public void showSavingList(List<DataDv> dataDv) {
				CariDataDv dataPencarian = new CariDataDv();
				dataPencarian.getResultTable().addAll(dataDv);
				ISaving myForm = appFactory.getListSaving();
				myForm.showData(dataPencarian);
			}

			@Override
			public void showLoading() {
				activity.showLoading();
			}

			@Override
			public void hideLoading() {
				activity.hideLoading();
			}

		};
		FindSaving findSaving = new FindSaving(getKey(), false, handler);
		findSaving.go(hasCode, hasName, hasDob, code, name, dob);
	}

	@Override
	public void initViewerEditor() {
		ISavingInput viewerForm = appFactory.getSavingViewer();
		viewerForm.setActivity(getInputActivity(), appFactory.getAppStatus());
		ISavingInput editorForm = appFactory.getSavingEditor();
		editorForm.setActivity(getInputActivity(), appFactory.getAppStatus());
	}

}
