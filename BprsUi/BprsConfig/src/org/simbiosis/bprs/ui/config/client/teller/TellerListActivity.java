package org.simbiosis.bprs.ui.config.client.teller;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.kembang.module.client.rpc.SystemService;
import org.kembang.module.client.rpc.SystemServiceAsync;
import org.kembang.module.shared.SimpleBranchDv;
import org.kembang.module.shared.UserDv;
import org.simbiosis.bprs.ui.config.client.AppFactory;
import org.simbiosis.bprs.ui.config.client.rpc.AppService;
import org.simbiosis.bprs.ui.config.client.rpc.AppServiceAsync;
import org.simbiosis.bprs.ui.config.client.teller.ITeller.Activity;
import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.SubBranchDv;
import org.simbiosis.bprs.ui.config.shared.TellerDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class TellerListActivity extends Activity {

	private final AppServiceAsync appSrv = GWT.create(AppService.class);
	private final SystemServiceAsync systemSrv = GWT
			.create(SystemService.class);

	Place myPlace;
	AppFactory appFactory;

	public TellerListActivity(Place myPlace, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ITeller myForm = appFactory.getTeller();
		myForm.setActivity(this, appFactory.getAppStatus());
		if (appFactory.getAppStatus().isLogin()) {
			loadCommonData();
		}
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_NEW:
			onNew();
			break;
		case FA_EDIT:
			onEdit();
			break;
		case FA_SAVE:
			onSave();
			break;
		case FA_RELOAD:
			onReload();
			break;
		case FA_BACK:
			onBack();
			break;
		default:
			break;
		}
	}

	private void onNew() {
		ITeller myForm = appFactory.getTeller();
		myForm.newUser();
	}

	private void onReload() {
		loadCoa();
		ITeller myForm = appFactory.getTeller();
		myForm.clearViewer();
	}

	private void onSave() {
		ITeller myForm = appFactory.getTeller();
		TellerDv dv = myForm.getUser();
		showLoading();
		appSrv.saveTeller(getKey(), dv, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				hideLoading();
				Window.alert("Data sudah disimpan");
				onReload();
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : saveTeller");
			}
		});

	}

	private void onBack() {
		ITeller myForm = appFactory.getTeller();
		myForm.viewSelected();
	}

	private void onEdit() {
		ITeller myForm = appFactory.getTeller();
		myForm.editSelected();
	}

	private void loadCommonData() {

		showLoading();
		appSrv.listUsers(getKey(), new AsyncCallback<List<UserDv>>() {

			@Override
			public void onSuccess(List<UserDv> result) {
				ITeller myForm = appFactory.getTeller();
				myForm.setUsers(result);
				loadBranches();
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listUser");
			}
		});

	}

	private void loadBranches() {
		systemSrv.listSimpleBranch(getKey(),
				new AsyncCallback<List<SimpleBranchDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listBranch");
					}

					@Override
					public void onSuccess(List<SimpleBranchDv> result) {
						ITeller myForm = appFactory.getTeller();
						myForm.setBranches(result);
						loadSubBranches();
					}
				});

		loadCoa();
		loadTeller();
	}

	private void loadSubBranches() {

		appSrv.listSubBranch(getKey(), new AsyncCallback<List<SubBranchDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listBranch");
			}

			@Override
			public void onSuccess(List<SubBranchDv> result) {
				// Tambah semua
				SubBranchDv all = new SubBranchDv();
				all.setId(0L);
				all.setBranch(0L);
				all.setName("SEMUA KAS");
				result.add(all);
				//
				ITeller myForm = appFactory.getTeller();
				myForm.setSubBranches(result);
			}
		});
	}

	private void loadCoa() {
		appSrv.listCoaForTransaction(getKey(),
				new AsyncCallback<List<CoaDv>>() {

					@Override
					public void onSuccess(List<CoaDv> result) {
						ITeller myForm = appFactory.getTeller();
						myForm.setCoa(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listCoa2");
					}
				});
	}

	private void loadTeller() {
		appSrv.listTeller(getKey(), new AsyncCallback<List<TellerDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listTeller");
			}

			@Override
			public void onSuccess(List<TellerDv> result) {
				ITeller myForm = appFactory.getTeller();
				myForm.setTellers(result);
				hideLoading();
			}
		});
	}
}
