package org.simbiosis.ui.bprs.loan.client.editor;

import java.util.Date;

import org.kembang.grid.client.AdvancedGrid;
import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.ColumnValue;
import org.simbiosis.ui.bprs.common.shared.LoanScheduleDv;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;

public class LoanScheduleEditorTable extends AdvancedGrid<LoanScheduleDv> {

	NumberFormat numberFormat = NumberFormat.getFormat("####,###.00");
	DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd-MM-yyyy");

	FlexTable footer = null;

	ColumnDef<LoanScheduleDv, String> colNr = new ColumnDef<LoanScheduleDv, String>(
			ColumnType.LABEL,"Nr","34px","30px") {

		@Override
		public String getDataValue(LoanScheduleDv data) {
			return data.getNr().toString();
		}
	};

	ColumnDef<LoanScheduleDv, Date> colDate = new ColumnDef<LoanScheduleDv, Date>(
			ColumnType.DATE,"Tanggal","94px","90px") {

		@Override
		public Date getDataValue(LoanScheduleDv data) {
			return data.getDate();
		}
	};

	ColumnDef<LoanScheduleDv, String> colPrincipal = new ColumnDef<LoanScheduleDv, String>(
			ColumnType.TEXT,"Pokok","204px","200px") {

		@Override
		public String getDataValue(LoanScheduleDv data) {
			return numberFormat.format(data.getPrincipal());
		}
	};

	ColumnDef<LoanScheduleDv, String> colMargin = new ColumnDef<LoanScheduleDv, String>(
			ColumnType.TEXT,"Marjin","204px","200px") {

		@Override
		public String getDataValue(LoanScheduleDv data) {
			return numberFormat.format(data.getMargin());
		}
	};

	ColumnDef<LoanScheduleDv, String> colTotal = new ColumnDef<LoanScheduleDv, String>(
			ColumnType.LABEL,"Total") {

		@Override
		public String getDataValue(LoanScheduleDv data) {
			return numberFormat.format(data.getTotal());
		}
	};

	public LoanScheduleEditorTable() {
		addColumn(colNr);
		colDate.setDateTimeFormat(dateFormat);
		colDate.setDateBoxHandler(new ColumnValue<LoanScheduleDv>() {

			@Override
			public void setDataValue(LoanScheduleDv data) {
				data.setDate(getDate());
			}
		});
		addColumn(colDate);
		colPrincipal.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		colPrincipal.setTextBoxHandler(new ColumnValue<LoanScheduleDv>() {

			@Override
			public void setDataValue(LoanScheduleDv data) {
				data.setPrincipal(numberFormat.parse(getText()));
				//
				setTotalValue(data);
			}
		});

		addColumn(colPrincipal);
		colMargin.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		colMargin.setTextBoxHandler(new ColumnValue<LoanScheduleDv>() {

			@Override
			public void setDataValue(LoanScheduleDv data) {
				data.setMargin(numberFormat.parse(getText()));
				//
				setTotalValue(data);
			}
		});
		addColumn(colMargin);
		colTotal.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colTotal);
	}

	void setTotalValue(LoanScheduleDv data) {
		double total = data.getPrincipal() + data.getMargin();
		Label label = (Label) getSelectedWidget(4);
		label.setText(numberFormat.format(total));
		data.setTotal(total);
	}

	public void setFooter(FlexTable footer) {
		this.footer = footer;
	}

}
