package org.simbiosis.bp.micbank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.simbiosis.bp.gl.GlTransMsg;
import org.simbiosis.bp.gl.IGlTransMessaging;
import org.simbiosis.bp.savingMsg.SavingTransMessaging;
import org.simbiosis.bp.savingMsg.SavingTransMsg;
import org.simbiosis.gl.ICashBank;
import org.simbiosis.gl.ICoa;
import org.simbiosis.gl.ILedger;
import org.simbiosis.gl.model.CashTrans;
import org.simbiosis.gl.model.CashTransItem;
import org.simbiosis.gl.model.Coa;
import org.simbiosis.gl.model.GlTrans;
import org.simbiosis.gl.model.GlTransItem;
import org.simbiosis.microbank.ISaving;
import org.simbiosis.microbank.ITeller;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.SavingProductDto;
import org.simbiosis.microbank.SavingTransactionDto;
import org.simbiosis.microbank.TellerDto;
import org.simbiosis.microbank.TellerTransactionDto;
import org.simbiosis.microbank.VaultTransactionDto;
import org.simbiosis.system.AuthDto;
import org.simbiosis.system.BranchDto;
import org.simbiosis.system.ConfigDto;
import org.simbiosis.system.ISystem;
import org.simbiosis.system.SubBranchDto;
import org.simbiosis.system.UserDto;

@Stateless
@Remote(ITellerBp.class)
public class TellerBp implements ITellerBp {

	@EJB(lookup = "java:global/SystemEar/SystemEjb/SystemImpl")
	ISystem system;
	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/TellerImpl")
	ITeller teller;
	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/SavingImpl")
	ISaving saving;
	@EJB(lookup = "java:global/GlEar/GlEjb/CashBankImpl")
	ICashBank cashBank;
	@EJB(lookup = "java:global/GlEar/GlEjb/CoaImpl")
	ICoa iCoa;
	@EJB(lookup = "java:global/GlEar/GlEjb/LedgerImpl")
	ILedger iLedger;

	@EJB(lookup = "java:module/SavingTransMessaging")
	SavingTransMessaging savingMsg;
	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/GlTransMessaging")
	IGlTransMessaging glMsg;

	String fillers[] = { "", "0", "00", "000", "0000", "00000" };
	int lengths[] = { 6, 5 };
	private int LEVEL_SEE_ALL = 4;

	/*
	 * Function : createTellerTransCode Description : Untuk membuat kode
	 * transaksi pada teller
	 */
	String createTellerTransCode(long company, String prefixCode) {
		// Cari dulu yang sudah ada
		Long lCode = teller.getTransCodeCounter(company, prefixCode);
		String numberCode = lCode.toString();
		String code = fillers[lengths[0] - numberCode.length()] + numberCode;
		return prefixCode + code;
	}

	/*
	 * Function : createVaultTransCode Description : Untuk membuat kode
	 * transaksi vault pada teller
	 */
	String createVaultTransCode(long company, long branch, String prefixCode) {
		// Buat kode baru
		String myPrefixCode = prefixCode;
		// Cari dulu yang sudah ada
		String code = teller
				.getMaxVaultTransCode(company, branch, myPrefixCode);
		if (code != null) {
			int maxLength = code.length();
			int number = Integer.parseInt(code.substring(maxLength - 6,
					maxLength - 1)) + 1;
			String numberCode = "" + number;
			code = fillers[lengths[0] - numberCode.length()] + numberCode;
		} else {
			code = fillers[lengths[1]] + "1";
		}
		return myPrefixCode + code;
	}

	/*
	 * Function : createGlTrans Description : Create glTrans dari teller dan coa
	 */
	GlTrans createGlTrans(TellerTransactionDto transDto, long coa1, long coa2) {
		GlTrans glTrans = new GlTrans();
		glTrans.setDate(transDto.getDate());
		glTrans.setCode(transDto.getCode());
		glTrans.setCompany(transDto.getCompany());
		glTrans.setBranch(transDto.getBranch());
		glTrans.setDescription(transDto.getDescription());
		glTrans.setType(1);
		glTrans.setReleased(1);
		//
		GlTransItem itemDto = new GlTransItem();
		itemDto.setDescription(transDto.getDescription());
		itemDto.setDirection(transDto.getDirection());
		itemDto.setValue(transDto.getValue());
		itemDto.setCoa(iCoa.get(coa1));
		glTrans.getItems().add(itemDto);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription(transDto.getDescription());
		itemDto.setDirection(transDto.getDirection() == 1 ? 2 : 1);
		itemDto.setValue(transDto.getValue());
		itemDto.setCoa(iCoa.get(coa2));
		glTrans.getItems().add(itemDto);
		//
		return glTrans;
	}

