package org.simbiosis.ui.bprs.teller.client.kolektif;

import java.util.List;

import org.kembang.grid.client.FlexTableHelper;
import org.simbiosis.ui.bprs.teller.client.editor.SavingUploadCollectiveTable;
import org.simbiosis.ui.bprs.teller.client.editor.TellerListEditor;
import org.simbiosis.ui.bprs.teller.shared.TellerDv;
import org.simbiosis.ui.bprs.teller.shared.UploadCollectiveDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;

public class SavingConfirmCollective extends Composite {

	UploadCollective parent;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, SavingConfirmCollective> {
	}

	@UiField
	SavingUploadCollectiveTable ctTable;
	@UiField
	TellerListEditor tellers;
	@UiField
	FlexTable transactionFooter;

	String[] footerText = { "", "Jumlah", "", "", "", "", "0", "0" };
	String[] widthsText = { "30px", "70px", "80px", "130px", "130px", "90px",
			"90px", "90px" };
	NumberFormat numberFormat = NumberFormat.getFormat("####,###.00");

	public SavingConfirmCollective() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		FlexTableHelper.setupHeader(transactionFooter, 8, footerText,
				widthsText);

	}

	public void setParent(UploadCollective parent) {
		this.parent = parent;
	}

	public void setData(List<UploadCollectiveDv> datas) {
		ctTable.clear();
		ctTable.setValue(datas);
		//
		double debit=0,kredit=0;
		for (UploadCollectiveDv data : datas) {
			debit += data.getDebit();
			kredit += data.getCredit();
		}
		transactionFooter.getCellFormatter().setHorizontalAlignment(0, 6,
				HasHorizontalAlignment.ALIGN_RIGHT);
		FlexTableHelper.setTextFooter(transactionFooter, 6,
				numberFormat.format(debit));
		transactionFooter.getCellFormatter().setHorizontalAlignment(0, 7,
				HasHorizontalAlignment.ALIGN_RIGHT);
		FlexTableHelper.setTextFooter(transactionFooter, 7,
				numberFormat.format(kredit));
	}

	public List<UploadCollectiveDv> getData() {
		return ctTable.getValue();
	}

	public void setTellers(List<TellerDv> coas) {
		tellers.setList(coas);
	}

	public Long getTeller() {
		return tellers.getValue();
	}

	@UiHandler("btnExecute")
	public void onExecute(ClickEvent e) {
		parent.executeUpload(tellers.getValue());
	}
}
