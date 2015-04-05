package org.simbiosis.ui.bprs.security.client.auth;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.security.client.BprsSecurityFactory;
import org.simbiosis.ui.bprs.security.client.auth.IAuthList.Activity;
import org.simbiosis.ui.bprs.security.client.rpc.SecService;
import org.simbiosis.ui.bprs.security.client.rpc.SecServiceAsync;
import org.simbiosis.ui.bprs.security.shared.AuthDv;
import org.simbiosis.ui.bprs.security.shared.AuthListDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class AuthListActivity extends Activity {
	private final SecServiceAsync srv = GWT.create(SecService.class);

	AuthListPlace myPlace;
	BprsSecurityFactory appFactory;

	public AuthListActivity(AuthListPlace myPlace,
			BprsSecurityFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IAuthList myForm = appFactory.getAuthList();
		myForm.setActivity(this, appFactory.getAppStatus());
		if (appFactory.getAppStatus().isLogin()) {
			onReload();
		}
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_RELOAD:
			onReload();
			break;
		default:
			break;
		}
	}

	private void onReload() {
		showLoading();
		srv.listAuth(getKey(), new AsyncCallback<List<AuthDv>>() {

			@Override
			public void onSuccess(List<AuthDv> result) {
				hideLoading();
				IAuthList view = appFactory.getAuthList();
				view.setData(new AuthListDv(result));
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listAuth");
			}
		});
	}

	@Override
	public void authorize(AuthDv data) {
		showLoading();
		srv.authorize(getKey(), data.getId(), new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				hideLoading();
				onReload();
				Window.alert("Otorisasi sudah dilakukan");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : authorize");
			}
		});
	}

}