	/*
	 * Function : createGlTrans Description : Create glTrans dari teller dan coa
	 */
	GlTrans createGlTransSavingClose(String rek, String name,
			TellerTransactionDto transDto, double ballance,
			double adminClosing, double ballanceRemaining, long coaProduct,
			long coaAdmin, long coaLaba) {
		TellerDto tellerDto = teller.getTeller(transDto.getTeller());
		GlTrans glTrans = new GlTrans();
		glTrans.setDate(transDto.getDate());
		glTrans.setCode(transDto.getCode());
		glTrans.setCompany(transDto.getCompany());
		glTrans.setBranch(transDto.getBranch());
		glTrans.setDescription("TARIK TUNAI DAN TUTUP TABUNGAN AN " + name
				+ " (" + rek + ")");
		glTrans.setType(1);
		glTrans.setReleased(1);
		//
		GlTransItem itemDto = new GlTransItem();
		itemDto.setDescription(transDto.getDescription());
		itemDto.setDirection(transDto.getDirection());
		itemDto.setValue(transDto.getValue());
		itemDto.setCoa(iCoa.get(tellerDto.getCoa()));
		glTrans.getItems().add(itemDto);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription("BIAYA ADMINISTRASI PENUTUPAN TABUNGAN AN "
				+ name + " (" + rek + ")");
		itemDto.setDirection(1);
		itemDto.setValue(ballance);
		itemDto.setCoa(iCoa.get(coaProduct));
		glTrans.getItems().add(itemDto);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription("PENDAPATAN PENUTUPAN TABUNGAN AN " + name
				+ " (" + rek + ")");
		itemDto.setDirection(2);
		itemDto.setValue(adminClosing);
		itemDto.setCoa(iCoa.get(coaAdmin));
		glTrans.getItems().add(itemDto);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription(transDto.getDescription());
		itemDto.setDirection(2);
		itemDto.setValue(ballanceRemaining);
		itemDto.setCoa(iCoa.get(coaLaba));
		glTrans.getItems().add(itemDto);
		//
		return glTrans;
	}

	/*
	 * Function : createGlTrans Description : Create glTrans dari teller dan coa
	 */
	GlTrans createGlTrans(TellerTransactionDto transDto, long coa) {
		TellerDto tellerDto = teller.getTeller(transDto.getTeller());
		return createGlTrans(transDto, tellerDto.getCoa(), coa);
	}

	/*
	 * Function : setupTellerTrans Description : Setting data untuk transaksi
	 * teller
	 */
	TellerTransactionDto setupTellerTrans(TellerTransactionDto transDto,
			TellerDto teller) {
		Date now = new Date();
		//
		transDto.setCompany(teller.getCompany());
		transDto.setBranch(teller.getBranch());
		transDto.setSubBranch(teller.getSubBranch());
		transDto.setTeller(teller.getId());
		transDto.setTimestamp(now);
		if (transDto.getId() == 0 && transDto.getCode().isEmpty()) {
			BranchDto branchDto = system.getBranch(teller.getBranch());
			SubBranchDto subBranchDto = system.getSubBranch(teller
					.getSubBranch());
			String tellerTransCode = createTellerTransCode(
					transDto.getCompany(),
					branchDto.getCode() + subBranchDto.getCode()
							+ teller.getCode());
			transDto.setCode(tellerTransCode);
		}
		return transDto;
	}

