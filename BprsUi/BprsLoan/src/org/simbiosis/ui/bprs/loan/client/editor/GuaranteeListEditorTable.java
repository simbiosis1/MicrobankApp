package org.simbiosis.ui.bprs.loan.client.editor;

import org.kembang.grid.client.AdvancedGrid;
import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.simbiosis.ui.bprs.common.shared.GuaranteeDv;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;

public class GuaranteeListEditorTable extends AdvancedGrid<GuaranteeDv> {

	NumberFormat numberFormat = NumberFormat.getFormat("####,###.00");
	DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd-MM-yyyy");

	FlexTable footer = null;

	ColumnDef<GuaranteeDv, String> colNr = new ColumnDef<GuaranteeDv, String>(
			ColumnType.LABEL, "Nr", "34px", "30px") {

		@Override
		public String getDataValue(GuaranteeDv data) {
			return data.getNr().toString();
		}
	};

	ColumnDef<GuaranteeDv, String> colCode = new ColumnDef<GuaranteeDv, String>(
			ColumnType.LABEL, "Kode", "84px", "80px") {

		@Override
		public String getDataValue(GuaranteeDv data) {
			return data.getCode();
		}
	};

	ColumnDef<GuaranteeDv, String> colType = new ColumnDef<GuaranteeDv, String>(
			ColumnType.LABEL, "Jenis", "124px", "120px") {

		@Override
		public String getDataValue(GuaranteeDv data) {
			return data.getStrType();
		}
	};

	ColumnDef<GuaranteeDv, String> colDoc = new ColumnDef<GuaranteeDv, String>(
			ColumnType.LABEL, "Dokumen", "124px", "120px") {

		@Override
		public String getDataValue(GuaranteeDv data) {
			return data.getNumber();
		}
	};

	ColumnDef<GuaranteeDv, String> colValue = new ColumnDef<GuaranteeDv, String>(
			ColumnType.LABEL, "Nilai") {

		@Override
		public String getDataValue(GuaranteeDv data) {
			return data.getStrAppraisalIntValue();
		}
	};

	public GuaranteeListEditorTable() {
		addColumn(colNr);
		addColumn(colCode);
		addColumn(colType);
		addColumn(colDoc);
		addColumn(colValue);
	}

}
