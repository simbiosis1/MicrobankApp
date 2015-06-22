package org.simbiosis.ui.bprs.teller.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.SimpleGrid;
import org.simbiosis.ui.bprs.teller.shared.UploadCollectiveDv;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class SavingUploadCollectiveTable extends SimpleGrid<UploadCollectiveDv> {

	NumberFormat numberFormat = NumberFormat.getFormat("#,##0.00");

	FlexTable footer = null;

	ColumnDef<UploadCollectiveDv, String> colNr = new ColumnDef<UploadCollectiveDv, String>(
			ColumnType.LABEL, "Nr", "34px", "30px") {

		@Override
		public String getDataValue(UploadCollectiveDv data) {
			return data.getNr().toString();
		}
	};

	ColumnDef<UploadCollectiveDv, String> colRefCode = new ColumnDef<UploadCollectiveDv, String>(
			ColumnType.LABEL, "Slip", "74px", "70px") {

		@Override
		public String getDataValue(UploadCollectiveDv data) {
			return data.getRefCode();
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
			ColumnType.LABEL, "Nama sistem", "154px", "150px") {

		@Override
		public String getDataValue(UploadCollectiveDv data) {
			return data.getSystemName();
		}
	};

	ColumnDef<UploadCollectiveDv, String> colDescription = new ColumnDef<UploadCollectiveDv, String>(
			ColumnType.LABEL, "Keterangan", "104px", "100px") {

		@Override
		public String getDataValue(UploadCollectiveDv data) {
			return data.getDescription();
		}
	};

	ColumnDef<UploadCollectiveDv, String> colDebit = new ColumnDef<UploadCollectiveDv, String>(
			ColumnType.LABEL, "Debit", "94px", "90px") {

		@Override
		public String getDataValue(UploadCollectiveDv data) {
			return numberFormat.format(data.getDebit());
		}
	};

	ColumnDef<UploadCollectiveDv, String> colCredit = new ColumnDef<UploadCollectiveDv, String>(
			ColumnType.LABEL, "Kredit", "94px", "90px") {

		@Override
		public String getDataValue(UploadCollectiveDv data) {
			return numberFormat.format(data.getCredit());
		}
	};

	ColumnDef<UploadCollectiveDv, String> colStatus = new ColumnDef<UploadCollectiveDv, String>(
			ColumnType.LABEL, "Status") {

		@Override
		public String getDataValue(UploadCollectiveDv data) {
			switch (data.getStatus()) {
			case 1:
				return "REKENING TIDAK ADA";
			case 2:
				return "MASALAH NILAI DEBIT KREDIT";
			case 3:
				return "MASALAH SALDO PENARIKAN";
			default:
				return "";
			}
		}
	};

	public SavingUploadCollectiveTable() {
		addColumn(colNr);
		addColumn(colRefCode);
		addColumn(colCode);
		addColumn(colName);
		addColumn(colNameSystem);
		addColumn(colDescription);
		colDebit.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colDebit);
		colCredit.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colCredit);
		addColumn(colStatus);
	}

}
