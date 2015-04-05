package org.simbiosis.bprs.ui.config.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.SimpleGrid;
import org.simbiosis.bprs.ui.config.shared.SubBranchDv;

public class SubBranchTable extends SimpleGrid<SubBranchDv> {

	ColumnDef<SubBranchDv, String> colNr = new ColumnDef<SubBranchDv, String>(
			ColumnType.LABEL, "No", "24px", "20px") {

		@Override
		public String getDataValue(SubBranchDv data) {
			return data.getNr().toString();
		}
	};

	ColumnDef<SubBranchDv, String> colBranch = new ColumnDef<SubBranchDv, String>(
			ColumnType.LABEL, "Cabang", "104px", "100px") {

		@Override
		public String getDataValue(SubBranchDv data) {
			return data.getStrBranch();
		}
	};

	ColumnDef<SubBranchDv, String> colCode = new ColumnDef<SubBranchDv, String>(
			ColumnType.LABEL, "Kode", "34px", "30px") {

		@Override
		public String getDataValue(SubBranchDv data) {
			return data.getCode();
		}
	};

	ColumnDef<SubBranchDv, String> colName = new ColumnDef<SubBranchDv, String>(
			ColumnType.LABEL, "Kas", "104px", "100px") {

		@Override
		public String getDataValue(SubBranchDv data) {
			return data.getName();
		}
	};

	ColumnDef<SubBranchDv, String> colAddress = new ColumnDef<SubBranchDv, String>(
			ColumnType.LABEL, "Alamat") {

		@Override
		public String getDataValue(SubBranchDv data) {
			return data.getAddress();
		}
	};

	public SubBranchTable() {
		addColumn(colNr);
		addColumn(colBranch);
		addColumn(colCode);
		addColumn(colName);
		addColumn(colAddress);
	}

}
