package org.simbiosis.cli.financial.lib;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.MicrobankCoreClient;
import org.simbiosis.cli.base.MicrobankReportClient;
import org.simbiosis.gl.model.FinancialRpt;
import org.simbiosis.gl.model.FinancialStartRpt;
import org.simbiosis.system.ConfigDto;

public class StartValue {
	String strEndDate;
	String year;
	long coaLRTL;
	long coaLR;
	boolean periodStart = true;
	MicrobankCoreClient coreClient = null;
	MicrobankReportClient reportClient = null;

	Map<Long, NeracaItem> neracaMap = new HashMap<Long, NeracaItem>();
	List<NeracaItem> itemList = new ArrayList<NeracaItem>();
	List<Long> branches = new ArrayList<Long>();

	public StartValue(MicrobankCoreClient coreClient,
			MicrobankReportClient reportClient, String year) {
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");

		this.coreClient = coreClient;
		this.reportClient = reportClient;
		this.year = year;
		// Buat endDate
		DateTime lastDateLastYear = new DateTime().dayOfYear()
				.withMinimumValue().minusDays(1);
		strEndDate = sdf.print(lastDateLastYear);
	}

	public void execute() {
		createBranches();
		createConfig();
		clearStartValue();
		for (Long branch : branches) {
			System.out.println("Buat start value");
			createStartValue(branch);
			System.out.println("Simpan start value");
			saveStartValue(branch);
		}
	}

	void createStartValue(Long branch) {
		// Clear
		neracaMap.clear();
		itemList.clear();
		// Neraca
		List<FinancialRpt> finReports = listFinReport(branch);
		createStartValue(finReports);
	}

	private void clearStartValue() {
		//
		reportClient.sendRawData("deleteFinancialStart", year);
	}

	private void createStartValue(List<FinancialRpt> finReports) {
		for (FinancialRpt finReport : finReports) {
			if (finReport.getGroup() == 1 || finReport.getGroup() == 2) {
				NeracaItem item = null;
				if (finReport.getId().getCoa() == coaLR
						|| finReport.getId().getCoa() == coaLRTL) {
					item = neracaMap.get(coaLRTL);
					if (item == null) {
						item = new NeracaItem();
						item.setCoa(coaLRTL);
						item.setGroup(finReport.getGroup());
						item.setCode(finReport.getCoaCode());
						item.setDescription(finReport.getCoaDescription());
						item.setStartValue(finReport.getEndValue());
						item.setFactor(finReport.getFactor());
					} else {
						item.setStartValue(item.getStartValue()
								+ finReport.getEndValue());
					}
				} else {
					item = new NeracaItem();
					item.setCoa(finReport.getId().getCoa());
					item.setGroup(finReport.getGroup());
					item.setCode(finReport.getCoaCode());
					item.setDescription(finReport.getCoaDescription());
					item.setStartValue(finReport.getEndValue()
							* finReport.getFactor());
					item.setFactor(finReport.getFactor());
				}
				neracaMap.put(item.getCoa(), item);
			}
		}

	}

	void saveStartValue(Long branch) {
		List<FinancialStartRpt> toSend = new ArrayList<FinancialStartRpt>();
		System.out.println("Jumlah : " + neracaMap.values().size());
		for (NeracaItem item : neracaMap.values()) {
			// Skip nol
			if (item.getStartValue() > 0.009 || item.getStartValue() < -0.009) {
				FinancialStartRpt val = new FinancialStartRpt();
				val.setBranch(branch);
				val.setGroup(item.getGroup());
				val.setCoa(item.getCoa());
				val.setMonth(1);
				val.setYear(Integer.parseInt(year));
				if (val.getCoa() == coaLR || val.getCoa() == coaLRTL) {
					val.setValue(item.getStartValue());
				} else {
					val.setValue(item.getStartValue() * item.getFactor());
				}
				toSend.add(val);
			}
		}
		System.out.println("Jumlah data " + toSend.size());
		//
		try {
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, toSend);
			reportClient.sendRawData("saveFinancialStart", sw.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createBranches() {
		String strBranchData = coreClient.sendRawData("listBranchId", "");
		System.out.println(strBranchData);
		String[] strBranches = strBranchData.split(";");
		for (String strBranch : strBranches) {
			branches.add(Long.parseLong(strBranch));
		}
	}

	private void createConfig() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String data = coreClient.sendRawData("getConfig",
					"finReport.lastPL");
			ConfigDto config = mapper.readValue(data, ConfigDto.class);
			coaLRTL = config.getLongValue();
			data = coreClient.sendRawData("getConfig", "finReport.curPL");
			config = mapper.readValue(data, ConfigDto.class);
			coaLR = config.getLongValue();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<FinancialRpt> listFinReport(Long branch) {
		List<FinancialRpt> result = new ArrayList<FinancialRpt>();
		System.out.println(strEndDate);
		String data = reportClient.sendRawData("listFinancialReport", branch
				+ ";" + strEndDate);
		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.readValue(data, TypeFactory.collectionType(
					ArrayList.class, FinancialRpt.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
