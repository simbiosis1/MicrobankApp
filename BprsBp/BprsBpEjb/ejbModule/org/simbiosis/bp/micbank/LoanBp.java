package org.simbiosis.bp.micbank;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.simbiosis.bp.dto.ValidationDto;
import org.simbiosis.bp.savingMsg.SavingTransMessaging;
import org.simbiosis.bp.savingMsg.SavingTransMsg;
import org.simbiosis.microbank.CustomerDto;
import org.simbiosis.microbank.DepositDto;
import org.simbiosis.microbank.FindDepositDto;
import org.simbiosis.microbank.FindLoanDto;
import org.simbiosis.microbank.FindSavingDto;
import org.simbiosis.microbank.GuaranteeDto;
import org.simbiosis.microbank.ICustomer;
import org.simbiosis.microbank.ILoan;
import org.simbiosis.microbank.ISaving;
import org.simbiosis.microbank.LoanDto;
import org.simbiosis.microbank.LoanInformationDto;
import org.simbiosis.microbank.LoanProductDto;
import org.simbiosis.microbank.LoanQualityDto;
import org.simbiosis.microbank.LoanScheduleDto;
import org.simbiosis.microbank.LoanTransInfoDto;
import org.simbiosis.microbank.LoanTransactionDto;
import org.simbiosis.microbank.SavingDto;
import org.simbiosis.microbank.SavingTransactionDto;
import org.simbiosis.system.BranchDto;
import org.simbiosis.system.ISystem;
import org.simbiosis.system.SubBranchDto;
import org.simbiosis.system.UserDto;

@Stateless
@Remote(ILoanBp.class)
public class LoanBp implements ILoanBp {

	@EJB(lookup = "java:module/LoanDropping")
	LoanDropping dropping;
	@EJB(lookup = "java:module/LoanAngsur")
	LoanAngsur angsur;
	@EJB(lookup = "java:global/SystemEar/SystemEjb/SystemImpl")
	ISystem system;
	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/LoanImpl")
	ILoan loan;
	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/SavingImpl")
	ISaving saving;
	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/CustomerImpl")
	ICustomer customer;
	@EJB(lookup = "java:module/SavingBp")
	ISavingBp savingBp;
	@EJB(lookup = "java:module/DepositBp")
	IDepositBp depositBp;
	@EJB(lookup = "java:module/SavingTransMessaging")
	SavingTransMessaging savingMsg;

	String fillers[] = { "", "0", "00", "000", "0000", "00000", "000000" };
	int lengthsLoan[] = { 5, 4 };
	int lengthsTrans[] = { 6, 5 };

	String createLoanCode(long company, String prefixCode) {
		// Cari dulu yang sudah ada
		Long lCode = loan.getCodeCounter(company, prefixCode);
		String numberCode = lCode.toString();
		String code = fillers[lengthsLoan[0] - numberCode.length()]
				+ numberCode;
		return prefixCode + code;
	}

	String createTransCode(long company, long branch, String prefixCode) {
		// Buat kode baru
		String myPrefixCode = prefixCode;
		// Cari dulu yang sudah ada
		String code = loan.getMaxLoanTransCode(company, branch, myPrefixCode);
		if (code != null) {
			int subStrLen = myPrefixCode.length();
			int number = Integer.parseInt(code.substring(subStrLen)) + 1;
			String numberCode = "" + number;
			int length = (numberCode.length() > lengthsTrans[0]) ? 0
					: (lengthsTrans[0] - numberCode.length());
			code = fillers[length] + numberCode;
		} else {
			code = fillers[lengthsTrans[1]] + "1";
		}
		return myPrefixCode + code;
	}

