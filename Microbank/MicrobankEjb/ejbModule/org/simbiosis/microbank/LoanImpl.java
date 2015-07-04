package org.simbiosis.microbank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.joda.time.DateTime;
import org.simbiosis.microbank.model.Customer;
import org.simbiosis.microbank.model.Guarantee;
import org.simbiosis.microbank.model.Loan;
import org.simbiosis.microbank.model.LoanCode;
import org.simbiosis.microbank.model.LoanProduct;
import org.simbiosis.microbank.model.LoanSchedule;
import org.simbiosis.microbank.model.LoanTransaction;
import org.simbiosis.microbank.model.Saving;

@Stateless
@Remote(ILoan.class)
public class LoanImpl implements ILoan {

	@PersistenceContext(unitName = "MicrobankEjb", type = PersistenceContextType.TRANSACTION)
	EntityManager em;

	Loan createLoanFromDto(LoanDto dto) {
		Loan loan = new Loan();
		loan.setId(dto.getId());
		loan.setRefId(dto.getRefId());
		loan.setRegistration(dto.getRegistration());
		loan.setCode(dto.getCode());
		loan.setRefCode(dto.getRefCode());
		loan.setPrincipal(dto.getPrincipal());
		loan.setRate(dto.getRate());
		loan.setMargin(dto.getMargin());
		loan.setTenor(dto.getTenor());
		loan.setContract(dto.getContract());
		loan.setContractDate(dto.getContractDate());
		loan.setDropped(dto.isDropped() ? 1 : 0);
		loan.setActive(dto.getActive());
		loan.setPurpose(dto.getPurpose());
		loan.setBiSektor(dto.getBiSektor());
		LoanProduct product = em.find(LoanProduct.class, dto.getProduct());
		loan.setProduct(product);
		Saving saving = em.find(Saving.class, dto.getSaving());
		loan.setSaving(saving);
		Customer customer = em.find(Customer.class, dto.getCustomer());
		loan.setCustomer(customer);
		for (LoanScheduleDto scheduleDto : dto.getSchedules()) {
			LoanSchedule schedule = new LoanSchedule();
			schedule.setId(scheduleDto.getId());
			schedule.setDate(scheduleDto.getDate());
			schedule.setPrincipal(scheduleDto.getPrincipal());
			schedule.setMargin(scheduleDto.getMargin());
			schedule.setTotal(scheduleDto.getTotal());
			schedule.setLoan(loan);
			loan.getSchedules().add(schedule);
		}
		if (dto.getId() == 0) {
			Guarantee gua = createGuaranteeFromDto(dto.getGuarantees().get(0));
			em.persist(gua);
			loan.getGuarantees().add(gua);
		} else {
			for (GuaranteeDto guaDto : dto.getGuarantees()) {
				Guarantee gua = em.find(Guarantee.class, guaDto.getId());
				loan.getGuarantees().add(gua);
			}
		}
		//
		loan.setCompany(dto.getCompany());
		loan.setBranch(dto.getBranch());
		loan.setAo(dto.getAo());
		loan.setAoHistory(dto.getAoHistory());
		loan.setReschedule(dto.getReschedule());
		loan.setAdmin(dto.getAdmin());
		loan.setFine(dto.getFine());
		return loan;
	}

	Loan createLoanRescheduleFromDto(LoanDto dto) {
		Loan loan = new Loan();
		loan.setId(dto.getId());
		loan.setRefId(dto.getRefId());
		loan.setRegistration(dto.getRegistration());
		loan.setCode(dto.getCode());
		loan.setRefCode(dto.getRefCode());
		loan.setPrincipal(dto.getPrincipal());
		loan.setRate(dto.getRate());
		loan.setMargin(dto.getMargin());
		loan.setTenor(dto.getTenor());
		loan.setContract(dto.getContract());
		loan.setContractDate(dto.getContractDate());
		loan.setDropped(dto.isDropped() ? 1 : 0);
		loan.setActive(dto.getActive());
		loan.setPurpose(dto.getPurpose());
		loan.setBiSektor(dto.getBiSektor());
		LoanProduct product = em.find(LoanProduct.class, dto.getProduct());
		loan.setProduct(product);
		Saving saving = em.find(Saving.class, dto.getSaving());
		loan.setSaving(saving);
		Customer customer = em.find(Customer.class, dto.getCustomer());
		loan.setCustomer(customer);
		for (LoanScheduleDto scheduleDto : dto.getSchedules()) {
			LoanSchedule schedule = new LoanSchedule();
			schedule.setId(scheduleDto.getId());
			schedule.setDate(scheduleDto.getDate());
			schedule.setPrincipal(scheduleDto.getPrincipal());
			schedule.setMargin(scheduleDto.getMargin());
			schedule.setTotal(scheduleDto.getTotal());
			schedule.setLoan(loan);
			loan.getSchedules().add(schedule);
		}
		for (GuaranteeDto guaDto : dto.getGuarantees()) {
			Guarantee gua = em.find(Guarantee.class, guaDto.getId());
			loan.getGuarantees().add(gua);
		}
		//
		loan.setCompany(dto.getCompany());
		loan.setBranch(dto.getBranch());
		loan.setAo(dto.getAo());
		loan.setAoHistory(dto.getAoHistory());
		loan.setReschedule(dto.getReschedule());
		loan.setAdmin(dto.getAdmin());
		loan.setFine(dto.getFine());
		return loan;
	}

	LoanDto createLoanToDto(Loan loan) {
		LoanDto dto = new LoanDto();
		dto.setId(loan.getId());
		dto.setRefId(loan.getRefId());
		dto.setRegistration(loan.getRegistration());
		dto.setCode(loan.getCode());
		dto.setRefCode(loan.getRefCode());
		dto.setPrincipal(loan.getPrincipal());
		dto.setRate(loan.getRate());
		dto.setMargin(loan.getMargin());
		dto.setTenor(loan.getTenor());
		dto.setContract(loan.getContract());
		dto.setContractDate(loan.getContractDate());
		dto.setDropped(loan.getDropped() == 1);
		dto.setActive(loan.getActive());
		dto.setClosing(loan.getClosing());
		dto.setSaving(loan.getSaving().getId());
		dto.setProduct(loan.getProduct().getId());
		dto.setCustomer(loan.getCustomer().getId());
		dto.setAo(loan.getAo());
		dto.setPurpose(loan.getPurpose());
		dto.setBiSektor(loan.getBiSektor());
		for (LoanSchedule schedule : loan.getSchedules()) {
			LoanScheduleDto scheduleDto = new LoanScheduleDto();
			scheduleDto.setId(schedule.getId());
			scheduleDto.setDate(schedule.getDate());
			scheduleDto.setPrincipal(schedule.getPrincipal());
			scheduleDto.setMargin(schedule.getMargin());
			scheduleDto.setTotal(schedule.getTotal());
			scheduleDto.setLoan(loan.getId());
			dto.getSchedules().add(scheduleDto);
		}
		for (Guarantee gua : loan.getGuarantees()) {
			GuaranteeDto gDto = new GuaranteeDto();
			gDto.setId(gua.getId());
			gDto.setActive(gua.getActive());
			gDto.setCode(gua.getCode());
			gDto.setCustomer(gua.getCustomer().getId());
			gDto.setNumber(gua.getNumber());
			gDto.setType(gua.getType());
			gDto.setAppraisalIntValue(gua.getAppraisalIntValue());
			dto.getGuarantees().add(gDto);
		}
		dto.setCompany(loan.getCompany());
		dto.setBranch(loan.getBranch());
		dto.setAoHistory(loan.getAoHistory());
		dto.setReschedule(loan.getReschedule());
		dto.setAdmin(loan.getAdmin());
		dto.setFine(loan.getFine());
		return dto;
	}

