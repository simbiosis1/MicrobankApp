package org.simbiosis.ui.bprs.admin.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.SimpleGrid;
import org.simbiosis.ui.bprs.admin.shared.TransferCollectiveDv;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class TransferCollectiveTable extends SimpleGrid<TransferCollectiveDv> {

	NumberFormat numberFormat = NumberFormat.getFormat("#,##0.00");

	FlexTable footer = null;

	ColumnDef<TransferCollectiveDv, String> colNr = new ColumnDef<TransferCollectiveDv, String>(
			ColumnType.LABEL, "Nr", "34px", "30px") {

		@Override
		public String getDataValue(TransferCollectiveDv data) {
			return data.getNr().toString();
		}
	};

	ColumnDef<TransferCollectiveDv, String> colCode = new ColumnDef<TransferCollectiveDv, String>(
			ColumnType.LABEL, "Rek asal", "84px", "80px") {

		@Override
		public String getDataValue(TransferCollectiveDv data) {
			return data.getCode();
		}
	};

	ColumnDef<TransferCollectiveDv, String> colName = new ColumnDef<TransferCollectiveDv, String>(
			ColumnType.LABEL, "Nama", "134px", "130px") {

		@Override
		public String getDataValue(TransferCollectiveDv data) {
			return data.getName();
		}
	};

	ColumnDef<TransferCollectiveDv, String> colNameSystem = new ColumnDef<TransferCollectiveDv, String>(
			ColumnType.LABEL, "Nama sistem", "134px", "130px") {

		@Override
		public String getDataValue(TransferCollectiveDv data) {
			return data.getSystemName();
		}
	};

	
	ColumnDef<TransferCollectiveDv, String> colCodeDest = new ColumnDef<TransferCollectiveDv, String>(
			ColumnType.LABEL, "Rek tujuan", "84px", "80px") {

		@Override
		public String getDataValue(TransferCollectiveDv data) {
			return data.getDestCode();
		}
	};

	ColumnDef<TransferCollectiveDv, String> colNameDest = new ColumnDef<TransferCollectiveDv, String>(
			ColumnType.LABEL, "Nama", "134px", "130px") {

		@Override
		public String getDataValue(TransferCollectiveDv data) {
			return data.getDestName();
		}
	};

	ColumnDef<TransferCollectiveDv, String> colNameSystemDest = new ColumnDef<TransferCollectiveDv, String>(
			ColumnType.LABEL, "Nama sistem", "134px", "130px") {

		@Override
		public String getDataValue(TransferCollectiveDv data) {
			return data.getDestSystemName();
		}
	};

	ColumnDef<TransferCollectiveDv, String> colValue = new ColumnDef<TransferCollectiveDv, String>(
			ColumnType.LABEL, "Nilai", "114px", "110px") {

		@Override
		public String getDataValue(TransferCollectiveDv data) {
			return numberFormat.format(data.getValue());
		}
	};

	ColumnDef<TransferCollectiveDv, String> colStatus = new ColumnDef<TransferCollectiveDv, String>(
			ColumnType.LABEL, "Status") {

		@Override
		public String getDataValue(TransferCollectiveDv data) {
			return data.getStatus();
		}
	};

	public TransferCollectiveTable() {
		addColumn(colNr);
		addColumn(colCode);
		addColumn(colName);
		addColumn(colNameSystem);
		addColumn(colCodeDest);
		addColumn(colNameDest);
		addColumn(colNameSystemDest);
		colValue.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colValue);
		addColumn(colStatus);
	}

}
