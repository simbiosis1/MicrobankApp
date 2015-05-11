package org.simbiosis.cli.loan.lib;

import java.io.IOException;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.cli.base.MicrobankCoreClient;
import org.simbiosis.microbank.LoanInformationDto;
import org.simbiosis.microbank.LoanScheduleDto;
import org.simbiosis.microbank.LoanTransactionDto;

public class LoanBillingGenerator {

	String dateBegin = "";
	String dateEnd = "";

	//
	MicrobankCoreClient jsonMain;

	Map<Long, Double> savingMap = new HashMap<Long, Double>();
	Map<Long, LoanBillingDv> loanMap = null;
	Map<String, Integer> blockirMap = new HashMap<String, Integer>();

	public LoanBillingGenerator(MicrobankCoreClient jsonMain) {
		this.jsonMain = jsonMain;
		loanMap = new HashMap<Long, LoanBillingDv>();
	}

	public LoanBillingGenerator(MicrobankCoreClient jsonMain,
			Map<Long, LoanBillingDv> loanBilling) {
		this.jsonMain = jsonMain;
		this.loanMap = loanBilling;
	}

	// Jalankan seluruh proses pendebetan
	public void createBillingList(String beginDate, String endDate) {
		this.dateBegin = beginDate;
		this.dateEnd = endDate;
		//
		createBlockir();
		//
		fillLoanBilling();
		listLoanSchedule();
		// markHasPayd();
		//
		System.out.println("Jumlah data : " + loanMap.values().size());
	}

	// Susun daftar blokir tabungan
	private void createBlockir() {
		/*
		 * for (Object data : datas) { Object[] item = (Object[]) data; String
		 * loan = (String) item[0]; Integer value = (Integer) item[1];
		 * blockirMap.put(loan, value); }
		 */
	}

	private double getBlockir(String code) {
		Integer blockir = blockirMap.get(code);
		return (blockir != null) ? blockir : 0;
	}

	// Bayar tagihan
	public void payBilling() {
		int counter = 1;
		NumberFormat nf = NumberFormat.getInstance();
		for (LoanBillingDv billing : loanMap.values()) {
			Double val = savingMap.get(billing.getSaving());
			Double blockir = getBlockir(billing.getCode());
			billing.setBillable(val > (billing.getBill() + blockir));
			// if (billing.getId() == 2817) {
			// System.out.println(billing.isBillable() + "-"
			// + billing.getDueDate() + "-" + billing.getPrincipal()
			// + "-" + billing.getMargin());
			// }
			if (billing.isBillable()
					&& billing.getDueDate() != null
					&& (billing.getPrincipal() > 0.1 || billing.getMargin() > 0.1)) {
				//
				sendPay(billing);
				//
				String line = "" + counter++ + "\t" + billing.getProduct()
						+ "\t" + billing.getCode() + "\t" + billing.getName()
						+ "\t" + billing.getDueDate() + "\t"
						+ nf.format(billing.getPrincipal()) + "\t"
						+ nf.format(billing.getMargin()) + "\t"
						+ nf.format(billing.getBill()) + "\t"
						+ nf.format(billing.getSavingBallance());
				System.out.println(line);
				//
				val -= (billing.getBill() + blockir);
				savingMap.put(billing.getSaving(), val);
			}
		}
	}

