package org.simbiosis.cli.revsharing.lib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.MicrobankCoreClient;
import org.simbiosis.microbank.CustomerDto;
import org.simbiosis.microbank.DepositInformationDto;
import org.simbiosis.microbank.RevenueSharingDto;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.SavingTransactionDto;

public class Funding {

	String beforeDate;
	String beginDate;
	String endDate;
	DateTimeFormatter dayFormat = DateTimeFormat.forPattern("dd");
	int days;

	Map<String, RevenueSharingDto> revSharingMap = null;
	Map<Long, Double> taxMap = null;
	double totalAverageBallance = 0;
	double totalWadiahAverageBallance = 0;

	MicrobankCoreClient jsonClient;

	public Funding(MicrobankCoreClient jsonClient, String beforeDate,
			String beginDate, String endDate, int days,
			Map<String, RevenueSharingDto> revSharingMap,
			Map<Long, Double> taxMap) {
		this.revSharingMap = revSharingMap;
		this.taxMap = taxMap;
		this.beforeDate = beforeDate;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.days = days;
		this.jsonClient = jsonClient;
	}

	public void execute() {
		System.out.println("- initBallances");
		initBallances();
		System.out.println("- fillSavingData");
		fillSavingData();
		System.out.println("- fillDepositData");
		fillDepositData();
	}

	public double getTotalAverageBallance() {
		return totalAverageBallance;
	}

	public double getTotalWadiahAverageBallance() {
		return totalWadiahAverageBallance;
	}

	Double[] ballances = new Double[32];

	void initBallances() {
		for (int i = 0; i <= days; i++)
			ballances[i] = 0D;
	}

	void setBallances(int beginDay, double ballance) {
		for (int i = beginDay; i <= days; i++) {
			ballances[i] = ballance;
		}
	}

	double calcTotalBallance(int endDay) {
		double totalBallance = 0;
		for (int i = endDay; i >= 1; i--) {
			totalBallance += ballances[i];
		}
		return totalBallance;
	}

	double createSavingAverageBallance(long id, double startBallance) {
		//

		setBallances(1, startBallance);
		//
		String param = "" + id + ";" + beginDate + ";" + endDate;
		String data = jsonClient.sendRawData("listSavingTransRange", param);
		ObjectMapper mapper = new ObjectMapper();
		List<SavingTransactionDto> scheds = new ArrayList<SavingTransactionDto>();
		try {
			scheds = mapper.readValue(
					data,
					mapper.getTypeFactory().constructCollectionType(
							ArrayList.class, SavingTransactionDto.class));
			for (SavingTransactionDto sched : scheds) {
				int day = Integer.parseInt(dayFormat.print(new DateTime(sched
						.getDate())));
				double nextBallance = ballances[day];
				if (sched.getDirection() == 1) {
					nextBallance += sched.getValue();
				} else {
					nextBallance -= sched.getValue();
				}
				setBallances(day, nextBallance);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return calcTotalBallance(days) / days;
	}

	SavingInformationDto getSavingInformation(String id) {
		String data = jsonClient.sendRawData("getSavingInformation", id);
		ObjectMapper mapper = new ObjectMapper();
		try {
			SavingInformationDto info = mapper.readValue(data,
					SavingInformationDto.class);
			return info;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new SavingInformationDto();
	}

	DepositInformationDto getDepositInformation(long id) {
		String param = "" + id;
		String data = jsonClient.sendRawData("getDepositInformation", param);
		ObjectMapper mapper = new ObjectMapper();
		try {
			DepositInformationDto info = mapper.readValue(data,
					DepositInformationDto.class);
			return info;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new DepositInformationDto();
	}

	CustomerDto getCif(long id) {
		String param = "" + id;
		String data = jsonClient.sendRawData("getCif", param);
		ObjectMapper mapper = new ObjectMapper();
		try {
			CustomerDto info = mapper.readValue(data, CustomerDto.class);
			return info;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new CustomerDto();
	}

	void fillSavingData() {
		String data = jsonClient.sendRawData("listSavingId", endDate);
		String[] ids = data.split(";");
		for (String id : ids) {
			long savingId = Long.parseLong(id);
			//
			String ballance = jsonClient.sendRawData("getSavingBallance", id
					+ ";" + beforeDate);
			double startBallance = Double.parseDouble(ballance);
			//
			double averageBallance = createSavingAverageBallance(savingId,
					startBallance);
			double endBallance = ballances[days];
			//
			SavingInformationDto info = getSavingInformation(id);
			CustomerDto cif = getCif(info.getCustomer());
			//
			RevenueSharingDto item = new RevenueSharingDto();
			item.setAccount(savingId);
			item.setSaving(savingId);
			item.setType(1);
			item.setStartValue(startBallance);
			item.setEndValue(endBallance);
			item.setAverageValue(averageBallance < 0 ? 0 : averageBallance);
			item.setCode(info.getCode());
			item.setCustomer(info.getCustomer());
			item.setName(info.getName());
			item.setProduct(info.getProductName());
			item.setSharing(info.getSharing());
			item.setHasShare(info.getHasShare());
			item.setZakat(info.getZakat());
			if (item.getAverageValue() > 0) {
				if (cif.getTaxable() == 1) {
					// Pajak
					Double value = taxMap.get(item.getCustomer());
					if (value != null) {
						value += item.getEndValue();
					} else {
						value = item.getEndValue();
					}
					taxMap.put(item.getCustomer(), value);
				}
				totalAverageBallance += item.getAverageValue();
				if (info.getSchema() == 1 && info.getHasShare() == 1) {
					totalWadiahAverageBallance += item.getAverageValue();
				}
				// Masukkan
				revSharingMap.put("1;" + savingId, item);
			} else {
				System.out.println("Nilai saldo rata2 salah " + savingId + "="
						+ averageBallance);
			}
		}

	}

	void fillDepositData() {
		String data = jsonClient.sendRawData("listDepositId", endDate);
		if (!data.isEmpty()) {
			String[] ids = data.split(";");
			for (String id : ids) {
				long depositId = Long.parseLong(id);
				//
				DepositInformationDto info = getDepositInformation(depositId);
				CustomerDto cif = getCif(info.getCustomer());
				//
				RevenueSharingDto item = new RevenueSharingDto();
				item.setAccount(depositId);
				item.setType(2);
				item.setStartValue(info.getValue());
				item.setEndValue(info.getValue());
				item.setAverageValue(info.getValue());
				item.setCode(info.getCode());
				item.setCustomer(info.getCustomer());
				item.setName(info.getName());
				item.setProduct(info.getProductName());
				item.setSharing(info.getSharing());
				item.setHasShare(1);
				item.setSaving(info.getSaving());
				item.setZakat(info.getZakat());
				//
				if (cif.getTaxable() == 1) {
					Double value = taxMap.get(item.getCustomer());
					if (value != null) {
						value += item.getEndValue();
					} else {
						value = item.getEndValue();
					}
					taxMap.put(item.getCustomer(), value);
				}
				//
				totalAverageBallance += item.getAverageValue();
				// Masukkan
				revSharingMap.put("2;" + depositId, item);
			}
		}
	}

}
