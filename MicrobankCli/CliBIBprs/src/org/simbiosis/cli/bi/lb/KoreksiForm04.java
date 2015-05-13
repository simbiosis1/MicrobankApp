package org.simbiosis.cli.bi.lb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.simbiosis.cli.bi.lib.StrUtils;

public class KoreksiForm04 {

	String newLine = "\r\n";
	String buffer = "";

	public static void main(String[] args) {
		KoreksiForm04 koreksi = new KoreksiForm04();
		koreksi.readFile();
		koreksi.writeFile();
		// bagiHasil.execute();
	}

	public KoreksiForm04() {

	}

	public void readFile() {
		ManualPPAPJam data = new ManualPPAPJam();
		data.loadData();
		try {
			FileInputStream fstream = new FileInputStream(
					"620113001042015_ori.exp");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				if (strLine.substring(0, 4).equalsIgnoreCase("BS04")) {
					String code = strLine.substring(4, 19);
					String depan = strLine.substring(0, 104);
					String nilai = strLine.substring(104, 116);
					String akhir = strLine.substring(116);
					Double dMargin = data.getData(code.trim()).getJaminan();
					if (dMargin != 0) {
						dMargin = dMargin / 1000;
						Long iMargin = Math.round(dMargin);
						nilai = StrUtils.lpadded(iMargin.toString(), 12, '0');
					}
					System.out.println(code + " - " + nilai);
					buffer += (depan + nilai + akhir + newLine);
				} else {
					buffer += (strLine + newLine);
				}
			}
			// Close the input stream
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeFile() {
		try {
			FileOutputStream fstream = new FileOutputStream(
					"620113001042015.exp");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					fstream));
			writer.write(buffer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
