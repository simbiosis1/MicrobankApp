package org.simbiosis.ui.bprs.common.client.savinghelper;

import java.util.List;

import org.kembang.editor.client.KembangDialogBox;
import org.simbiosis.ui.bprs.common.client.handler.GetSavingHandler;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankService;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankServiceAsync;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class DlgGetSaving extends KembangDialogBox {

	private final MicBankServiceAsync koperasiService = GWT
			.create(MicBankService.class);

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, DlgGetSaving> {
	}

	@UiField
	FindSaving cariSimpanan;

	String key;
	Boolean tellerTransaction;
	DataDv dataDv = null;
	GetSavingHandler handler;

	public DlgGetSaving(String key, Boolean tellerTransaction,
			GetSavingHandler handler) {
		super();
		//
		setWidget(uiBinder.createAndBindUi(this));
		setText("Pencarian data tabungan");
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
		this.tellerTransaction = tellerTransaction;
		this.handler = handler;
	}

	void onBtnSearch() {
		handler.showLoading();
		koperasiService.findSaving(key, tellerTransaction,
				cariSimpanan.isHasCode(), cariSimpanan.isHasName(),
				cariSimpanan.isHasDob(), cariSimpanan.getCode(),
				cariSimpanan.getName(), cariSimpanan.getDob(),
				new AsyncCallback<List<DataDv>>() {

					@Override
					public void onSuccess(List<DataDv> result) {
						CariDataDv dataPencarian = new CariDataDv();
						dataPencarian.getResultTable().addAll(result);
						cariSimpanan.showData(dataPencarian);
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
		dataDv = cariSimpanan.getSelectedData();
		showData(dataDv);
		hide();
	}

	void showData(DataDv dataDv) {
		if (dataDv != null) {
			Long simpananId = dataDv.getId();
			//
			handler.showLoading();
			koperasiService.getSaving(simpananId,
					new AsyncCallback<SavingDv>() {

						@Override
						public void onFailure(Throwable caught) {
							handler.hideLoading();
							Window.alert("Error : getSaving");
						}

						@Override
						public void onSuccess(SavingDv savingDv) {
							handler.hideLoading();
							handler.showSaving(savingDv);
						}
					});
		}
	}

}
