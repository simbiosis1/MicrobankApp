package org.simbiosis.ui.bprs.common.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.SimpleGrid;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class ResultTransactionTableEditor extends SimpleGrid<TransactionDv> {

	NumberFormat numberFormat = NumberFormat.getFormat("####,###.00");
	DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd-MM-yyyy");

	ColumnDef<TransactionDv, String> colNr = new ColumnDef<TransactionDv, String>(
			ColumnType.LABEL, "No", "34px", "30px") {

		@Override
		public String getDataValue(TransactionDv data) {
			return "" + data.getNr();
		}
	};

	ColumnDef<TransactionDv, String> colDate = new ColumnDef<TransactionDv, String>(
			ColumnType.LABEL, "Tanggal", "104px", "100px") {

		@Override
		public String getDataValue(TransactionDv data) {
			return dateFormat.format(data.getDate());
		}
	};

	ColumnDef<TransactionDv, String> colKode = new ColumnDef<TransactionDv, String>(
			ColumnType.LABEL, "Kode", "104px", "100px") {

		@Override
		public String getDataValue(TransactionDv data) {
			return data.getCode();
		}
	};

	ColumnDef<TransactionDv, String> colType = new ColumnDef<TransactionDv, String>(
			ColumnType.LABEL, "Jns", "34px", "30px") {

		@Override
		public String getDataValue(TransactionDv data) {
			return data.getStrType();
		}
	};

	ColumnDef<TransactionDv, String> colDescription = new ColumnDef<TransactionDv, String>(
			ColumnType.LABEL, "Keterangan") {

		@Override
		public String getDataValue(TransactionDv data) {
			return data.getDescription();
		}
	};

	ColumnDef<TransactionDv, String> colDebit = new ColumnDef<TransactionDv, String>(
			ColumnType.LABEL, "Debit", "104px", "100px") {

		@Override
		public String getDataValue(TransactionDv data) {
			return (data.getDebit() == 0) ? "" : numberFormat.format(data
					.getDebit());
		}
	};

	ColumnDef<TransactionDv, String> colCredit = new ColumnDef<TransactionDv, String>(
			ColumnType.LABEL, "Kredit", "104px", "100px") {

		@Override
		public String getDataValue(TransactionDv data) {
			return (data.getCredit() == 0) ? "" : numberFormat.format(data
					.getCredit());
		}
	};

	ColumnDef<TransactionDv, String> colTotal = new ColumnDef<TransactionDv, String>(
			ColumnType.LABEL, "Total", "104px", "100px") {

		@Override
		public String getDataValue(TransactionDv data) {
			return numberFormat.format(data.getTotal());
		}
	};

	public ResultTransactionTableEditor() {
		addColumn(colNr);
		addColumn(colDate);
		addColumn(colKode);
		addColumn(colType);
		addColumn(colDescription);
		colDebit.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colDebit);
		colCredit.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colCredit);
		colTotal.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colTotal);
	}
}