	@Override
	public long saveTellerSavingTrans(String key, TellerTransactionDto transDto) {
		UserDto user = system.getUserFromSession(key);
		long id = 0;
		if (user != null) {
			double ballance = transDto.getValue();
			double ballanceRemaining = transDto.getValue();
			double adminClosing = 0;
			//
			// Cari jumlah tabungan
			// Cari biaya administrasi
			// ConfigDto configAdmClosing = system.getConfig(user.getCompany(),
			// "saving.admClosing");
			// // Cari coa penampung sisa transaksi selain biaya admin
			// ConfigDto configCoaClosing = system.getConfig(user.getCompany(),
			// "saving.revCoaClosing");
			//
			TellerDto tellerDto = teller.getTellerByUser(user.getId());
			Date now = new Date();
			SavingInformationDto infoSaving = saving.getInformation(transDto
					.getAccountId());
			SavingProductDto product = saving.getProduct(infoSaving
					.getProduct());
			//
			String transDesc = (transDto.getDirection() == 1) ? "SETOR TUNAI - "
					: "TARIK TUNAI - ";
			transDesc += infoSaving.getName() + " (" + infoSaving.getCode()
					+ ")";
			transDesc += transDto.getDescription().isEmpty() ? ""
					: (" - " + transDto.getDescription());
			//
			if (infoSaving.getOnClose() == 1) {
				// Cari jumlah tabungan
				ballance = saving.getBallance(transDto.getAccountId(),
						transDto.getDate(), true);
				ballanceRemaining = ballance - transDto.getValue();
			}
			//
			transDto = setupTellerTrans(transDto, tellerDto);
			transDto.setDescription(transDesc.toUpperCase());
			transDto.setType(3); // for saving transaction
			id = teller.saveTellerTransaction(transDto);
			//
			SavingTransactionDto savingTransDto = new SavingTransactionDto();
			savingTransDto.setDate(transDto.getDate());
			savingTransDto.setTimestamp(now);
			savingTransDto.setCode(transDto.getCode());
			savingTransDto.setHasCode(true);
			savingTransDto.setDescription(transDto.getDescription());
			savingTransDto.setDirection(transDto.getDirection());
			savingTransDto.setValue(transDto.getValue());
			// Teller transaction masuk->1, keluar->2
			savingTransDto.setType(transDto.getDirection());
			savingTransDto.setSaving(transDto.getAccountId());
			savingTransDto.setCompany(user.getCompany());
			savingTransDto.setBranch(user.getBranch());
			SavingTransMsg savingTransMsg = new SavingTransMsg();
			savingTransMsg.setIdSource(id);
			savingTransMsg.setQueueName("java:jboss/queue/transTellerIn");
			savingTransMsg.setSavingTransactionDto(savingTransDto);
			savingMsg.sendSavingTrans(savingTransMsg);
			//
			//
			if (infoSaving.getOnClose() == 1) {
				adminClosing = product.getCloseAdmin();
				// keluarkan biaya administrasi
				savingTransDto = new SavingTransactionDto();
				savingTransDto.setDate(transDto.getDate());
				savingTransDto.setTimestamp(now);
				savingTransDto.setCode(transDto.getCode());
				savingTransDto.setHasCode(true);
				savingTransDto
						.setDescription("BIAYA ADMINISTRASI PENUTUPAN TABUNGAN AN "
								+ infoSaving.getName()
								+ " ("
								+ infoSaving.getCode() + ")");
				savingTransDto.setDirection(transDto.getDirection());
				savingTransDto.setValue(adminClosing);
				// Teller transaction masuk->1, keluar->2
				savingTransDto.setType(2);
				savingTransDto.setSaving(transDto.getAccountId());
				savingTransDto.setCompany(user.getCompany());
				savingTransDto.setBranch(user.getBranch());
				savingTransMsg = new SavingTransMsg();
				savingTransMsg.setIdSource(id);
				savingTransMsg.setQueueName("java:jboss/queue/transTellerIn");
				savingTransMsg.setSavingTransactionDto(savingTransDto);
				savingMsg.sendSavingTrans(savingTransMsg);
				// keluarkan sisa
				ballanceRemaining -= adminClosing;
				// keluarkan biaya administrasi
				savingTransDto = new SavingTransactionDto();
				savingTransDto.setDate(transDto.getDate());
				savingTransDto.setTimestamp(now);
				savingTransDto.setCode(transDto.getCode());
				savingTransDto.setHasCode(true);
				savingTransDto
						.setDescription("PENDAPATAN PENUTUPAN TABUNGAN AN "
								+ infoSaving.getName() + " ("
								+ infoSaving.getCode() + ")");
				savingTransDto.setDirection(transDto.getDirection());
				savingTransDto.setValue(ballanceRemaining);
				// Teller transaction masuk->1, keluar->2
				savingTransDto.setType(2);
				savingTransDto.setSaving(transDto.getAccountId());
				savingTransDto.setCompany(user.getCompany());
				savingTransDto.setBranch(user.getBranch());
				savingTransMsg = new SavingTransMsg();
				savingTransMsg.setIdSource(id);
				savingTransMsg.setQueueName("java:jboss/queue/transTellerIn");
				savingTransMsg.setSavingTransactionDto(savingTransDto);
				savingMsg.sendSavingTrans(savingTransMsg);
				//
				saving.closeSaving(transDto.getAccountId(), transDto.getDate());
			}
			//
			saveTellerSavingJournal(transDto, infoSaving, product, ballance,
					adminClosing, ballanceRemaining);
		}
		return id;
	}

