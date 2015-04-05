package org.simbiosis.bprs.ui.config.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.SimpleGrid;
import org.simbiosis.bprs.ui.config.shared.ProductDv;

public class ProductTable extends SimpleGrid<ProductDv> {
	ColumnDef<ProductDv, String> colNr = new ColumnDef<ProductDv, String>(
			ColumnType.LABEL, "No", "24px", "20px") {

		@Override
		public String getDataValue(ProductDv data) {
			return data.getNr().toString();
		}
	};

	ColumnDef<ProductDv, String> colKode = new ColumnDef<ProductDv, String>(
			ColumnType.LABEL, "Kode", "34px", "30px") {

		@Override
		public String getDataValue(ProductDv data) {
			return data.getCode();
		}
	};
	ColumnDef<ProductDv, String> colName = new ColumnDef<ProductDv, String>(
			ColumnType.LABEL, "Nama") {

		@Override
		public String getDataValue(ProductDv data) {
			return data.getName();
		}
	};

	public ProductTable() {
		addColumn(colNr);
		addColumn(colKode);
		addColumn(colName);
	}

}
