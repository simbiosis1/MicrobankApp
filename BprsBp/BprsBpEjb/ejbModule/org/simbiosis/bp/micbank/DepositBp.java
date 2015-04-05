package org.simbiosis.bp.micbank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.joda.time.DateTime;
import org.simbiosis.bp.gl.GlTransMsg;
import org.simbiosis.bp.gl.IGlTransMessaging;
import org.simbiosis.bp.savingMsg.SavingTransMessaging;
import org.simbiosis.bp.savingMsg.SavingTransMsg;
import org.simbiosis.gl.ICoa;
import org.simbiosis.gl.model.GlTrans;
import org.simbiosis.gl.model.GlTransItem;
import org.simbiosis.microbank.DepositDto;
import org.simbiosis.microbank.DepositInformationDto;
import org.simbiosis.microbank.DepositProductDto;
import org.simbiosis.microbank.DepositTransactionDto;
import org.simbiosis.microbank.FindDepositDto;
import org.simbiosis.microbank.IDeposit;
import org.simbiosis.microbank.ILoan;
import org.simbiosis.microbank.ISaving;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.SavingTransactionDto;
import org.simbiosis.system.BranchDto;
import org.simbiosis.system.ISystem;
import org.simbiosis.system.SubBranchDto;
import org.simbiosis.system.UserDto;

@Stateless
@Remote(IDepositBp.class)
public class DepositBp implements IDepositBp {

	@EJB(lookup = "java:global/SystemEar/SystemEjb/SystemImpl")
	ISystem iSystem;
	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/DepositImpl")
	IDeposit iDeposit;
	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/SavingImpl")
	ISaving savingBean;
	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/LoanImpl")
	ILoan loanBean;
	@EJB(lookup = "java:global/GlEar/GlEjb/CoaImpl")
	ICoa iCoa;
	@EJB(lookup = "java:module/SavingTransMessaging")
	SavingTransMessaging savingMsg;
	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/GlTransMessaging")
	IGlTransMessaging glMsg;

	String fillers[] = { "", "0", "00", "000", "0000", "00000" };
	int lengthsDeposit[] = { 5, 4 };
	int lengthsTrans[] = { 5, 4 };

	String createDepositCode(long company, String prefixCode) {
		// Cari dulu yang sudah ada
		Long lCode = iDeposit.getCodeCounter(company, prefixCode);
		String numberCode = lCode.toString();
		String code = fillers[lengthsDeposit[0] - numberCode.length()]
				+ numberCode;
		return prefixCode + code;
	}

	String createTransCode(long company, long branch, String prefixCode) {
		// Buat kode baru
		String myPrefixCode = prefixCode;
		// Cari dulu yang sudah ada
		String code = iDeposit.getMaxDepositTransCode(company, branch,
				myPrefixCode);
		if (code != null) {
			int subStrLen = myPrefixCode.length();
			int number = Integer.parseInt(code.substring(subStrLen)) + 1;
			String numberCode = "" + number;
			int length = numberCode.length();
			length = (length > lengthsTrans[0]) ? 0
					: (lengthsTrans[0] - length);
			code = fillers[length] + numberCode;
		} else {
			code = fillers[lengthsTrans[1]] + "1";
		}
		return myPrefixCode + code;
	}