	LoanDto createLoanToShortDto(Loan Loan) {
		LoanDto loanDto = new LoanDto();
		loanDto.setId(Loan.getId());
		loanDto.setRefId(Loan.getRefId());
		loanDto.setCode(Loan.getCode());
		loanDto.setRefCode(Loan.getRefCode());
		loanDto.setPrincipal(Loan.getPrincipal());
		loanDto.setRate(Loan.getRate());
		loanDto.setTenor(Loan.getTenor());
		loanDto.setProduct(Loan.getProduct().getId());
		loanDto.setCustomer(Loan.getCustomer().getId());
		loanDto.setAo(Loan.getAo());
		return loanDto;
	}

	LoanProduct createLoanProductFromDto(LoanProductDto dto) {
		LoanProduct product = new LoanProduct();
		product.setId(dto.getId());
		product.setRefId(dto.getRefId());
		product.setCode(dto.getCode());
		product.setName(dto.getName());
		product.setCompany(dto.getCompany());
		product.setSchema(dto.getSchema());
		product.setCoa1(dto.getCoa1());
		product.setCoa2(dto.getCoa2());
		product.setCoa3(dto.getCoa3());
		product.setCoa4(dto.getCoa4());
		product.setCoa5(dto.getCoa5());
		product.setCoa6(dto.getCoa6());
		product.setActive(dto.getActive());
		product.setProfitShared(dto.getProfitShared());
		return product;
	}

	LoanProductDto createLoanProductToDto(LoanProduct product) {
		LoanProductDto dto = new LoanProductDto();
		dto.setId(product.getId());
		dto.setRefId(product.getRefId());
		dto.setCode(product.getCode());
		dto.setName(product.getName());
		dto.setCompany(product.getCompany());
		dto.setSchema(product.getSchema());
		dto.setCoa1(product.getCoa1());
		dto.setCoa2(product.getCoa2());
		dto.setCoa3(product.getCoa3());
		dto.setCoa4(product.getCoa4());
		dto.setCoa5(product.getCoa5());
		dto.setCoa6(product.getCoa6());
		dto.setActive(product.getActive());
		dto.setProfitShared(product.getProfitShared());
		return dto;
	}

	LoanTransaction createLoanTransactionFromDto(LoanTransactionDto transDto) {
		LoanTransaction trans = new LoanTransaction();
		trans.setId(transDto.getId());
		trans.setRefId(transDto.getRefId());
		trans.setCode(transDto.getCode());
		trans.setRefCode(transDto.getRefCode());
		trans.setDate(transDto.getDate());
		trans.setDescription(transDto.getDescription());
		trans.setType(transDto.getType());
		trans.setDirection(transDto.getDirection());
		trans.setPrincipal(transDto.getPrincipal());
		trans.setMargin(transDto.getMargin());
		trans.setDiscount(transDto.getDiscount());
		Loan saving = em.find(Loan.class, transDto.getLoan());
		trans.setLoan(saving);
		trans.setCompany(transDto.getCompany());
		trans.setBranch(transDto.getBranch());
		return trans;
	}

