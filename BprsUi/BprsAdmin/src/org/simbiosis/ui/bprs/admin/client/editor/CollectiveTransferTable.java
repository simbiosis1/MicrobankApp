package org.simbiosis.ui.bprs.admin.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.SimpleGrid;
import org.simbiosis.ui.bprs.admin.shared.TransferCollectiveDv;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class CollectiveTransferTable extends SimpleGrid<TransferCollectiveDv> {

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
			ColumnType.LABEL, "No rek", "84px", "80px") {

		@Override
		public String getDataValue(TransferCollectiveDv data) {
			return data.getCode();
		}
	};

	ColumnDef<TransferCollectiveDv, String> colName = new ColumnDef<TransferCollectiveDv, String>(
			ColumnType.LABEL, "Nama", "154px", "150px") {

		@Override
		public String getDataValue(TransferCollectiveDv data) {
			return data.getName();
		}
	};

	ColumnDef<TransferCollectiveDv, String> colNameSystem = new ColumnDef<TransferCollectiveDv, String>(
			ColumnType.LABEL, "Nama sistem", "154px", "150px") {

		@Override
		public String getDataValue(TransferCollectiveDv data) {
			return data.getSystemName();
		}
	};

	ColumnDef<TransferCollectiveDv, String> colValue = new ColumnDef<TransferCollectiveDv, String>(
			ColumnType.LABEL, "Nilai", "154px", "150px") {

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

	public CollectiveTransferTable() {
		addColumn(colNr);
		addColumn(colCode);
		addColumn(colName);
		addColumn(colNameSystem);
		colValue.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colValue);
		addColumn(colStatus);
	}

}