	@Override
	public long saveDepositProduct(String key,
			DepositProductDto depositProductDto) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			depositProductDto.setCompany(user.getCompany());
			return iDeposit.saveDepositProduct(depositProductDto);
		}
		return 0;
	}

	@Override
	public DepositProductDto getDepositProduct(long id) {
		return iDeposit.getDepositProduct(id);
	}

	@Override
	public List<DepositProductDto> listDepositProduct(String key) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			return iDeposit.listDepositProduct(user.getCompany());
		}
		return new ArrayList<DepositProductDto>();
	}

	@Override
	public long saveDeposit(String key, DepositDto dto) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			dto.setCompany(user.getCompany());
			dto.setBranch(user.getBranch());
			DepositProductDto productDto = null;
			if (dto.getId() == 0) {
				productDto = iDeposit.getDepositProduct(dto.getProduct());
				// Buat code yang berurutan
				if (!dto.isHasCode()) {
					BranchDto branchDto = iSystem.getBranch(user.getBranch());
					SubBranchDto subBranchDto = iSystem.getSubBranch(user
							.getSubBranch());
					String depositCode = createDepositCode(dto.getCompany(),
							branchDto.getCode() + subBranchDto.getCode()
									+ productDto.getCode());
					dto.setCode(depositCode);
					// Set tanggal-tanggal
					dto.setRegistration(new Date());
					dto.setBegin(dto.getRegistration());
					//
					dto.setActive(1);
				}
			} else {
				DepositDto myDeposit = iDeposit.getDeposit(dto.getId());
				productDto = iDeposit.getDepositProduct(myDeposit.getProduct());
				myDeposit.setCode(dto.getCode());
				myDeposit.setProduct(dto.getProduct());
				myDeposit.setAro(dto.getAro());
				myDeposit.setBilyet(dto.getBilyet());
				myDeposit.setValue(dto.getValue());
				myDeposit.setZakat(dto.getZakat());
				if (dto.getBegin() != null) {
					myDeposit.setBegin(dto.getBegin());
				}
				dto = myDeposit;
			}
			// Set akhir dari deposito/tergantung product
			DateTime end = new DateTime(dto.getBegin()).plusMonths(productDto
					.getTerm());
			dto.setEnd(end.toDate());
			//
			return iDeposit.saveDeposit(dto);
		}
		return 0;
	}

	@Override
	public DepositDto getDeposit(long id) {
		return iDeposit.getDeposit(id);
	}

	@Override
	public List<DepositDto> listDeposit(String key, FindDepositDto findDeposit) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			findDeposit.setCompany(user.getCompany());
			findDeposit.setBranch(user.getBranch());
			return iDeposit.listDeposit(findDeposit);
		}
		return new ArrayList<DepositDto>();
	}

	@Override
	public List<DepositDto> findDeposit(String key, FindDepositDto findDeposit) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			findDeposit.setCompany(user.getCompany());
			findDeposit.setBranch(user.getBranch());
			return iDeposit.findDeposit(findDeposit);
		}
		return new ArrayList<DepositDto>();
	}

	@Override
	public long saveDepositTrans(String key, DepositTransactionDto trans) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			trans.setCompany(user.getCompany());
			trans.setBranch(user.getBranch());
			return iDeposit.saveDepositTransaction(trans);
		}
		return 0;
	}

	@Override
	public DepositTransactionDto saveDepositTrans(String key,
			DepositTransactionDto trans, long savingId) {
		// Cari informasi tentang deposit
		DepositInformationDto depInfo = iDeposit.getDepositInformation(trans
				.getDeposit());
		// Simpan transaksi deposit dan saving nya
		trans = saveDepositTrans(trans, depInfo, savingId);
		// Buat Gl
		if (trans.getDirection() == 1) {
			createSetor(trans, depInfo, savingId);
		} else {
			createCair(trans, depInfo, savingId);
		}
		// Set flag apakah aktif ato di tutup
		if (trans.getDirection() == 1) {

		} else {
			iDeposit.closeDeposit(depInfo.getId(), trans.getDate());
		}
		//
		return trans;
	}

	@Override
	public void closeDeposit(long id, Date date) {
		iDeposit.closeDeposit(id, date);
	}

	DepositTransactionDto saveDepositTrans(DepositTransactionDto dto,
			DepositInformationDto info, long savingId) {
		Date now = new Date();
		String transDesc = (dto.getDirection() == 1) ? "SETORAN DEPOSITO - "
				: "PENCAIRAN DEPOSITO - ";
		transDesc += (info.getName() + " (" + info.getCode() + ")");
		// Generate code
		BranchDto branchDto = iSystem.getBranch(info.getBranch());
		String transCode = createTransCode(info.getCompany(), info.getBranch(),
				branchDto.getCode() + dto.getDirection());
		// Save Deposit Transaction
		dto.setCode(transCode);
		dto.setDescription(transDesc);
		dto.setCompany(info.getCompany());
		dto.setBranch(info.getBranch());
		long id = iDeposit.saveDepositTransaction(dto);
		// Save saving transaction
		SavingTransactionDto savingTrans = new SavingTransactionDto();
		savingTrans.setDate(dto.getDate());
		savingTrans.setTimestamp(now);
		savingTrans.setCode(transCode);
		savingTrans.setHasCode(true);
		savingTrans.setDescription(transDesc);
		savingTrans.setDirection(dto.getDirection() == 1 ? 2 : 1);
		savingTrans.setValue(dto.getValue());
		// Teller transaction masuk->1, keluar->2
		savingTrans.setType(dto.getDirection() == 1 ? 4 : 3);
		savingTrans.setSaving(savingId);
		savingTrans.setCompany(info.getCompany());
		savingTrans.setBranch(info.getBranch());
		SavingTransMsg savingTransactionMsg = new SavingTransMsg();
		savingTransactionMsg.setIdSource(id);
		savingTransactionMsg.setQueueName("java:jboss/queue/transTellerIn");
		savingTransactionMsg.setSavingTransactionDto(savingTrans);
		savingMsg.sendSavingTrans(savingTransactionMsg);
		//
		return dto;
	}

	void createSetor(DepositTransactionDto transDto,
			DepositInformationDto infoDep, long savingId) {
		SavingInformationDto infoSaving = savingBean.getInformation(savingId);
		long akunPenampung = 0;
		// Kalo tabungan dan deposito tidak di satu tempat
		if (infoSaving.getBranch() != transDto.getBranch()) {
			// Buat transaksi RAK
			BranchDto branch = iSystem.getBranch(infoDep.getBranch());
			//
			GlTrans glTrans = new GlTrans();
			glTrans.setDate(transDto.getDate());
			glTrans.setCode(transDto.getCode());
			glTrans.setCompany(transDto.getCompany());
			glTrans.setBranch(infoSaving.getBranch());
			glTrans.setDescription(transDto.getDescription());
			glTrans.setType(1);
			glTrans.setReleased(1);
			//
			GlTransItem itemDto = new GlTransItem();
			itemDto.setDescription(transDto.getDescription());
			itemDto.setDirection(1);
			itemDto.setValue(transDto.getValue());
			itemDto.setCoa(iCoa.get(infoSaving.getCoa1()));
			glTrans.getItems().add(itemDto);
			//
			itemDto = new GlTransItem();
			itemDto.setDescription(transDto.getDescription());
			itemDto.setDirection(2);
			itemDto.setValue(transDto.getValue());
			itemDto.setCoa(iCoa.get(branch.getParam1()));
			glTrans.getItems().add(itemDto);
			//
			GlTransMsg transMsg = new GlTransMsg();
			transMsg.setIdSource(transDto.getId());
			transMsg.setQueueName("java:jboss/queue/transTellerIn");
			transMsg.setGlTransDto(glTrans);
			glMsg.sendGlTrans(transMsg);
			//
			branch = iSystem.getBranch(infoSaving.getBranch());
			akunPenampung = branch.getParam1();
		} else {
			akunPenampung = infoSaving.getCoa1();
		}
		// Buat transaksi utama
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
		itemDto.setDirection(1);
		itemDto.setValue(transDto.getValue());
		itemDto.setCoa(iCoa.get(akunPenampung));
		glTrans.getItems().add(itemDto);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription(transDto.getDescription());
		itemDto.setDirection(2);
		itemDto.setValue(transDto.getValue());
		itemDto.setCoa(iCoa.get(infoDep.getCoa1()));
		glTrans.getItems().add(itemDto);
		//
		GlTransMsg transMsg = new GlTransMsg();
		transMsg.setIdSource(transDto.getId());
		transMsg.setQueueName("java:jboss/queue/transTellerIn");
		transMsg.setGlTransDto(glTrans);
		glMsg.sendGlTrans(transMsg);
	}

	void createCair(DepositTransactionDto transDto,
			DepositInformationDto infoDep, long savingId) {
		SavingInformationDto infoSaving = savingBean.getInformation(savingId);
		long akunPenampung = 0;
		// Kalo tabungan dan pembiayaan tidak di satu tempat
		if (infoSaving.getBranch() != transDto.getBranch()) {
			// Buat transaksi RAK
			BranchDto branch = iSystem.getBranch(infoDep.getBranch());
			//
			GlTrans glTrans = new GlTrans();
			glTrans.setDate(transDto.getDate());
			glTrans.setCode(transDto.getCode());
			glTrans.setCompany(transDto.getCompany());
			glTrans.setBranch(infoSaving.getBranch());
			glTrans.setDescription(transDto.getDescription());
			glTrans.setType(1);
			glTrans.setReleased(1);
			//
			GlTransItem itemDto = new GlTransItem();
			itemDto.setDescription(transDto.getDescription());
			itemDto.setDirection(1);
			itemDto.setValue(transDto.getValue());
			itemDto.setCoa(iCoa.get(branch.getParam1()));
			glTrans.getItems().add(itemDto);
			//
			itemDto = new GlTransItem();
			itemDto.setDescription(transDto.getDescription());
			itemDto.setDirection(2);
			itemDto.setValue(transDto.getValue());
			itemDto.setCoa(iCoa.get(infoSaving.getCoa1()));
			glTrans.getItems().add(itemDto);
			//
			GlTransMsg transMsg = new GlTransMsg();
			transMsg.setIdSource(transDto.getId());
			transMsg.setQueueName("java:jboss/queue/transTellerIn");
			transMsg.setGlTransDto(glTrans);
			glMsg.sendGlTrans(transMsg);
			//
			branch = iSystem.getBranch(infoSaving.getBranch());
			akunPenampung = branch.getParam1();
		} else {
			akunPenampung = infoSaving.getCoa1();
		}
		// Buat transaksi utama
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
		itemDto.setDirection(2);
		itemDto.setValue(transDto.getValue());
		itemDto.setCoa(iCoa.get(akunPenampung));
		glTrans.getItems().add(itemDto);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription(transDto.getDescription());
		itemDto.setDirection(1);
		itemDto.setValue(transDto.getValue());
		itemDto.setCoa(iCoa.get(infoDep.getCoa1()));
		glTrans.getItems().add(itemDto);
		//
		GlTransMsg transMsg = new GlTransMsg();
		transMsg.setIdSource(transDto.getId());
		transMsg.setQueueName("java:jboss/queue/transTellerIn");
		transMsg.setGlTransDto(glTrans);
		glMsg.sendGlTrans(transMsg);
	}

	@Override
	public boolean isDepositAsGuarantee(String key, long depId) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			DepositDto dto = iDeposit.getDeposit(depId);
			return loanBean.isDepositAsGuarantee(user.getCompany(),
					dto.getCode());
		}
		return false;
	}

	@Override
	public List<Long> listDepositId(String key, Date date) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			FindDepositDto find = new FindDepositDto();
			find.setActive(1);
			find.setCompany(user.getCompany());
			return iDeposit.listDepositId(find, date);
		}
		return new ArrayList<Long>();
	}

	@Override
	public DepositInformationDto getDepositInformation(long id) {
		return iDeposit.getDepositInformation(id);
	}

	@Override
	public long isDepositExistByRefId(String key, long branch, long refId) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			return iDeposit.isDepositExistByRefId(user.getCompany(), branch,
					refId);
		}
		return 0;
	}

	@Override
	public long getDepositByCode(String key, String code) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			return iDeposit.getDepositByCode(user.getCompany(), code);
		}
		return 0;
	}
}
