package org.simbiosis.ui.bprs.common.server;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.bp.micbank.ICustomerBp;
import org.simbiosis.bp.micbank.IDepositBp;
import org.simbiosis.bp.micbank.ILoanBp;
import org.simbiosis.bp.micbank.ISavingBp;
import org.simbiosis.microbank.CustomerDto;
import org.simbiosis.microbank.DepositDto;
import org.simbiosis.microbank.DepositProductDto;
import org.simbiosis.microbank.FindCustomerDto;
import org.simbiosis.microbank.FindDepositDto;
import org.simbiosis.microbank.FindLoanDto;
import org.simbiosis.microbank.FindSavingDto;
import org.simbiosis.microbank.LoanDto;
import org.simbiosis.microbank.LoanProductDto;
import org.simbiosis.microbank.LoanScheduleDto;
import org.simbiosis.microbank.SavingDto;
import org.simbiosis.microbank.SavingProductDto;
import org.simbiosis.ui.bprs.common.client.rpc.MicBankService;
import org.simbiosis.ui.bprs.common.shared.CustomerDv;
import org.simbiosis.ui.bprs.common.shared.CustomerDv.IdTypeEnum;
import org.simbiosis.ui.bprs.common.shared.CustomerDv.SexTypeEnum;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.DepositDv;
import org.simbiosis.ui.bprs.common.shared.LoanDv;
import org.simbiosis.ui.bprs.common.shared.LoanScheduleDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class MicBankServiceImpl extends RemoteServiceServlet implements
		MicBankService {

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/CustomerBp")
	ICustomerBp customerBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/SavingBp")
	ISavingBp savingBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/DepositBp")
	IDepositBp depositBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/LoanBp")
	ILoanBp loanBp;

	DecimalFormat nf = new DecimalFormat("#,##0.00");
	DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");

	public MicBankServiceImpl() {
	}

	CustomerDv createCustomerDvFromDto(CustomerDto dto) {
		CustomerDv dv = new CustomerDv();
		dv.setId(dto.getId());
		dv.setCode(dto.getCode());
		dv.setRegistration(dto.getRegistration());
		dv.setStrRegistration(sdf.print(new DateTime(dv.getRegistration())));
		dv.setName(dto.getName());
		dv.setTitle(dto.getTitle());
		dv.setSex(dto.getSex());
		dv.setStrSex(SexTypeEnum.valueToString(dv.getSex()));
		dv.setPob(dto.getPob());
		dv.setDob(dto.getDob());
		dv.setStrDob(sdf.print(new DateTime(dv.getDob())));
		dv.setIdType(dto.getIdType());
		dv.setStrIdType(IdTypeEnum.valueToString(dv.getIdType()));
		dv.setIdCode(dto.getIdCode());
		dv.setMotherName(dto.getMotherName());
		dv.setSpouseName(dto.getSpouseName());
		dv.setAddress(dto.getAddress());
		dv.setVillage(dto.getVillage());
		dv.setDistrict(dto.getDistrict());
		dv.setCity(dto.getCity());
		dv.setPostCode(dto.getPostCode());
		// CityDto spDtos = savingBp.getCitys(dto.getProvince());
		// dv.setProvince(spDtos.getName());
		// dv.setStrProduct(spDto.getName());
		dv.setProvince(dto.getProvince());
		dv.setPhone(dto.getPhone());
		dv.setHandphone(dto.getHandphone());
		dv.setOccupation(dto.getOccupation());
		dv.setOfficeName(dto.getOfficeName());
		dv.setOfficeAddress(dto.getOfficeAddress());
		dv.setOfficeCity(dto.getOfficeCity());
		dv.setIncome(dto.getIncome());
		dv.setTaxable(dto.getTaxable() == 1);
		dv.setStrTaxable(dv.getTaxable() ? "PPH" : "NON PPH");
		dv.setTaxNr(dto.getTaxNr());
		dv.setDescendant(dto.getDescendant());
		dv.setDescAddress(dto.getDescAddress());
		dv.setType(dto.getType());
		dv.setBankRel(dto.getBankRel() == 1);
		dv.setStrBankRel(dv.getBankRel() ? "TERKAIT" : "TIDAK TERKAIT");
		return dv;
	}

	@Override
	public SavingDv getSaving(Long id) {
		SavingDv dv = new SavingDv();
		SavingDto dto = savingBp.getSaving(id);
		dv.setId(dto.getId());
		dv.setCode(dto.getCode());
		dv.setRegistration(dto.getRegistration());
		dv.setStrRegistration(sdf.print(new DateTime(dv.getRegistration())));
		SavingProductDto spDto = savingBp.getSavingProduct(dto.getProduct());
		dv.setProduct(spDto.getId());
		dv.setStrProduct(spDto.getName());
		dv.setZakat(dto.getZakat() == 1);
		dv.setStrZakat(dv.getZakat() ? "ZAKAT" : "NON ZAKAT");
		//
		CustomerDto customerDto = customerBp.getCustomer(dto.getCustomer());
		dv.setCustomer(createCustomerDvFromDto(customerDto));
		return dv;
	}

	@Override
	public List<DataDv> findSaving(String key, Boolean isTellerTransaction,
			Boolean hasCode, Boolean hasName, Boolean hasDob, String code,
			String name, Date dob) {
		List<DataDv> dataList = new ArrayList<DataDv>();
		int i = 1;
		FindSavingDto findSavingDto = new FindSavingDto();
		if (hasCode) {
			findSavingDto.setWithCode(true);
			findSavingDto.setCode(code);
		}
		if (hasName) {
			findSavingDto.setWithName(true);
			findSavingDto.setName(name);
		}
		if (hasDob) {
			findSavingDto.setWithDob(true);
			findSavingDto.setDob(dob);
		}
		findSavingDto.setTellerTransaction(isTellerTransaction);
		for (SavingDto savingDto : savingBp.findSaving(key, findSavingDto)) {
			CustomerDto customerDto = customerBp.getCustomer(savingDto
					.getCustomer());
			DataDv data = new DataDv();
			data.setId(savingDto.getId());
			data.setNr(i++);
			data.setKode(savingDto.getCode());
			SavingProductDto savingProduct = savingBp
					.getSavingProduct(savingDto.getProduct());
			data.setProduk(savingDto.getProduct());
			data.setStrProduk(savingProduct.getName());
			data.setNama(customerDto.getName());
			data.setAlamat(customerDto.getAddress() + ", "
					+ customerDto.getCity() + " " + customerDto.getPostCode());
			dataList.add(data);
		}
		return dataList;
	}

	@Override
	public CustomerDv getCustomer(Long id) {
		CustomerDto customerDto = customerBp.getCustomer(id);
		return createCustomerDvFromDto(customerDto);
	}

	@Override
	public List<DataDv> findCustomer(String key, Boolean hasName,
			Boolean hasDob, String name, Date dob) {
		List<DataDv> result = new ArrayList<DataDv>();
		FindCustomerDto findCustomer = new FindCustomerDto();
		if (hasName) {
			findCustomer.setWithName(true);
			findCustomer.setName(name);
		}
		if (hasDob) {
			findCustomer.setWithDob(true);
			findCustomer.setDob(dob);
		}
		List<CustomerDto> listCustomer = customerBp.findCustomer(key,
				findCustomer);
		int i = 1;
		for (CustomerDto customerDto : listCustomer) {
			DataDv customerDv = new DataDv();
			customerDv.setNr(i++);
			customerDv.setId(customerDto.getId());
			customerDv.setKode(customerDto.getCode());
			customerDv.setNama(customerDto.getName());
			customerDv.setAlamat(customerDto.getAddress() + ", "
					+ customerDto.getCity() + " " + customerDto.getPostCode());
			result.add(customerDv);
		}
		return result;
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

	@Override
	public LoanDv getLoan(Long id) {
		LoanDv dv = new LoanDv();
		LoanDto dto = loanBp.getLoan(id);
		dv.setId(dto.getId());
		dv.setCode(dto.getCode());
		dv.setRegistration(dto.getRegistration());
		dv.setStrRegistration(sdf.print(new DateTime(dv.getRegistration())));
		LoanProductDto loanProduct = loanBp.getLoanProduct(dto.getProduct());
		dv.setProduct(loanProduct.getId());
		dv.setStrProduct(loanProduct.getName());
		dv.setPrincipal(dto.getPrincipal());
		dv.setStrPrincipal(nf.format(dv.getPrincipal()));
		dv.setMargin(dto.getMargin());
		dv.setStrMargin(nf.format(dv.getMargin()));
		dv.setRate(dto.getRate());
		dv.setStrRate(nf.format(dv.getRate()));
		dv.setTenor(dto.getTenor());
		dv.setStrTenor("" + dv.getTenor());
		dv.setDropped(dto.isDropped());
		dv.setPurpose(dto.getPurpose());
		dv.setBiSektor(dto.getBiSektor());
		//
		int i = 1;
		for (LoanScheduleDto scheduleDto : dto.getSchedules()) {
			LoanScheduleDv scheduleDv = createScheduleDv(scheduleDto);
			scheduleDv.setNr(i++);
			scheduleDv.setLoan(dv.getId());
			dv.getSchedules().add(scheduleDv);
		}
		//
		CustomerDto customerDto = customerBp.getCustomer(dto.getCustomer());
		dv.setCustomer(createCustomerDvFromDto(customerDto));
		dv.setSaving(getSaving(dto.getSaving()));
		return dv;
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
	public List<DataDv> findDeposit(String key, Boolean hasCode,
			Boolean hasName, Boolean hasDob, String code, String name, Date dob) {
		List<DataDv> dataList = new ArrayList<DataDv>();
		int i = 1;
		FindDepositDto findDepositDto = new FindDepositDto();
		if (hasCode) {
			findDepositDto.setWithCode(true);
			findDepositDto.setCode(code);
		}
		if (hasName) {
			findDepositDto.setWithName(true);
			findDepositDto.setName(name);
		}
		for (DepositDto depositDto : depositBp.findDeposit(key, findDepositDto)) {
			CustomerDto customerDto = customerBp.getCustomer(depositDto
					.getCustomer());
			DataDv data = new DataDv();
			data.setId(depositDto.getId());
			data.setNr(i++);
			data.setKode(depositDto.getCode());
			DepositProductDto depositProduct = depositBp
					.getDepositProduct(depositDto.getProduct());
			data.setProduk(depositDto.getProduct());
			data.setStrProduk(depositProduct.getName());
			data.setNama(customerDto.getName());
			data.setAlamat(customerDto.getAddress() + ", "
					+ customerDto.getCity() + " " + customerDto.getPostCode());
			dataList.add(data);
		}
		return dataList;
	}

	@Override
	public DepositDv getDeposit(Long id) {
		DepositDv dv = new DepositDv();
		DepositDto dto = depositBp.getDeposit(id);
		dv.setId(dto.getId());
		dv.setCode(dto.getCode());
		dv.setRegistration(dto.getRegistration());
		dv.setStrRegistration(sdf.print(new DateTime(dv.getRegistration())));
		DepositProductDto product = depositBp.getDepositProduct(dto
				.getProduct());
		dv.setProduct(product.getId());
		dv.setStrProduct(product.getName());
		dv.setAro(dto.getAro() == 1);
		dv.setStrAro(dv.getAro() ? "ARO" : "NON ARO");
		dv.setZakat(dto.getZakat() == 1);
		dv.setStrZakat(dv.getZakat() ? "ZAKAT" : "NON ZAKAT");
		dv.setValue(dto.getValue());
		dv.setStrValue(nf.format(dv.getValue()));
		dv.setRate(dto.getRate());
		dv.setStrRate(nf.format(dv.getRate()));
		dv.setSpecialRate(dto.getSpecialRate());
		dv.setStrSpecialRate(nf.format(dv.getSpecialRate()));
		dv.setBilyet(dto.getBilyet());
		//
		CustomerDto customerDto = customerBp.getCustomer(dto.getCustomer());
		dv.setCustomer(createCustomerDvFromDto(customerDto));
		//
		dv.setSaving(getSaving(dto.getSaving()));
		return dv;
	}
}
