package org.simbiosis.ui.bprs.cs.client.editor;

import java.util.ArrayList;
import java.util.List;

import org.kembang.grid.client.AdvancedGrid;
import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.ColumnValue;
import org.simbiosis.ui.bprs.cs.shared.SavingBlockirDv;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class SavingBlockirTable extends AdvancedGrid<SavingBlockirDv> {

	NumberFormat nf = NumberFormat.getFormat("####,###.00");

	ColumnDef<SavingBlockirDv, Integer> colType = new ColumnDef<SavingBlockirDv, Integer>(
			ColumnType.COMBO, "Jenis", "104px", "100px") {

		@Override
		public Integer getDataValue(SavingBlockirDv data) {
			return data.getType();
		}
	};

	ColumnDef<SavingBlockirDv, String> colDescription = new ColumnDef<SavingBlockirDv, String>(
			ColumnType.TEXT, "Keterangan", "254px", "250px") {

		@Override
		public String getDataValue(SavingBlockirDv data) {
			return data.getDescription();
		}
	};

	ColumnDef<SavingBlockirDv, String> colValue = new ColumnDef<SavingBlockirDv, String>(
			ColumnType.TEXT, "Nilai") {

		@Override
		public String getDataValue(SavingBlockirDv data) {
			return nf.format(data.getValue());
		}
	};

	private List<String> types = new ArrayList<String>();

	public SavingBlockirTable() {
		types.add("ANGSURAN");
		types.add("LAIN-LAIN");
		colType.setDataSource(types);
		colType.setListBoxHandler(new ColumnValue<SavingBlockirDv>() {

			@Override
			public void setDataValue(SavingBlockirDv data) {
				data.setType(getIndex());
			}
		});
		addColumn(colType);
		colDescription.setTextBoxHandler(new ColumnValue<SavingBlockirDv>() {

			@Override
			public void setDataValue(SavingBlockirDv data) {
				data.setDescription(getText());
			}
		});
		addColumn(colDescription);
		colValue.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		colValue.setTextBoxHandler(new ColumnValue<SavingBlockirDv>() {

			@Override
			public void setDataValue(SavingBlockirDv data) {
				data.setValue(getText().isEmpty() ? 0.00 : Double
						.parseDouble(getText()));
			}
		});
		addColumn(colValue);
	}
}
