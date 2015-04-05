package org.simbiosis.cli.loan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.CliBase;
import org.simbiosis.microbank.LoanInformationDto;
import org.simbiosis.microbank.LoanScheduleDto;
import org.simbiosis.microbank.LoanTransInfoDto;
import org.simbiosis.microbank.LoanTransactionDto;

public class CorrectBill extends CliBase {

	String strEndDate = "";

	Map<Long, Long> schedMaps = new HashMap<Long, Long>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CorrectBill cb = new CorrectBill();
		cb.execute();
	}

	public CorrectBill() {
		super("cli.properties");
		//
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		strEndDate = sdf.print(new DateTime());
	}

	public void execute() {
		while (next()) {
			// Ambil semua id pembiayaan
			System.out.println("List loan transaction payd");
			List<LoanTransInfoDto> payds = listSimpleLoanTransInfo(strEndDate);
			System.out.println("Hitung sisa pembayaran");
			for (LoanTransInfoDto trans : payds) {
				Long maxSched = 0L;
				// ambil angsuran yang sudah dibayar
				if (trans.getPaidMargin() + trans.getPaidPrincipal() > 0.01) {
					LoanInformationDto loan = getLoanInformation(trans.getId());
					double sisa = trans.getPaidPrincipal()
							+ (loan.getSchema() == 5 || loan.getSchema() == 6 ? 0
									: trans.getPaidMargin());
					// double sisaMargin = trans.getMargin();
					// ambil semua schedule
					List<LoanScheduleDto> scheds = listLoanSchedule(trans
							.getId());
					String schedIds = "";
					// int n = 1;
					for (LoanScheduleDto sched : scheds) {
						double pengurang = sched.getPrincipal()
								+ (loan.getSchema() == 5
										|| loan.getSchema() == 6 ? 0 : sched
										.getMargin());
						// if (sched.getLoan() == 63) {
						// System.out.println("Angsuran ke " + sched.getId()
						// + "-" + sched.getDate() + "-" + sisa + "-"
						// + pengurang);
						// }
						// if (trans.getId() == 1763) {
						// System.out.println(n++ + ". Angsuran : " + sisaPokok
						// + ", " + sched.getPrincipal() + ", "
						// + sched.getId());
						// }
						// Mensiasati jenis double bisa jadi selisih koma masih
						// ada
						// maka di anggap 0 saja

						if (sisa - pengurang > -0.01) {
							if (sched.getId() > maxSched) {
								maxSched = sched.getId();
								//
								if (!schedIds.isEmpty())
									schedIds += ";";
								schedIds += maxSched;
							}
							sisa -= pengurang;
						} else {
							sisa = 0;
							break;
						}
					}
				}
				schedMaps.put(trans.getId(), maxSched);
			}
			//
			System.out.println("Update jadwal");
			updateScheds();
		}
	}

	void updateScheds() {
		String data = "";
		for (Long idLoan : schedMaps.keySet()) {
			// System.out.println(data);
			Long maxScheds = schedMaps.get(idLoan);
			if (!data.isEmpty())
				data += ";";
			data += idLoan + ";" + maxScheds;
		}
		getCoreClient().sendRawData("updatePaydSchedule", data);
		// System.out.println(data);
	}

	LoanTransactionDto getSumLoanTransaction(String id) {
		LoanTransactionDto result = null;
		String data = getCoreClient().sendRawData("getSumLoanTransaction",
				id + ";" + strEndDate);
		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.readValue(data, LoanTransactionDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	List<LoanScheduleDto> listLoanSchedule(Long id) {
		List<LoanScheduleDto> scheds = new ArrayList<LoanScheduleDto>();
		String data = getCoreClient().sendRawData("listLoanSchedule",
				id.toString());
		ObjectMapper mapper = new ObjectMapper();
		try {
			scheds = mapper.readValue(data, TypeFactory.collectionType(
					ArrayList.class, LoanScheduleDto.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return scheds;
	}

	List<LoanTransInfoDto> listSimpleLoanTransInfo(String date) {
		List<LoanTransInfoDto> scheds = new ArrayList<LoanTransInfoDto>();
		String data = getCoreClient().sendRawData("listSimpleLoanTransInfo",
				date);
		ObjectMapper mapper = new ObjectMapper();
		try {
			scheds = mapper.readValue(data, TypeFactory.collectionType(
					ArrayList.class, LoanTransInfoDto.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return scheds;
	}

	LoanInformationDto getLoanInformation(Long id) {
		String data = getCoreClient().sendRawData("getLoanInfo", id.toString());
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(data, LoanInformationDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