	@Override
	public long saveLoanProduct(String key, LoanProductDto loanProductDto) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			loanProductDto.setCompany(user.getCompany());
			return loan.saveLoanProduct(loanProductDto);
		}
		return 0;
	}

	@Override
	public long saveLoan(String key, LoanDto loanDto) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			if ((loanDto.getId() == 0)) {
				loanDto.setCompany(user.getCompany());
				loanDto.setBranch(user.getBranch());
				if (!loanDto.isHasCode()) {
					BranchDto branchDto = system.getBranch(user.getBranch());
					SubBranchDto subBranchDto = system.getSubBranch(user
							.getSubBranch());
					LoanProductDto productDto = loan.getLoanProduct(loanDto
							.getProduct());
					String code = createLoanCode(loanDto.getCompany(),
							branchDto.getCode() + subBranchDto.getCode()
									+ productDto.getCode());
					loanDto.setCode(code);
				}
				loanDto.setActive(1);
				// =====================================================
				// Buat jaminan kosong kalau pembiayaan baru
				// =====================================================
				//
				GuaranteeDto guaDto = new GuaranteeDto();
				guaDto.setCode(loanDto.getCode());
				guaDto.setActive(1);
				guaDto.setType(0);
				guaDto.setRegistration(loanDto.getRegistration());
				guaDto.setCustomer(loanDto.getCustomer());
				guaDto.setCompany(loanDto.getCompany());
				guaDto.setBranch(loanDto.getBranch());
				loanDto.getGuarantees().add(guaDto);
				//
			} else {
				LoanDto dto = loan.getLoan(loanDto.getId());
				dto.setCode(loanDto.getCode());
				dto.setProduct(loanDto.getProduct());
				dto.setPrincipal(loanDto.getPrincipal());
				dto.setRate(loanDto.getRate());
				dto.setTenor(loanDto.getTenor());
				dto.setContract(loanDto.getContract());
				dto.setContractDate(loanDto.getContractDate());
				dto.setSchedules(loanDto.getSchedules());
				dto.setGuarantees(loanDto.getGuarantees());
				dto.setAo(loanDto.getAo());
				dto.setAoHistory(loanDto.getAoHistory());
				dto.setAdmin(loanDto.getAdmin());
				dto.setFine(loanDto.getFine());
				dto.setPurpose(loanDto.getPurpose());
				dto.setBiSektor(loanDto.getBiSektor());
				loanDto = dto;
				//
				// =====================================================
				// Sambungkan jaminan-jaminan yang lain
				// =====================================================
				//
				Map<Long, GuaranteeDto> guaMap = new HashMap<Long, GuaranteeDto>();
				for (GuaranteeDto myGua : dto.getGuarantees()) {
					guaMap.put(myGua.getId(), myGua);
				}
				//
				for (GuaranteeDto myGua : loanDto.getGuarantees()) {
					GuaranteeDto thisGua = guaMap.get(myGua.getId());
					if (thisGua == null) {
						loanDto.getGuarantees().add(thisGua);
					}
				}
				//

			}
			// Hitung margin
			double margin = 0;
			for (LoanScheduleDto sched : loanDto.getSchedules()) {
				margin += sched.getMargin();
			}
			loanDto.setMargin(margin);
			return loan.saveLoan(loanDto);
		}
		return 0;
	}

	@Override
	public long saveLoanReschedule(String key, LoanDto loanDto) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			// Cari data yang lama
			LoanDto dto = loan.getLoan(loanDto.getId());
			// Tutup yang lama
			loan.closeLoan(loanDto.getId(), loanDto.getRegistration());
			//
			loanDto.setId(0);
			dto.setId(0);
			dto.setRegistration(loanDto.getRegistration());
			dto.setCode(loanDto.getCode());
			dto.setProduct(loanDto.getProduct());
			dto.setPrincipal(loanDto.getPrincipal());
			dto.setRate(loanDto.getRate());
			dto.setTenor(loanDto.getTenor());
			dto.setContract(loanDto.getContract());
			dto.setContractDate(loanDto.getContractDate());
			dto.setSchedules(loanDto.getSchedules());
			dto.setGuarantees(loanDto.getGuarantees());
			dto.setAo(loanDto.getAo());
			dto.setAoHistory(loanDto.getAoHistory());
			dto.setAdmin(loanDto.getAdmin());
			dto.setFine(loanDto.getFine());
			dto.setPurpose(loanDto.getPurpose());
			dto.setBiSektor(loanDto.getBiSektor());
			loanDto = dto;
			// Hitung margin
			double margin = 0;
			for (LoanScheduleDto sched : loanDto.getSchedules()) {
				sched.setId(0);
				margin += sched.getMargin();
			}
			loanDto.setMargin(margin);
			return loan.saveLoanReschedule(loanDto);
		}
		return 0;
	}

	@Override
	public LoanDto getLoan(long id) {
		return loan.getLoan(id);
	}

	@Override
	public List<LoanDto> listLoan(String key, FindLoanDto findLoan) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			findLoan.setCompany(user.getCompany());
			findLoan.setBranch(user.getBranch());
			return loan.listLoan(findLoan);
		}
		return new ArrayList<LoanDto>();
	}

	@Override
	public List<LoanDto> findLoan(String key, FindLoanDto findLoan) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			findLoan.setCompany(user.getCompany());
			findLoan.setBranch(user.getLevel() <= 4 ? 0 : user.getBranch());
			return loan.findLoan(findLoan);
		}
		return new ArrayList<LoanDto>();
	}

	Double roundedToNearestHunderts(Double input) {
		int rounded = 1000;
		int adder = rounded - 1;
		//
		Integer inputRounded = ((input.intValue() + adder) / rounded) * rounded;
		return inputRounded.doubleValue();
	}

	private List<LoanScheduleDto> createRoundedSchedule(
			List<LoanScheduleDto> input) {
		double totPrincipal = 0;
		double totPrincipalSched = 0;
		double totMargin = 0;
		double totMarginSched = 0;
		int n = input.size();
		for (LoanScheduleDto schedule : input) {
			if (n == 1) {
				totPrincipal += schedule.getPrincipal();
				totMargin += schedule.getMargin();
				break;
			}
			//
			Double principal = roundedToNearestHunderts(schedule.getPrincipal());
			totPrincipal += schedule.getPrincipal();
			totPrincipalSched += principal;
			schedule.setPrincipal(principal);
			//
			Double margin = roundedToNearestHunderts(schedule.getMargin());
			totMargin += schedule.getMargin();
			totMarginSched += margin;
			schedule.setMargin(margin);
			//
			schedule.setTotal(schedule.getPrincipal() + schedule.getMargin());
			//
			n--;
		}
		n = input.size() - 1;
		LoanScheduleDto schedule = input.get(n);
		schedule.setPrincipal(totPrincipal - totPrincipalSched);
		Double mar = totMargin - totMarginSched < 0 ? 0 : totMargin
				- totMarginSched;
		schedule.setMargin(roundedToNearestHunderts(mar));

		schedule.setTotal(schedule.getPrincipal() + schedule.getMargin());
		//
		return input;
	}

	@Override
	public List<LoanScheduleDto> createFlatSchedule(double principal,
			double tenor, double rate, Date beginDate) {
		return createRoundedSchedule(loan.createFlatSchedule(principal, tenor,
				rate, beginDate));
	}

	@Override
	public List<LoanScheduleDto> createEffectiveSchedule(double principal,
			double tenor, double rate, Date beginDate) {
		return createRoundedSchedule(loan.createEffectiveSchedule(principal,
				tenor, rate, beginDate));
	}

	@Override
	public List<LoanScheduleDto> createAnuitasSchedule(double principal,
			double tenor, double rate, Date beginDate) {
		return createRoundedSchedule(loan.createAnuitasSchedule(principal,
				tenor, rate, beginDate));
	}

	@Override
	public LoanProductDto getLoanProduct(long id) {
		return loan.getLoanProduct(id);
	}

	@Override
	public List<LoanProductDto> listLoanProduct(String key) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			return loan.listLoanProduct(user.getCompany());
		}
		return new ArrayList<LoanProductDto>();
	}

	@Override
	public void saveLoanJournal(LoanTransactionDto loanTrans,
			LoanInformationDto loanInfo) {
		long savingId = loanInfo.getSaving();
		switch (loanTrans.getType()) {
		case 1: // Pencairan
			switch (loanInfo.getSchema()) {
			case 1: // Murabahah
				dropping.createDroppingMbhGL(loanTrans, loanInfo, savingId);
				break;
			case 2: // Salam
			case 3: // Istishna
			case 8: // Multijasa
				dropping.createDroppingSlmIstMjGL(loanTrans, loanInfo, savingId);
				break;
			case 4:
			case 5:
			case 6:
			case 7:
			case 11:
				dropping.createDroppingQordhGL(loanTrans, loanInfo, savingId);
				break;
			}
			loan.droppLoan(loanTrans.getLoan(), 1);
			break;
		case 2: // Angsuran
		case 3: // Pelunasan
			switch (loanInfo.getSchema()) {
			case 1:
			case 2:
			case 3:
			case 8:
				angsur.createAngsurMbhGL(loanTrans, loanInfo, savingId);
				break;
			case 4:
				angsur.createAngsurIjarahGL(loanTrans, loanInfo, savingId);
				break;
			case 5:
			case 6:
			case 7:
			case 11:
				angsur.createAngsurQordhGL(loanTrans, loanInfo, savingId);
				break;
			}
			break;
		}
	}

	@Override
	public LoanTransactionDto saveCompleteLoanTrans(String key,
			LoanTransactionDto loanTrans) {
		LoanInformationDto loanInfo = loan.getLoanInformation(loanTrans
				.getLoan());
		loanTrans = createSavingLoanTrans(loanTrans, loanInfo,
				loanInfo.getSaving());
		//
		saveLoanJournal(loanTrans, loanInfo);
		//
		return loanTrans;
	}

	LoanTransactionDto createSavingLoanTrans(LoanTransactionDto transDto,
			LoanInformationDto info, long savingId) {
		Date now = new Date();
		String transDesc = (transDto.getDirection() == 1) ? "PENCAIRAN PEMBIAYAAN - "
				: "ANGSURAN - ";
		transDesc += (info.getName() + " (" + info.getCode() + ")");
		// Generate code
		BranchDto branchDto = system.getBranch(info.getBranch());
		String transCode = createTransCode(info.getCompany(), info.getBranch(),
				branchDto.getCode() + transDto.getType());
		// Save Loan Transaction
		transDto.setCode(transCode);
		transDto.setDescription(transDesc);
		transDto.setCompany(info.getCompany());
		transDto.setBranch(info.getBranch());
		long id = loan.saveLoanTransaction(transDto);
		// Save saving transaction
		SavingTransactionDto savingTrans = new SavingTransactionDto();
		savingTrans.setDate(transDto.getDate());
		savingTrans.setTimestamp(now);
		savingTrans.setCode(transCode);
		savingTrans.setHasCode(true);
		savingTrans.setDescription(transDesc);
		savingTrans.setDirection(transDto.getDirection());
		if (transDto.getDirection() == 1) {
			savingTrans.setValue(transDto.getPrincipal());
		} else {
			savingTrans
					.setValue(transDto.getPrincipal() + transDto.getMargin());
		}
		// Teller transaction masuk->1, keluar->2
		savingTrans.setType(transDto.getDirection() == 1 ? 3 : 4);
		savingTrans.setSaving(savingId);
		savingTrans.setCompany(info.getCompany());
		savingTrans.setBranch(info.getBranch());
		SavingTransMsg savingTransactionMsg = new SavingTransMsg();
		savingTransactionMsg.setIdSource(id);
		savingTransactionMsg.setQueueName("java:jboss/queue/transTellerIn");
		savingTransactionMsg.setSavingTransactionDto(savingTrans);
		savingMsg.sendSavingTrans(savingTransactionMsg);
		//
		return transDto;
	}

	@Override
	public List<LoanTransactionDto> listLoanTransaction(long id) {
		return loan.listLoanTransaction(id);
	}

	@Override
	public long saveGuarantee(String key, GuaranteeDto dto) {
		UserDto user = system.getUserFromSession(key);
		if (dto.getId() == 0) {
			if (user != null) {
				dto.setCompany(user.getCompany());
				dto.setBranch(user.getBranch());
				return loan.saveGuarantee(dto);
			}
		} else {
			return loan.saveGuarantee(dto);
		}
		return 0;
	}

	@Override
	public GuaranteeDto getGuarantee(long id) {
		return loan.getGuarantee(id);
	}

	@Override
	public List<GuaranteeDto> findGuarantee(String key, FindLoanDto findLoan) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			findLoan.setCompany(user.getCompany());
			findLoan.setBranch(user.getBranch());
			return loan.findGuarantee(findLoan);
		}
		return new ArrayList<GuaranteeDto>();
	}

	@Override
	public ValidationDto validateGuarantee(String key, GuaranteeDto dto) {
		ValidationDto validDto = new ValidationDto();
		if ((dto.getType() == 3) || (dto.getType() == 4)) {
			switch (dto.getType()) {
			case 3:
				FindSavingDto fs = new FindSavingDto();
				fs.setWithCode(true);
				fs.setCode(dto.getNumber());
				List<SavingDto> savingResult = savingBp.findSaving(key, fs);
				if (savingResult.size() != 1) {
					validDto.addErrorMessage("Rekening tabungan "
							+ dto.getNumber() + " tidak ditemukan");
				}
				break;
			case 4:
				FindDepositDto fd = new FindDepositDto();
				fd.setWithCode(true);
				fd.setCode(dto.getNumber());
				List<DepositDto> depositResult = depositBp.findDeposit(key, fd);
				if (depositResult.size() != 1) {
					validDto.addErrorMessage("Rekening deposito "
							+ dto.getNumber() + " tidak ditemukan");
				}
				break;
			}
		}
		return validDto;
	}

	@Override
	public LoanInformationDto getInformation(long id) {
		return loan.getLoanInformation(id);
	}

	@Override
	public List<Long> listLoanId(String key, Date date) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			FindLoanDto find = new FindLoanDto();
			find.setActive(1);
			find.setCompany(user.getCompany());
			return loan.listLoanId(find, date);
		}
		return new ArrayList<Long>();
	}

	@Override
	public List<LoanScheduleDto> listLoanSchedule(long id) {
		return loan.listLoanSchedule(id);
	}

	@Override
	public List<LoanTransactionDto> listTransactionByRange(String key,
			long branch, int direction, Date beginDate, Date endDate) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			return loan.listAllLoanTransactionByRange(user.getCompany(),
					branch, direction, beginDate, endDate);
		}
		return new ArrayList<LoanTransactionDto>();
	}

	@Override
	public List<LoanScheduleDto> listLoanBill(String key, Date endDate) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			return loan.listLoanBill(user.getCompany(), endDate);
		}
		return new ArrayList<LoanScheduleDto>();
	}

	@Override
	public LoanTransactionDto getSumLoanTransaction(long id, int direction,
			Date date) {
		return loan.getSumLoanTransaction(id, direction, date);
	}

	@Override
	public List<LoanScheduleDto> listLoanScheduleByRange(String key,
			Date beginDate, Date endDate) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			return loan.listAllLoanScheduleByRange(user.getCompany(), beginDate,
					endDate);
		}
		return new ArrayList<LoanScheduleDto>();
	}

	@Override
	public List<LoanInformationDto> listLoanInformation(String key, Date date) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			return loan.listLoanInfo(user.getCompany(), 0, date);
		}
		return new ArrayList<LoanInformationDto>();
	}

	@Override
	public LoanQualityDto getLoanQuality(LoanTransInfoDto trans, Date date) {
		return loan.getLoanQuality(trans.getId(), trans.getSchema(), date,
				trans.getPaidPrincipal(), trans.getPaidMargin());
	}

	@Override
	public List<LoanTransInfoDto> listSimpleLoanTransInfo(String key, Date date) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			return loan.listSimpleLoanTransInfo(user.getCompany(), 0, date);
		}
		return new ArrayList<LoanTransInfoDto>();
	}

	@Override
	public List<LoanInformationDto> listSimpleLoanInfo(String key, Date date) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			return loan.listSimpleLoanInfo(user.getCompany(), 0, date);
		}
		return new ArrayList<LoanInformationDto>();
	}

	@Override
	public List<CustomerDto> listLoanCifNotRegistered(String key, Date date) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			return loan.listLoanCifNotRegistered(user.getCompany(),
					user.getBranch(), date);
		}
		return new ArrayList<CustomerDto>();
	}

	@Override
	public void resetLoanSchedulePayd(String key) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			loan.resetLoanSchedulePayd(user.getCompany());
		}
	}

	@Override
	public void payLoanScheduleBulk(long idLoan, long maxSched) {
		loan.payLoanScheduleBulk(idLoan, maxSched);
	}

	@Override
	public List<String> listGuaranteeType(String key) {
		String[] guaranteeTypes = { "TANPA JAMINAN", "PERSONAL GUARANTEE",
				"DOKUMEN", "TABUNGAN", "DEPOSITO", "EMAS DAN LOGAM MULIA",
				"KENDARAAN BERMOTOR", "TANAH DAN BANGUNAN" };
		List<String> result = new ArrayList<String>();
		for (String type : guaranteeTypes) {
			result.add(type);
		}
		return result;
	}

	@Override
	public List<LoanTransInfoDto> listLoanTransSumGroup(long company,
			long branch, int direction, Date beginDate, Date endDate) {
		return loan.listLoanTransSumGroup(company, branch, direction,
				beginDate, endDate);
	}

	@Override
	public List<Long> listAoLoanId(String key, long branch) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			FindLoanDto find = new FindLoanDto();
			find.setActive(1);
			find.setCompany(user.getCompany());
			find.setBranch(branch);
			return loan.listAoLoanId(find);
		}
		return new ArrayList<Long>();
	}

	@Override
	public LoanScheduleDto getNormalRepayment(long id) {
		return loan.getNormalRepayment(id);
	}

	@Override
	public LoanScheduleDto getEarlyRepayment(long id) {
		return loan.getEarlyRepayment(id);
	}

	@Override
	public long isLoanExistByRefId(String key, long refId) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			return loan.isLoanExistByRefId(user.getCompany(), user.getBranch(),
					refId);
		}
		return 0;
	}

	@Override
	public LoanTransactionDto getLoanTransByDateCode(long loanId, Date date,
			String code) {
		return loan.getLoanTransByDateCode(loanId, date, code);
	}

	@Override
	public long getLoanTransIdByRef(String key, long refId) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			return loan.getLoanTransIdByRef(user.getCompany(),
					user.getBranch(), refId);
		}
		return 0;
	}

	@Override
	public long saveLoanTrans(String key, LoanTransactionDto loanTrans) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			loanTrans.setCompany(user.getCompany());
			loanTrans.setBranch(user.getBranch());
			return loan.saveLoanTransaction(loanTrans);
		}
		return 0;
	}

	@Override
	public long getLoanIdByCode(long company, String code, int active) {
		return loan.getLoanIdByCode(company, code, active);
	}

	@Override
	public List<LoanScheduleDto> listRepaymentByRange(long id, Date beginDate,
			Date endDate) {
		return loan.listRepaymentByRange(id, beginDate, endDate);
	}

	@Override
	public void closeLoan(long loanId) {
		LoanScheduleDto sched = loan.getLastPayment(loanId);
		loan.closeLoan(loanId, sched.getDate());
	}

	@Override
	public List<LoanInformationDto> listDroppedLoan(String key, long branch,
			Date beginDate, Date endDate) {
		UserDto myUser = system.getUserFromSession(key);
		List<LoanInformationDto> result = new ArrayList<LoanInformationDto>(); 
		if (myUser != null) {
			List<UserDto> users = system.listUsers(myUser.getCompany(), 0);
			Map<Long, UserDto> userMap = new HashMap<Long, UserDto>();
			for (UserDto user : users){
				userMap.put(user.getId(), user);
			}
			result = loan.listDroppedLoan(myUser.getCompany(), branch, beginDate,
					endDate);
			for (LoanInformationDto loan : result){
				loan.setAoName(userMap.get(loan.getAo()).getRealName());
			}
		}
		return result; 
	}

	@Override
	public List<LoanDto> listLoanBySchema(String key, int scheme) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			return loan.listLoanBySchema(user.getCompany(), scheme);
		}
		return new ArrayList<LoanDto>();
	}

	@Override
	public List<GuaranteeDto> listGuaranteeByCode(String key, String code) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			return loan.listGuaranteeByCode(user.getCompany(), code);
		}
		return new ArrayList<GuaranteeDto>();
	}
}
