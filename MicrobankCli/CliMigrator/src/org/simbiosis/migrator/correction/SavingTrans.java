package org.simbiosis.migrator.correction;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.CliBase;
import org.simbiosis.microbank.SavingTransactionDto;

public class SavingTrans extends CliBase {

	String coa = "950";

	public static void main(String[] args) {
		SavingTrans customer = new SavingTrans();
		customer.execute();
	}

	public SavingTrans() {
		super("cli.properties");
	}

	private void execute() {
		while (next()) {
			process();
		}
	}

	private void process() {
		for (SavingTransactionDto st : listSavingTransaction()) {
			if (st.getId()==500034 || st.getId()==500035 || st.getId()==500077){
			//if (!st.getCode().substring(3, 4).equalsIgnoreCase("T")
			//		&& st.getId() > 467433) {
				if (st.getDescription().substring(0, 8)
						.equalsIgnoreCase("ANGSURAN")) {
					int length = st.getDescription().length();
					String loanCode = st.getDescription().substring(
							length - 11, length - 1);
					System.out.println("ANGSURAN " + loanCode + " => "
							+ st.getDescription());
					saveLoanJournal(loanCode, st.getDate(), st.getCode());
				} else if (st.getDescription().substring(0, 20)
						.equalsIgnoreCase("PENCAIRAN PEMBIAYAAN")) {
					int length = st.getDescription().length();
					String loanCode = st.getDescription().substring(
							length - 11, length - 1);
					System.out.println("PENCAIRAN " + loanCode + " => "
							+ st.getDescription());
					saveLoanJournal(loanCode, st.getDate(), st.getCode());
				} else {
					System.out.println("OTHER " + " => " + st.getDescription());
					//saveOtherSavingJournal(st);
				}
			}
		}
	}

	void saveOtherSavingJournal(SavingTransactionDto st) {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, st);
			getCoreClient().sendRawData("saveSavingJournalTrans", coa, "",
					sw.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void saveLoanJournal(String loan, Date date, String code) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
		String strDate = dtf.print(new DateTime(date));
		getCoreClient().sendRawData("saveLoanJournalByAccDateCode",
				loan + ";" + strDate + ";" + code);
	}

	List<SavingTransactionDto> listSavingTransaction() {
		//
		List<SavingTransactionDto> result = new ArrayList<SavingTransactionDto>();
		// kirim data
		ObjectMapper mapper = new ObjectMapper();
		String strDate = getStrBulkBegin() + ";" + getStrBulkEnd();
		String data = getCoreClient().sendRawData("listSavingTrans", strDate);
		try {
			result = mapper.readValue(data, TypeFactory.collectionType(
					ArrayList.class, SavingTransactionDto.class));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
