package org.simbiosis.cli.financial.lib;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.cli.base.MicrobankCoreClient;
import org.simbiosis.cli.base.MicrobankReportClient;
import org.simbiosis.gl.model.Coa;
import org.simbiosis.gl.model.CoaGroup;
import org.simbiosis.gl.model.FinancialRpt;
import org.simbiosis.gl.model.FinancialRptPK;
import org.simbiosis.gl.model.FinancialStartRpt;
import org.simbiosis.system.ConfigDto;

public class Neraca {

	MicrobankCoreClient coreClient;
	MicrobankReportClient reportClient;

	long coaLRTL;
	Long coaLR;
	String coaLRCode = "";
	String coaLRDescription = "";
	String coaRakAktiva = "xxx";
	String coaRakPasiva = "xxx";
	double[] values = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	long branch;

	Date endDate;
	String year;
	String strBeginDate = "";
	String strEndDate = "";
	boolean coaLRAda = false;

	// Worker count
	int workerCount = 3;
	int runningWorker = 0;
	int waitingTime = 2500;

	Map<Long, NeracaItem> neracaMap = new HashMap<Long, NeracaItem>();
	List<NeracaItem> itemList = new ArrayList<NeracaItem>();
	List<Long> branches = new ArrayList<Long>();

	public Neraca(String strBeginDate, String strEndDate, Date endDate,
			MicrobankCoreClient coreClient, MicrobankReportClient reportClient) {
		this.coreClient = coreClient;
		this.reportClient = reportClient;
		//
		this.strBeginDate = strBeginDate;
		this.strEndDate = strEndDate;
		this.endDate = endDate;
		year = strBeginDate.substring(6);
		//
		createBranches();
		createConfig();
	}

	public void execute() {
		//
		deleteFinReport();
		//
		for (Long currentBranch : branches) {
			branch = currentBranch;
			System.out.println("Cabang : " + branch);
			clearNeracaLR();
			createNeracaLR();
			saveFinReport();
		}
	}

	private synchronized void incrementRunningWorker() {
		runningWorker++;
	}

	private synchronized void decrementRunningWorker() {
		runningWorker--;
	}

	private void clearNeracaLR() {
		for (int i = 0; i < values.length; i++) {
			values[i] = 0;
		}
		neracaMap.clear();
		itemList.clear();
	}

	private void createNeracaLR() {
		// createNeraca();
		System.out.println("Ambil start value");
		loadStartValue();
		System.out.println("Buat current value");
		createNeraca();
		createLabaRugi();
		createOthers();
		System.out.println("Buat end value dan kalkulasi");
		createEndValue();
		calculateValues();
	}

	private void createNeraca() {
		//
		for (CoaGroup dto : listCoaGroup(1)) {
			createCurrentValue(dto);
		}
		for (CoaGroup dto : listCoaGroup(2)) {
			createCurrentValue(dto);
		}
	}

	private void createLabaRugi() {
		//
		for (CoaGroup dto : listCoaGroup(3)) {
			createCurrentValue(dto);
		}
		for (CoaGroup dto : listCoaGroup(4)) {
			createCurrentValue(dto);
		}
	}

	private void createOthers() {
		//
		for (CoaGroup dto : listCoaGroup(7)) {
			createCurrentValue(dto);
		}
	}

	public void calculateValues() {
		// Hitung nilai-nilai
		for (NeracaItem item : itemList) {
			values[item.getGroup()] += item.getEndValue();
		}
		// Perhitungan laba rugi
		boolean found = false;
		values[10] = values[3] + values[5] - values[4] - values[6] - values[7];
		for (NeracaItem item : itemList) {
			if (item.getCoa() == coaLR) {
				item.setCurrentValue(values[10]);
				item.setEndValue(values[10]);
				found = true;
			}
			if (found)
				break;
		}
		if (!found) {

		}
		// Koreksi pasiva = 614
		values[2] += values[10];
	}

