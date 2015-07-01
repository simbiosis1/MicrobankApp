package org.simbiosis.cli.revsharing.lib;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.simbiosis.cli.base.MicrobankCoreClient;
import org.simbiosis.microbank.RevenueSharingDto;

public class Interest {

	int days;

	Map<String, RevenueSharingDto> revSharingMap = new HashMap<String, RevenueSharingDto>();
	Map<Long, Double> taxList = new HashMap<Long, Double>();

	Funding funding = null;
	// sungai atang
	// double minAverageValue = 2000000;
	// karya indah
	double minAverageValue = 100000;
	// double admin = 2500;
	double admin = 0;

	public Interest(MicrobankCoreClient jsonClient, String beforeDate,
			String beginDate, int days, String endDate) {
		this.days = days;
		funding = new Funding(jsonClient, beforeDate, beginDate, endDate, days,
				revSharingMap, taxList);
	}

	public void execute() {
		System.out.println("Funding...");
		funding.execute();
		System.out.println("Disburst interest...");
		disburstRevenue();
	}

	public void disburstRevenue() {
		for (RevenueSharingDto revSharing : revSharingMap.values()) {
			if (revSharing.getAverageValue() >= minAverageValue) {
				revSharing.setTotalSharing(0);
				revSharing.setCustomerSharing(revSharing.getAverageValue()
						* revSharing.getSharing() * days / 36500);
				revSharing.setZakat(admin);
				revSharing.setTax(0);
			} else {
				revSharing.setTotalSharing(0);
				revSharing.setCustomerSharing(0);
				revSharing.setZakat(admin);
				revSharing.setTax(0);
			}
		}
	}

	public Collection<RevenueSharingDto> getRevenueSharing() {
		return revSharingMap.values();
	}

}
