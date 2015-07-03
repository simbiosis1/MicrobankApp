package org.simbiosis.bp.micbank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.simbiosis.bp.gl.IGlTransMessaging;
import org.simbiosis.bp.savingMsg.SavingTransMessaging;
import org.simbiosis.bp.savingMsg.SavingTransMsg;
import org.simbiosis.microbank.FindSavingDto;
import org.simbiosis.microbank.ISaving;
import org.simbiosis.microbank.SavingBlockirDto;
import org.simbiosis.microbank.SavingDto;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.SavingPrintCodeRefDto;
import org.simbiosis.microbank.SavingProductDto;
import org.simbiosis.microbank.SavingTransInfoDto;
import org.simbiosis.microbank.SavingTransactionDto;
import org.simbiosis.system.BranchDto;
import org.simbiosis.system.ISystem;
import org.simbiosis.system.SubBranchDto;
import org.simbiosis.system.UserDto;

@Stateless
@Remote(ISavingBp.class)
public class SavingBp implements ISavingBp {

	@EJB(lookup = "java:global/SystemEar/SystemEjb/SystemImpl")
	ISystem iSystem;
	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/SavingImpl")
	ISaving iSaving;

	@EJB(lookup = "java:module/SavingTrans")
	SavingTrans savingTrans;

	@EJB(lookup = "java:module/SavingTransMessaging")
	SavingTransMessaging savingMsg;
	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/GlTransMessaging")
	IGlTransMessaging glMsg;

	String fillers[] = { "", "0", "00", "000", "0000", "00000" };
	int lengthsSaving[] = { 5, 4 };
	int lengthsTrans[] = { 6, 5 };

	String createSavingCode(long company, String prefixCode) {
		// Cari dulu yang sudah ada
		Long lCode = iSaving.getCodeCounter(company, prefixCode);
		String numberCode = lCode.toString();
		String code = fillers[lengthsSaving[0] - numberCode.length()]
				+ numberCode;
		return prefixCode + code;
	}

	String createSavingTransCode(long company, long branch, String prefixCode) {
		// Buat kode baru
		String myPrefixCode = prefixCode;
		// Cari dulu yang sudah ada
		String code = iSaving.getMaxTransCode(company, branch, myPrefixCode);
		if (code != null) {
			String strNumber = code.substring(code.length() - 6);
			strNumber = strNumber.replace("A", "");
			int number = Integer.parseInt(strNumber) + 1;
			String numberCode = "" + number;
			int length = lengthsTrans[0] - numberCode.length();
			if (length > 5) {
				length = 5;
			} else if (length < 0) {
				length = 0;
			}
			code = fillers[length] + numberCode;
		} else {
			code = fillers[lengthsTrans[1]] + "1";
		}
		return myPrefixCode + code;
	}

