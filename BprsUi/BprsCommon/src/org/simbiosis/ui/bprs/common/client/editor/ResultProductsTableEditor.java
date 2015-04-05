package org.simbiosis.ui.bprs.common.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.SimpleGrid;
import org.simbiosis.ui.bprs.common.shared.DataDv;

public class ResultProductsTableEditor extends SimpleGrid<DataDv>{
	ColumnDef<DataDv, String> colNr = new ColumnDef<DataDv, String>(
			ColumnType.LABEL,"No","34px","30px") {

		@Override
		public String getDataValue(DataDv data) {
			return "" + data.getNr();
		}
	};

	ColumnDef<DataDv, String> colKode = new ColumnDef<DataDv, String>(
			ColumnType.LABEL,"Kode","104px","100px") {

		@Override
		public String getDataValue(DataDv data) {
			return data.getKode();
		}
	};

	ColumnDef<DataDv, String> colProduk = new ColumnDef<DataDv, String>(
			ColumnType.LABEL,"Produk","179px","175px") {

		@Override
		public String getDataValue(DataDv data) {
			return data.getStrProduk();
		}
	};

	ColumnDef<DataDv, String> colNama = new ColumnDef<DataDv, String>(
			ColumnType.LABEL,"Nama","154px","150px") {

		@Override
		public String getDataValue(DataDv data) {
			return data.getNama();
		}
	};

	ColumnDef<DataDv, String> colAlamat = new ColumnDef<DataDv, String>(
			ColumnType.LABEL,"Alamat") {

		@Override
		public String getDataValue(DataDv data) {
			return data.getAlamat();
		}
	};

	public ResultProductsTableEditor() {
		addColumn(colNr);
		addColumn(colKode);
		addColumn(colProduk);
		addColumn(colNama);
		addColumn(colAlamat);
	}
}
