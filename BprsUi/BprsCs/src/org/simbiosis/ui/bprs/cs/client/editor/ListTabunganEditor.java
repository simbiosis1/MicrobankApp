package org.simbiosis.ui.bprs.cs.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.SimpleGrid;
import org.simbiosis.ui.bprs.common.shared.SavingDv;

public class ListTabunganEditor extends SimpleGrid<SavingDv>{
	ColumnDef<SavingDv, String> colNr = new ColumnDef<SavingDv, String>(
			ColumnType.LABEL,"No") {

		@Override
		public String getDataValue(SavingDv data) {
			return "" + data.getNr();
		}
	};

	ColumnDef<SavingDv, String> colKode = new ColumnDef<SavingDv, String>(
			ColumnType.LABEL,"Kode") {

		@Override
		public String getDataValue(SavingDv data) {
			return data.getCode();
		}
	};

	ColumnDef<SavingDv, String> colNama = new ColumnDef<SavingDv, String>(
			ColumnType.LABEL,"Nama") {

		@Override
		public String getDataValue(SavingDv data) {
			return data.getCustomer().getName();
		}
	};

	public ListTabunganEditor() {
		addColumn(colNr);
		addColumn(colKode);
		addColumn(colNama);
	}

}