	@Override
	public long saveSavingProduct(String key, SavingProductDto savingProductDto) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			savingProductDto.setCompany(user.getCompany());
			return iSaving.saveProduct(savingProductDto);
		}
		return 0;
	}

	@Override
	public SavingProductDto getSavingProduct(long id) {
		return iSaving.getProduct(id);
	}

	@Override
	public List<SavingProductDto> listSavingProduct(String key) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			return iSaving.listProduct(user.getCompany());
		}
		return new ArrayList<SavingProductDto>();
	}

	@Override
	public long saveSaving(String key, SavingDto savingDto) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			savingDto.setCompany(user.getCompany());
			savingDto.setBranch(user.getBranch());
			if (savingDto.getId() == 0) {
				if (!savingDto.isHasCode()) {
					BranchDto branchDto = iSystem.getBranch(user.getBranch());
					SubBranchDto subBranchDto = iSystem.getSubBranch(user
							.getSubBranch());
					SavingProductDto productDto = iSaving.getProduct(savingDto
							.getProduct());
					String savingCode = createSavingCode(
							savingDto.getCompany(),
							branchDto.getCode() + subBranchDto.getCode()
									+ productDto.getCode());
					savingDto.setCode(savingCode);
				}
				savingDto.setActive(1);
			}
			return iSaving.save(savingDto);
		}
		return 0;
	}

	@Override
	public List<SavingDto> listSaving(String key) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			FindSavingDto findSavingDto = new FindSavingDto();
			findSavingDto.setCompany(user.getCompany());
			return iSaving.list(findSavingDto);
		}
		return new ArrayList<SavingDto>();
	}

	@Override
	public SavingDto getSaving(long id) {
		return iSaving.get(id);
	}

	@Override
	public long getSavingIdByCode(String key, String code) {
		UserDto user = iSystem.getUserFromSession(key);
		SavingDto saving = iSaving.getByCode(user.getCompany(), code);
		return saving != null ? saving.getId() : 0;
	}

	@Override
	public List<SavingDto> findSaving(String key, FindSavingDto findSavingDto) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			findSavingDto.setCompany(user.getCompany());
			if (user.getLevel() <= 3 || findSavingDto.isTellerTransaction()) {
				findSavingDto.setConfidential(1);
			} else {
				findSavingDto.setConfidential(0);
			}
			return iSaving.find(findSavingDto);
		}
		return new ArrayList<SavingDto>();
	}

	@Override
	public long saveTransaction(String key, SavingTransactionDto transDto) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			//
			transDto.setCompany(user.getCompany());
			transDto.setBranch(user.getBranch());
			//
			if (transDto.getId() == 0 && !transDto.isHasCode()) {
				BranchDto branchDto = iSystem.getBranch(transDto.getBranch());
				String code = createSavingTransCode(transDto.getCompany(),
						transDto.getBranch(), branchDto.getCode());
				transDto.setCode(code);
			}
			return iSaving.saveTransaction(transDto);
		}
		return 0;
	}

	@Override
	public long saveSavingTransaction(SavingTransactionDto transDto) {
		if (transDto.getId() == 0 && !transDto.isHasCode()) {
			BranchDto branchDto = iSystem.getBranch(transDto.getBranch());
			String tellerTransCode = createSavingTransCode(
					transDto.getCompany(), transDto.getBranch(),
					branchDto.getCode());
			transDto.setCode(tellerTransCode);
		}
		return iSaving.saveTransaction(transDto);
	}

	@Override
	public SavingTransactionDto getSavingTransaction(long id) {
		return iSaving.getTransaction(id);
	}

	@Override
	public List<SavingTransactionDto> listTransUntil(long id, Date date) {
		return iSaving.listTransUntil(id, date);
	}

	@Override
	public List<SavingTransactionDto> listTransFrom(long id, Date date) {
		return iSaving.listTransFrom(id, date);
	}

	@Override
	public double getBallanceBefore(long id, Date date) {
		return iSaving.getBallance(id, date, false);
	}

	@Override
	public double getBallanceBeforeNuc(long id) {
		// Cari ballance
		List<Object[]> result = iSaving.getTotalTransBeforeNuc(id);
		double ballance = 0;
		for (Object[] objects : result) {
			int direction = (Integer) objects[0];
			double value = (Double) objects[1];
			if (direction == 1) {
				ballance += value;
			} else {
				ballance -= value;
			}
		}
		return ballance;
	}

	@Override
	public List<SavingTransactionDto> listTransForPrint(long id, Date date,
			int status) {
		List<SavingTransactionDto> transList = null;
		if (status == 0) {
			transList = iSaving.listTransWithoutNuc(id);
		} else {
			// Tandai nuc sebelumnya
			iSaving.setupAllNUCBefore(id, date);
			//
			transList = iSaving.listTransFrom(id, date);
		}
		return transList;
	}

	public void saveNUC(long id, int nuc) {
		iSaving.saveNUC(id, nuc);
	}

	@Override
	public long saveSavingTrfTrans(String key, SavingTransactionDto srcDto,
			SavingTransactionDto destDto) {
		//
		Date now = new Date();
		SavingInformationDto infoSrc = iSaving.getInformation(srcDto
				.getSaving());
		SavingInformationDto infoDest = iSaving.getInformation(destDto
				.getSaving());
		BranchDto branch = iSystem.getBranch(infoSrc.getBranch());
		String prefix = branch.getCode() + "21";
		String code = createSavingTransCode(infoSrc.getCompany(),
				infoSrc.getCompany(), prefix);
		String descriptionSrc = "TRF KE "
				+ infoDest.getName()
				+ " ("
				+ infoDest.getCode()
				+ ")"
				+ (srcDto.getDescription().isEmpty() ? "" : " - "
						+ srcDto.getDescription().toUpperCase());
		String descriptionDest = "TRF DARI "
				+ infoSrc.getName()
				+ " ("
				+ infoSrc.getCode()
				+ ")"
				+ (srcDto.getDescription().isEmpty() ? "" : " - "
						+ srcDto.getDescription().toUpperCase());
		String description = "TRF DARI "
				+ infoSrc.getName()
				+ " ("
				+ infoSrc.getCode()
				+ ") KE "
				+ infoDest.getName()
				+ " ("
				+ infoDest.getCode()
				+ ")"
				+ (srcDto.getDescription().isEmpty() ? "" : " - "
						+ srcDto.getDescription().toUpperCase());
		//
		// Save saving transaction
		//
		srcDto.setDescription(descriptionSrc);
		srcDto.setTimestamp(now);
		srcDto.setCode(code);
		srcDto.setHasCode(true);
		// Teller transaction masuk->1, keluar->2
		srcDto.setType(4);
		srcDto.setCompany(infoSrc.getCompany());
		srcDto.setBranch(infoSrc.getBranch());
		SavingTransMsg savingTransactionMsg = new SavingTransMsg();
		// FIXME: Messaging source
		savingTransactionMsg.setIdSource(1);
		savingTransactionMsg.setQueueName("java:jboss/queue/transTellerIn");
		savingTransactionMsg.setSavingTransactionDto(srcDto);
		savingMsg.sendSavingTrans(savingTransactionMsg);
		//
		// Save saving transaction
		//
		destDto.setDescription(descriptionDest);
		destDto.setTimestamp(now);
		destDto.setCode(code);
		destDto.setHasCode(true);
		// Teller transaction masuk->1, keluar->2
		destDto.setType(3);
		destDto.setCompany(infoDest.getCompany());
		destDto.setBranch(infoDest.getBranch());
		savingTransactionMsg = new SavingTransMsg();
		// FIXME: Messaging source
		savingTransactionMsg.setIdSource(1);
		savingTransactionMsg.setQueueName("java:jboss/queue/transTellerIn");
		savingTransactionMsg.setSavingTransactionDto(destDto);
		savingMsg.sendSavingTrans(savingTransactionMsg);
		//
		srcDto.setDescription(description);
		if (infoSrc.getBranch() == infoDest.getBranch()) {
			savingTrans.createJournalGL(srcDto, infoSrc, infoDest.getCoa1());
		} else {
			savingTrans.createRAKJournalGL(srcDto, infoSrc,
					infoDest.getBranch(), infoDest.getCoa1());
		}
		return 0;
	}

	@Override
	public SavingTransactionDto saveSavingJournalTrans(String key,
			SavingTransactionDto transDto, long coa) {
		Date now = new Date();
		UserDto user = iSystem.getUserFromSession(key);
		SavingInformationDto info = iSaving
				.getInformation(transDto.getSaving());
		BranchDto branch = iSystem.getBranch(info.getBranch());
		String prefix = branch.getCode() + transDto.getDirection();
		String code = createSavingTransCode(info.getCompany(),
				info.getCompany(), prefix);
		//
		// Save saving transaction
		String description = transDto.getDescription().toUpperCase();
		description += " - " + info.getName() + " (" + info.getCode() + ")";
		//
		transDto.setDescription(description);
		transDto.setTimestamp(now);
		transDto.setCode(code);
		transDto.setHasCode(true);
		// Teller transaction masuk->1, keluar->2
		transDto.setType(transDto.getDirection() == 1 ? 3 : 4);
		transDto.setCompany(info.getCompany());
		transDto.setBranch(info.getBranch());
		SavingTransMsg savingTransactionMsg = new SavingTransMsg();
		// FIXME: Messaging source
		savingTransactionMsg.setIdSource(1);
		savingTransactionMsg.setQueueName("java:jboss/queue/transTellerIn");
		savingTransactionMsg.setSavingTransactionDto(transDto);
		savingMsg.sendSavingTrans(savingTransactionMsg);
		//
		if (user.getBranch() == branch.getId()) {
			savingTrans.createJournalGL(transDto, info, coa);
		} else {
			savingTrans.createRAKJournalGL(transDto, info, user.getBranch(),
					coa);
		}
		//
		return transDto;
	}

	@Override
	public SavingTransactionDto saveSavingJournalRevenueTrans(String key,
			SavingTransactionDto transDto, long savBranch, long coa) {
		Date now = new Date();
		long depBranch = transDto.getBranch();
		SavingInformationDto info = iSaving
				.getInformation(transDto.getSaving());
		BranchDto branch = iSystem.getBranch(info.getBranch());
		String prefix = branch.getCode() + transDto.getDirection();
		String code = createSavingTransCode(info.getCompany(),
				info.getBranch(), prefix);
		//
		// Save saving transaction
		String description = transDto.getDescription().toUpperCase();
		description += " - " + info.getName() + " (" + info.getCode() + ")";
		//
		transDto.setDescription(description);
		transDto.setTimestamp(now);
		transDto.setCode(code);
		transDto.setHasCode(true);
		// Teller transaction masuk->1, keluar->2
		transDto.setType(transDto.getDirection() == 1 ? 3 : 4);
		transDto.setCompany(info.getCompany());
		transDto.setBranch(info.getBranch());
		SavingTransMsg savingTransactionMsg = new SavingTransMsg();
		// FIXME: Messaging source
		savingTransactionMsg.setIdSource(1);
		savingTransactionMsg.setQueueName("java:jboss/queue/transTellerIn");
		savingTransactionMsg.setSavingTransactionDto(transDto);
		savingMsg.sendSavingTrans(savingTransactionMsg);
		//
		if (depBranch == savBranch) {
			savingTrans.createJournalGL(transDto, info, coa);
		} else {
			savingTrans.createRAKJournalGL(transDto, info, depBranch, coa);
		}
		//
		return transDto;
	}

	@Override
	public int getLastNuc(long id) {
		return iSaving.getLastNuc(id);
	}

	@Override
	public boolean isWithdrawable(long id, double withdrawal, Date date,
			boolean repayment) {
		double withdrawable = iSaving
				.getWithdrawalBallance(id, date, repayment);
		return withdrawable > withdrawal;
	}

	@Override
	public List<SavingTransactionDto> listSavingStatement(long id, Date begin,
			Date end) {
		List<SavingTransactionDto> result = new ArrayList<SavingTransactionDto>();
		double ballance = iSaving.getBallance(id, begin, false);
		SavingTransactionDto trans = new SavingTransactionDto();
		trans.setDate(begin);
		trans.setDescription("SALDO");
		trans.setValue(ballance);
		trans.setDirection(1);
		result.add(trans);
		//
		result.addAll(iSaving.listTransRange(id, begin, end));
		return result;
	}

	@Override
	public List<Long> listSavingId(String key, Date date) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			FindSavingDto find = new FindSavingDto();
			find.setActive(1);
			find.setCompany(user.getCompany());
			return iSaving.listSavingId(find, date);
		}
		return new ArrayList<Long>();
	}

	@Override
	public List<SavingTransInfoDto> listBallance(String key, Date date) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			return iSaving.listBallance(user.getCompany(), 0L, date);
		}
		return new ArrayList<SavingTransInfoDto>();
	}

	@Override
	public double getBallance(long id, Date date, boolean includeDate) {
		return iSaving.getBallance(id, date, includeDate);
	}

	@Override
	public SavingInformationDto getInformation(long id) {
		return iSaving.getInformation(id);
	}

	@Override
	public double getWithdrawalBallance(long id, Date date, boolean repayment) {
		return iSaving.getWithdrawalBallance(id, date, repayment);
	}

	@Override
	public List<SavingTransactionDto> listTrans(String key, Date beginDate,
			Date endDate) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			return iSaving.listTrans(user.getCompany(), beginDate, endDate);
		}
		return new ArrayList<SavingTransactionDto>();
	}

	@Override
	public List<SavingTransactionDto> listTransRange(long id, Date beginDate,
			Date endDate) {
		return iSaving.listTransRange(id, beginDate, endDate);
	}

	@Override
	public long isExistByRefId(long company, long branch, long refId) {
		return iSaving.isExistByRefId(company, branch, refId);
	}

	@Override
	public void startCloseSaving(long id, String reason) {
		iSaving.startCloseSaving(id, reason);
	}

	@Override
	public void closeSaving(long id, Date closing) {
		iSaving.closeSaving(id, closing);
	}

	@Override
	public long saveSaving(SavingDto savingDto) {
		return iSaving.save(savingDto);
	}

	@Override
	public long isSavingTransExist(long company, long branch, long refId) {
		return iSaving.isSavingTransExist(company, branch, refId);
	}

	@Override
	public List<SavingBlockirDto> listBlockir(long id) {
		return iSaving.listSavingBlock(id);
	}

	@Override
	public void saveBlockir(SavingBlockirDto dto) {
		iSaving.saveSavingBlock(dto);
	}

	@Override
	public void removeBlockir(long id) {
		iSaving.removeSavingBlock(id);
	}

	@Override
	public List<SavingPrintCodeRefDto> listPrintCode(String session) {
		UserDto user = iSystem.getUserFromSession(session);
		if (user != null) {
			return iSaving.listPrintCode(user.getCompany());
		}
		return new ArrayList<SavingPrintCodeRefDto>();
	}
}
