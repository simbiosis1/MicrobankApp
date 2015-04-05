package org.simbiosis.ui.bprs.teller.client.savingtrans;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.bprs.common.client.handler.GetSavingHandler;
import org.simbiosis.ui.bprs.common.client.savinghelper.DlgGetSaving;
import org.simbiosis.ui.bprs.common.shared.FindTransactionDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;
import org.simbiosis.ui.bprs.teller.client.AppFactory;
import org.simbiosis.ui.bprs.teller.client.rpc.AppService;
import org.simbiosis.ui.bprs.teller.client.rpc.AppServiceAsync;
import org.simbiosis.ui.bprs.teller.client.savingtrans.ISavingTransList.Activity;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class SavingTransListActivity extends Activity {

	private final AppServiceAsync tellerSrv = GWT
			.create(AppService.class);

	SavingTransListPlace myPlace;
	AppFactory appFactory;
	Activity ctactivity;

	public SavingTransListActivity(SavingTransListPlace myPlace,
			AppFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
		this.ctactivity = this;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ISavingTransList myForm = appFactory.getSavingTransList();
		myForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_SEARCH:
			onSearch();
			break;
		case FA_RELOAD:
			onReload();
			break;
		case FA_EXPORTPDF:
			onExportPdf();
			break;
		default:
			break;
		}
	}

	private void onExportPdf() {
		DateTimeFormat fmt = DateTimeFormat.getFormat("dd-MM-yyyy");
		ISavingTransList myForm = appFactory.getSavingTransList();
		Window.Location.replace("/BprsReportingService/getSavingTransPdf?id="
				+ myForm.getSaving().getId() + "&begin="
				+ fmt.format(myForm.getBeginDate()) + "&end="
				+ fmt.format(myForm.getEndDate()));
	}

	@Override
	public void createSavingTransList(long id, Date beginDate, Date endDate) {
		showLoading();
		tellerSrv.listSavingTransaction(getKey(), id, beginDate, endDate,
				new AsyncCallback<List<TransactionDv>>() {

					@Override
					public void onSuccess(List<TransactionDv> result) {
						hideLoading();
						ISavingTransList myForm = appFactory
								.getSavingTransList();
						FindTransactionDv findTransactionDv = new FindTransactionDv();
						findTransactionDv.getResultTable().clear();
						findTransactionDv.getResultTable().addAll(result);
						myForm.setListTransaction(findTransactionDv);
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listSavingTransaction");
					}
				});
	}

	private void onSearch() {
		GetSavingHandler handler = new GetSavingHandler() {

			@Override
			public void showSaving(SavingDv savingDv) {
				Long savingId = savingDv.getId();
				ISavingTransList myForm = appFactory.getSavingTransList();
				savingDv.copySavingData();
				myForm.setSaving(savingDv);
				ctactivity.createSavingTransList(savingId,
						myForm.getBeginDate(), myForm.getEndDate());
			}

			@Override
			public void showLoading() {
				ctactivity.showLoading();
			}

			@Override
			public void hideLoading() {
				ctactivity.hideLoading();
			}

		};
		DlgGetSaving dlgCariSimpanan = new DlgGetSaving(getKey(), false,
				handler);
		dlgCariSimpanan.center();
	}

	void onReload() {
		ISavingTransList myForm = appFactory.getSavingTransList();
		createSavingTransList(myForm.getSaving().getId(),
				myForm.getBeginDate(), myForm.getEndDate());
	}

}
