package org.simbiosis.cli.bi.lb;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ManualPPAPJam {

	public class DataPPAPJam {
		private String code;
		private String strJaminan;
		private String strPpap;
		private double jaminan;
		private double ppap;

		public DataPPAPJam() {
			jaminan = 0;
			ppap = 0;
		}

		public DataPPAPJam(String code, String strJaminan, String strPpap) {
			this.code = code;
			this.strJaminan = strJaminan;
			this.strPpap = strPpap;
		}

		public DataPPAPJam processData() {
			if (!strJaminan.isEmpty()) {
				jaminan = Double.parseDouble(strJaminan);
			}
			if (!strPpap.isEmpty()) {
				ppap = Double.parseDouble(strPpap);
			}
			return this;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getStrJaminan() {
			return strJaminan;
		}

		public void setStrJaminan(String strJaminan) {
			this.strJaminan = strJaminan;
		}

		public String getStrPpap() {
			return strPpap;
		}

		public void setStrPpap(String strPpap) {
			this.strPpap = strPpap;
		}

		public double getJaminan() {
			return jaminan;
		}

		public void setJaminan(double jaminan) {
			this.jaminan = jaminan;
		}

		public double getPpap() {
			return ppap;
		}

		public void setPpap(double ppap) {
			this.ppap = ppap;
		}

	}

	Map<String, DataPPAPJam> dataMap = new HashMap<String, DataPPAPJam>();

	public ManualPPAPJam() {

	}

	public void loadData() {
		try {
			FileInputStream fstream = new FileInputStream("ppapjam.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				if (!strLine.isEmpty()) {
					String items[] = strLine.split("\t");
					DataPPAPJam data = new DataPPAPJam(items[0].trim(), items[1].trim(),
							items[2].trim());
					data.processData();
					dataMap.put(data.getCode(), data.processData());
				}
			}
			// Close the input stream
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	DataPPAPJam getData(String loanCode) {
		DataPPAPJam result = dataMap.get(loanCode);
		return result == null ? new DataPPAPJam() : result;
	}
}
