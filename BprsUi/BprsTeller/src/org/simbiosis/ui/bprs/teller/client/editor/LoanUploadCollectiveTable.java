package org.simbiosis.ui.bprs.teller.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.SimpleGrid;
import org.simbiosis.ui.bprs.teller.shared.UploadCollectiveDv;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class LoanUploadCollectiveTable extends SimpleGrid<UploadCollectiveDv> {

	NumberFormat numberFormat = NumberFormat.getFormat("#,##0.00");

	FlexTable footer = null;

	ColumnDef<UploadCollectiveDv, String> colNr = new ColumnDef<UploadCollectiveDv, String>(
			ColumnType.LABEL, "Nr", "34px", "30px") {

		@Override
		public String getDataValue(UploadCollectiveDv data) {
			return data.getNr().toString();
		}
	};

	ColumnDef<UploadCollectiveDv, String> colCode = new ColumnDef<UploadCollectiveDv, String>(
			ColumnType.LABEL, "No rek", "84px", "80px") {

		@Override
		public String getDataValue(UploadCollectiveDv data) {
			return data.getCode();
		}
	};

	ColumnDef<UploadCollectiveDv, String> colName = new ColumnDef<UploadCollectiveDv, String>(
			ColumnType.LABEL, "Nama", "154px", "150px") {

		@Override
		public String getDataValue(UploadCollectiveDv data) {
			return data.getName();
		}
	};

	ColumnDef<UploadCollectiveDv, String> colNameSystem = new ColumnDef<UploadCollectiveDv, String>(
			ColumnType.LABEL, "Nama system", "154px", "150px") {

		@Override
		public String getDataValue(UploadCollectiveDv data) {
			return data.getSystemName();
		}
	};

	ColumnDef<UploadCollectiveDv, String> colDebit = new ColumnDef<UploadCollectiveDv, String>(
			ColumnType.LABEL, "Debit", "104px", "100px") {

		@Override
		public String getDataValue(UploadCollectiveDv data) {
			return numberFormat.format(data.getDebit());
		}
	};

	ColumnDef<UploadCollectiveDv, String> colCredit = new ColumnDef<UploadCollectiveDv, String>(
			ColumnType.LABEL, "Debit", "104px", "100px") {

		@Override
		public String getDataValue(UploadCollectiveDv data) {
			return numberFormat.format(data.getDebit());
		}
	};

	public LoanUploadCollectiveTable() {
		addColumn(colNr);
		addColumn(colCode);
		addColumn(colName);
		addColumn(colNameSystem);
		colDebit.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colDebit);
		colCredit.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colCredit);
	}

}
