package org.simbiosis.bprs.ui.config.client.gl;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.bprs.ui.config.client.AppFactory;
import org.simbiosis.bprs.ui.config.client.gl.IGlConfig.Activity;
import org.simbiosis.bprs.ui.config.client.places.GlConfig;
import org.simbiosis.bprs.ui.config.client.rpc.AppService;
import org.simbiosis.bprs.ui.config.client.rpc.AppServiceAsync;
import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.ConfigDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class GlConfigActivity extends Activity {

	private final AppServiceAsync glSrv = GWT.create(AppService.class);
	// private final SystemServiceAsync systemSrv = GWT
	// .create(SystemService.class);

	AppFactory appFactory;
	GlConfig place;

	public GlConfigActivity(GlConfig place, AppFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IGlConfig myForm = appFactory.getGlConfig();
		//
		if (appFactory.getAppStatus().isLogin()) {
			listCoaForTransaction();
		}
		myForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_EDIT:
			onEdit();
			break;
		case FA_SAVE:
			onSave();
			break;
		case FA_BACK:
			onBack();
			break;
		default:
			break;
		}
	}

	private void onBack() {
		showLoading();
		loadConfig();
	}

	void onEdit() {
		IGlConfig myForm = appFactory.getGlConfig();
		myForm.editData();
	}

	void onSave() {
		showLoading();
		IGlConfig myForm = appFactory.getGlConfig();
		ConfigDv dv = myForm.getData();
		glSrv.saveConfig(getKey(), dv, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : saveConfig");
			}

			@Override
			public void onSuccess(Void data) {
				hideLoading();
				Window.alert("Data konfigurasi sudah tersimpan");
				//
				showLoading();
				loadConfig();
			}
		});
	}

	private void listCoaForTransaction() {
		showLoading();
		glSrv.listCoaForTransaction(getKey(), new AsyncCallback<List<CoaDv>>() {

			@Override
			public void onSuccess(List<CoaDv> result) {
				IGlConfig myForm = appFactory.getGlConfig();
				myForm.setCoaList(result);
				loadConfig();
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listCoaForTransaction");
			}
		});
	}

	private void loadConfig() {
		glSrv.loadConfig(getKey(), new AsyncCallback<ConfigDv>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : loadConfig");
			}

			@Override
			public void onSuccess(ConfigDv result) {
				hideLoading();
				IGlConfig myForm = appFactory.getGlConfig();
				myForm.setData(result);
			}
		});
	}
}
