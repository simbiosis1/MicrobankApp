package org.simbiosis.ui.bprs.teller.server;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.bp.micbank.ISavingBp;
import org.simbiosis.bp.micbank.ITellerBp;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.SavingTransactionDto;
import org.simbiosis.microbank.TellerDto;
import org.simbiosis.microbank.TellerTransactionDto;
import org.simbiosis.microbank.VaultTransactionDto;
import org.simbiosis.microbank.VaultTransactionItemDto;
import org.simbiosis.system.UserDto;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;
import org.simbiosis.ui.bprs.teller.client.rpc.AppService;
import org.simbiosis.ui.bprs.teller.shared.TellerDv;
import org.simbiosis.ui.bprs.teller.shared.UploadCollectiveDv;
import org.simbiosis.ui.bprs.teller.shared.VaultTransactionDv;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class AppServiceImpl extends RemoteServiceServlet implements AppService {

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/SavingBp")
	ISavingBp savingBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/TellerBp")
	ITellerBp tellerBp;

	DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");

	public AppServiceImpl() {
	}

	String formatNumber(double number) {
		String format = "#,###,##0.00";
		DecimalFormatSymbols forSpace = new DecimalFormatSymbols();
		forSpace.setGroupingSeparator('.');
		forSpace.setMonetaryDecimalSeparator(',');
		DecimalFormat formatter = new DecimalFormat(format, forSpace);
		return formatter.format(number);
	}

	Double getNumber(String number) {
		String format = "#,###,##0.00";
		DecimalFormatSymbols forSpace = new DecimalFormatSymbols();
		forSpace.setGroupingSeparator('.');
		forSpace.setMonetaryDecimalSeparator(',');
		DecimalFormat formatter = new DecimalFormat(format, forSpace);
		Double d = 0D;
		try {
			Number n = formatter.parse(number);
			d = n.doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	@Override
	public TransactionDv saveSavingTrans(String key, TransactionDv transDv)
			throws IllegalArgumentException {
		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		TellerDto teller = tellerBp.getTellerByUser(key);
		// Validasi teller exist apa enggak
		if (teller == null) {
			IllegalArgumentException e = new IllegalArgumentException(
					"Pengguna tidak terdaftar sebagai teller");
			throw e;
		}
		// Validasi teller exist end
		TellerTransactionDto transDto = new TellerTransactionDto();
		transDto.setDate(transDv.getDate());
		transDto.setDirection(transDv.getDirection());
		transDto.setRefCode(transDv.getRefCode() != null ? transDv.getRefCode()
				.toUpperCase() : "");
		transDto.setDescription(transDv.getDescription() != null ? transDv
				.getDescription().toUpperCase() : "");
		// transDto.setValue(Double.parseDouble(transDv.getStrValue()));
		transDto.setValue(transDv.getValue());
		transDto.setAccountId(transDv.getSaving().getId());
		//
		SavingInformationDto info = savingBp.getInformation(transDto
				.getAccountId());
		//
		// Validasi kecukupan saldo tabungan dan saldo teller ketika penarikan
		if (transDto.getDirection() == 2) {
			double saldo = 0;
			// Kecukupan saldo tabungan
			if (info.getOnClose() == 1) {
				//
				double closingAdm = savingBp
						.getSavingProduct(info.getProduct()).getCloseAdmin();
				//
				saldo = savingBp.getBallance(transDv.getSaving().getId(),
						transDv.getDate(), true);
				if (transDto.getValue() + closingAdm > saldo) {
					throw new IllegalArgumentException(
							"Saldo rekening tabungan " + info.getCode()
									+ " tidak mencukupi");
				}
			} else {
				saldo = savingBp.getWithdrawalBallance(transDv.getSaving()
						.getId(), transDv.getDate(), false);
				if (transDto.getValue() > saldo) {
					throw new IllegalArgumentException(
							"Saldo rekening tabungan " + info.getCode()
									+ " tidak mencukupi");
				}
			}
			// Kecukupan saldo teller
			saldo = tellerBp.getBallance(teller.getId(), transDv.getDate());
			if (transDto.getValue() > saldo) {
				throw new IllegalArgumentException(
						"Saldo teller tidak mencukupi");
			}
			// Otorisasi
			int level = tellerBp.getAuthorizedLevel(key, "TARIK TUNAI AN "
					+ info.getName() + " (" + info.getCode() + ") SEBESAR "
					+ df.format(transDto.getValue()), transDto.getValue());
			if (level != 0) {
				throw new IllegalArgumentException("AUTH");
			}
		}
		//
		long id = tellerBp.saveTellerSavingTrans(key, transDto);
		transDto = tellerBp.getTellerTransaction(id);
		transDv.setCode(transDto.getCode());
		// Validasi
		String baris1 = transDto.getCode() + " " + transDto.getDirection()
				+ " " + info.getCode() + " " + info.getName();
		String baris2 = "IDR " + formatNumber(transDv.getValue());
		String baris3 = "" + transDto.getTimestamp() + " " + teller.getCode()
				+ " " + teller.getName();
		transDv.setValidationText(baris1 + "<>" + baris2 + "<>" + baris3);
		return transDv;
	}

	@Override
	public TransactionDv saveCashTrans(String key, TransactionDv transDv)
			throws IllegalArgumentException {
		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		TellerDto teller = tellerBp.getTellerByUser(key);
		// Validasi teller exist apa enggak
		if (teller == null) {
			IllegalArgumentException e = new IllegalArgumentException(
					"Pengguna tidak terdaftar sebagai teller");
			throw e;
		}
		TellerTransactionDto transDto = new TellerTransactionDto();
		transDto.setDate(transDv.getDate());
		transDto.setDirection(transDv.getDirection());
		transDto.setRefCode(transDv.getRefCode() != null ? transDv.getRefCode()
				.toUpperCase() : "");
		transDto.setDescription(transDv.getDescription() != null ? transDv
				.getDescription().toUpperCase() : "");
		// transDto.setValue(Double.parseDouble(transDv.getStrValue()));
		transDto.setValue(transDv.getValue());
		transDto.setMaker(transDv.getMaker() != null ? transDv.getMaker()
				.toUpperCase() : "");
		// Validasi kecukupan saldo tabungan dan saldo teller ketika penarikan
		if (transDto.getDirection() == 2) {
			// Kecukupan saldo teller
			double saldo = tellerBp.getBallance(teller.getId(),
					transDv.getDate());
			if (transDto.getValue() > saldo) {
				IllegalArgumentException e = new IllegalArgumentException(
						"Saldo teller tidak mencukupi");
				throw e;
			}
			// Otorisasi
			int level = tellerBp.getAuthorizedLevel(key,
					"TARIK KAS UNTUK : " + transDto.getDescription()
							+ " SEBESAR " + df.format(transDto.getValue()),
					transDto.getValue());
			if (level != 0) {
				throw new IllegalArgumentException("AUTH");
			}
		}
		long id = tellerBp.saveTellerCashTrans(key, transDto);
		transDto = tellerBp.getTellerTransaction(id);
		transDv.setRefCode(transDto.getRefCode());
		transDv.setCode(transDto.getCode());
		transDv.setMaker(transDto.getMaker());
		transDv.setDescription(transDto.getDescription());
		// Validasi
		String baris1 = transDto.getCode() + " " + transDto.getDirection()
				+ " CASH " + transDto.getMaker();
		String baris2 = "IDR " + formatNumber(transDv.getValue());
		String baris3 = "" + transDto.getTimestamp() + " " + teller.getCode()
				+ " " + teller.getName();
		transDv.setValidationText(baris1 + "<>" + baris2 + "<>" + baris3);
		return transDv;
	}

	@Override
	public List<TransactionDv> listTellerTransactionByTeller(String key,
			Long tellerId, Date date) throws IllegalArgumentException {
		List<TransactionDv> result = new ArrayList<TransactionDv>();
		List<TellerTransactionDto> transDtos = tellerBp.listTellerTransaction(
				tellerId, date);
		int nr = 1;
		double saldo = 0;
		for (TellerTransactionDto transDto : transDtos) {
			TransactionDv transDv = new TransactionDv();
			transDv.setId(transDto.getId());
			transDv.setNr(nr++);
			transDv.setDate(transDto.getDate());
			transDv.setCode(transDto.getCode());
			transDv.setRefCode(transDto.getRefCode());
			switch (transDto.getType()) {
			case 1:
				transDv.setStrType("VAU");
				break;
			case 2:
				transDv.setStrType("KAS");
				break;
			case 3:
				transDv.setStrType("TAB");
				break;
			}
			transDv.setDescription(transDto.getDescription());
			if (transDto.getDirection() == 1) {
				transDv.setDebit(transDto.getValue());
				transDv.setCredit(0D);
				saldo += transDto.getValue();
			} else {
				transDv.setDebit(0D);
				transDv.setCredit(transDto.getValue());
				saldo -= transDto.getValue();
			}
			transDv.setTotal(saldo);
			result.add(transDv);
		}
		return result;
	}

	VaultTransactionItemDto createVaultItem(double value, int type,
			String amount) {
		VaultTransactionItemDto item = new VaultTransactionItemDto();
		item.setValue(value);
		item.setAmount(Double.parseDouble(amount));
		item.setType(type);
		return item;
	}

	@Override
	public VaultTransactionDv saveVaultTrans(String key,
			VaultTransactionDv transDv) throws IllegalArgumentException {
		TellerDto teller = tellerBp.getTellerByUser(key);
		// Validasi teller exist apa enggak
		if (teller == null) {
			IllegalArgumentException e = new IllegalArgumentException(
					"Pengguna tidak terdaftar sebagai teller");
			throw e;
		}
		VaultTransactionDto transDto = new VaultTransactionDto();
		transDto.setDirection(transDv.getDirection());
		transDto.setDate(transDv.getDate());
		transDto.setStatus(0);
		String strValue = "";
		if (transDv.getDirection() == 1) {
			transDto.setTeller(teller.getId());
			// transDto.setValue(Double.parseDouble(transDv.getStrValue()));
			transDto.setValue(transDv.getValue());
			strValue = formatNumber(transDv.getValue());
			// Validasi kecukupan saldo tabungan dan saldo teller ketika
			// penarikan
			// Kecukupan saldo teller
			double saldo = tellerBp.getBallance(teller.getId(),
					transDv.getDate());
			if (transDto.getValue() > saldo) {
				IllegalArgumentException e = new IllegalArgumentException(
						"Saldo teller tidak mencukupi");
				throw e;

			}
		} else {
			transDto.setTeller(transDv.getTeller());
			VaultTransactionItemDto item = null;
			Double value = 0D;
			//
			item = createVaultItem(50, 1, transDv.getStr50L());
			transDto.getItems().add(item);
			value += (item.getValue() * item.getAmount());
			//
			item = createVaultItem(100, 1, transDv.getStr100L());
			transDto.getItems().add(item);
			value += (item.getValue() * item.getAmount());
			//
			item = createVaultItem(200, 1, transDv.getStr200L());
			transDto.getItems().add(item);
			value += (item.getValue() * item.getAmount());
			//
			item = createVaultItem(500, 1, transDv.getStr500L());
			transDto.getItems().add(item);
			value += (item.getValue() * item.getAmount());
			//
			item = createVaultItem(1000, 1, transDv.getStr1000L());
			transDto.getItems().add(item);
			value += (item.getValue() * item.getAmount());
			//
			item = createVaultItem(1000, 2, transDv.getStr1000K());
			transDto.getItems().add(item);
			value += (item.getValue() * item.getAmount());
			//
			item = createVaultItem(2000, 2, transDv.getStr2000K());
			transDto.getItems().add(item);
			value += (item.getValue() * item.getAmount());
			//
			item = createVaultItem(5000, 2, transDv.getStr5000K());
			transDto.getItems().add(item);
			value += (item.getValue() * item.getAmount());
			//
			item = createVaultItem(10000, 2, transDv.getStr10000K());
			transDto.getItems().add(item);
			value += (item.getValue() * item.getAmount());
			//
			item = createVaultItem(20000, 2, transDv.getStr20000K());
			transDto.getItems().add(item);
			value += (item.getValue() * item.getAmount());
			//
			item = createVaultItem(50000, 2, transDv.getStr50000K());
			transDto.getItems().add(item);
			value += (item.getValue() * item.getAmount());
			//
			item = createVaultItem(100000, 2, transDv.getStr100000K());
			transDto.getItems().add(item);
			value += (item.getValue() * item.getAmount());
			//
			strValue = "" + value;
		}
		long id = tellerBp.saveVaultTrans(key, transDto);
		transDto = tellerBp.getVaultTrans(id);
		transDv.setCode(transDto.getCode());
		String baris1 = transDto.getCode() + " " + transDto.getDirection()
				+ " VAULT";
		String baris2 = "IDR " + strValue;
		String baris3 = "" + new Date() + " " + teller.getCode() + " "
				+ teller.getName();
		transDv.setValidationText(baris1 + "<>" + baris2 + "<>" + baris3);
		return transDv;
	}

	@Override
	public VaultTransactionDv getAvailableVaultTrans(String key,
			VaultTransactionDv transDv) throws IllegalArgumentException {
		VaultTransactionDto transDto = null;
		transDv.setValue(0D);
		transDv.setStrValue("---");
		transDv.setCode("---");
		if (transDv.getDirection() == 1) {
			transDto = tellerBp.getVaultInProcess(key, transDv.getTeller(),
					transDv.getDate());
			if (transDto != null) {
				transDv.setValue(transDto.getValue());
				transDv.setStrValue(formatNumber(transDv.getValue()));
			}
		} else {
			TellerDto teller = tellerBp.getTellerByUser(key);
			transDto = tellerBp.getVaultInProcess(key, teller.getId(),
					transDv.getDate());
			if (transDto != null) {
				double value = 0;
				for (VaultTransactionItemDto itemDto : transDto.getItems()) {
					value += (itemDto.getValue() * itemDto.getAmount());
				}
				transDv.setValue(value);
				transDv.setStrValue(formatNumber(transDv.getValue()));
			}
		}
		if (transDto != null) {
			transDv.setId(transDto.getId());
			transDv.setCode(transDto.getCode());
			transDv.setDate(transDto.getDate());
			transDv.setStrDate(dtf.print(new DateTime(transDv.getDate())));
		}
		return transDv;
	}

	@Override
	public VaultTransactionDv approveVaultTrans(String key,
			VaultTransactionDv transDv) throws IllegalArgumentException {
		VaultTransactionDto transDto = tellerBp.getVaultTrans(transDv.getId());
		String strValue = "";
		if (transDto.getDirection() == 1) {
			transDto.setTeller(transDv.getTeller());
			//
			//
			double value = 0;
			VaultTransactionItemDto item = null;
			//
			item = createVaultItem(50, 1, transDv.getStr50L());
			value += (item.getValue() * item.getAmount());
			transDto.getItems().add(item);
			//
			item = createVaultItem(100, 1, transDv.getStr100L());
			value += (item.getValue() * item.getAmount());
			transDto.getItems().add(item);
			//
			item = createVaultItem(200, 1, transDv.getStr200L());
			value += (item.getValue() * item.getAmount());
			transDto.getItems().add(item);
			//
			item = createVaultItem(500, 1, transDv.getStr500L());
			value += (item.getValue() * item.getAmount());
			transDto.getItems().add(item);
			//
			item = createVaultItem(1000, 1, transDv.getStr1000L());
			value += (item.getValue() * item.getAmount());
			transDto.getItems().add(item);
			//
			item = createVaultItem(1000, 2, transDv.getStr1000K());
			value += (item.getValue() * item.getAmount());
			transDto.getItems().add(item);
			//
			item = createVaultItem(2000, 2, transDv.getStr2000K());
			value += (item.getValue() * item.getAmount());
			transDto.getItems().add(item);
			//
			item = createVaultItem(5000, 2, transDv.getStr5000K());
			value += (item.getValue() * item.getAmount());
			transDto.getItems().add(item);
			//
			item = createVaultItem(10000, 2, transDv.getStr10000K());
			value += (item.getValue() * item.getAmount());
			transDto.getItems().add(item);
			//
			item = createVaultItem(20000, 2, transDv.getStr20000K());
			value += (item.getValue() * item.getAmount());
			transDto.getItems().add(item);
			//
			item = createVaultItem(50000, 2, transDv.getStr50000K());
			value += (item.getValue() * item.getAmount());
			transDto.getItems().add(item);
			//
			item = createVaultItem(100000, 2, transDv.getStr100000K());
			value += (item.getValue() * item.getAmount());
			transDto.getItems().add(item);
			//
			if (value != transDto.getValue()) {
				System.out.println("Nilai gak sama");
				IllegalArgumentException e = new IllegalArgumentException(
						"Jumlah dana teller tidak sama dengan jumlah detil");
				throw e;
			} else {
				strValue = "" + value;
			}
			//
		} else {
			double value = 0;
			for (VaultTransactionItemDto item : transDto.getItems()) {
				value += (item.getValue() * item.getAmount());
			}
			transDto.setValue(value);
		}
		transDto = tellerBp.approveVaultTrans(key, transDto);
		transDv.setCode(transDto.getCode());
		UserDto user = tellerBp.getUserTeller(transDto.getTeller());
		//
		String baris1 = transDto.getCode() + " " + transDto.getDirection()
				+ " VAULT";
		String baris2 = "IDR " + strValue;
		// FIXME : Harus di cari siapa tellernya
		String baris3 = "" + new Date() + " T " + user.getName();
		transDv.setValidationText(baris1 + "<>" + baris2 + "<>" + baris3);
		return transDv;
	}

	@Override
	public List<TellerDv> listTeller(String key)
			throws IllegalArgumentException {
		List<TellerDv> listTellerDv = new ArrayList<TellerDv>();
		List<TellerDto> listTellerDto = tellerBp.listTellerByLevel(key);
		for (TellerDto tellerDto : listTellerDto) {
			TellerDv tellerDv = new TellerDv();
			tellerDv.setId(tellerDto.getId());
			tellerDv.setCode(tellerDto.getCode());
			tellerDv.setName(tellerDto.getName());
			listTellerDv.add(tellerDv);
		}
		return listTellerDv;
	}

	@Override
	public List<TransactionDv> listSavingTransaction(String key, Long savingId,
			Date beginDate, Date endDate) throws IllegalArgumentException {
		List<TransactionDv> result = new ArrayList<TransactionDv>();
		List<SavingTransactionDto> savTransDtos = savingBp.listSavingStatement(
				savingId, beginDate, endDate);
		int nr = 1;
		double saldo = 0;
		for (SavingTransactionDto savTransDto : savTransDtos) {
			TransactionDv transaction = new TransactionDv();
			transaction.setId(savTransDto.getId());
			transaction.setNr(nr++);
			transaction.setDate(savTransDto.getDate());
			transaction.setCode(savTransDto.getCode());
			transaction.setRefCode(savTransDto.getRefCode());
			switch (savTransDto.getType()) {
			case 1:
				transaction.setStrType("ST");
				break;
			case 2:
				transaction.setStrType("TT");
				break;
			case 3:
				transaction.setStrType("SPB");
				break;
			case 4:
				transaction.setStrType("TPB");
				break;
			}
			transaction.setDescription(savTransDto.getDescription());
			if (savTransDto.getDirection() == 1) {
				transaction.setCredit(savTransDto.getValue());
				transaction.setDebit(0D);
				saldo += savTransDto.getValue();
			} else {
				transaction.setCredit(0D);
				transaction.setDebit(savTransDto.getValue());
				saldo -= savTransDto.getValue();
			}
			transaction.setTotal(saldo);
			result.add(transaction);
		}
		return result;
	}

	@Override
	public Boolean hasTellerApproval(String key)
			throws IllegalArgumentException {
		return tellerBp.hasTellerApproval(key);
	}

	@Override
	public List<UploadCollectiveDv> listConfirmSavingCollective(String key,Date date,
			String srcData) throws IllegalArgumentException {
		List<UploadCollectiveDv> result = new ArrayList<UploadCollectiveDv>();
		String source = srcData.replace("\r", "");
		String datas[] = source.split("\n");
		double total = 0;
		for (int i = 0; i < datas.length; i++) {
			String line[] = datas[i].split("\t");
			// Slip Code, rek, nama, ket, debit, kredit
			if (line.length >= 6) {
				double debit = line[4].replace(",", "").isEmpty() ? 0 : Double
						.parseDouble(line[4].replace(",", ""));
				double credit = line[5].replace(",", "").isEmpty() ? 0 : Double
						.parseDouble(line[5].replace(",", ""));
				total += (credit - debit);
				UploadCollectiveDv dv = new UploadCollectiveDv(i + 1, line[0],
						line[1], line[2], "", line[3], debit, credit, total);
				long id = savingBp.getSavingIdByCode(key, line[1]);
				if (id != 0) {
					SavingInformationDto info = savingBp.getInformation(id);
					dv.setSystemName(info.getName());
					dv.setSavingId(info.getId());
					dv.processSavingTrans();
					double saldo = savingBp.getWithdrawalBallance(id, date, false);
					if (dv.getDirection()==2 && dv.getValue()>saldo){
						dv.setStatus(3);
					}
				} else{
					dv.setStatus(1);
				}
				result.add(dv);
			}
		}
		return result;
	}

	@Override
	public void uploadSavingCollective(String key, Date date, Long teller,
			List<UploadCollectiveDv> datas) throws IllegalArgumentException {
		for (UploadCollectiveDv data : datas){
			TellerTransactionDto transDto = new TellerTransactionDto();
			transDto.setDate(date);
			transDto.setDirection(data.getDirection());
			transDto.setCode(data.getRefCode());
			transDto.setRefCode(data.getRefCode());
			transDto.setDescription(data.getDescription().toUpperCase());
			transDto.setValue(data.getValue());
			transDto.setAccountId(data.getSavingId());
			transDto.setTeller(teller);
			//
			tellerBp.savePrivateTellerSavingTrans(key, transDto);
		}
	}

	@Override
	public void executeCollective(String key, Long coa,
			List<UploadCollectiveDv> data) throws IllegalArgumentException {
		DateTime now = new DateTime();
		DateTimeFormatter df = DateTimeFormat.forPattern("yyyyMMdd");
		String code = "GAJ" + df.print(now);
		System.out.println("Coa : " + coa.toString());
		Map<Long, Double> mapValues = new HashMap<Long, Double>();
		for (UploadCollectiveDv dv : data) {
			SavingInformationDto info = savingBp.getInformation(dv
					.getSavingId());
			Double currentValue = mapValues.get(info.getCoa1());
			if (currentValue == null) {
				// currentValue = dv.getValue();
			} else {
				// currentValue += dv.getValue();
			}
			mapValues.put(info.getCoa1(), currentValue);
			SavingTransactionDto trans = new SavingTransactionDto();
			trans.setDate(now.toDate());
			trans.setCode(code);
			trans.setHasCode(true);
			trans.setDirection(1);
			trans.setDescription("GAJI - " + info.getName() + " ("
					+ info.getCode() + ")");
			trans.setType(3);
			// trans.setValue(dv.getValue());
			trans.setSaving(dv.getSavingId());
			savingBp.saveTransaction(key, trans);
			// System.out.println("Transfer : " + dv.getSavingId() + ", "
			// + dv.getValue().toString());
		}
		Set<Long> keys = mapValues.keySet();
		for (Long lKey : keys) {
			System.out.println("Coa : " + lKey + ", " + mapValues.get(lKey));
		}
	}

	@Override
	public void executeUpdate(String key, Date date, Long teller,
			List<UploadCollectiveDv> data) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

}
