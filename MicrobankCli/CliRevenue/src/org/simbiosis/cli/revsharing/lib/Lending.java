package org.simbiosis.cli.revsharing.lib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.simbiosis.cli.base.MicrobankCoreClient;
import org.simbiosis.microbank.RevenueDto;

public class Lending {
	String beginDate;
	String endDate;

	List<RevenueDto> listRevenue = new ArrayList<>();
	double totalRevenue = 0;

	MicrobankCoreClient jsonClient;

	public Lending(MicrobankCoreClient jsonClient, String beginDate,
			String endDate) {
		super();
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.jsonClient = jsonClient;
	}

	public void execute() {
		System.out.println("- Create revenue");
		listRevenue.addAll(listRevenue());
	}

	@SuppressWarnings("rawtypes")
	public Collection getListRevenue() {
		return listRevenue;
	}

	public double getTotalRevenue() {
		return totalRevenue;
	}

	List<RevenueDto> listRevenue() {
		String result = jsonClient.sendRawData("listRevenue", beginDate + ";"
				+ endDate);
		//System.out.println(result);
		ObjectMapper mapper = new ObjectMapper();
		try {
			ArrayList<RevenueDto> listRevenue = mapper.readValue(result,
					TypeFactory.collectionType(ArrayList.class,
							RevenueDto.class));
			for (RevenueDto rev : listRevenue) {
				totalRevenue += rev.getValue();
			}
			return listRevenue;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<RevenueDto>();
	}

}
