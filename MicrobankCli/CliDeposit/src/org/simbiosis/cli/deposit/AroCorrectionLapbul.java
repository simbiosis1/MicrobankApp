package org.simbiosis.cli.deposit;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.CliBase;
import org.simbiosis.microbank.DepositDto;
import org.simbiosis.microbank.DepositProductDto;

public class AroCorrectionLapbul extends CliBase {
	String strEndDate = "";

	Map<Long, Long> schedMaps = new HashMap<Long, Long>();
	Date endOfMonth = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AroCorrectionLapbul cb = new AroCorrectionLapbul();
		cb.execute();
	}

	public AroCorrectionLapbul() {
		super("cli.properties");
		//
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		DateTime now = new DateTime();
		//
		strEndDate = dtf.print(now);
		endOfMonth = now.dayOfMonth().withMinimumValue().minusDays(1).toDate();
	}

	public void execute() {
		open();
		List<DepositProductDto> listProduct = listDepositProduct();
		listDeposit(listProduct);
		close();
	}

	private void listDeposit(List<DepositProductDto> listProduct) {
		//
		Map<Long, DepositProductDto> map = new HashMap<>();
		for (DepositProductDto prd : listProduct) {
			map.put(prd.getId(), prd);
		}
		//
		String data = getCoreClient().sendRawData("listDepositId", strEndDate);
		String[] ids = data.split(";");
		for (String id : ids) {
			DepositDto deposit = getDeposit(id);
			DateTime end = new DateTime(endOfMonth);
			if (deposit.getEnd() == null) {
				DateTime current = new DateTime(deposit.getBegin())
						.plusMonths(map.get(deposit.getProduct()).getTerm());
				while (current.isBefore(end)) {
					deposit.setBegin(current.toDate());
					current = current.plusMonths(map.get(deposit.getProduct())
							.getTerm());
				}
				System.out.println("Update Data Null");
				saveDeposit(deposit);
			} else {
				if (deposit.getEnd().before(endOfMonth)) {
					DateTime current = new DateTime(deposit.getEnd());
					while (current.isBefore(end)) {
						deposit.setBegin(current.toDate());
						current = current.plusMonths(map.get(
								deposit.getProduct()).getTerm());
					}
					System.out.println("Update Data");
					saveDeposit(deposit);
				} else {
					System.out.println("Normal");
				}
			}
		}
	}

	private DepositDto getDeposit(String id) {
		String data = getCoreClient().sendRawData("getDeposit", id);
		ObjectMapper mapper = new ObjectMapper();
		try {
			DepositDto info = mapper.readValue(data, DepositDto.class);
			return info;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new DepositDto();
	}

	private List<DepositProductDto> listDepositProduct() {
		String data = getCoreClient().sendRawData("listDepositProduct", "");
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<DepositProductDto> list = mapper.readValue(
					data,
					mapper.getTypeFactory().constructCollectionType(
							ArrayList.class, DepositProductDto.class));
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<DepositProductDto>();
	}

	private void saveDeposit(DepositDto deposit) {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, deposit);
			String result = sw.toString();
			getCoreClient().sendRawData("saveDepositDate", result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
