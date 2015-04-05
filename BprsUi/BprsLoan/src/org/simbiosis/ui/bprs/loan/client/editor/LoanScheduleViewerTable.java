package org.simbiosis.ui.bprs.loan.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.SimpleGrid;
import org.simbiosis.ui.bprs.common.shared.LoanScheduleDv;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class LoanScheduleViewerTable extends SimpleGrid<LoanScheduleDv> {
	NumberFormat numberFormat = NumberFormat.getFormat("####,###.00");
	DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd-MM-yyyy");
	ColumnDef<LoanScheduleDv, String> colNr = new ColumnDef<LoanScheduleDv, String>(
			ColumnType.LABEL,"No","34px","30px") {

		@Override
		public String getDataValue(LoanScheduleDv data) {
			return data.getNr().toString();
		}
	};

	ColumnDef<LoanScheduleDv, String> colDate = new ColumnDef<LoanScheduleDv, String>(
			ColumnType.LABEL,"Tanggal","79px","75px") {

		@Override
		public String getDataValue(LoanScheduleDv data) {
			return dateFormat.format(data.getDate());
		}
	};

	ColumnDef<LoanScheduleDv, String> colPrincipal = new ColumnDef<LoanScheduleDv, String>(
			ColumnType.LABEL,"Pokok","124px","120px") {

		@Override
		public String getDataValue(LoanScheduleDv data) {
			return numberFormat.format(data.getPrincipal());
		}
	};

	ColumnDef<LoanScheduleDv, String> colMargin = new ColumnDef<LoanScheduleDv, String>(
			ColumnType.LABEL,"Marjin","124px","120px") {

		@Override
		public String getDataValue(LoanScheduleDv data) {
			return numberFormat.format(data.getMargin());
		}
	};

	ColumnDef<LoanScheduleDv, String> colTotal = new ColumnDef<LoanScheduleDv, String>(
			ColumnType.LABEL,"Total") {

		@Override
		public String getDataValue(LoanScheduleDv data) {
			return numberFormat.format(data.getTotal());
		}
	};

	public LoanScheduleViewerTable() {
		addColumn(colNr);
		addColumn(colDate);
		colPrincipal.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colPrincipal);
		colMargin.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colMargin);
		colTotal.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colTotal);
	}

}