	@Override
	public long savePrivateTellerSavingTrans(String key,
			TellerTransactionDto transDto) {
		UserDto user = system.getUserFromSession(key);
		long id = 0;
		if (user != null) {
			//
			SavingInformationDto infoSaving = saving.getInformation(transDto
					.getAccountId());
			SavingProductDto product = saving.getProduct(infoSaving
					.getProduct());
			//
			String transDesc = (transDto.getDirection() == 1) ? "SETOR TUNAI - "
					: "TARIK TUNAI - ";
			transDesc += infoSaving.getName() + " (" + infoSaving.getCode()
					+ ")";
			transDesc += transDto.getDescription().isEmpty() ? ""
					: (" - " + transDto.getDescription());
			//
			TellerDto tellerDto = teller.getTeller(transDto.getTeller());
			//
			transDto = setupTellerTrans(transDto, tellerDto);
			transDto.setDescription(transDesc.toUpperCase());
			transDto.setType(3); // for saving transaction
			id = teller.saveTellerTransaction(transDto);
			//
			SavingTransactionDto savingTransDto = new SavingTransactionDto();
			savingTransDto.setDate(transDto.getDate());
			savingTransDto.setTimestamp(new Date());
			savingTransDto.setCode(transDto.getCode());
			savingTransDto.setHasCode(true);
			savingTransDto.setDescription(transDto.getDescription());
			savingTransDto.setDirection(transDto.getDirection());
			savingTransDto.setValue(transDto.getValue());
			// Teller transaction masuk->1, keluar->2
			savingTransDto.setType(transDto.getDirection());
			savingTransDto.setSaving(transDto.getAccountId());
			savingTransDto.setCompany(user.getCompany());
			savingTransDto.setBranch(user.getBranch());
			SavingTransMsg savingTransMsg = new SavingTransMsg();
			savingTransMsg.setIdSource(id);
			savingTransMsg.setQueueName("java:jboss/queue/transTellerIn");
			savingTransMsg.setSavingTransactionDto(savingTransDto);
			savingMsg.sendSavingTrans(savingTransMsg);
			//
			saveTellerSavingJournal(transDto, infoSaving, product, 0, 0, 0);
		}
		return id;
	}

