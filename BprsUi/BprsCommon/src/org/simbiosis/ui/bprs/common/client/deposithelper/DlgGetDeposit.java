package org.simbiosis.ui.bprs.common.client.deposithelper;

import java.util.List;

import org.kembang.editor.client.KembangDialogBox;
import org.simbiosis.ui.bprs.common.client.handler.GetDepositHandler;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankService;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankServiceAsync;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.DepositDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class DlgGetDeposit extends KembangDialogBox {

	private final MicBankServiceAsync comService = GWT
			.create(MicBankService.class);

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, DlgGetDeposit> {
	}

	@UiField
	FindDeposit findDeposit;

	String key;
	DataDv dataDv = null;
	GetDepositHandler handler;

	public DlgGetDeposit(String key, GetDepositHandler handler) {
		super();
		//
		setWidget(uiBinder.createAndBindUi(this));
		setText("Pencarian data deposito");
		//
		Button btnSearch = new Button("Cari");
		btnSearch.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onBtnSearch();
			}
		});
		addButton(btnSearch);
		//
		Button btnSelect = new Button("Pilih");
		btnSelect.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onBtnSelect();
			}
		});
		addButton(btnSelect);
		//
		Button btnCancel = new Button("Batal");
		btnCancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		addButton(btnCancel);
		//
		this.key = key;
		this.handler = handler;
	}

	void onBtnSearch() {
		handler.showLoading();
		comService.findDeposit(key, findDeposit.isHasCode(),
				findDeposit.isHasName(), findDeposit.isHasDob(),
				findDeposit.getCode(), findDeposit.getName(),
				findDeposit.getDob(), new AsyncCallback<List<DataDv>>() {

					@Override
					public void onSuccess(List<DataDv> result) {
						CariDataDv dataPencarian = new CariDataDv();
						dataPencarian.getResultTable().addAll(result);
						findDeposit.showData(dataPencarian);
						handler.hideLoading();
					}

					@Override
					public void onFailure(Throwable caught) {
						handler.hideLoading();
						Window.alert("Error : findCustomer");
					}
				});
	}

	void onBtnSelect() {
		dataDv = findDeposit.getSelectedData();
		showData(dataDv);
		hide();
	}

	void showData(DataDv dataDv) {
		if (dataDv != null) {
			Long simpananId = dataDv.getId();
			//
			handler.showLoading();
			comService.getDeposit(simpananId, new AsyncCallback<DepositDv>() {

				@Override
				public void onFailure(Throwable caught) {
					handler.hideLoading();
					Window.alert("Error : getSaving");
				}

				@Override
				public void onSuccess(DepositDv savingDv) {
					handler.hideLoading();
					handler.showDeposit(savingDv);
				}
			});
		}
	}
}
