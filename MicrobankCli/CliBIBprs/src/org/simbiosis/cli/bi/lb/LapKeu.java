package org.simbiosis.cli.bi.lb;

import org.simbiosis.cli.base.MicrobankReportClient;
import org.simbiosis.cli.bi.lib.StrUtils;
import org.simbiosis.cli.bi.model.Konversi;
import org.simbiosis.cli.bi.model.LapKeuDv;

public class LapKeu {

	String newLine;
	String kode = "BS0";
	Integer line = 1;

	Konversi konversi;

	public LapKeu(MicrobankReportClient reportClient, int month, int year,
			String newLine) {
		this.newLine = newLine;
		konversi = new Konversi(reportClient, month, year);
	}

	public void retrieve() {
		konversi.retrieve();
	}

	String createString(LapKeuDv data, int type) {
		String value = StrUtils.lpadded(data.getValue().toString(), 12, '0');
		String strLine = StrUtils.lpadded(line.toString(), 5, '0');
		line++;
		return kode + type + data.getCoa() + value + strLine;
	}

	String create01() {
		String buffer = "";
		for (LapKeuDv data : konversi.getBi1LapKeu()) {
			buffer += createString(data, 1) + newLine;
		}
		return buffer;
	}

	String create02() {
		String buffer = "";
		for (LapKeuDv data : konversi.getBi2LapKeu()) {
			buffer += createString(data, 2) + newLine;
		}
		return buffer;
	}

	void resetLine() {
		line = 1;
	}

}