	private void createEndValue() {
		//
		NeracaItem lrtl = neracaMap.get(coaLRTL);
		if (lrtl != null) {
			lrtl.setGroup(2);
			neracaMap.put(coaLRTL, lrtl);
		}
		//
		NeracaItem lr = neracaMap.get(coaLR);
		if (lr == null) {
			NeracaItem item = new NeracaItem();
			item.setGroup(2);
			item.setCoa(coaLR);
			item.setCode(coaLRCode);
			item.setDescription(coaLRDescription);
			item.setStartValue(0);
			item.setFactor(1);
			neracaMap.put(item.getCoa(), item);
		}
		//
		itemList.addAll(neracaMap.values());
		Collections.sort(itemList, new Comparator<NeracaItem>() {

			@Override
			public int compare(NeracaItem o1, NeracaItem o2) {
				return o1.getCode().compareTo(o2.getCode());
			}
		});
		for (NeracaItem item : itemList) {
			item.setCurrentValue(item.getCurrentValue() * item.getFactor());
			item.setEndValue(item.getStartValue() + item.getCurrentValue());
		}
	}

	private List<CoaGroup> listCoaGroup(int group) {
		String data = coreClient.sendRawData("listCoaGroup", "" + group);
		ObjectMapper mapper = new ObjectMapper();
		List<CoaGroup> objects = null;
		try {
			objects = mapper.readValue(data, mapper.getTypeFactory()
					.constructCollectionType(ArrayList.class, CoaGroup.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objects;
	}

	private void loadStartValue() {
		String data = reportClient.sendRawData("listFinancialStart", branch
				+ ";1;" + year);
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<FinancialStartRpt> objects = mapper.readValue(
					data,
					mapper.getTypeFactory().constructCollectionType(
							ArrayList.class, FinancialStartRpt.class));
			for (FinancialStartRpt rs : objects) {
				if (branch == 0
						&& (rs.getCoaCode().contains(coaRakAktiva) || rs
								.getCoaCode().contains(coaRakPasiva))) {
					// GAk usah ngapa2in
				} else {
					NeracaItem item = new NeracaItem();
					item.setGroup(rs.getGroup());
					item.setCoa(rs.getCoa());
					item.setCode(rs.getCoaCode());
					item.setDescription(rs.getCoaDescription());
					item.setStartValue(rs.getValue());
					item.setFactor(1);
					neracaMap.put(rs.getCoa(), item);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createCurrentValue(CoaGroup dto) {
		String data = coreClient.sendRawData("listSumGlTransGroupRange", branch
				+ ";" + strBeginDate + ";" + strEndDate + ";" + dto.getCode());
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<Object[]> objects = mapper.readValue(
					data,
					mapper.getTypeFactory().constructCollectionType(
							ArrayList.class, Object[].class));
			for (Object[] rs : objects) {
				long id = Long.parseLong(rs[0].toString());
				String coaCode = rs[1].toString();
				String description = rs[2].toString();
				int direction = Integer.parseInt(rs[3].toString());
				double value = Double.parseDouble(rs[4].toString());
				if (branch == 0
						&& (coaCode.contains(coaRakAktiva) || coaCode
								.contains(coaRakPasiva))) {

				} else {
					NeracaItem item = neracaMap.get(id);
					if (item == null) {
						item = new NeracaItem();
						item.setCoa(id);
						item.setCode(coaCode);
						item.setDescription(description);
						item.setStartValue(0D);
						item.setCurrentValue(direction == 1 ? value : -value);
						if (direction == 1) {
							item.setDebit(value);
						} else {
							item.setCredit(value);
						}
					} else {
						if (direction == 1) {
							item.setCurrentValue(item.getCurrentValue() + value);
							item.setDebit(item.getDebit() + value);
						} else {
							item.setCurrentValue(item.getCurrentValue() - value);
							item.setCredit(item.getCredit() + value);
						}
					}
					item.setGroup(dto.getGroup());
					item.setFactor(dto.getFactor());
					neracaMap.put(id, item);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void checkBallance() {
		double value = 0;
		for (NeracaItem item : itemList) {
			value += item.getCurrentValue();
		}
		if (Math.abs(value) > 0.01) {
			System.out.println("Gak ballance");
		} else {
			System.out.println("Ok");
		}
	}

	private void createBranches() {
		branches.clear();
		branches.add(0L);
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
			//
			data = coreClient.sendRawData("getConfig", "finReport.curPL");
			config = mapper.readValue(data, ConfigDto.class);
			coaLR = config.getLongValue();
			//
			data = coreClient.sendRawData("getConfig", "finReport.conActiva");
			config = mapper.readValue(data, ConfigDto.class);
			if (config != null) {
				coaRakAktiva = config.getStrValue();
			}
			//
			data = coreClient.sendRawData("getConfig", "finReport.conPassiva");
			config = mapper.readValue(data, ConfigDto.class);
			if (config != null) {
				coaRakPasiva = config.getStrValue();
			}
			//
			// Ambil data coaLR
			data = coreClient.sendRawData("getCoa", coaLR.toString());
			Coa coa = mapper.readValue(data, Coa.class);
			coaLRCode = coa.getCode();
			coaLRDescription = coa.getDescription();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void deleteFinReport() {
		reportClient.sendRawData("deleteFinancialReport", strEndDate);
	}

	public class Worker extends Thread {

		List<FinancialRpt> toSend = new ArrayList<FinancialRpt>();

		public void add(NeracaItem item) {
			FinancialRptPK id = new FinancialRptPK(endDate, item.getCoa(), 0,
					branch);
			FinancialRpt fr = new FinancialRpt();
			fr.setId(id);
			fr.setGroup(item.getGroup());
			fr.setFactor(item.getFactor());
			fr.setStartValue(item.getStartValue());
			fr.setCurrentValue(item.getCurrentValue());
			fr.setEndValue(item.getEndValue());
			fr.setDebit(item.getDebit());
			fr.setCredit(item.getCredit());
			toSend.add(fr);
		}

		@Override
		public void run() {
			try {
				ObjectMapper mapper = new ObjectMapper();
				StringWriter sw = new StringWriter();
				mapper.writeValue(sw, toSend);
				reportClient.sendRawData("saveFinancialReport", sw.toString());
				decrementRunningWorker();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void saveFinReport() {
		// When item 3 times bigger then workercount
		int minItem = workerCount * 3;
		if (itemList.size() > minItem) {
			// Buat list worker
			List<Worker> workers = new ArrayList<Worker>();
			for (int i = 0; i < workerCount; i++) {
				workers.add(new Worker());
			}
			// Isi worker
			int itemNumber = itemList.size() - 1;
			int itemPerWorker = (itemList.size() / workerCount) + 1;
			for (int i = 0; i < workerCount; i++) {
				for (int j = 0; j < itemPerWorker; j++) {
					NeracaItem item = itemList.get(itemNumber--);
					if ((Math.abs(item.getStartValue()) > 0.01)
							|| (Math.abs(item.getCurrentValue()) > 0.01)
							|| (Math.abs(item.getEndValue()) > 0.01)) {
						workers.get(i).add(item);
					}
					if (itemNumber < 0)
						break;
				}
			}
			// Jalankan worker
			for (Worker worker : workers) {
				worker.start();
				incrementRunningWorker();
			}
			// Tunggu worker
			while (runningWorker > 0) {
				try {
					Thread.sleep(waitingTime);
					// System.out.print(".");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			List<FinancialRpt> toSend = new ArrayList<FinancialRpt>();
			for (NeracaItem item : itemList) {
				if ((Math.abs(item.getStartValue()) > 0.01)
						|| (Math.abs(item.getCurrentValue()) > 0.01)
						|| (Math.abs(item.getEndValue()) > 0.01)) {
					FinancialRptPK id = new FinancialRptPK(endDate,
							item.getCoa(), 0, branch);
					FinancialRpt fr = new FinancialRpt();
					fr.setId(id);
					fr.setGroup(item.getGroup());
					fr.setFactor(item.getFactor());
					fr.setStartValue(item.getStartValue());
					fr.setCurrentValue(item.getCurrentValue());
					fr.setEndValue(item.getEndValue());
					fr.setDebit(item.getDebit());
					fr.setCredit(item.getCredit());
					toSend.add(fr);
				}
				try {
					ObjectMapper mapper = new ObjectMapper();
					StringWriter sw = new StringWriter();
					mapper.writeValue(sw, toSend);
					reportClient.sendRawData("saveFinancialReport",
							sw.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//

	}

}
