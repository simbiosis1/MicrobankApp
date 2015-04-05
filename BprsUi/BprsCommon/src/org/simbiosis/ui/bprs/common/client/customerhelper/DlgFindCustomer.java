package org.simbiosis.ui.bprs.common.client.customerhelper;

import java.util.List;

import org.kembang.editor.client.KembangDialogBox;
import org.simbiosis.ui.bprs.common.client.handler.FindCustomer;
import org.simbiosis.ui.bprs.common.client.handler.FindCustomerHandler;
import org.simbiosis.ui.bprs.common.client.handler.GetCustomer;
import org.simbiosis.ui.bprs.common.client.handler.GetCustomerHandler;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class DlgFindCustomer extends KembangDialogBox {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, DlgFindCustomer> {
	}

	@UiField
	CustomerFind cariAnggota;

	String key;
	DataDv dataDv = null;
	GetCustomerHandler dlgHandler;

	public DlgFindCustomer(String key, Boolean hasNew, GetCustomerHandler dlgHandler) {
		super();
		//
		setWidget(uiBinder.createAndBindUi(this));
		setText("Pencarian data nasabah");
		//
		if (hasNew){
			Button btnNew = new Button("Baru");
			btnNew.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					onBtnNew();
				}
			});
			addButton(btnNew);
		}
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
		this.dlgHandler = dlgHandler;
	}

	void onBtnNew() {
		dlgHandler.showSaving(null);
		hide();
	}

	void onBtnSearch() {
		FindCustomerHandler handler = new FindCustomerHandler() {

			@Override
			public void showLoading() {
				dlgHandler.showLoading();
			}

			@Override
			public void showCustomerList(List<DataDv> result) {
				CariDataDv dataPencarian = new CariDataDv();
				dataPencarian.getResultTable().addAll(result);
				cariAnggota.showData(dataPencarian);
				dlgHandler.hideLoading();
			}

			@Override
			public void hideLoading() {
				dlgHandler.hideLoading();
			}
		};
		FindCustomer findCustomer = new FindCustomer(key, handler);
		findCustomer.go(cariAnggota.isHasName(), cariAnggota.isHasDob(),
				cariAnggota.getName(), cariAnggota.getDob());
	}

	void onBtnSelect() {
		dataDv = cariAnggota.getSelectedData();

		GetCustomerHandler handler = new GetCustomerHandler() {

			@Override
			public void showSaving(SavingDv savingDv) {
				dlgHandler.showSaving(savingDv);
			}

			@Override
			public void showLoading() {
				dlgHandler.showLoading();
			}

			@Override
			public void hideLoading() {
				dlgHandler.hideLoading();
			}
		};

		GetCustomer getCustomer = new GetCustomer(handler);
		getCustomer.go(dataDv.getId());
		hide();
	}

}
