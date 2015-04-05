package org.simbiosis.ui.bprs.security.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.SimpleGrid;
import org.simbiosis.ui.bprs.security.shared.AuthDv;

public class AuthListTable extends SimpleGrid<AuthDv> {

	ColumnDef<AuthDv, String> colNr = new ColumnDef<AuthDv, String>(
			ColumnType.LABEL, "No", "34px", "30px") {

		@Override
		public String getDataValue(AuthDv data) {
			return "" + data.getNr();
		}
	};

	ColumnDef<AuthDv, String> colBranch = new ColumnDef<AuthDv, String>(
			ColumnType.LABEL, "Cabang", "154px", "150px") {

		@Override
		public String getDataValue(AuthDv data) {
			return data.getStrBranch();
		}
	};

	ColumnDef<AuthDv, String> colUser = new ColumnDef<AuthDv, String>(
			ColumnType.LABEL, "Pengguna", "154px", "150px") {

		@Override
		public String getDataValue(AuthDv data) {
			return data.getStrUser();
		}
	};

	ColumnDef<AuthDv, String> colDescription = new ColumnDef<AuthDv, String>(
			ColumnType.LABEL, "Keterangan") {

		@Override
		public String getDataValue(AuthDv data) {
			return data.getDescription();
		}
	};

	public AuthListTable() {
		addColumn(colNr);
		addColumn(colBranch);
		addColumn(colUser);
		addColumn(colDescription);
	}

}