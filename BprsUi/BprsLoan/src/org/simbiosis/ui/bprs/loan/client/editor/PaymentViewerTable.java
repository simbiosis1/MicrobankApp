package org.simbiosis.ui.bprs.loan.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.SimpleGrid;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class PaymentViewerTable extends SimpleGrid<TransactionDv> {
	NumberFormat numberFormat = NumberFormat.getFormat("####,###.00");
	DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd-MM-yyyy");

	ColumnDef<TransactionDv, String> colNr = new ColumnDef<TransactionDv, String>(
			ColumnType.LABEL, "No", "34px", "30px") {

		@Override
		public String getDataValue(TransactionDv data) {
			return data.getNr().toString();
		}
	};

	ColumnDef<TransactionDv, String> colDate = new ColumnDef<TransactionDv, String>(
			ColumnType.LABEL, "Tanggal", "79px", "75px") {

		@Override
		public String getDataValue(TransactionDv data) {
			return dateFormat.format(data.getDate());
		}
	};

	ColumnDef<TransactionDv, String> colCode = new ColumnDef<TransactionDv, String>(
			ColumnType.LABEL, "Kode", "134px", "130px") {

		@Override
		public String getDataValue(TransactionDv data) {
			return data.getCode();
		}
	};
	ColumnDef<TransactionDv, String> colPrincipal = new ColumnDef<TransactionDv, String>(
			ColumnType.LABEL, "Pokok", "104px", "100px") {

		@Override
		public String getDataValue(TransactionDv data) {
			return numberFormat.format(data.getPrincipal());
		}
	};

	ColumnDef<TransactionDv, String> colMargin = new ColumnDef<TransactionDv, String>(
			ColumnType.LABEL, "Marjin", "104px", "100px") {

		@Override
		public String getDataValue(TransactionDv data) {
			return numberFormat.format(data.getMargin());
		}
	};

	ColumnDef<TransactionDv, String> colDiscount = new ColumnDef<TransactionDv, String>(
			ColumnType.LABEL, "Diskon", "104px", "100px") {

		@Override
		public String getDataValue(TransactionDv data) {
			return numberFormat.format(data.getDiscount());
		}
	};

	ColumnDef<TransactionDv, String> colTotal = new ColumnDef<TransactionDv, String>(
			ColumnType.LABEL, "Total") {

		@Override
		public String getDataValue(TransactionDv data) {
			return numberFormat.format(data.getTotal());
		}
	};

	public PaymentViewerTable() {
		addColumn(colNr);
		addColumn(colDate);
		addColumn(colCode);
		colPrincipal.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colPrincipal);
		colMargin.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colMargin);
		colDiscount.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colDiscount);
		colTotal.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colTotal);
	}

}
