package org.simbiosis.migrator.correction;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.simbiosis.cli.base.CliBase;
import org.simbiosis.microbank.TellerTransactionDto;

public class TellerTrans extends CliBase {

	public static void main(String[] args) {
		TellerTrans customer = new TellerTrans();
		customer.execute();
	}

	public TellerTrans() {
		super("cli.properties");
	}

	private void execute() {
		while (next()) {
			process();
		}
	}

	private void process() {
		for (TellerTransactionDto tt : listTellerTransaction()) {
			if (tt.getId() >= 195026 && tt.getId() <= 195211) {
				switch (tt.getType()) {
				case 1:
					saveVaultTrans(tt);
					break;
				case 2:
					saveCashTrans(tt);
					break;
				case 3:
					saveSavingTrans(tt);
					break;
				default:
					break;
				}
			}
		}
	}

	private void saveVaultTrans(TellerTransactionDto tt) {
		try {
			System.out.println(tt.getId() + " - " + tt.getCode() + " - "
					+ tt.getDescription());
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, tt);
			getCoreClient().sendRawData("saveVaultTellerTrans", sw.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveCashTrans(TellerTransactionDto tt) {
		try {
			System.out.println(tt.getId() + " - " + tt.getCode() + " - "
					+ tt.getDescription());
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, tt);
			getCoreClient().sendRawData("saveCashTellerTrans", sw.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveSavingTrans(TellerTransactionDto tt) {
		try {
			System.out.println(tt.getId() + " - " + tt.getCode() + " - "
					+ tt.getDescription());
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, tt);
			getCoreClient().sendRawData("saveSavingTellerTrans", sw.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	List<TellerTransactionDto> listTellerTransaction() {
		//
		List<TellerTransactionDto> result = new ArrayList<TellerTransactionDto>();
		// kirim data
		ObjectMapper mapper = new ObjectMapper();
		String strDate = getStrBulkBegin() + ";" + getStrBulkEnd();
		String data = getCoreClient().sendRawData("listTellerTransaction",
				strDate);
		try {
			result = mapper.readValue(data, TypeFactory.collectionType(
					ArrayList.class, TellerTransactionDto.class));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
