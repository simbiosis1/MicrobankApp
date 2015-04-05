package org.simbiosis.ui.bprs.common.client.loanhelper;

import java.util.List;

import org.kembang.editor.client.KembangDialogBox;
import org.simbiosis.ui.bprs.common.client.handler.GetLoanHandler;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankService;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankServiceAsync;
import org.simbiosis.ui.bprs.common.shared.CariDataDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.LoanDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class DlgGetLoan extends KembangDialogBox {

	private final MicBankServiceAsync koperasiService = GWT
			.create(MicBankService.class);

	private static DlgGetLoanUiBinder uiBinder = GWT
			.create(DlgGetLoanUiBinder.class);

	interface DlgGetLoanUiBinder extends UiBinder<Widget, DlgGetLoan> {
	}

	@UiField
	FindLoan findLoan;

	String key;
	DataDv dataDv = null;
	GetLoanHandler handler;

	public DlgGetLoan(String key, GetLoanHandler handler) {
		super();
		//
		setWidget(uiBinder.createAndBindUi(this));
		setText("Pencarian data pembiayaan");
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
		koperasiService.findLoan(key, findLoan.isHasCode(),
				findLoan.isHasName(), findLoan.isHasDob(), findLoan.getCode(),
				findLoan.getName(), findLoan.getDob(),
				new AsyncCallback<List<DataDv>>() {

					@Override
					public void onSuccess(List<DataDv> result) {
						CariDataDv dataPencarian = new CariDataDv();
						dataPencarian.getResultTable().addAll(result);
						findLoan.showData(dataPencarian);
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
		dataDv = findLoan.getSelectedData();
		showData(dataDv);
		hide();
	}

	void showData(DataDv dataDv) {
		if (dataDv != null) {
			Long simpananId = dataDv.getId();
			//
			handler.showLoading();
			koperasiService.getLoan(simpananId, new AsyncCallback<LoanDv>() {

				@Override
				public void onFailure(Throwable caught) {
					handler.hideLoading();
					Window.alert("Error : getSaving");
				}

				@Override
				public void onSuccess(LoanDv savingDv) {
					handler.hideLoading();
					handler.showLoan(savingDv);
				}
			});
		}
	}

}