	private void saveTellerSavingJournal(TellerTransactionDto transDto,
			SavingInformationDto infoSaving, SavingProductDto product,
			double ballance, double adminClosing, double ballanceRemaining) {
		// Untuk pengolahan GL ada dua jenis yaitu untuk yang perlu rak, ato
		// tidak
		if (infoSaving.getBranch() == transDto.getBranch()) {
			// Tidak perlu rak
			GlTrans glTrans = null;
			if (infoSaving.getOnClose() == 1) {
				glTrans = createGlTransSavingClose(infoSaving.getCode(),
						infoSaving.getName(), transDto, ballance, adminClosing,
						ballanceRemaining, infoSaving.getCoa1(),
						infoSaving.getCoa3(), product.getCoa4());
			} else {
				glTrans = createGlTrans(transDto, infoSaving.getCoa1());
			}
			GlTransMsg transMsg = new GlTransMsg();
			transMsg.setIdSource(transDto.getId());
			transMsg.setQueueName("java:jboss/queue/transTellerIn");
			transMsg.setGlTransDto(glTrans);
			glMsg.sendGlTrans(transMsg);
		} else {
			// Perlu rak
			BranchDto branch = system.getBranch(infoSaving.getBranch());
			GlTrans glTrans = createGlTrans(transDto, branch.getParam1());
			GlTransMsg transMsg = new GlTransMsg();
			transMsg.setIdSource(transDto.getId());
			transMsg.setQueueName("java:jboss/queue/transTellerIn");
			transMsg.setGlTransDto(glTrans);
			glMsg.sendGlTrans(transMsg);
			//
			branch = system.getBranch(transDto.getBranch());
			glTrans = createGlTrans(transDto, branch.getParam1(),
					infoSaving.getCoa1());
			glTrans.setBranch(infoSaving.getBranch());
			transMsg = new GlTransMsg();
			transMsg.setIdSource(transDto.getId());
			transMsg.setQueueName("java:jboss/queue/transTellerIn");
			transMsg.setGlTransDto(glTrans);
			glMsg.sendGlTrans(transMsg);
		}
	}

	@Override
	public long saveTellerCashTrans(String key, TellerTransactionDto transDto) {
		UserDto user = system.getUserFromSession(key);
		long id = 0;
		if (user != null) {
			String transDesc = (transDto.getDirection() == 1) ? "SETOR KAS AN "
					: "TARIK KAS AN ";
			transDesc += transDto.getMaker();
			transDesc += transDto.getDescription().isEmpty() ? ""
					: (" - " + transDto.getDescription());
			//
			TellerDto tellerDto = teller.getTellerByUser(user.getId());
			transDto = setupTellerTrans(transDto, tellerDto);
			transDto.setDescription(transDesc);
			transDto.setType(2); // for cash transaction
			id = teller.saveTellerTransaction(transDto);
			//
			CashTrans ctransDto = new CashTrans();
			ctransDto.setCompany(user.getCompany());
			ctransDto.setBranch(user.getBranch());
			ctransDto.setCode(transDto.getCode());
			ctransDto.setDescription(transDto.getDescription());
			ctransDto.setMaker(transDto.getMaker());
			ctransDto.setDate(transDto.getDate());
			ctransDto.setDirection(transDto.getDirection());
			ctransDto.setHasRab(1);
			Coa coa = iCoa.get(tellerDto.getCoa());
			ctransDto.setCoa(coa);
			CashTransItem item = new CashTransItem();
			item.setDescription(transDto.getDescription());
			item.setValue(transDto.getValue());
			ConfigDto config = system.getConfig(tellerDto.getCompany(),
					"teller.RabCoa");
			coa = iCoa.get(config.getLongValue());
			item.setCoa(coa);
			ctransDto.getItems().add(item);
			cashBank.saveCashTrans(ctransDto);
			//
			saveTellerVaultCashJournal(transDto, config.getLongValue());
		}
		return id;
	}

	private void saveTellerVaultCashJournal(TellerTransactionDto transDto,
			long destCoa) {
		GlTrans glTrans = createGlTrans(transDto, destCoa);
		GlTransMsg transMsg = new GlTransMsg();
		transMsg.setIdSource(transDto.getId());
		transMsg.setQueueName("java:jboss/queue/transTellerIn");
		transMsg.setGlTransDto(glTrans);
		glMsg.sendGlTrans(transMsg);
	}

	@Override
	public TellerTransactionDto getTellerTransaction(long id) {
		return teller.getTellerTransaction(id);
	}

	@Override
	public List<TellerTransactionDto> listTellerTransaction(long tellerId,
			Date date) {
		return teller.listTellerTransactionByTeller(tellerId, date);
	}

