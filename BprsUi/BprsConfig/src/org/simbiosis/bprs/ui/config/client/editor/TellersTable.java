package org.simbiosis.bprs.ui.config.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.SimpleGrid;
import org.simbiosis.bprs.ui.config.shared.TellerDv;

public class TellersTable extends SimpleGrid<TellerDv> {

	ColumnDef<TellerDv, String> colNr = new ColumnDef<TellerDv, String>(
			ColumnType.LABEL, "No", "24px", "20px") {

		@Override
		public String getDataValue(TellerDv data) {
			return data.getNr().toString();
		}
	};

	ColumnDef<TellerDv, String> colCode = new ColumnDef<TellerDv, String>(
			ColumnType.LABEL, "Kode", "64px", "60px") {

		@Override
		public String getDataValue(TellerDv data) {
			return data.getCode();
		}
	};

	ColumnDef<TellerDv, String> colRealName = new ColumnDef<TellerDv, String>(
			ColumnType.LABEL, "Nama", "184px", "180px") {

		@Override
		public String getDataValue(TellerDv data) {
			return data.getName();
		}
	};

	ColumnDef<TellerDv, String> colBranch = new ColumnDef<TellerDv, String>(
			ColumnType.LABEL, "Cabang") {

		@Override
		public String getDataValue(TellerDv data) {
			return data.getStrBranch();
		}
	};

	public TellersTable() {
		addColumn(colNr);
		addColumn(colCode);
		addColumn(colRealName);
		addColumn(colBranch);
	}

}
