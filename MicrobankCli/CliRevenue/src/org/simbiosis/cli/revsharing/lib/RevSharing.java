package org.simbiosis.cli.revsharing.lib;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.cli.base.MicrobankCoreClient;
import org.simbiosis.microbank.RevenueDto;
import org.simbiosis.microbank.RevenueSharingDto;
import org.simbiosis.system.ConfigDto;

public class RevSharing {

	int days;

	Map<String, RevenueSharingDto> revSharingMap = new HashMap<String, RevenueSharingDto>();
	Map<Long, Double> taxMap = new HashMap<Long, Double>();

	Funding funding = null;
	Lending landing = null;
	double wadiahSharing = 0;
	double taxableMin = 7500000;

	public RevSharing(MicrobankCoreClient coreClient, String beforeDate,
			String beginDate, int days, String endDate) {
		this.days = days;
		funding = new Funding(coreClient, beforeDate, beginDate, endDate, days,
				revSharingMap, taxMap);
		landing = new Lending(coreClient, beginDate, endDate);
		//
		//
		String data = coreClient.sendRawData("getConfig",
				"revenue.wadiahSharing");
		ObjectMapper mapper = new ObjectMapper();
		try {
			ConfigDto config = mapper.readValue(data, ConfigDto.class);
			wadiahSharing = config.getDoubleValue();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void execute() {
		System.out.println("Landing...");
		landing.execute();
		System.out.println("Funding...");
		funding.execute();
		System.out.println("Disburst revenue...");
		disburstRevenue();
	}

	public void disburstRevenue() {
		double factor = landing.getTotalRevenue()
				/ funding.getTotalAverageBallance();
		double wadiahFactor = wadiahSharing
				/ funding.getTotalWadiahAverageBallance();
		for (RevenueSharingDto revSharing : revSharingMap.values()) {
			if (revSharing.getAverageValue() > 0) {
				double totalSharing = revSharing.getAverageValue() * factor;
				double customerSharing = 0;
				if (revSharing.getSharing() > 0) {
					customerSharing = totalSharing * revSharing.getSharing()
							/ 100;
				} else if (revSharing.getHasShare() == 1) {
					customerSharing = revSharing.getAverageValue()
							* wadiahFactor;
				}
				revSharing.setTotalSharing(totalSharing);
				revSharing.setCustomerSharing(customerSharing);
				//
				double zakat = 0.025 * revSharing.getCustomerSharing()
						* revSharing.getZakat();
				revSharing.setZakat(zakat);
				Double taxBallance = taxMap.get(revSharing.getCustomer());
				if (taxBallance != null && taxBallance > taxableMin) {
					Double tax = revSharing.getCustomerSharing() - zakat;
					revSharing.setTax(0.2 * tax);
				}
			} else {
				revSharing.setTotalSharing(0);
				revSharing.setCustomerSharing(0);
				revSharing.setTax(0);
				revSharing.setZakat(0);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<RevenueDto> getRevenue() {
		return (List<RevenueDto>) landing.getListRevenue();
	}

	public Collection<RevenueSharingDto> getRevenueSharing() {
		return revSharingMap.values();
	}

}