	@Override
	public List<TellerTransactionDto> listTellerTransactionByUser(String key,
			Date date) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			TellerDto tellerDto = teller.getTellerByUser(user.getId());
			if (tellerDto != null) {
				return teller.listTellerTransactionByTeller(tellerDto.getId(),
						date);
			}
		}
		return new ArrayList<TellerTransactionDto>();
	}

	@Override
	public List<TellerDto> listTellerByLevel(String key) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			List<TellerDto> listTeller = null;
			if (user.getLevel() <= LEVEL_SEE_ALL) {
				listTeller = teller.listTeller(user.getCompany(), 0, 0);
			} else if (user.getLevel() <= 6) {
				listTeller = teller.listTeller(user.getCompany(),
						user.getBranch(), 0);
			} else {
				TellerDto teller = getTellerByUser(key);
				listTeller = new ArrayList<TellerDto>();
				listTeller.add(teller);
			}
			for (TellerDto teller : listTeller) {
				UserDto userDto = system.getUser(teller.getUser());
				teller.setName(userDto.getRealName());
			}
			return listTeller;
		}
		return new ArrayList<TellerDto>();
	}

	@Override
	public List<TellerDto> listAllTeller(String key) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			List<TellerDto> listTeller = teller.listTeller(user.getCompany(),
					0, 0);
			for (TellerDto teller : listTeller) {
				UserDto userDto = system.getUser(teller.getUser());
				teller.setName(userDto.getRealName());
			}
			return listTeller;
		}
		return new ArrayList<TellerDto>();
	}

	@Override
	public List<TellerDto> listTeller(String key) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			List<TellerDto> listTeller = teller.listTeller(user.getCompany(),
					user.getBranch(), user.getSubBranch());
			for (TellerDto teller : listTeller) {
				UserDto userDto = system.getUser(teller.getUser());
				teller.setName(userDto.getRealName());
			}
			return listTeller;
		}
		return new ArrayList<TellerDto>();
	}

	@Override
	public TellerDto getTellerByUser(String key) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			TellerDto tellerFound = teller.getTellerByUser(user.getId());
			if (tellerFound == null) {
				return null;
			}
			tellerFound.setName(user.getName());
			return tellerFound;
		}
		return null;
	}

	@Override
	public long saveVaultTrans(String key, VaultTransactionDto transDto) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			TellerDto selectedTeller = teller.getTeller(transDto.getTeller());
			transDto.setCompany(user.getCompany());
			transDto.setBranch(selectedTeller.getBranch());
			transDto.setSubBranch(user.getSubBranch());
			if (transDto.getId() == 0) {
				BranchDto branchDto = system.getBranch(user.getBranch());
				String transCode = createVaultTransCode(transDto.getCompany(),
						transDto.getBranch(), branchDto.getCode());
				transDto.setCode(transCode);
			}

			return teller.saveVaultTransaction(transDto);
		}
		return 0;
	}

	@Override
	public VaultTransactionDto getVaultInProcess(String key, long tellerId,
			Date date) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			TellerDto tellerDto = teller.getTeller(tellerId);
			return teller.getVaultInProcess(user.getCompany(),
					tellerDto.getBranch(), tellerId, date);
		}
		return null;
	}

	@Override
	public VaultTransactionDto getVaultTrans(long id) {
		return teller.getVaultTransaction(id);
	}

	@Override
	public VaultTransactionDto approveVaultTrans(String key,
			VaultTransactionDto transDto) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			//
			TellerDto tellerDto = teller.getTeller(transDto.getTeller());
			TellerTransactionDto ttDto = new TellerTransactionDto();
			ttDto.setDate(transDto.getDate());
			ttDto.setDirection((transDto.getDirection() == 1) ? 2 : 1);
			ttDto.setValue(transDto.getValue());
			ttDto.setDescription((transDto.getDirection() == 1) ? "PENGEMBALIAN DANA KE KHASANAH"
					: "PENGAMBILAN DANA DARI KHASANAH");
			ttDto = setupTellerTrans(ttDto, tellerDto);
			ttDto.setType(1); // for vault transaction
			teller.saveTellerTransaction(ttDto);
			//
			VaultTransactionDto approveDto = teller
					.getVaultTransaction(transDto.getId());
			approveDto.setStatus(1);
			approveDto.setCode(ttDto.getCode());
			if (transDto.getDirection() == 1) {
				approveDto.getItems().addAll(transDto.getItems());
			} else {
				approveDto.setValue(transDto.getValue());
			}
			teller.saveVaultTransaction(approveDto);
			//
			ConfigDto config = system
					.getConfig(ttDto.getCompany(), "vault.coa");
			saveTellerVaultCashJournal(ttDto, config.getLongValue());
			//
			return approveDto;
		}
		return null;
	}

	@Override
	public double getBallance(long id, Date date) {
		return teller.getBallance(id, date);
	}

	@Override
	public int getAuthorizedLevel(String key, String description, double value) {
		UserDto user = system.getUserFromSession(key);
		// =====================================================================
		// FIXME: Ini harus di masukkan ke dalam konfigurasi
		// =====================================================================
		double[] values = { 0, 1000000000, 1000000000, 10000000, 10000000,
				50000000, 1000000, 1000000 };
		long BRANCH_PUSAT = 3;
		int DIREKTUR = 2;
		int GMOP = 3;
		int KACAB = 5;
		int HEADTELLER = 6;
		// =====================================================================
		if (user != null) {
			// Kalau punya otorisasi lanjut
			AuthDto auth = system.getAuthByKey(key);
			if ((auth != null) && (auth.getAuthorizer() != 0)) {
				system.disposeAuth(auth.getId());
				return 0;
			}
			// Kalau di bawah batas max teller lanjut
			if (value <= values[user.getLevel()]) {
				return 0;
			} else {
				int level = HEADTELLER;
				// Create return value
				if (user.getBranch() == BRANCH_PUSAT) {
					if (value > values[GMOP]) {
						level = DIREKTUR;
					} else {
						level = GMOP;
					}
				} else {
					if (value > values[HEADTELLER]) {
						if (value > values[KACAB]) {
							level = DIREKTUR;
						} else {
							level = KACAB;
						}
					}
				}
				// Create auth if not exist
				if (auth == null) {
					AuthDto dto = new AuthDto();
					dto.setTimestamp(new Date());
					dto.setKey(key);
					dto.setUser(user.getId());
					dto.setCompany(user.getCompany());
					dto.setBranch(user.getBranch());
					dto.setDescription(description);
					dto.setActive(1);
					dto.setLevel(level);
					system.saveAuth(dto);
				}
				// return
				return level;
			}
		}
		return 0;
	}

	@Override
	public boolean hasTellerApproval(String key) {
		AuthDto auth = system.getAuthByKey(key);
		if (auth != null && auth.getAuthorizer() != 0) {
			return true;
		}
		return false;
	}

	@Override
	public long saveTeller(String key, TellerDto tellerDto) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			tellerDto.setCompany(user.getCompany());
			return teller.saveTeller(tellerDto);
		}
		return 0;
	}

	@Override
	public UserDto getUserTeller(long tellerId) {
		TellerDto tellerDto = teller.getTeller(tellerId);
		return system.getUser(tellerDto.getUser());
	}

	@Override
	public List<TellerTransactionDto> listTellerTransaction(String key,
			Date startDate, Date endDate) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			return teller.listTellerTransaction(user.getCompany(), 0,
					startDate, endDate);
		}
		return new ArrayList<TellerTransactionDto>();
	}

	@Override
	public void saveGlVaultTellerTrans(String key, TellerTransactionDto transDto) {
		ConfigDto config = system.getConfig(transDto.getCompany(), "vault.coa");
		saveTellerVaultCashJournal(transDto, config.getLongValue());
	}

	@Override
	public void saveGlCashTellerTrans(String key, TellerTransactionDto transDto) {
		ConfigDto config = system.getConfig(transDto.getCompany(),
				"teller.RabCoa");
		saveTellerVaultCashJournal(transDto, config.getLongValue());
	}

	@Override
	public void saveGlSavingTellerTrans(String key,
			TellerTransactionDto transDto) {
		double ballance = transDto.getValue();
		double ballanceRemaining = transDto.getValue();
		double adminClosing = 0;
		//
		SavingInformationDto infoSaving = saving.getInformation(transDto
				.getAccountId());
		SavingProductDto product = saving.getProduct(infoSaving.getProduct());
		//
		saveTellerSavingJournal(transDto, infoSaving, product, ballance,
				adminClosing, ballanceRemaining);
		//
	}

}