	// Kirim pembayaran ke system
	private void sendPay(LoanBillingDv loan) {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			LoanTransactionDto loanTransDto = new LoanTransactionDto();
			loanTransDto.setDate(new Date());
			loanTransDto.setLoan(loan.getId());
			loanTransDto.setRefCode("ADL");
			loanTransDto.setType(2);
			loanTransDto.setDirection(2);
			loanTransDto.setPrincipal(loan.getPrincipal());
			loanTransDto.setMargin(loan.getMargin());
			mapper.writeValue(sw, loanTransDto);
			jsonMain.sendRawData("payBilling", sw.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String createText() {
		NumberFormat nf = NumberFormat.getInstance();
		int counter = 0;
		String buffer = "";
		for (LoanBillingDv billing : loanMap.values()) {
			if (billing.isBillable() && billing.getPrincipal() > 0.1
					&& billing.getMargin() > 0.1) {
				counter++;
				//
				String line = "" + counter + "\t" + billing.getProduct() + "\t"
						+ billing.getCode() + "\t" + billing.getName() + "\t"
						+ billing.getCount() + "\t"
						+ nf.format(billing.getPrincipal()) + "\t"
						+ nf.format(billing.getMargin()) + "\t"
						+ nf.format(billing.getBill()) + "\t"
						+ nf.format(billing.getSavingBallance());
				buffer += line;
				if (!buffer.isEmpty())
					buffer += "\n";
			}
		}
		return buffer;
	}

	// Tandai bahwa bulan ini dia sudah bayar
	// private void markHasPayd() {
	// // Direction (angsuran=2),begin, end
	// String param = "2;" + dateBegin + ";" + dateEnd;
	// String data = jsonMain.sendRawData("listLoanTransactionByRange", param);
	// ObjectMapper mapper = new ObjectMapper();
	// List<LoanTransactionDto> scheds = new ArrayList<LoanTransactionDto>();
	// try {
	// scheds = mapper.readValue(data, TypeFactory.collectionType(
	// ArrayList.class, LoanTransactionDto.class));
	// for (LoanTransactionDto sched : scheds) {
	// LoanBillingDv billing = loanMap.get(sched.getLoan());
	// if (billing != null)
	// billing.setBillable(false);
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	// Buat list daftar tagihan
	void listLoanSchedule() {
		System.out.println("\nlistLoanSchedule....");
		// Buat daftar tagihan seluruh pembiayaan
		// Satu pembiayaan bisa jadi punya beberapa tagihan kalo nunggak
		String data = jsonMain.sendRawData("listLoanBill", dateEnd);
		ObjectMapper mapper = new ObjectMapper();
		List<LoanScheduleDto> scheds = new ArrayList<LoanScheduleDto>();
		try {
			scheds = mapper.readValue(
					data,
					mapper.getTypeFactory().constructCollectionType(
							ArrayList.class, LoanScheduleDto.class));
			for (LoanScheduleDto sched : scheds) {
				LoanBillingDv billing = loanMap.get(sched.getLoan());
				if (billing != null && billing.isBillable()) {
					if ((billing.getBill() > 0)
							&& (billing.getBill() + sched.getTotal() <= billing
									.getSavingBallance())) {
						billing.setCount(billing.getCount() + 1);
						billing.setBill(billing.getBill() + sched.getTotal());
						billing.setPrincipal(billing.getPrincipal()
								+ sched.getPrincipal());
						billing.setMargin(billing.getMargin()
								+ sched.getMargin());
					}
					if (billing.getBill() <= 0) {
						billing.setCount(1);
						billing.setBill(sched.getTotal());
						billing.setPrincipal(sched.getPrincipal());
						billing.setMargin(sched.getMargin());
						billing.setBillable(billing.getBill() <= billing
								.getSavingBallance());
						billing.setDueDate(sched.getDate());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Buat list data pembiayaan yang aktif
	void fillLoanBilling() {
		System.out.println("fillLoanBilling....");
		// List seluruh pembiayaan yang sudah di create bulan lalu
		// Yang create bulan ini pasti belum wajib ditagih
		String data = jsonMain.sendRawData("listLoanId", dateBegin);
		String[] ids = data.split(";");
		for (String id : ids) {
			if (!id.isEmpty()) {
				// Ambil data pembiayaan dan yang berkaitan
				LoanInformationDto loanInfo = getLoanInfo(id);
				LoanTransactionDto loanTrans = getLoanTransInfo(id, dateEnd);
				double blockir = getBlockir(loanInfo.getCode());
				double sb = getWithdrawalSavingBallance(loanInfo.getSaving(),
						dateEnd);
				// Isi data-datanya
				LoanBillingDv loanBilling = new LoanBillingDv();
				loanBilling.setId(loanInfo.getId());
				loanBilling.setCode(loanInfo.getCode());
				loanBilling.setSchema(loanInfo.getSchema());
				loanBilling.setProduct(loanInfo.getProductName());
				loanBilling.setName(loanInfo.getName());
				loanBilling.setSaving(loanInfo.getSaving());
				Double savingBallance = savingMap.get(loanInfo.getSaving());
				if (savingBallance == null) {
					savingMap.put(loanInfo.getSaving(), sb);
				}
				loanBilling.setSavingBallance(sb - blockir);
				loanBilling.setPrincipalPayd(loanTrans.getPrincipal());
				loanBilling.setMarginPayd(loanTrans.getMargin());
				loanBilling.setBillable(loanTrans.getPrincipal() < loanInfo
						.getPrincipal());
				loanMap.put(loanInfo.getId(), loanBilling);
			}
		}
	}

	private LoanInformationDto getLoanInfo(String id) {
		ObjectMapper mapper = new ObjectMapper();
		String strInfo = jsonMain.sendRawData("getLoanInfo", id);
		LoanInformationDto info = null;
		try {
			info = mapper.readValue(strInfo, LoanInformationDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

	private LoanTransactionDto getLoanTransInfo(String id, String date) {
		ObjectMapper mapper = new ObjectMapper();
		String strLoanTrans = jsonMain.sendRawData("getSumLoanTransaction", id
				+ ";" + date);
		LoanTransactionDto loanTransDto = null;
		try {
			loanTransDto = mapper.readValue(strLoanTrans,
					LoanTransactionDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loanTransDto;
	}

	private double getWithdrawalSavingBallance(long id, String date) {
		String strBallance = jsonMain.sendRawData(
				"getWithdrawalSavingBallance", id + ";" + date);
		Double ballance = Double.parseDouble(strBallance);
		return ballance == null ? 0 : ballance;
	}

}
