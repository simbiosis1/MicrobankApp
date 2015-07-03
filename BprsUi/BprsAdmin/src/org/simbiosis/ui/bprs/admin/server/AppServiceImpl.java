package org.simbiosis.ui.bprs.admin.server;

import java.text.DecimalFormat;
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
import org.simbiosis.bp.gl.IGlBp;
import org.simbiosis.bp.micbank.IDepositBp;
import org.simbiosis.bp.micbank.ILoanBp;
import org.simbiosis.bp.micbank.ISavingBp;
import org.simbiosis.gl.model.Coa;
import org.simbiosis.gl.model.GlTrans;
import org.simbiosis.gl.model.GlTransItem;
import org.simbiosis.microbank.DepositTransactionDto;
import org.simbiosis.microbank.GuaranteeDto;
import org.simbiosis.microbank.LoanDto;
import org.simbiosis.microbank.LoanScheduleDto;
import org.simbiosis.microbank.LoanTransactionDto;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.SavingTransactionDto;
import org.simbiosis.ui.bprs.admin.client.rpc.AppService;
import org.simbiosis.ui.bprs.admin.shared.CoaDv;
import org.simbiosis.ui.bprs.admin.shared.TransferCollectiveDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class AppServiceImpl extends RemoteServiceServlet implements AppService {

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/DepositBp")
	IDepositBp depositBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/LoanBp")
	ILoanBp loanBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/SavingBp")
	ISavingBp savingBp;
	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/GlBp")
	IGlBp glBp;

	DecimalFormat nf = new DecimalFormat("#,##0.00");

	public AppServiceImpl() {
	}

	private CoaDv createCoaDv(Coa coa) {
		CoaDv coaDv = new CoaDv();
		coaDv.setId(coa.getId());
		coaDv.setCode(coa.getCode());
		coaDv.setDescription(coa.getDescription());
		return coaDv;
	}

	@Override
	public List<CoaDv> listCoaForTransaction(String key) {
		List<CoaDv> returnList = new ArrayList<CoaDv>();
		for (Coa coa : glBp.listCoaForTransaction(key)) {
			returnList.add(createCoaDv(coa));
		}
		return returnList;
	}

	@Override
	public TransactionDv saveLoanTrans(String key, TransactionDv dv)
			throws IllegalArgumentException {
		LoanTransactionDto dto = new LoanTransactionDto();
		dto.setDate(dv.getDate());
		dto.setLoan(dv.getLoan().getId());
		dto.setRefCode(dv.getRefCode() == null ? "" : dv.getRefCode()
				.toUpperCase());
		LoanDto loanDto = loanBp.getLoan(dv.getLoan().getId());
		String baris2 = "IDR ";
		if (dv.getLoan().isDropped()) {
			dto.setType(dv.getType());
			dto.setDirection(2);
			dto.setPrincipal(dv.getPrincipal());
			dto.setMargin(dv.getMargin());
			dto.setDiscount(dv.getDiscount());
			//
			long savingId = dv.getLoan().getSaving().getId();
			double value = dto.getPrincipal() + dto.getMargin();
			// Kecukupan saldo tabungan
			double saldo = savingBp.getWithdrawalBallance(savingId,
					dto.getDate(), true);
			SavingInformationDto info = savingBp.getInformation(savingId);
			if (value > saldo) {
				throw new IllegalArgumentException("Saldo rekening tabungan "
						+ info.getCode() + " tidak mencukupi");
			}
			baris2 += nf.format(dv.getPrincipal()) + "+"
					+ nf.format(dv.getMargin());

		} else {
			dto.setType(dv.getType());
			dto.setDirection(1);
			dto.setPrincipal(dv.getValue());
			dto.setMargin(dv.getMargin());
			//
			if (loanDto.getGuarantees().size() == 0) {
				throw new IllegalArgumentException(
						"Belum ada jaminan untuk pembiayaan ini");
			}
			GuaranteeDto guaranteeDto = loanBp.getGuarantee(loanDto
					.getGuarantees().get(0).getId());
			if (guaranteeDto.getType() == 0) {
				throw new IllegalArgumentException(
						"Jaminan untuk pembiayaan ini belum diinput dengan benar");
			}
			baris2 += nf.format(dv.getValue());
		}
		dto = loanBp.saveCompleteLoanTrans(key, dto);
		dv.setCode(dto.getCode());
		// Validasi
		String baris1 = dto.getCode() + " " + dto.getDirection() + " "
				+ loanDto.getCode() + " - " + dv.getSaving().getCode() + " "
				+ dv.getLoan().getName();
		String baris3 = "" + new Date();
		dv.setValidationText(baris1 + "<>" + baris2 + "<>" + baris3);
		//
		return dv;
	}

	@Override
	public TransactionDv saveDepositTrans(String key, TransactionDv dv)
			throws IllegalArgumentException {
		DepositTransactionDto dto = new DepositTransactionDto();
		dto.setDate(dv.getDate());
		dto.setDeposit(dv.getDeposit().getId());
		dto.setRefCode(dv.getRefCode() == null ? "" : dv.getRefCode()
				.toUpperCase());
		dto.setValue(dv.getValue());
		dto.setDirection(dv.getType());
		if (dto.getDirection() == 1) {
			// Kecukupan saldo tabungan ketika mau setoran
			double saldo = savingBp.getWithdrawalBallance(dv.getSaving()
					.getId(), dto.getDate(), false);
			SavingInformationDto info = savingBp.getInformation(dv.getSaving()
					.getId());
			if (dto.getValue() > saldo) {
				throw new IllegalArgumentException("Saldo rekening tabungan "
						+ info.getCode() + " tidak mencukupi");
			}
		} else {
			// Kalu mau dicairkan liat dulu apa dia di jadikan jaminan
			if (depositBp.isDepositAsGuarantee(key, dto.getDeposit())) {
				throw new IllegalArgumentException(
						"Deposito tidak bisa dicairkan karena digunakan sebagai jaminan");
			}
		}

		dto = depositBp.saveDepositTrans(key, dto, dv.getSaving().getId());
		dv.setValue(dto.getValue());
		dv.setCode(dto.getCode());
		// Validasi
		String baris1 = dto.getCode() + " " + dto.getDirection() + " "
				+ dv.getDeposit().getCode() + " - " + dv.getSaving().getCode()
				+ " " + dv.getDeposit().getName();
		String baris2 = "IDR " + nf.format(dv.getValue());
		String baris3 = "" + new Date();
		dv.setValidationText(baris1 + "<>" + baris2 + "<>" + baris3);
		//
		return dv;
	}

	@Override
	public TransactionDv saveSavingJournalTrans(String key, TransactionDv dv)
			throws IllegalArgumentException {
		SavingTransactionDto dto = new SavingTransactionDto();
		dto.setDate(dv.getDate());
		dto.setSaving(dv.getSaving().getId());
		dto.setRefCode(dv.getRefCode() == null ? "" : dv.getRefCode()
				.toUpperCase());
		dto.setDescription(dv.getDescription() == null ? "" : dv
				.getDescription().toUpperCase());
		// dto.setValue(Double.parseDouble(dv.getStrValue().replace(",", "")));
		dto.setValue(dv.getValue());
		dto.setDirection(dv.getType());
		if (dto.getDirection() == 2) {
			// Kecukupan saldo tabungan
			double saldo = savingBp.getWithdrawalBallance(dto.getSaving(),
					dto.getDate(), false);
			SavingInformationDto info = savingBp
					.getInformation(dto.getSaving());
			if (dto.getValue() > saldo) {
				throw new IllegalArgumentException("Saldo rekening tabungan "
						+ info.getCode() + " tidak mencukupi");
			}
		}
		SavingTransactionDto transDto = savingBp.saveSavingJournalTrans(key,
				dto, dv.getCoa());
		dv.setValue(dto.getValue());
		dv.setCode(transDto.getCode());
		// Validasi
		Coa coa = glBp.getCoa(dv.getCoa());
		dv.setStrCoa(coa.getCode() + " - " + coa.getDescription());
		String baris1 = transDto.getCode() + " " + transDto.getDirection()
				+ " " + coa.getCode() + " - " + dv.getSaving().getCode() + " "
				+ dv.getSaving().getName();
		String baris2 = "IDR " + nf.format(dv.getValue());
		String baris3 = "" + new Date();
		dv.setValidationText(baris1 + "<>" + baris2 + "<>" + baris3);
		//
		return dv;
	}

	@Override
	public TransactionDv saveTransferSaving(String key, TransactionDv dv)
			throws IllegalArgumentException {
		SavingTransactionDto srcDto = new SavingTransactionDto();
		srcDto.setDate(dv.getDate());
		srcDto.setSaving(dv.getSaving().getId());
		srcDto.setRefCode(dv.getRefCode() == null ? "" : dv.getRefCode()
				.toUpperCase());
		srcDto.setDescription(dv.getDescription() == null ? "" : dv
				.getDescription().toUpperCase());
		srcDto.setValue(dv.getValue());
		srcDto.setDirection(2);
		// Kecukupan saldo tabungan
		double saldo = savingBp.getWithdrawalBallance(srcDto.getSaving(),
				srcDto.getDate(), false);
		SavingInformationDto info = savingBp.getInformation(srcDto.getSaving());
		if (srcDto.getValue() > saldo) {
			throw new IllegalArgumentException("Saldo rekening tabungan "
					+ info.getCode() + " tidak mencukupi");
		}
		//
		SavingTransactionDto destDto = new SavingTransactionDto();
		destDto.setDate(dv.getDate());
		destDto.setSaving(dv.getSavingDest().getId());
		srcDto.setRefCode(dv.getRefCode() == null ? "" : dv.getRefCode()
				.toUpperCase());
		srcDto.setDescription(dv.getDescription() == null ? "" : dv
				.getDescription().toUpperCase());
		destDto.setValue(dv.getValue());
		destDto.setDirection(1);
		SavingTransactionDto transDto = new SavingTransactionDto();
		Long id = savingBp.saveSavingTrfTrans(key, srcDto, destDto);
		transDto.setTimestamp(new Date());
		transDto.setCode(id.toString());
		dv.setCode(transDto.getCode());
		// Validasi
		String baris1 = transDto.getCode() + " 2 " + dv.getSaving().getCode()
				+ " " + dv.getSaving().getName() + " - 1 "
				+ dv.getSavingDest().getCode() + " "
				+ dv.getSavingDest().getName();
		String baris2 = "IDR " + nf.format(dv.getValue());
		String baris3 = "" + transDto.getTimestamp();
		dv.setValidationText(baris1 + "<>" + baris2 + "<>" + baris3);
		//
		return dv;
	}

	@Override
	public List<TransactionDv> getRepaymentValue(Long id)
			throws IllegalArgumentException {
		List<TransactionDv> result = new ArrayList<TransactionDv>();
		//
		LoanScheduleDto dto = loanBp.getNormalRepayment(id);
		TransactionDv trans = new TransactionDv();
		trans.setDate(dto.getDate());
		trans.setPrincipal(dto.getPrincipal());
		trans.setMargin(dto.getMargin());
		trans.setTotal(trans.getPrincipal() + trans.getMargin());
		result.add(trans);
		//
		dto = loanBp.getEarlyRepayment(id);
		trans = new TransactionDv();
		trans.setDate(dto.getDate());
		trans.setPrincipal(dto.getPrincipal());
		trans.setMargin(dto.getMargin());
		trans.setTotal(trans.getPrincipal() + trans.getMargin());
		result.add(trans);
		//
		return result;
	}

	@Override
	public List<TransferCollectiveDv> listConfirmGaji(String key, String srcData)
			throws IllegalArgumentException {
		List<TransferCollectiveDv> result = new ArrayList<TransferCollectiveDv>();
		String source = srcData.replace("\r", "");
		String datas[] = source.split("\n");
		for (int i = 0; i < datas.length; i++) {
			String line[] = datas[i].split("\t");
			if (line.length >= 3) {
				String accountCode = line[0].trim();
				TransferCollectiveDv dv = new TransferCollectiveDv(i + 1,
						accountCode, line[1], Double.parseDouble(line[2]
								.replace(",", "")));
				//
				long id = savingBp.getSavingIdByCode(key, accountCode);
				if (id != 0) {
					SavingInformationDto info = savingBp.getInformation(id);
					dv.setSystemName(info.getName());
					dv.setSavingId(info.getId());
				} else {
					dv.setStatus("Rekening tidak ditemukan");
				}
				result.add(dv);
			}
		}
		return result;
	}

	@Override
	public List<TransferCollectiveDv> listConfirmTransfer(String key,
			String srcData) throws IllegalArgumentException {
		List<TransferCollectiveDv> result = new ArrayList<TransferCollectiveDv>();
		String source = srcData.replace("\r", "");
		String datas[] = source.split("\n");
		for (int i = 0; i < datas.length; i++) {
			String line[] = datas[i].split("\t");
			if (line.length >= 5) {
				String srcCode = line[0].trim();
				String destCode = line[2].trim();
				TransferCollectiveDv dv = new TransferCollectiveDv(i + 1,
						srcCode, line[1], destCode, line[3],
						Double.parseDouble(line[4].replace(",", "")));
				//
				long srcId = savingBp.getSavingIdByCode(key, srcCode);
				long destId = savingBp.getSavingIdByCode(key, destCode);
				// cek rekening asal
				if (srcId != 0) {
					SavingInformationDto info = savingBp.getInformation(srcId);
					dv.setSystemName(info.getName());
					dv.setSavingId(info.getId());
					// check rekening tujuan
					if (destId != 0) {
						info = savingBp.getInformation(destId);
						dv.setDestSystemName(info.getName());
						dv.setDestSavingId(info.getId());
						// check saldo
						double saldo = savingBp.getWithdrawalBallance(srcId,
								new Date(), false);
						if (saldo < dv.getValue()) {
							dv.setStatus("Saldo sumber tidak mencukupi "
									+ saldo);
						}
					} else {
						dv.setStatus("Rekening tujuan tidak ditemukan");
					}
				} else {
					dv.setStatus("Rekening sumber tidak ditemukan");
				}
				result.add(dv);
			}
		}
		return result;
	}

	@Override
	public void executeCollectiveGaji(String key, String description,
			Integer type, Long lcoa, String acc, List<TransferCollectiveDv> data)
			throws IllegalArgumentException {
		DateTime now = new DateTime();
		DateTimeFormatter df = DateTimeFormat.forPattern("yyyyMMdd");
		DateTimeFormatter mf = DateTimeFormat.forPattern("MM/yyyy");
		String code = "GAJ" + df.print(now);
		Double totalValue = 0D;
		Map<Long, Double> mapValues = new HashMap<Long, Double>();
		//
		// Kirim ke masing-masing tabungan
		for (TransferCollectiveDv dv : data) {
			SavingInformationDto info = savingBp.getInformation(dv
					.getSavingId());
			Double currentValue = mapValues.get(info.getCoa1());
			if (currentValue == null) {
				currentValue = dv.getValue();
			} else {
				currentValue += dv.getValue();
			}
			mapValues.put(info.getCoa1(), currentValue);
			SavingTransactionDto trans = new SavingTransactionDto();
			trans.setDate(now.toDate());
			trans.setCode(code);
			trans.setHasCode(true);
			trans.setDirection(1);
			trans.setDescription(description.toUpperCase() + mf.print(now)
					+ " - " + info.getName() + " (" + info.getCode() + ")");
			trans.setType(3);
			trans.setValue(dv.getValue());
			totalValue += dv.getValue();
			trans.setSaving(dv.getSavingId());
			savingBp.saveTransaction(key, trans);
		}
		// Cari tabungan asal
		if (type == 2) {
			long accId = savingBp.getSavingIdByCode(key, acc);
			SavingInformationDto accInfo = savingBp.getInformation(accId);
			lcoa = accInfo.getCoa1();
			//
			SavingTransactionDto trans = new SavingTransactionDto();
			trans.setDate(now.toDate());
			trans.setCode(code);
			trans.setHasCode(true);
			trans.setDirection(2);
			trans.setDescription("PENGELUARAN " + description.toUpperCase()
					+ " " + mf.print(now) + " - " + accInfo.getName() + " ("
					+ accInfo.getCode() + ")");
			trans.setType(4);
			trans.setValue(totalValue);
			trans.setSaving(accId);
			savingBp.saveTransaction(key, trans);
		}

		// Buat jurnal
		Set<Long> keys = mapValues.keySet();
		GlTrans jurnal = new GlTrans();
		jurnal.setCode(code);
		jurnal.setDate(now.toDate());
		jurnal.setDescription("PENGELUARAN " + description.toUpperCase() + " "
				+ mf.print(now));
		// jurnal.setUser(user);
		Coa coa = glBp.getCoa(lcoa);
		GlTransItem item = new GlTransItem();
		item.setCoa(coa);
		item.setDescription(description);
		item.setValue(totalValue);
		item.setDirection(1);
		item.setTransaction(jurnal);
		jurnal.getItems().add(item);
		for (Long lKey : keys) {
			item = new GlTransItem();
			coa = glBp.getCoa(lKey);
			item.setCoa(coa);
			item.setDescription(description);
			item.setValue(mapValues.get(lKey));
			item.setDirection(2);
			item.setTransaction(jurnal);
			jurnal.getItems().add(item);
		}
		glBp.saveGLTrans(key, jurnal);
	}

	@Override
	public void executeCollectiveTransfer(String key, String description,
			List<TransferCollectiveDv> datas) throws IllegalArgumentException {
		Date now = new Date();
		for (TransferCollectiveDv data : datas) {
			TransactionDv dv = new TransactionDv();
			dv.setDate(now);
			dv.setDescription(description.toUpperCase());
			SavingDv savingDv = new SavingDv();
			savingDv.setId(data.getSavingId());
			savingDv.setCode(data.getCode());
			savingDv.setName(data.getSystemName());
			dv.setSaving(savingDv);
			savingDv = new SavingDv();
			savingDv.setId(data.getDestSavingId());
			savingDv.setCode(data.getDestCode());
			savingDv.setName(data.getDestSystemName());
			dv.setSavingDest(savingDv);
			dv.setValue(data.getValue());
			saveTransferSaving(key, dv);
		}

	}

}
