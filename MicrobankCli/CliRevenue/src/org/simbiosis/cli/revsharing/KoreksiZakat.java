package org.simbiosis.cli.revsharing;

import java.io.IOException;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.CliBase;
import org.simbiosis.microbank.DepositProductDto;
import org.simbiosis.microbank.RevenueSharingDto;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.SavingProductDto;
import org.simbiosis.microbank.SavingTransactionDto;

public class KoreksiZakat extends CliBase {
	//
	NumberFormat nf = NumberFormat.getInstance();
	//
	int month;
	int nextMonth;
	int year;
	String endDate;
	Date date = null;
	//
	String period;
	String ref = "BHS";
	//
	//
	Map<Long, SavingProductDto> spMap = new HashMap<Long, SavingProductDto>();
	Map<Long, DepositProductDto> dpMap = new HashMap<Long, DepositProductDto>();

	public static void main(String[] args) {
		KoreksiZakat distribusi = new KoreksiZakat();
		distribusi.execute();
	}

	public KoreksiZakat() {
		super("cli.properties");
		DateTimeFormatter sMonth = DateTimeFormat.forPattern("MM");
		DateTimeFormatter sYear = DateTimeFormat.forPattern("yyyy");
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		Date now = sdf.parseDateTime("30-11-2013").toDate();
		//
		month = Integer.parseInt(sMonth.print(new DateTime(now)));
		year = Integer.parseInt(sYear.print(new DateTime(now)));
		nextMonth = month + 1;
		endDate = getEndMonths(month) + "-" + year;
		//
		period = month + "/" + year;
		ref += month + "" + year;
		//
		date = sdf.parseDateTime(endDate).toDate();
	}

	void execute() {
		while (next()) {
			//
			String data = getCoreClient().sendRawData("listRevenueSharing",
					month + ";" + year + ";0");
			ObjectMapper mapper = new ObjectMapper();
			try {
				List<RevenueSharingDto> result = mapper.readValue(data,
						TypeFactory.collectionType(ArrayList.class,
								RevenueSharingDto.class));
				for (RevenueSharingDto rs : result) {
					if (rs.getType() == 1) {
						if (rs.getCustomerSharing() > 0)
							saveSavingTrans(rs);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	SavingTransactionDto createSavingTrans(long saving, long branch,
			double value, String ref, String description, int direction) {
		SavingTransactionDto transDto = new SavingTransactionDto();
		transDto.setDate(date);
		transDto.setTimestamp(new Date());
		transDto.setSaving(saving);
		transDto.setCode(ref);
		transDto.setRefCode(ref);
		transDto.setDescription(description);
		transDto.setValue(value);
		transDto.setDirection(direction);
		transDto.setBranch(branch);
		return transDto;
	}

	SavingInformationDto getSavingInformation(long id) {
		String data = getCoreClient().sendRawData("getSavingInformation",
				"" + id);
		try {
			//
			ObjectMapper mapper = new ObjectMapper();
			SavingInformationDto si = mapper.readValue(data,
					SavingInformationDto.class);
			return si;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	void saveSavingTrans(RevenueSharingDto rs) {
		try {
			SavingInformationDto si = getSavingInformation(rs.getSaving());
			//
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			//
			if (si.getZakat() > 0) {
				double zakat = 0.025 * rs.getCustomerSharing();
				SavingTransactionDto transZakat = createSavingTrans(
						rs.getSaving(), si.getBranch(), zakat, ref, "ZAKAT "
								+ period, 2);
				mapper = new ObjectMapper();
				sw = new StringWriter();
				mapper.writeValue(sw, transZakat);
				System.out.println(sw.toString());
				sendRawData("saveSavingJournalRevenueTrans", "680",
						sw.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void sendRawData(String action, String id, String data) {
		getCoreClient().sendRawData(action, id, "", data);
	}

}
