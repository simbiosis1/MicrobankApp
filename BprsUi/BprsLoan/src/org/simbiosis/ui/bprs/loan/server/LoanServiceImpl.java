package org.simbiosis.ui.bprs.loan.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

import org.joda.time.DateTime;
import org.simbiosis.bp.dto.ValidationDto;
import org.simbiosis.bp.micbank.ICustomerBp;
import org.simbiosis.bp.micbank.IDepositBp;
import org.simbiosis.bp.micbank.ILoanBp;
import org.simbiosis.bp.micbank.ISavingBp;
import org.simbiosis.bp.system.ISystemBp;
import org.simbiosis.microbank.CustomerDto;
import org.simbiosis.microbank.FindLoanDto;
import org.simbiosis.microbank.GuaranteeDto;
import org.simbiosis.microbank.ILoanReport;
import org.simbiosis.microbank.LoanDto;
import org.simbiosis.microbank.LoanProductDto;
import org.simbiosis.microbank.LoanScheduleDto;
import org.simbiosis.microbank.LoanTransactionDto;
import org.simbiosis.microbank.SavingDto;
import org.simbiosis.microbank.SavingProductDto;
import org.simbiosis.microbank.model.LoanRpt;
import org.simbiosis.system.UserDto;
import org.simbiosis.ui.bprs.common.shared.CustomerDv;
import org.simbiosis.ui.bprs.common.shared.CustomerDv.IdTypeEnum;
import org.simbiosis.ui.bprs.common.shared.CustomerDv.SexTypeEnum;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.GuaranteeDv;
import org.simbiosis.ui.bprs.common.shared.LoanDv;
import org.simbiosis.ui.bprs.common.shared.LoanScheduleDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.common.shared.TransactionDv;
import org.simbiosis.ui.bprs.common.shared.ValidationDv;
import org.simbiosis.ui.bprs.loan.client.rpc.LoanService;
import org.simbiosis.ui.bprs.loan.shared.InfoLoanDv;
import org.simbiosis.ui.bprs.loan.shared.TypeDv;
import org.simbiosis.ui.bprs.loan.shared.UserDv;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class LoanServiceImpl extends RemoteServiceServlet implements
		LoanService {

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/CustomerBp")
	ICustomerBp customerBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/SavingBp")
	ISavingBp savingBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/DepositBp")
	IDepositBp depositBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/LoanBp")
	ILoanBp loanBp;
	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/LoanReport")
	ILoanReport loanReport;
	@EJB(lookup = "java:global/SystemBpEar/SystemBpEjb/SystemBp")
	ISystemBp systemBp;

	// DecimalFormat nf = new DecimalFormat("#,##0.00");
	// DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");

	public LoanServiceImpl() {
	}

	private CustomerDv createCustomerDvFromDto(CustomerDto customerDto) {
		CustomerDv customerDv = new CustomerDv();
		customerDv.setId(customerDto.getId());
		customerDv.setCode(customerDto.getCode());
		customerDv.setRegistration(customerDto.getRegistration());
		customerDv.setName(customerDto.getName());
		customerDv.setSex(customerDto.getSex());
		customerDv.setStrSex(SexTypeEnum.valueToString(customerDv.getSex()));
		customerDv.setPob(customerDto.getPob());
		customerDv.setDob(customerDto.getDob());
		customerDv.setIdType(customerDto.getIdType());
		customerDv
				.setStrIdType(IdTypeEnum.valueToString(customerDv.getIdType()));
		customerDv.setIdCode(customerDto.getIdCode());
		customerDv.setMotherName(customerDto.getMotherName());
		customerDv.setAddress(customerDto.getAddress());
		customerDv.setCity(customerDto.getCity());
		customerDv.setPostCode(customerDto.getPostCode());
		customerDv.setProvince(customerDto.getProvince());
		customerDv.setPhone(customerDto.getPhone());
		customerDv.setHandphone(customerDto.getHandphone());
		return customerDv;
	}

	public LoanScheduleDv createScheduleDv(LoanScheduleDto scheduleDto) {
		LoanScheduleDv scheduleDv = new LoanScheduleDv();
		scheduleDv.setId(scheduleDto.getId());
		scheduleDv.setDate(scheduleDto.getDate());
		scheduleDv.setPrincipal(scheduleDto.getPrincipal());
		scheduleDv.setMargin(scheduleDto.getMargin());
		scheduleDv.setTotal(scheduleDto.getTotal());
		return scheduleDv;
	}

	private LoanScheduleDto createScheduleFromDv(LoanScheduleDv scheduleDv) {
		LoanScheduleDto scheduleDto = new LoanScheduleDto();
		scheduleDto.setDate(scheduleDv.getDate());
		scheduleDto.setPrincipal(scheduleDv.getPrincipal());
		scheduleDto.setMargin(scheduleDv.getMargin());
		scheduleDto.setTotal(scheduleDv.getTotal());
		return scheduleDto;
	}

	public GuaranteeDto createGuaranteFromDv(GuaranteeDv dv) {
		GuaranteeDto dto = new GuaranteeDto();
		dto.setId(dv.getId());
		dto.setCompany(dv.getCompany());
		dto.setBranch(dv.getBranch());
		dto.setCustomer(dv.getCustomer().getId());
		dto.setRegistration(dv.getRegistration());
		dto.setCode(dv.getCode());
		dto.setType(dv.getType());
		dto.setAppraisalIntValue(dv.getAppraisalIntValue());
		dto.setAppraisalMarkValue(dv.getAppraisalMarkValue());
		dto.setAppraisalOJKValue(dv.getAppraisalOJKValue());
		dto.setNumber(dv.getNumber());
		dto.setDescription(dv.getDescription());
		dto.setActive(dv.getActive());
		return dto;
	}

	@Override
	public List<UserDv> listUsers(String key) throws IllegalArgumentException {
		List<UserDv> result = new ArrayList<UserDv>();
		List<UserDto> users = systemBp.listUsers(key);
		for (UserDto user : users) {
			UserDv dv = new UserDv();
			dv.setId(user.getId());
			dv.setRealName(user.getRealName());
			result.add(dv);
		}
		return result;
	}

	@Override
	public List<DataDv> findLoan(String key, Boolean hasCode, Boolean hasName,
			Boolean hasDob, String code, String name, Date dob) {
		List<DataDv> dataList = new ArrayList<DataDv>();
		int i = 1;
		FindLoanDto findLoanDto = new FindLoanDto();
		if (hasCode) {
			findLoanDto.setWithCode(true);
			findLoanDto.setCode(code);
		}
		if (hasName) {
			findLoanDto.setWithName(true);
			findLoanDto.setName(name);
		}
		for (LoanDto loanDto : loanBp.findLoan(key, findLoanDto)) {
			CustomerDto customerDto = customerBp.getCustomer(loanDto
					.getCustomer());
			DataDv data = new DataDv();
			data.setId(loanDto.getId());
			data.setNr(i++);
			data.setKode(loanDto.getCode());
			LoanProductDto financingProduct = loanBp.getLoanProduct(loanDto
					.getProduct());
			data.setProduk(loanDto.getProduct());
			data.setStrProduk(financingProduct.getName());
			data.setNama(customerDto.getName());
			data.setAlamat(customerDto.getAddress() + ", "
					+ customerDto.getCity() + " " + customerDto.getPostCode());
			dataList.add(data);
		}
		return dataList;
	}

	@Override
	public LoanDv getLoan(Long id) {
		List<String> guaranteeTypes = loanBp.listGuaranteeType("");
		LoanDv dv = new LoanDv();
		LoanDto dto = loanBp.getLoan(id);
		dv.setId(dto.getId());
		dv.setCode(dto.getCode());
		dv.setRegistration(dto.getRegistration());
		dv.setContract(dto.getContract());
		dv.setContractDate(dto.getContractDate());
		LoanProductDto loanProduct = loanBp.getLoanProduct(dto.getProduct());
		dv.setProduct(loanProduct.getId());
		dv.setStrProduct(loanProduct.getName());
		dv.setPrincipal(dto.getPrincipal());
		dv.setRate(dto.getRate());
		dv.setTenor(dto.getTenor());
		dv.setPurpose(dto.getPurpose());
		dv.setBiSektor(dto.getBiSektor());
		//
		SavingDto savingDto = savingBp.getSaving(dto.getSaving());
		SavingDv savingDv = new SavingDv();
		savingDv.setId(savingDto.getId());
		savingDv.setCode(savingDto.getCode());
		SavingProductDto savProdDto = savingBp.getSavingProduct(savingDto
				.getProduct());
		savingDv.setStrProduct(savProdDto.getName());
		dv.setSaving(savingDv);
		//
		dv.setScheduleType(1);
		int i = 1;
		for (LoanScheduleDto scheduleDto : dto.getSchedules()) {
			LoanScheduleDv scheduleDv = createScheduleDv(scheduleDto);
			scheduleDv.setNr(i++);
			scheduleDv.setLoan(dv.getId());
			dv.getSchedules().add(scheduleDv);
		}
		//
		i = 1;
		for (GuaranteeDto gDto : dto.getGuarantees()) {
			GuaranteeDv gDv = new GuaranteeDv();
			gDv.setNr(i++);
			gDv.setId(gDto.getId());
			gDv.setCode(gDto.getCode());
			gDv.setStrType(guaranteeTypes.get(gDto.getType()));
			gDv.setNumber(gDto.getNumber());
			dv.getGuarantees().add(gDv);
		}
		//
		CustomerDto customerDto = customerBp.getCustomer(dto.getCustomer());
		dv.setCustomer(createCustomerDvFromDto(customerDto));
		dv.setAo(dto.getAo());
		UserDto user = systemBp.getUser(dv.getAo());
		dv.setStrAo(user.getRealName());
		dv.setAoHistory(dto.getAoHistory());
		dv.setFine(dto.getFine());
		dv.setAdmin(dto.getAdmin());
		return dv;
	}

	@Override
	public List<DataDv> loadCommonListLoan(String key) {
		List<DataDv> result = new ArrayList<DataDv>();
		List<LoanProductDto> financingProductList = loanBp.listLoanProduct(key);
		for (LoanProductDto financingProduct : financingProductList) {
			DataDv dataDv = new DataDv();
			dataDv.setId(financingProduct.getId());
			dataDv.setNama(financingProduct.getName());
			result.add(dataDv);
		}
		return result;
	}

	private LoanDto createLoanToDto(LoanDv dv) {
		long customerId = dv.getCustomer().getId();
		LoanDto dto = new LoanDto();
		dto.setId(dv.getId());
		dto.setProduct(dv.getProduct());
		dto.setCustomer(customerId);
		dto.setContract(dv.getContract() == null ? "" : dv.getContract()
				.toUpperCase());
		dto.setContractDate(dv.getContractDate());
		dto.setPrincipal(dv.getPrincipal());
		dto.setRate(dv.getRate());
		dto.setTenor(dv.getTenor());
		dto.setSaving(dv.getSaving().getId());
		dto.setRegistration(dv.getRegistration());
		dto.setAo(dv.getAo());
		dto.setAoHistory(dv.getAoHistory());
		dto.setPurpose(dv.getPurpose() == null ? "" : dv.getPurpose()
				.toUpperCase());
		dto.setBiSektor(dv.getBiSektor());
		dto.setAdmin(dv.getAdmin());
		dto.setFine(dv.getFine());
		for (LoanScheduleDv scheduleDv : dv.getSchedules()) {
			dto.getSchedules().add(createScheduleFromDv(scheduleDv));
		}
		for (GuaranteeDv gua : dv.getGuarantees()) {
			dto.getGuarantees().add(createGuaranteFromDv(gua));
		}
		if (dv.getId() != 0) {
			dto.setCode(dv.getCode());
		}
		return dto;
	}

	@Override
	public LoanDv saveLoan(String key, LoanDv dv) {
		Long financingId = loanBp.saveLoan(key, createLoanToDto(dv));
		return getLoan(financingId);
	}

	@Override
	public LoanDv saveLoanReschedule(String key, LoanDv dv) {
		Long financingId = loanBp.saveLoanReschedule(key, createLoanToDto(dv));
		return getLoan(financingId);
	}

	@Override
	public List<LoanScheduleDv> createLoanSchedule(Double strPrincipal,
			Integer strTenor, Double strRate, Date beginDate, int type) {
		List<LoanScheduleDv> result = new ArrayList<LoanScheduleDv>();
		DateTime begin = new DateTime(beginDate).plusMonths(1);
		beginDate = begin.toDate();
		List<LoanScheduleDto> loanScheduleDtos = new ArrayList<LoanScheduleDto>();
		switch (type) {
		case 1:
			loanScheduleDtos = loanBp.createFlatSchedule(strPrincipal,
					strTenor, strRate, beginDate);
			break;
		case 2:
			loanScheduleDtos = loanBp.createEffectiveSchedule(strPrincipal,
					strTenor, strRate, beginDate);
			break;
		case 3:
			loanScheduleDtos = loanBp.createAnuitasSchedule(strPrincipal,
					strTenor, strRate, beginDate);
			break;
		default:
			break;
		}

		int i = 1;
		for (LoanScheduleDto scheduleDto : loanScheduleDtos) {
			LoanScheduleDv scheduleDv = createScheduleDv(scheduleDto);
			scheduleDv.setNr(i++);
			result.add(scheduleDv);
		}
		return result;
	}

	@Override
	public InfoLoanDv getPaymentInfo(Long id) {
		InfoLoanDv result = new InfoLoanDv();
		List<LoanTransactionDto> listTrans = loanBp.listLoanTransaction(id);
		LoanRpt loanRpt = loanReport.getDailyLoan(id, new Date());
		if (loanRpt != null) {
			//
			result.setOsPrincipal(loanRpt.getOsPrincipal());
			result.setOsMargin(loanRpt.getOsMargin());
			result.setOsTotal(loanRpt.getOsTotal());
			result.setOsDueCount(loanRpt.getDueOs());
			result.setOsDueValue(loanRpt.getOutstanding());
			result.setQuality(loanRpt.getQuality());
		}
		//
		int i = 1;
		for (LoanTransactionDto trans : listTrans) {
			TransactionDv dv = new TransactionDv();
			dv.setNr(i++);
			dv.setCode(trans.getCode());
			dv.setDate(trans.getDate());
			dv.setPrincipal(trans.getPrincipal());
			dv.setMargin(trans.getMargin());
			dv.setDiscount(trans.getDiscount());
			dv.setTotal(trans.getPrincipal() + trans.getMargin());
			result.getLoanPayments().add(dv);
		}
		return result;
	}

	@Override
	public GuaranteeDv saveGuarantee(String key, GuaranteeDv dv) {
		List<String> guaranteeTypes = loanBp.listGuaranteeType(key);
		GuaranteeDto dto = new GuaranteeDto();
		dto.setId(dv.getId());
		dto.setCompany(dv.getCompany());
		dto.setBranch(dv.getBranch());
		dto.setCustomer(dv.getCustomer().getId());
		dto.setRegistration(dv.getRegistration());
		dto.setActive(dv.getActive());
		dto.setCode(dv.getCode());
		dto.setOwner(dv.getOwnerName() != null ? dv.getOwnerName()
				.toUpperCase() : "");
		dto.setNumber(dv.getNumber() != null ? dv.getNumber().toUpperCase()
				: "");
		dto.setDescription(dv.getDescription() != null ? dv.getDescription()
				.toUpperCase() : "");
		dto.setAppraisalIntValue(dv.getAppraisalIntValue());
		dto.setAppraisalMarkValue(dv.getAppraisalMarkValue());
		dto.setAppraisalOJKValue(dv.getAppraisalOJKValue());
		dto.setType(dv.getType());
		long id = loanBp.saveGuarantee(key, dto);
		dv.setId(id);
		dv.setCode(dto.getCode());
		dv.setAppraisalIntValue(dto.getAppraisalIntValue());
		dv.setAppraisalMarkValue(dto.getAppraisalMarkValue());
		dv.setAppraisalOJKValue(dto.getAppraisalOJKValue());
		dv.setStrType(guaranteeTypes.get(dv.getType()));
		return dv;
	}

	private GuaranteeDv createGuaranteeToDv(GuaranteeDto dto,
			List<String> guaranteeTypes) {
		GuaranteeDv dv = new GuaranteeDv();
		dv.setId(dto.getId());
		dv.setCompany(dto.getCompany());
		dv.setBranch(dto.getBranch());
		CustomerDto cdto = customerBp.getCustomer(dto.getCustomer());
		dv.setCustomer(createCustomerDvFromDto(cdto));
		dv.setRegistration(dto.getRegistration());
		dv.setCode(dto.getCode());
		dv.setType(dto.getType());
		dv.setStrType(guaranteeTypes.get(dto.getType()));
		dv.setAppraisalIntValue(dto.getAppraisalIntValue());
		dv.setAppraisalMarkValue(dto.getAppraisalMarkValue());
		dv.setAppraisalOJKValue(dto.getAppraisalOJKValue());
		dv.setNumber(dto.getNumber());
		dv.setDescription(dto.getDescription());
		dv.setOwnerName(dto.getOwner());
		dv.setActive(dto.getActive());
		return dv;
	}

	@Override
	public GuaranteeDv getGuarantee(Long id) {
		List<String> guaranteeTypes = loanBp.listGuaranteeType("");
		GuaranteeDto dto = loanBp.getGuarantee(id);
		return createGuaranteeToDv(dto, guaranteeTypes);
	}

	@Override
	public List<DataDv> findGuarantee(String key, Boolean hasCode,
			Boolean hasName, Boolean hasDob, String code, String name, Date dob) {
		List<String> guaranteeTypes = loanBp.listGuaranteeType(key);
		List<DataDv> dataList = new ArrayList<DataDv>();
		int i = 1;
		FindLoanDto findLoanDto = new FindLoanDto();
		if (hasCode) {
			findLoanDto.setWithCode(true);
			findLoanDto.setCode(code);
		}
		if (hasName) {
			findLoanDto.setWithName(true);
			findLoanDto.setName(name);
		}
		for (GuaranteeDto loanDto : loanBp.findGuarantee(key, findLoanDto)) {
			CustomerDto customerDto = customerBp.getCustomer(loanDto
					.getCustomer());
			DataDv data = new DataDv();
			data.setId(loanDto.getId());
			data.setNr(i++);
			data.setKode(loanDto.getCode());
			data.setProduk((long) loanDto.getType());
			data.setStrProduk(guaranteeTypes.get(loanDto.getType()));
			data.setNama(customerDto.getName());
			data.setAlamat(customerDto.getAddress() + ", "
					+ customerDto.getCity() + " " + customerDto.getPostCode());
			dataList.add(data);
		}
		return dataList;
	}

	@Override
	public List<TypeDv> loadCommonListGuarantee(String key) {
		List<String> guaranteeTypes = loanBp.listGuaranteeType(key);
		List<TypeDv> typeList = new ArrayList<TypeDv>();
		int i = 0;
		for (String type : guaranteeTypes) {
			TypeDv typeDv = new TypeDv();
			typeDv.setType(i++);
			typeDv.setDescription(type);
			typeList.add(typeDv);
		}
		return typeList;
	}

	@Override
	public ValidationDv validateGuarantee(String key, GuaranteeDv dv) {
		ValidationDv result = new ValidationDv();
		GuaranteeDto dto = new GuaranteeDto();
		dto.setType(dv.getType());
		dto.setNumber(dv.getNumber());
		ValidationDto validDto = loanBp.validateGuarantee(key, dto);
		if (validDto.getError() > 0) {
			result.setError(validDto.getError());
			result.setErrorMessage(validDto.getErrorMessage());
		}
		return result;
	}

	@Override
	public List<String> listBISektor() {
		List<String> result = new ArrayList<String>();
		result.add("Pertanian, Perburuan dan Kehutanan");
		result.add("Perikanan");
		result.add("Pertambangan dan Penggalian");
		result.add("Industri Pengolahan");
		result.add("Listrik, Gas dan Air");
		result.add("Konstruksi");
		result.add("Perdagangan Besar dan Eceran");
		result.add("Penyediaan Akomodasi dan Penyediaan Makan Minum");
		result.add("Transportasi, Pergudangan dan Komunikasi");
		result.add("Perantara Keuangan");
		result.add("Real Estate");
		result.add("Administrasi Pemerintahan, Pertanahan dan Jaminan Sosial Wajib");
		result.add("Jasa Pendidikan");
		result.add("Jasa Kesehatan dan Kegiatan Sosial");
		result.add("Jasa Kemasyarakatan, Sosial Budaya, Hiburan dan Perorangan Lainnya");
		result.add("Jasa Perorangan yang Melayani Rumah Tangga");
		result.add("Kegiatan Usaha yang Belum Jelas Batasannya");
		result.add("Bukan Lapangan Usaha – Rumah Tangga");
		result.add("Bukan Lapangan Usaha – Lainnya");
		return result;
	}

	@Override
	public LoanDv newLoan() {
		LoanDv loanDv = new LoanDv();
		loanDv.setRegistration(new Date());
		loanDv.setContractDate(loanDv.getRegistration());
		return loanDv;
	}

	@Override
	public LoanDv newReschedule(Long oldId) {
		// Ambil data loan lama
		LoanDv oldLoan = getLoan(oldId);
		//
		oldLoan.setCode(oldLoan.getCode() + "A1");
		oldLoan.setRegistration(new Date());
		oldLoan.setContract("");
		oldLoan.setContractDate(oldLoan.getRegistration());
		oldLoan.getSchedules().clear();
		//
		LoanRpt report = loanReport.getDailyLoan(oldId,
				oldLoan.getRegistration());
		oldLoan.setPrincipal(report.getOsPrincipal());
		return oldLoan;
	}

	@Override
	public List<GuaranteeDv> listGuaranteeByCode(String key, String code) {
		List<String> guaranteeTypes = loanBp.listGuaranteeType("");
		List<GuaranteeDv> result = new ArrayList<GuaranteeDv>();
		for (GuaranteeDto dto : loanBp.listGuaranteeByCode(key, code)) {
			result.add(createGuaranteeToDv(dto, guaranteeTypes));
		}
		return result;
	}
}