	LoanTransactionDto createLoanTransactionToDto(LoanTransaction trans) {
		LoanTransactionDto transDto = new LoanTransactionDto();
		transDto.setId(trans.getId());
		transDto.setRefId(trans.getRefId());
		transDto.setCode(trans.getCode());
		transDto.setRefCode(trans.getRefCode());
		transDto.setDate(trans.getDate());
		transDto.setDescription(trans.getDescription());
		transDto.setType(trans.getType());
		transDto.setDirection(trans.getDirection());
		transDto.setPrincipal(trans.getPrincipal());
		transDto.setMargin(trans.getMargin());
		transDto.setDiscount(trans.getDiscount());
		transDto.setLoan(trans.getLoan().getId());
		transDto.setCompany(trans.getCompany());
		transDto.setBranch(trans.getBranch());
		return transDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long getCodeCounter(long company, String prefix) {
		long result = 1;
		Query qry = em.createNamedQuery("getLoanCodeCounter");
		qry.setParameter("company", company);
		qry.setParameter("prefix", prefix);
		List<LoanCode> codes = qry.getResultList();
		LoanCode code = null;
		if (codes.size() > 0) {
			code = codes.get(0);
			result = code.getCounter();
		} else {
			code = new LoanCode();
			code.setCompany(company);
			code.setPrefix(prefix);
		}
		code.setCounter(result + 1);
		em.persist(code);
		return result;
	}

	@Override
	public long saveLoan(LoanDto loanDto) {
		Loan loan = createLoanFromDto(loanDto);
		if (loanDto.getId() == 0) {
			em.persist(loan);
		} else {
			//
			Query qry = em.createNamedQuery("deleteLoanSchedules");
			qry.setParameter("loanId", loan.getId());
			qry.executeUpdate();
			//
			em.merge(loan);
		}
		return loan.getId();
	}

	@Override
	public long saveLoanReschedule(LoanDto loanDto) {
		Loan loan = createLoanRescheduleFromDto(loanDto);
		em.persist(loan);
		return loan.getId();
	}

	@Override
	public LoanDto getLoan(long id) {
		Loan loan = em.find(Loan.class, id);
		return createLoanToDto(loan);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanDto> listLoan(FindLoanDto findLoan) {
		List<LoanDto> result = new ArrayList<LoanDto>();
		Query qry = null;
		if (findLoan.getBranch() == 0) {
			qry = em.createNamedQuery("listLoanByCompany");
			qry.setParameter("company", findLoan.getCompany());
		} else {
			qry = em.createNamedQuery("listLoanByCompanyBranch");
			qry.setParameter("company", findLoan.getCompany());
			qry.setParameter("branch", findLoan.getBranch());
		}
		List<Loan> loans = qry.getResultList();
		for (Loan loan : loans) {
			result.add(createLoanToDto(loan));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanDto> findLoan(FindLoanDto findLoan) {
		List<LoanDto> result = new ArrayList<LoanDto>();
		String strQry = "select x from Loan x where ";
		if (findLoan.getBranch() != 0) {
			strQry += "x.branch=:branch ";
		} else {
			strQry += "x.company=:company ";
		}
		if (findLoan.isWithCode()) {
			strQry += "and x.code=:code ";
		}
		if (findLoan.isWithName()) {
			strQry += "and upper(x.customer.name) like :name ";
		}
		Query qry = em.createQuery(strQry);
		if (findLoan.getBranch() != 0) {
			qry.setParameter("branch", findLoan.getBranch());
		} else {
			qry.setParameter("company", findLoan.getCompany());
		}
		if (findLoan.isWithCode()) {
			qry.setParameter("code", findLoan.getCode());
		}
		if (findLoan.isWithName()) {
			String name = findLoan.getName().toUpperCase();
			if (name.isEmpty()) {
				name = "---";
			} else {
				name = "%" + name + "%";
			}
			qry.setParameter("name", name);
		}
		List<Loan> LoanList = qry.getResultList();
		for (Loan Loan : LoanList) {
			result.add(createLoanToShortDto(Loan));
		}
		return result;
	}

	Date createNextDate(Date date) {
		DateTime now = new DateTime(date);
		return now.plusMonths(1).toDate();
	}

	@Override
	public List<LoanScheduleDto> createFlatSchedule(double principal,
			double tenor, double rate, Date beginDate) {
		List<LoanScheduleDto> schedules = new ArrayList<LoanScheduleDto>();
		double myRate = rate / 100;
		double angsuranPokok = principal / tenor;
		double angsuranMarjin = (principal * myRate) / 12;
		double angsuranTotal = angsuranPokok + angsuranMarjin;
		Date angsuranDate = beginDate;
		for (int i = 0; i < tenor; i++) {
			schedules.add(new LoanScheduleDto(angsuranDate, angsuranPokok,
					angsuranMarjin, angsuranTotal));
			angsuranDate = createNextDate(angsuranDate);
		}
		return schedules;
	}

	@Override
	public List<LoanScheduleDto> createEffectiveSchedule(double principal,
			double tenor, double rate, Date beginDate) {
		List<LoanScheduleDto> schedules = new ArrayList<LoanScheduleDto>();
		double myRate = rate / 100;
		double angsuranPokok = principal / tenor;
		Date angsuranDate = beginDate;
		for (int i = 0; i < tenor; i++) {
			double angsuranMarjin = ((principal - (angsuranPokok * i)) * myRate) / 12;
			double angsuranTotal = angsuranPokok + angsuranMarjin;
			schedules.add(new LoanScheduleDto(angsuranDate, angsuranPokok,
					angsuranMarjin, angsuranTotal));
			angsuranDate = createNextDate(angsuranDate);
		}
		return schedules;
	}

	@Override
	public List<LoanScheduleDto> createAnuitasSchedule(double principal,
			double tenor, double rate, Date beginDate) {
		List<LoanScheduleDto> schedules = new ArrayList<LoanScheduleDto>();
		double myRate = rate / 100;
		double sisaPrincipal = principal;
		double powAngsuran = Math.pow(1 + (myRate / 12), tenor);
		double angsuranTotal = (principal * myRate / 12)
				* (1 / (1 - ((1 / powAngsuran))));
		Date angsuranDate = beginDate;
		for (int i = 0; i < tenor; i++) {
			double angsuranMarjin = sisaPrincipal * myRate / 12;
			double angsuranPokok = angsuranTotal - angsuranMarjin;
			schedules.add(new LoanScheduleDto(angsuranDate, angsuranPokok,
					angsuranMarjin, angsuranTotal));
			sisaPrincipal -= angsuranPokok;
			angsuranDate = createNextDate(angsuranDate);
		}
		return schedules;
	}

	@Override
	public long saveLoanProduct(LoanProductDto LoanProductDto) {
		LoanProduct LoanProduct = createLoanProductFromDto(LoanProductDto);
		if (LoanProductDto.getId() == 0) {
			em.persist(LoanProduct);
		} else {
			em.merge(LoanProduct);
		}
		return LoanProduct.getId();
	}

	@Override
	public LoanProductDto getLoanProduct(long id) {
		LoanProduct LoanProduct = em.find(LoanProduct.class, id);
		return createLoanProductToDto(LoanProduct);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanProductDto> listLoanProduct(long company) {
		List<LoanProductDto> LoanProductList = new ArrayList<LoanProductDto>();
		Query qry = em.createNamedQuery("listLoanProduct");
		qry.setParameter("company", company);
		List<LoanProduct> result = qry.getResultList();
		for (LoanProduct LoanProduct : result) {
			LoanProductList.add(createLoanProductToDto(LoanProduct));
		}
		return LoanProductList;
	}

	@Override
	public void closeLoan(long id, Date closing) {
		Loan loan = em.find(Loan.class, id);
		loan.setActive(0);
		loan.setClosing(closing);
		em.persist(loan);
	}

	@SuppressWarnings("unchecked")
	@Override
	public long isLoanProductExistByRefId(long company, long refId) {
		Query qry = em.createNamedQuery("getLoanProductByRefId");
		qry.setParameter("company", company);
		qry.setParameter("refId", refId);
		List<LoanProduct> listLoanProduct = qry.getResultList();
		if (listLoanProduct.size() > 0) {
			return listLoanProduct.get(0).getId();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LoanDto getLoanByRefId(long company, long branch, long refId) {
		Query qry = em.createNamedQuery("getLoanByRefId");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("refId", refId);
		List<Loan> listLoan = qry.getResultList();
		if (listLoan.size() > 0) {
			return createLoanToDto(listLoan.get(0));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long isLoanExistByRefId(long company, long branch, long refId) {
		Query qry = em.createNamedQuery("getLoanByRefId");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("refId", refId);
		List<Loan> listLoan = qry.getResultList();
		if (listLoan.size() > 0) {
			return listLoan.get(0).getId();
		}
		return 0;
	}

	@Override
	public String getMaxLoanTransCode(long company, long branch,
			String prefixCode) {
		Query qry = em.createNamedQuery("getMaxLoanTransCode");
		qry.setParameter("prefixCode", prefixCode + "%");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		String code = (String) qry.getSingleResult();
		return code;
	}

	@Override
	public long saveLoanTransaction(LoanTransactionDto transDto) {
		LoanTransaction trans = createLoanTransactionFromDto(transDto);
		if (transDto.getId() == 0) {
			em.persist(trans);
		} else {
			em.merge(trans);
		}
		return trans.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public LoanTransactionDto getLoanTransByDateCode(long loanId, Date date,
			String code) {
		Query qry = em.createNamedQuery("getLoanTransByDateCode");
		qry.setParameter("loanId", loanId);
		qry.setParameter("date", date);
		qry.setParameter("code", code);
		List<LoanTransaction> listLoan = qry.getResultList();
		if (listLoan.size() > 0) {
			return createLoanTransactionToDto(listLoan.get(0));
		}
		return null;
	}

	public LoanTransactionDto getLoanTransaction(long id) {
		LoanTransaction trans = em.find(LoanTransaction.class, id);
		return createLoanTransactionToDto(trans);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanTransactionDto> listLoanTransaction(long id) {
		List<LoanTransactionDto> result = new ArrayList<LoanTransactionDto>();
		Query qry = em.createNamedQuery("listLoanTransaction");
		qry.setParameter("id", id);
		List<LoanTransaction> listTrans = qry.getResultList();
		for (LoanTransaction trans : listTrans) {
			result.add(createLoanTransactionToDto(trans));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanTransactionDto> listLoanTransaction(long id, Date date) {
		List<LoanTransactionDto> result = new ArrayList<LoanTransactionDto>();
		Query qry = em.createNamedQuery("listLoanTransactionUntil");
		qry.setParameter("id", id);
		qry.setParameter("date", date);
		List<LoanTransaction> listTrans = qry.getResultList();
		for (LoanTransaction trans : listTrans) {
			result.add(createLoanTransactionToDto(trans));
		}
		return result;
	}

	@Override
	public String getMaxGuaranteeCode(long company, long branch,
			String prefixCode) {
		Query qry = em.createNamedQuery("getMaxGuaranteeCode");
		qry.setParameter("prefixCode", prefixCode + "%");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		String code = (String) qry.getSingleResult();
		return code;
	}

	GuaranteeDto createGuaranteeToDto(Guarantee gua) {
		GuaranteeDto dto = new GuaranteeDto();
		dto.setId(gua.getId());
		dto.setRefId(gua.getRefId());
		dto.setCompany(gua.getCompany());
		dto.setBranch(gua.getBranch());
		dto.setRegistration(gua.getRegistration());
		dto.setCode(gua.getCode());
		dto.setRefCode(gua.getRefCode());
		dto.setNumber(gua.getNumber());
		dto.setType(gua.getType());
		dto.setCustomer(gua.getCustomer().getId());
		dto.setOwner(gua.getOwner());
		dto.setDescription(gua.getDescription());
		dto.setAppraisalIntValue(gua.getAppraisalIntValue());
		dto.setAppraisalMarkValue(gua.getAppraisalMarkValue());
		dto.setAppraisalOJKValue(gua.getAppraisalOJKValue());
		dto.setActive(gua.getActive());
		return dto;
	}

	Guarantee createGuaranteeFromDto(GuaranteeDto dto) {
		Guarantee gua = new Guarantee();
		gua.setId(dto.getId());
		gua.setRefId(dto.getRefId());
		gua.setCompany(dto.getCompany());
		gua.setBranch(dto.getBranch());
		gua.setRegistration(dto.getRegistration());
		Customer customer = em.find(Customer.class, dto.getCustomer());
		gua.setCustomer(customer);
		gua.setCode(dto.getCode());
		gua.setRefCode(dto.getRefCode());
		gua.setNumber(dto.getNumber());
		gua.setType(dto.getType());
		gua.setOwner(dto.getOwner());
		gua.setDescription(dto.getDescription());
		gua.setAppraisalIntValue(dto.getAppraisalIntValue());
		gua.setAppraisalMarkValue(dto.getAppraisalMarkValue());
		gua.setAppraisalOJKValue(dto.getAppraisalOJKValue());
		gua.setActive(dto.getActive());
		return gua;
	}

	@Override
	public long saveGuarantee(GuaranteeDto dto) {
		Guarantee gua = createGuaranteeFromDto(dto);
		if (dto.getId() == 0) {
			em.persist(gua);
		} else {
			em.merge(gua);
		}
		return gua.getId();
	}

	@Override
	public GuaranteeDto getGuarantee(long id) {
		Guarantee gua = em.find(Guarantee.class, id);
		return createGuaranteeToDto(gua);
	}

	@SuppressWarnings("unchecked")
	@Override
	public GuaranteeDto getGuaranteeByRefId(long company, long refId) {
		Query qry = em.createNamedQuery("getGuaranteeByRefId");
		qry.setParameter("company", company);
		// qry.setParameter("branch", branch);
		qry.setParameter("refId", refId);
		List<Guarantee> listLoan = qry.getResultList();
		if (listLoan.size() > 0) {
			return createGuaranteeToDto(listLoan.get(0));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long isGuaranteeExistByRefId(long company, long refId) {
		Query qry = em.createNamedQuery("getGuaranteeByRefId");
		qry.setParameter("company", company);
		// qry.setParameter("branch", branch);
		qry.setParameter("refId", refId);
		List<Guarantee> listLoan = qry.getResultList();
		if (listLoan.size() > 0) {
			return listLoan.get(0).getId();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> listLoanId(FindLoanDto findLoanDto, Date date) {
		Query qry = null;
		if (findLoanDto.getBranch() == 0) {
			qry = em.createNamedQuery("listLoanIdByCompany");
			qry.setParameter("active", findLoanDto.getActive());
			qry.setParameter("company", findLoanDto.getCompany());
			qry.setParameter("date", date);
		} else {
			qry = em.createNamedQuery("listLoanIdByCompanyBranch");
			qry.setParameter("active", findLoanDto.getActive());
			qry.setParameter("company", findLoanDto.getCompany());
			qry.setParameter("branch", findLoanDto.getBranch());
			qry.setParameter("date", date);
		}
		List<Long> result = qry.getResultList();
		return result;
	}

	LoanInformationDto createSimpleLoanInformationToDto(Loan loan) {
		LoanInformationDto info = new LoanInformationDto();
		info.setId(loan.getId());
		info.setCompany(loan.getCompany());
		info.setBranch(loan.getBranch());
		info.setCode(loan.getCode());
		info.setCustomer(loan.getCustomer().getId());
		info.setName(loan.getCustomer().getName());
		info.setSchema(loan.getProduct().getSchema());
		info.setPrincipal(loan.getPrincipal());
		info.setMargin(loan.getMargin());
		info.setBegin(loan.getRegistration());
		Date end = getLoanEnd(loan.getId());
		info.setEnd(end);
		return info;
	}

	LoanInformationDto createLoanInformationToDto(Loan loan) {
		LoanInformationDto info = new LoanInformationDto();
		info.setId(loan.getId());
		info.setCompany(loan.getCompany());
		info.setBranch(loan.getBranch());
		info.setCode(loan.getCode());
		info.setProduct(loan.getProduct().getId());
		info.setSchema(loan.getProduct().getSchema());
		info.setProductCode(loan.getProduct().getCode());
		info.setProductName(loan.getProduct().getName());
		info.setContract(loan.getContract());
		info.setContractDate(loan.getContractDate());
		info.setCustomer(loan.getCustomer().getId());
		info.setName(loan.getCustomer().getName());
		info.setAddress(loan.getCustomer().getAddress() + " - "
				+ loan.getCustomer().getVillage() + " - "
				+ loan.getCustomer().getDistrict());
		info.setCity(loan.getCustomer().getCity());
		info.setPostCode(loan.getCustomer().getPostCode());
		info.setPhone(loan.getCustomer().getPhone());
		info.setHandphone(loan.getCustomer().getHandphone());
		info.setPrincipal(loan.getPrincipal());
		info.setMargin(loan.getMargin());
		info.setBegin(loan.getContractDate());
		Date end = getLoanEnd(loan.getId());
		info.setEnd(end);
		info.setSaving(loan.getSaving().getId());
		info.setCoa1(loan.getProduct().getCoa1());
		info.setCoa2(loan.getProduct().getCoa2());
		info.setCoa3(loan.getProduct().getCoa3());
		info.setCoa4(loan.getProduct().getCoa4());
		info.setCoa5(loan.getProduct().getCoa5());
		info.setAo(loan.getAo());
		return info;
	}

	@Override
	public LoanInformationDto getLoanInformation(long id) {
		Loan loan = em.find(Loan.class, id);
		return createLoanInformationToDto(loan);
	}

	@Override
	public void droppLoan(long id, int dropped) {
		Loan loan = em.find(Loan.class, id);
		loan.setDropped(dropped);
		em.persist(loan);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanScheduleDto> listAllLoanScheduleByRange(long company,
			Date beginDate, Date endDate) {
		List<LoanScheduleDto> result = new ArrayList<LoanScheduleDto>();
		Query qry = em.createNamedQuery("listAllLoanSchedulesByRange");
		qry.setParameter("company", company);
		qry.setParameter("beginDate", beginDate);
		qry.setParameter("endDate", endDate);
		List<LoanSchedule> list = qry.getResultList();
		for (LoanSchedule schedule : list) {
			LoanScheduleDto scheduleDto = new LoanScheduleDto();
			scheduleDto.setId(schedule.getId());
			scheduleDto.setDate(schedule.getDate());
			scheduleDto.setPrincipal(schedule.getPrincipal());
			scheduleDto.setMargin(schedule.getMargin());
			scheduleDto.setTotal(schedule.getTotal());
			scheduleDto.setLoan(schedule.getLoan().getId());
			result.add(scheduleDto);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanScheduleDto> listLoanScheduleByRange(long id,
			Date beginDate, Date endDate) {
		List<LoanScheduleDto> result = new ArrayList<LoanScheduleDto>();
		Query qry = em.createNamedQuery("listLoanScheduleByRange");
		qry.setParameter("id", id);
		qry.setParameter("beginDate", beginDate);
		qry.setParameter("endDate", endDate);
		List<LoanSchedule> list = qry.getResultList();
		for (LoanSchedule schedule : list) {
			LoanScheduleDto scheduleDto = new LoanScheduleDto();
			scheduleDto.setId(schedule.getId());
			scheduleDto.setDate(schedule.getDate());
			scheduleDto.setPrincipal(schedule.getPrincipal());
			scheduleDto.setMargin(schedule.getMargin());
			scheduleDto.setTotal(schedule.getTotal());
			scheduleDto.setLoan(schedule.getLoan().getId());
			result.add(scheduleDto);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanTransactionDto> listAllLoanTransactionByRange(long company,
			long branch, int direction, Date beginDate, Date endDate) {
		List<LoanTransactionDto> result = new ArrayList<LoanTransactionDto>();
		String strQry = "listAllLoanTransactionByRange"
				+ (branch == 0 ? "1" : "2");
		Query qry = em.createNamedQuery(strQry);
		qry.setParameter("direction", direction);
		qry.setParameter("beginDate", beginDate);
		qry.setParameter("endDate", endDate);
		if (branch == 0) {
			qry.setParameter("company", company);
		} else {
			qry.setParameter("branch", branch);
		}
		List<LoanTransaction> list = qry.getResultList();
		for (LoanTransaction trans : list) {
			result.add(createLoanTransactionToDto(trans));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanScheduleDto> listLoanRepaymentRange(long loanId,
			Date startDate, Date endDate) {
		List<LoanScheduleDto> result = new ArrayList<LoanScheduleDto>();
		Query qry = em.createNamedQuery("listLoanRepaymentByRange");
		qry.setParameter("id", loanId);
		qry.setParameter("startDate", startDate);
		qry.setParameter("endDate", endDate);
		List<LoanTransaction> list = qry.getResultList();
		for (LoanTransaction trans : list) {
			LoanScheduleDto scheduleDto = new LoanScheduleDto();
			scheduleDto.setDate(trans.getDate());
			scheduleDto.setPrincipal(trans.getPrincipal());
			scheduleDto.setMargin(trans.getMargin());
			scheduleDto.setTotal(trans.getPrincipal() + trans.getMargin());
			result.add(scheduleDto);
		}
		return result;
	}

	@Override
	public LoanTransactionDto getSumLoanTransaction(long id, int direction,
			Date date) {
		LoanTransactionDto trans = new LoanTransactionDto();
		Query qry = em.createNamedQuery("getSumLoanTransaction");
		qry.setParameter("loanId", id);
		qry.setParameter("direction", direction);
		qry.setParameter("date", date);
		try {
			Object[] result = (Object[]) qry.getSingleResult();
			if (result != null) {
				long loan = (Long) result[0];
				double principal = (result[1] != null) ? (Double) result[1] : 0;
				double margin = (result[2] != null) ? (Double) result[2] : 0;
				trans.setLoan(loan);
				trans.setPrincipal(principal);
				trans.setMargin(margin);
			}
		} catch (Exception e) {
			return trans;
		}
		return trans;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanScheduleDto> listLoanBill(long company, Date endDate) {
		List<LoanScheduleDto> result = new ArrayList<LoanScheduleDto>();
		Query qry = em.createNamedQuery("listLoanBill");
		qry.setParameter("company", company);
		qry.setParameter("date", endDate);
		List<LoanSchedule> list = qry.getResultList();
		for (LoanSchedule schedule : list) {
			LoanScheduleDto scheduleDto = new LoanScheduleDto();
			scheduleDto.setId(schedule.getId());
			scheduleDto.setDate(schedule.getDate());
			scheduleDto.setPrincipal(schedule.getPrincipal());
			scheduleDto.setMargin(schedule.getMargin());
			scheduleDto.setTotal(schedule.getTotal());
			scheduleDto.setLoan(schedule.getLoan().getId());
			result.add(scheduleDto);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	List<LoanSchedule> listSchedule(long id) {
		Query qry = em.createNamedQuery("listLoanSchedule");
		qry.setParameter("id", id);
		List<LoanSchedule> list = qry.getResultList();
		return list;
	}

	@Override
	public List<LoanScheduleDto> listLoanSchedule(long id) {
		List<LoanScheduleDto> result = new ArrayList<LoanScheduleDto>();
		List<LoanSchedule> list = listSchedule(id);
		for (LoanSchedule schedule : list) {
			LoanScheduleDto scheduleDto = new LoanScheduleDto();
			scheduleDto.setId(schedule.getId());
			scheduleDto.setDate(schedule.getDate());
			scheduleDto.setPrincipal(schedule.getPrincipal());
			scheduleDto.setMargin(schedule.getMargin());
			scheduleDto.setTotal(schedule.getTotal());
			scheduleDto.setLoan(schedule.getLoan().getId());
			result.add(scheduleDto);
		}
		return result;
	}

	@Override
	public void setLoanSchedulePayd(long id, int paid) {
		LoanSchedule sched = em.find(LoanSchedule.class, id);
		sched.setPaid(paid);
		em.persist(sched);
	}

	@Override
	public void payLoanScheduleBulk(long idLoan, long maxSched) {
		Query qry = em.createNamedQuery("payLoanScheduleBulk");
		qry.setParameter("idLoan", idLoan);
		qry.setParameter("maxSched", maxSched);
		qry.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDto> listLoanCifNotRegistered(long company,
			long branch, Date date) {
		List<CustomerDto> result = new ArrayList<CustomerDto>();
		String strQry = (branch == 0) ? "listLoanCifNR1" : "listLoanCifNR2";
		Query qry = em.createNamedQuery(strQry);
		if (branch == 0) {
			qry.setParameter("company", company);
		} else {
			qry.setParameter("branch", branch);
		}
		qry.setParameter("date", date);
		List<Customer> customers = qry.getResultList();
		for (Customer customer : customers) {
			result.add(CustomerHelper.createCustomerToDto(customer));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanTransInfoDto> listLoanTransInfo(long company, long branch,
			Date date) {
		List<LoanTransInfoDto> result = new ArrayList<LoanTransInfoDto>();
		String strQry = (branch == 0) ? "listLoanPayment1" : "listLoanPayment2";
		Query qry = em.createNamedQuery(strQry);
		qry.setParameter("date", date);
		if (branch == 0) {
			qry.setParameter("company", company);
		} else {
			qry.setParameter("branch", branch);
		}
		List<Object[]> loans = qry.getResultList();
		for (Object[] loan : loans) {
			long id = (Long) loan[0];
			String code = (String) loan[1];
			double principal = (Double) loan[2];
			double margin = (Double) loan[3];
			double discount = (Double) loan[4];
			LoanTransInfoDto info = new LoanTransInfoDto();
			LoanInformationDto loanInfo = getLoanInformation(id);
			Date maxDate = getLoanEnd(id);
			info.setId(id);
			info.setCode(code);
			info.setContract(loanInfo.getContract());
			info.setBegin(loanInfo.getBegin());
			info.setEnd(maxDate);
			info.setPaidPrincipal(principal);
			info.setPaidMargin(margin);
			info.setPaidDiscount(discount);
			info.setCity(info.getCity());
			result.add(info);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanTransInfoDto> listSimpleLoanTransInfo(long company,
			long branch, Date date) {
		List<LoanTransInfoDto> result = new ArrayList<LoanTransInfoDto>();
		String strQry = (branch == 0) ? "listLoanPayment1" : "listLoanPayment2";
		Query qry = em.createNamedQuery(strQry);
		qry.setParameter("date", date);
		if (branch == 0) {
			qry.setParameter("company", company);
		} else {
			qry.setParameter("branch", branch);
		}
		List<Object[]> loans = qry.getResultList();
		for (Object[] loan : loans) {
			long id = (Long) loan[0];
			String code = (String) loan[1];
			double principal = (Double) loan[2];
			double margin = (Double) loan[3];
			double discount = (Double) loan[4];
			LoanTransInfoDto info = new LoanTransInfoDto();
			info.setId(id);
			info.setCode(code);
			info.setPaidPrincipal(principal);
			info.setPaidMargin(margin);
			info.setPaidDiscount(discount);
			result.add(info);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanTransInfoDto> listLoanTransSumGroup(long company,
			long branch, int direction, Date beginDate, Date endDate) {
		List<LoanTransInfoDto> result = new ArrayList<LoanTransInfoDto>();
		String strQry = (branch == 0) ? "listSumLoanTransGroup1"
				: "listSumLoanTransGroup2";
		Query qry = em.createNamedQuery(strQry);
		qry.setParameter("beginDate", beginDate);
		qry.setParameter("endDate", endDate);
		qry.setParameter("direction", direction);
		if (branch == 0) {
			qry.setParameter("company", company);
		} else {
			qry.setParameter("branch", branch);
		}
		List<Object[]> loans = qry.getResultList();
		for (Object[] loan : loans) {
			long product = (Long) loan[0];
			double principal = (Double) loan[1];
			double margin = (Double) loan[2];
			double discount = (Double) loan[3];
			LoanTransInfoDto info = new LoanTransInfoDto();
			info.setProduct(product);
			LoanProduct productInfo = em.find(LoanProduct.class, product);
			info.setProductCode(productInfo.getCode());
			info.setProductName(productInfo.getName());
			info.setPaidPrincipal(principal);
			info.setPaidMargin(margin);
			info.setPaidDiscount(discount);
			result.add(info);
		}
		return result;
	}

	@Override
	public Date getLoanScheduleBegin(long id) {
		Query qry = em.createNamedQuery("minLoanSchedule");
		qry.setParameter("id", id);
		Date date = (Date) qry.getSingleResult();
		return date;
	}

	@Override
	public Date getLoanEnd(long id) {
		Query qry = em.createNamedQuery("maxLoanSchedule");
		qry.setParameter("id", id);
		Date date = (Date) qry.getSingleResult();
		return date;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanInformationDto> listLoanInfo(long company, long branch,
			Date date) {
		List<LoanInformationDto> result = new ArrayList<LoanInformationDto>();
		String strQry = (branch == 0) ? "listLoanInfo1" : "listLoanInfo2";
		Query qry = em.createNamedQuery(strQry);
		qry.setParameter("date", date);
		if (branch == 0) {
			qry.setParameter("company", company);
		} else {
			qry.setParameter("branch", branch);
		}
		List<Loan> loans = qry.getResultList();
		// System.out.println("Jumlah data : " + loans.size());
		for (int i = 0; i < loans.size(); i++) {
			// System.out.print(i + " ");
			result.add(createLoanInformationToDto(loans.get(i)));
		}
		// for (Loan loan : loans) {
		// System.out.print(".");
		// result.add(createLoanInformationToDto(loan));
		// }
		// System.out.println("Jumlah data : " + result.size());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanInformationDto> listSimpleLoanInfo(long company,
			long branch, Date date) {
		List<LoanInformationDto> result = new ArrayList<LoanInformationDto>();
		String strQry = (branch == 0) ? "listLoanInfo1" : "listLoanInfo2";
		Query qry = em.createNamedQuery(strQry);
		qry.setParameter("date", date);
		if (branch == 0) {
			qry.setParameter("company", company);
		} else {
			qry.setParameter("branch", branch);
		}
		List<Loan> loans = qry.getResultList();
		for (Loan loan : loans) {
			result.add(createSimpleLoanInformationToDto(loan));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	List<LoanSchedule> listScheduleUntil(long id, Date date) {
		Query qry = em.createNamedQuery("listLoanScheduleUntil");
		qry.setParameter("id", id);
		qry.setParameter("date", date);
		List<LoanSchedule> result = qry.getResultList();
		return result;
	}

	@Override
	public LoanQualityDto getLoanQuality(long id, int schema, Date date,
			double principle, double margin) {
		LoanQualityDto q = new LoanQualityDto(id);
		Date minSched = getLoanScheduleBegin(id);
		Date maxSched = getLoanEnd(id);
		// Date lastSched = maxSched;
		//
		if (maxSched == null || date.before(minSched)) {
			q.setValue(1);
			q.setOsPrincipal(0);
			q.setOsMargin(0);
			q.setDueOs(0);
			q.setLastPaid(date);
			return q;
		}
		//
		List<LoanSchedule> list = listScheduleUntil(id, date);

		double osPrincipal = 0;
		double osMargin = 0;
		double myPrincipal = principle;
		double myMargin = margin;
		int telat = 0;
		for (LoanSchedule sched : list) {
			// Apakah pokok masih bisa nutup kebutuhan angsuran
			double schedTotal = sched.getPrincipal()
					+ (schema == 5 || schema == 6 ? 0 : sched.getMargin());
			double myTotal = myPrincipal
					+ (schema == 5 || schema == 6 ? 0 : myMargin);
			if (schedTotal - myTotal <= 0.01) {
				// kalo bisa memenuhi
				double myPayment = myPrincipal;
				if (sched.getPrincipal() > 0.01) {
					myPrincipal -= sched.getPrincipal();
				}
				if (sched.getMargin() > 0.01) {
					myMargin -= sched.getMargin();
				}
				// System.out.println("Tanggal:" + sched.getDate()
				// + ", mPrincipal=" + myPrincipal + ", mMargin"
				// + myMargin);
				q.setLastPaid(sched.getDate());
				if (myPrincipal < 0) {
					if (telat == 0) {
						q.setLastPaid(sched.getDate());
					}
					if (myPayment < 0.01)
						telat++;
					osPrincipal += sched.getPrincipal();
					osMargin += sched.getMargin();
				}
			} else {
				// kalo tidak bisa memenuhi
				if (telat == 0) {
					q.setLastPaid(sched.getDate());
				}
				// lihat apakah dia masih punya sisa pokok
				// System.out
				// .println("Tanggal:" + sched.getDate() + "stotal="
				// + schedTotal + ", mtotal" + myTotal + ", telat"
				// + telat);
				if (myTotal > 0.01) {
					osPrincipal += sched.getPrincipal() - myPrincipal;
					osMargin += sched.getMargin()
							- (schema == 5 || schema == 6 ? 0 : myMargin);
					myPrincipal = 0;
					myMargin = 0;
				} else {
					telat++;
					osPrincipal += sched.getPrincipal();
					osMargin += sched.getMargin();
				}
			}
			// lastSched = sched.getDate();
		}
		// System.out.println("telat=" + telat);
		int duration = daysBetween(q.getLastPaid(), date);
		q.setDuration(duration);
		q.setDueOs(telat);
		q.setOsPrincipal(osPrincipal);
		q.setOsMargin(osMargin);
		//
		// Kalo dah jatuh tempo
		if (date.after(maxSched)) {
			// Kol = 4
			q.setValue(4);
			// Untuk murabahah dan multijasa agak lain
			if (schema < 5) {
				//
				int col = 2;
				DateTime dateTime = new DateTime(date);
				DateTime dt = new DateTime(maxSched);
				dt = dt.plusMonths(1);
				if (dt.isBefore(dateTime)) {
					col++;
					dt = dt.plusMonths(1);
					if (dt.isBefore(dateTime)) {
						col++;
					}
				}
				if (telat >= 12 && col < 4) {
					col = 4;
				} else if (telat >= 6 && col < 3) {
					col = 3;
				}
				q.setValue(col);
			}
		} else {
			// Perhitungan tidak jatuh tempo
			if (telat >= 12) {
				q.setValue(4);
			} else if (telat >= 6) {
				q.setValue(3);
			} else if (telat >= 3) {
				q.setValue(2);
			}
		}
		//
		if (q.getValue() == 0)
			q.setValue(1);
		return q;
	}

	@Override
	public void resetLoanSchedulePayd(long company) {
		Query qry = em.createNamedQuery("resetLoanSchedule");
		qry.setParameter("company", company);
		qry.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GuaranteeDto> findGuarantee(FindLoanDto findLoan) {
		List<GuaranteeDto> result = new ArrayList<GuaranteeDto>();
		String strQry = "select x from Guarantee x where x.company=:company ";
		if (findLoan.getBranch() != 0) {
			strQry += "and x.branch=:branch ";
		}
		if (findLoan.isWithCode()) {
			strQry += "and x.code=:code ";
		}
		if (findLoan.isWithName()) {
			strQry += "and upper(x.customer.name) like :name ";
		}
		Query qry = em.createQuery(strQry);
		qry.setParameter("company", findLoan.getCompany());
		if (findLoan.getBranch() != 0) {
			qry.setParameter("branch", findLoan.getBranch());
		}
		if (findLoan.isWithCode()) {
			qry.setParameter("code", findLoan.getCode());
		}
		if (findLoan.isWithName()) {
			String name = findLoan.getName().toUpperCase();
			if (name.isEmpty()) {
				name = "---";
			} else {
				name = "%" + name + "%";
			}
			qry.setParameter("name", name);
		}
		List<Guarantee> guarantees = qry.getResultList();
		for (Guarantee guarantee : guarantees) {
			result.add(createGuaranteeToDto(guarantee));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isDepositAsGuarantee(long company, String code) {
		Query qry = em.createNamedQuery("listDepositGuarantee");
		qry.setParameter("company", company);
		qry.setParameter("number", code);
		List<Guarantee> guarantees = qry.getResultList();
		return guarantees.size() > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LoanTransInfoDto getLoanTransInfo(long id, Date date) {
		Query qry = em.createNamedQuery("getLoanPayment");
		qry.setParameter("id", id);
		qry.setParameter("date", date);
		List<Object[]> loans = qry.getResultList();
		if (loans.size() > 0) {
			Object[] loan = loans.get(0);
			String code = (String) loan[1];
			double principal = (Double) loan[2];
			double margin = (Double) loan[3];
			double discount = (Double) loan[4];
			//
			LoanTransInfoDto info = new LoanTransInfoDto();
			LoanInformationDto loanInfo = getLoanInformation(id);
			Date maxDate = getLoanEnd(id);
			info.setId(id);
			info.setCode(code);
			info.setContract(loanInfo.getContract());
			info.setContractDate(loanInfo.getContractDate());
			info.setBegin(loanInfo.getBegin());
			info.setEnd(maxDate);
			info.setPaidPrincipal(principal);
			info.setPaidMargin(margin);
			info.setPaidDiscount(discount);
			info.setCity(info.getCity());
			return info;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> listAoLoanId(FindLoanDto findLoanDto) {
		Query qry = null;
		if (findLoanDto.getBranch() == 0) {
			qry = em.createNamedQuery("listAoLoanByCompany");
			qry.setParameter("active", findLoanDto.getActive());
			qry.setParameter("company", findLoanDto.getCompany());
		} else {
			qry = em.createNamedQuery("listAoLoanByCompanyBranch");
			qry.setParameter("active", findLoanDto.getActive());
			qry.setParameter("company", findLoanDto.getCompany());
			qry.setParameter("branch", findLoanDto.getBranch());
		}
		List<Long> result = qry.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long getLoanIdByCode(long company, String code, int active) {
		Query qry = em.createNamedQuery(active < 2 ? "getLoanByCode2"
				: "getLoanByCode1");
		qry.setParameter("company", company);
		qry.setParameter("code", code);
		if (active < 2) {
			qry.setParameter("active", active);
		}
		List<Loan> loans = qry.getResultList();
		if (loans.size() > 0) {
			return loans.get(0).getId();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LoanScheduleDto getNormalRepayment(long id) {
		// Get end of month
		DateTime today = new DateTime().dayOfMonth().withMaximumValue();
		//
		LoanScheduleDto result = new LoanScheduleDto();
		Query qry = em.createNamedQuery("listLoanScheduleNotPaid");
		qry.setParameter("id", id);
		qry.setParameter("date", today.toDate());
		List<LoanSchedule> schedules = qry.getResultList();
		if (schedules.size() > 0) {
			double principal = 0;
			double margin = 0;
			Date lastDate = null;
			for (LoanSchedule schedule : schedules) {
				principal += schedule.getPrincipal();
				margin += schedule.getMargin();
				lastDate = schedule.getDate();
			}
			result.setPrincipal(principal);
			result.setMargin(margin);
			result.setDate(lastDate);
		} else {
			result.setPrincipal(0);
			result.setMargin(0);
			result.setDate(new Date());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LoanScheduleDto getEarlyRepayment(long id) {
		LoanScheduleDto result = new LoanScheduleDto();

		Loan loan = em.find(Loan.class, id);
		double principal = loan.getPrincipal();
		double margin = loan.getMargin();

		Query q = em.createNamedQuery("getSumLoanTransaction");
		q.setParameter("loanId", id);
		q.setParameter("direction", 2);
		q.setParameter("date", new Date());
		List<Object[]> res = (List<Object[]>) q.getResultList();
		double paydPrincipal = 0;
		double paydMargin = 0;
		if (res.size() > 0) {
			Object[] obj = res.get(0);
			paydPrincipal = (double) obj[1];
			paydMargin = (double) obj[2];
		}
		principal = principal - paydPrincipal;
		margin = margin - paydMargin;
		result.setPrincipal(principal);
		if (loan.getProduct().getSchema() == 5
				|| loan.getProduct().getSchema() == 6
				|| loan.getProduct().getSchema() == 7) {
			result.setMargin(0);
		} else {
			result.setMargin(margin);
		}
		result.setDate(new Date());

		// Query qry = em.createNamedQuery("listLoanScheduleNotPaid2");
		// qry.setParameter("id", id);
		// List<LoanSchedule> schedules = qry.getResultList();
		// if (schedules.size() > 0) {
		// int schema = loan.getProduct().getSchema();
		// double principal = 0;
		// double firstMargin = 0;
		// double margin = 0;
		// for (LoanSchedule schedule : schedules) {
		// principal += schedule.getPrincipal();
		// margin += schedule.getMargin();
		// if (firstMargin == 0) {
		// firstMargin = schedule.getMargin();
		// }
		// }
		// result.setPrincipal(principal);
		// result.setMargin(schema == 1 ? margin : firstMargin);
		// } else {
		// result.setPrincipal(0);
		// result.setMargin(0);
		// }

		// Query qry = em.createNamedQuery("listLoanSchedule");
		// qry.setParameter("id", id);
		// List<LoanSchedule> schedules = qry.getResultList();
		// double principal = 0;
		// double margin = 0;
		// if (schedules.size() > 0) {
		// for (LoanSchedule schedule : schedules) {
		// principal += schedule.getPrincipal();
		// margin += schedule.getMargin();
		// }
		// Query q = em.createNamedQuery("getSumLoanTransaction");
		// q.setParameter("loanId", id);
		// q.setParameter("direction", 2);
		// q.setParameter("date", new Date());
		// List<Object[]> res = (List<Object[]>) q.getResultList();
		// double paydPrincipal = 0;
		// double paydMargin = 0;
		// if (res.size() > 0) {
		// Object[] obj = res.get(0);
		// paydPrincipal = (double) obj[1];
		// paydMargin = (double) obj[2];
		// }
		// principal = principal - paydPrincipal;
		// margin = margin - paydMargin;
		// result.setPrincipal(principal);
		// result.setMargin(margin);
		// } else {
		// result.setPrincipal(0);
		// result.setMargin(0);
		// }
		// result.setDate(new Date());
		return result;
	}

	public int daysBetween(Date d1, Date d2) {
		if (d1 == null) {
			return 0;
		} else {
			return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public long getLoanTransIdByRef(long company, long branch, long refId) {
		Query qry = em.createNamedQuery("getLoanTransByRefId");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("refId", refId);
		List<LoanTransaction> listLoan = qry.getResultList();
		if (listLoan.size() > 0) {
			return listLoan.get(0).getId();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanScheduleDto> listRepaymentByRange(long id, Date beginDate,
			Date endDate) {
		List<LoanScheduleDto> result = new ArrayList<LoanScheduleDto>();
		Query qry = em.createNamedQuery("listLoanRepaymentByRange");
		qry.setParameter("id", id);
		qry.setParameter("beginDate", beginDate);
		qry.setParameter("endDate", endDate);
		List<LoanTransaction> scheds = qry.getResultList();
		for (LoanTransaction sched : scheds) {
			LoanScheduleDto dto = new LoanScheduleDto();
			dto.setId(sched.getId());
			dto.setDate(sched.getDate());
			dto.setPrincipal(sched.getPrincipal());
			dto.setMargin(sched.getMargin());
			dto.setTotal(sched.getPrincipal() + sched.getMargin());
			result.add(dto);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LoanScheduleDto getLastPayment(long id) {
		Query qry = em.createNamedQuery("listLastPayment");
		qry.setParameter("loanId", id);
		List<LoanTransaction> transList = qry.getResultList();
		if (transList.size() > 0) {
			LoanTransaction trans = transList.get(0);
			LoanScheduleDto result = new LoanScheduleDto();
			result.setDate(trans.getDate());
			result.setPrincipal(trans.getPrincipal());
			result.setMargin(trans.getMargin());
			return result;
		}
		return null;
	}

	@Override
	public List<GuaranteeDto> listLoanGuarantee(long loanId) {
		List<GuaranteeDto> result = new ArrayList<GuaranteeDto>();
		Loan loan = em.find(Loan.class, loanId);
		for (Guarantee gua : loan.getGuarantees()) {
			result.add(createGuaranteeToDto(gua));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GuaranteeDto> listGuaranteeByCode(long company, String code) {
		List<GuaranteeDto> result = new ArrayList<GuaranteeDto>();
		Query qry = em.createNamedQuery("listGuaranteeByCode");
		qry.setParameter("company", company);
		qry.setParameter("code", code);
		List<Guarantee> guarantees = qry.getResultList();
		for (Guarantee gua : guarantees) {
			result.add(createGuaranteeToDto(gua));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanInformationDto> listDroppedLoan(long company, long branch,
			Date beginDate, Date endDate) {
		List<LoanInformationDto> result = new ArrayList<LoanInformationDto>();
		Query qry = em.createNamedQuery(branch == 0 ? "listLoanDropping1"
				: "listLoanDropping2");
		qry.setParameter("beginDate", beginDate);
		qry.setParameter("endDate", endDate);
		if (branch == 0) {
			qry.setParameter("company", company);
		} else {
			qry.setParameter("branch", branch);
		}
		List<Loan> loans = qry.getResultList();
		for (Loan loan : loans) {
			LoanInformationDto item = new LoanInformationDto();
			item.setCode(loan.getCode());
			item.setName(loan.getCustomer().getName());
			item.setContractDate(loan.getContractDate());
			item.setPrincipal(loan.getPrincipal());
			item.setMargin(loan.getMargin());
			item.setAo(loan.getAo());
			result.add(item);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanDto> listLoanBySchema(long company, int schema) {
		List<LoanDto> result = new ArrayList<LoanDto>();
		Query qry = em.createNamedQuery("listLoanBySchema");
		qry.setParameter("company", company);
		qry.setParameter("schema", schema);
		List<Loan> loans = qry.getResultList();
		for (Loan loan : loans) {
			result.add(createLoanToDto(loan));

		}
		return result;
	}

}
