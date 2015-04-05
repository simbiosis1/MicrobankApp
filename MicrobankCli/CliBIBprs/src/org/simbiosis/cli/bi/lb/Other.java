package org.simbiosis.cli.bi.lb;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Other {

	private String newLine = "";
	private Map<String, List<String>> labulMap = new HashMap<String, List<String>>();

	public Other(String newLine) {
		this.newLine = newLine;
	}

	public void readLabulUsed(String labulForms) {
		try {
			FileInputStream fstream = new FileInputStream(
					"LABUL_BULAN_LALU.exp");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				String prefix = strLine.substring(0, 4);
				if (labulForms.contains(prefix)) {
					List<String> contentList = labulMap.get(prefix);
					if (contentList == null) {
						contentList = new ArrayList<String>();
					}
					contentList.add(strLine);
					labulMap.put(prefix, contentList);
				}
			}
			// Close the input stream
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String create(String prefix) {
		String result = "";
		List<String> contentList = labulMap.get(prefix);
		if (contentList != null) {
			for (String strLine : contentList) {
				result += (strLine + newLine);
			}
		}
		return result;
	}

}
