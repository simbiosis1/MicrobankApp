package org.simbiosis.microbank;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.simbiosis.microbank.model.Customer;
import org.simbiosis.microbank.model.Saving;
import org.simbiosis.microbank.model.SavingBlockir;
import org.simbiosis.microbank.model.SavingCode;
import org.simbiosis.microbank.model.SavingPrintCodeRef;
import org.simbiosis.microbank.model.SavingProduct;
import org.simbiosis.microbank.model.SavingTransaction;

@Stateless
@Remote(ISaving.class)
public class SavingImpl implements ISaving {

	@PersistenceContext(unitName = "MicrobankEjb", type = PersistenceContextType.TRANSACTION)
	EntityManager em;

	Saving createSavingFromDto(SavingDto dto) {
		Saving saving = new Saving();
		saving.setId(dto.getId());
		saving.setRefId(dto.getRefId());
		saving.setCode(dto.getCode());
		saving.setRefCode(dto.getRefCode());
		saving.setRegistration(dto.getRegistration());
		saving.setConfidential(dto.getConfidential());
		saving.setSpecialRate(dto.getSpecialRate());
		saving.setActive(dto.getActive());
		saving.setClosing(dto.getClosing());
		saving.setZakat(dto.getZakat());
		saving.setCompany(dto.getCompany());
		saving.setBranch(dto.getBranch());
		Customer customer = em.find(Customer.class, dto.getCustomer());
		saving.setCustomer(customer);
		SavingProduct product = em.find(SavingProduct.class, dto.getProduct());
		saving.setProduct(product);
		return saving;
	}

	SavingDto createSavingToDto(Saving saving) {
		SavingDto dto = new SavingDto();
		dto.setId(saving.getId());
		dto.setRefId(saving.getRefId());
		dto.setCode(saving.getCode());
		dto.setRefCode(saving.getRefCode());
		dto.setRegistration(saving.getRegistration());
		dto.setConfidential(saving.getConfidential());
		dto.setRate(saving.getProduct().getSharing());
		dto.setSpecialRate(saving.getSpecialRate());
		dto.setActive(saving.getActive());
		dto.setZakat(saving.getZakat());
		dto.setCompany(saving.getCompany());
		dto.setBranch(saving.getBranch());
		dto.setCustomer(saving.getCustomer().getId());
		dto.setProduct(saving.getProduct().getId());
		dto.setClosing(saving.getClosing());
		dto.setClosingReason(saving.getClosingReason());
		return dto;
	}

	SavingProduct createSavingProductFromDto(SavingProductDto dto) {
		SavingProduct product = new SavingProduct();
		product.setId(dto.getId());
		product.setRefId(dto.getRefId());
		product.setCode(dto.getCode());
		product.setName(dto.getName());
		product.setCompany(dto.getCompany());
		product.setSchema(dto.getSchema());
		product.setSharing(dto.getSharing());
		product.setHasShare(dto.getHasShare());
		product.setCoa1(dto.getCoa1());
		product.setCoa2(dto.getCoa2());
		product.setCoa3(dto.getCoa3());
		product.setCoa4(dto.getCoa4());
		product.setMinValue(dto.getMinValue());
		product.setMinSharable(dto.getMinSharable());
		product.setCloseAdmin(dto.getCloseAdmin());
		product.setMonthlyAdmin(dto.getMonthlyAdmin());
		return product;
	}

	SavingProductDto createSavingProductToDto(SavingProduct product) {
		SavingProductDto dto = new SavingProductDto();
		dto.setId(product.getId());
		dto.setRefId(product.getRefId());
		dto.setCode(product.getCode());
		dto.setName(product.getName());
		dto.setCompany(product.getCompany());
		dto.setSchema(product.getSchema());
		dto.setSharing(product.getSharing());
		dto.setHasShare(product.getHasShare());
		dto.setCoa1(product.getCoa1());
		dto.setCoa2(product.getCoa2());
		dto.setCoa3(product.getCoa3());
		dto.setCoa4(product.getCoa4());
		dto.setMinValue(product.getMinValue());
		dto.setMinSharable(product.getMinSharable());
		dto.setCloseAdmin(product.getCloseAdmin());
		dto.setMonthlyAdmin(product.getMonthlyAdmin());
		return dto;
	}

	SavingTransaction createSavingTransactionFromDto(SavingTransactionDto dto) {
		SavingTransaction trans = new SavingTransaction();
		trans.setId(dto.getId());
		trans.setRefId(dto.getRefId());
		trans.setCode(dto.getCode());
		trans.setRefCode(dto.getRefCode());
		trans.setDate(dto.getDate());
		trans.setTimestamp(dto.getTimestamp());
		trans.setDescription(dto.getDescription());
		trans.setDirection(dto.getDirection());
		trans.setType(dto.getType());
		trans.setValue(dto.getValue());
		Saving saving = em.find(Saving.class, dto.getSaving());
		trans.setSaving(saving);
		trans.setCompany(dto.getCompany());
		trans.setBranch(dto.getBranch());
		return trans;
	}

	SavingTransactionDto createSavingTransactionToDto(SavingTransaction trans) {
		SavingTransactionDto dto = new SavingTransactionDto();
		dto.setId(trans.getId());
		dto.setRefId(trans.getRefId());
		dto.setCode(trans.getCode());
		dto.setRefCode(trans.getRefCode());
		dto.setDate(trans.getDate());
		dto.setTimestamp(trans.getTimestamp());
		dto.setDescription(trans.getDescription());
		dto.setDirection(trans.getDirection());
		dto.setType(trans.getType());
		dto.setValue(trans.getValue());
		dto.setSaving(trans.getSaving().getId());
		dto.setCompany(trans.getCompany());
		dto.setBranch(trans.getBranch());
		return dto;
	}

	@Override
	public long saveProduct(SavingProductDto dto) {
		SavingProduct product = createSavingProductFromDto(dto);
		if (dto.getId() == 0) {
			em.persist(product);
		} else {
			em.merge(product);
		}
		return product.getId();
	}

	public SavingProductDto getProduct(long id) {
		SavingProduct savingProduct = em.find(SavingProduct.class, id);
		return createSavingProductToDto(savingProduct);
	}

	@SuppressWarnings("unchecked")
	List<SavingProduct> _listSavingProduct(long company) {
		Query qry = em.createNamedQuery("listSavingProduct");
		qry.setParameter("company", company);
		List<SavingProduct> result = qry.getResultList();
		return result;
	}

	@Override
	public List<SavingProductDto> listProduct(long company) {
		List<SavingProductDto> productList = new ArrayList<SavingProductDto>();
		for (SavingProduct product : _listSavingProduct(company)) {
			productList.add(createSavingProductToDto(product));
		}
		return productList;
	}

	@Override
	public long save(SavingDto savingDto) {
		Saving saving = createSavingFromDto(savingDto);
		if (savingDto.getId() == 0) {
			em.persist(saving);
		} else {
			em.merge(saving);
		}
		return saving.getId();
	}

	@Override
	public SavingDto get(long id) {
		Saving saving = em.find(Saving.class, id);
		return createSavingToDto(saving);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> listSavingId(FindSavingDto findSavingDto, Date date) {
		Query qry = null;
		if (findSavingDto.getBranch() == 0) {
			qry = em.createNamedQuery("listSavingIdByCompany");
			qry.setParameter("active", findSavingDto.getActive());
			qry.setParameter("company", findSavingDto.getCompany());
			qry.setParameter("date", date);
		} else {
			qry = em.createNamedQuery("listSavingIdByBranch");
			qry.setParameter("active", findSavingDto.getActive());
			qry.setParameter("branch", findSavingDto.getBranch());
			qry.setParameter("date", date);
		}
		List<Long> result = qry.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SavingDto> list(FindSavingDto findSavingDto) {
		List<SavingDto> savingList = new ArrayList<SavingDto>();
		Query qry = null;
		if (findSavingDto.getBranch() == 0) {
			qry = em.createNamedQuery("listSavingByCompany");
			qry.setParameter("company", findSavingDto.getCompany());
		} else {
			qry = em.createNamedQuery("listSavingByCompanyBranch");
			qry.setParameter("company", findSavingDto.getCompany());
			qry.setParameter("branch", findSavingDto.getBranch());
		}
		List<Saving> result = qry.getResultList();
		for (Saving saving : result) {
			savingList.add(createSavingToDto(saving));
		}
		return savingList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SavingDto> find(FindSavingDto findSavingDto) {
		List<SavingDto> savingList = new ArrayList<SavingDto>();
		String strQuery = "select x from Saving x where x.active=1 and x.company=:company ";
		if (findSavingDto.getBranch() != 0) {
			strQuery += "and x.branch=:branch ";
		}
		if (findSavingDto.isWithName()) {
			strQuery += "and upper(x.customer.name) like :name ";
		}
		if (findSavingDto.isWithCode()) {
			strQuery += "and x.code=:code ";
		}
		if (findSavingDto.isWithDob()) {
			strQuery += "and x.customer.dob=:dob ";
		}
		if (findSavingDto.getConfidential() == 0) {
			strQuery += "and x.confidential=0 ";
		}
		Query qry = em.createQuery(strQuery);
		qry.setParameter("company", findSavingDto.getCompany());
		if (findSavingDto.getBranch() != 0) {
			qry.setParameter("branch", findSavingDto.getBranch());
		}
		if (findSavingDto.isWithCode()) {
			qry.setParameter("code", findSavingDto.getCode());
		}
		if (findSavingDto.isWithName()) {
			String name = findSavingDto.getName().toUpperCase();
			if (name.isEmpty()) {
				name = "---";
			} else {
				name = "%" + name + "%";
			}
			qry.setParameter("name", name);
		}
		if (findSavingDto.isWithDob()) {
			qry.setParameter("dob", findSavingDto.getDob());
		}
		List<Saving> result = qry.getResultList();
		for (Saving saving : result) {
			savingList.add(createSavingToDto(saving));
		}
		return savingList;
	}

	@Override
	public String getMaxTransCode(long company, long branch, String prefixCode) {
		Query qry = em.createNamedQuery("getMaxSavingTransCode");
		qry.setParameter("prefixCode", prefixCode + "%");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		String code = (String) qry.getSingleResult();
		return code;
	}

	@Override
	public long saveTransaction(SavingTransactionDto savingTransactionDto) {
		SavingTransaction savingTransaction = createSavingTransactionFromDto(savingTransactionDto);
		if (savingTransactionDto.getId() == 0) {
			em.persist(savingTransaction);
		} else {
			em.merge(savingTransaction);
		}
		return savingTransaction.getId();
	}

	@Override
	public SavingTransactionDto getTransaction(long id) {
		SavingTransaction savingTransaction = em.find(SavingTransaction.class,
				id);
		return createSavingTransactionToDto(savingTransaction);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SavingTransactionDto> listTransUntil(long id, Date date) {
		List<SavingTransactionDto> result = new ArrayList<SavingTransactionDto>();
		Query qry = em.createNamedQuery("listSavingTransUntil");
		qry.setParameter("id", id);
		qry.setParameter("date", date);
		List<SavingTransaction> listSavingTrans = qry.getResultList();
		for (SavingTransaction trans : listSavingTrans) {
			result.add(createSavingTransactionToDto(trans));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SavingTransactionDto> listTransFrom(long id, Date date) {
		List<SavingTransactionDto> result = new ArrayList<SavingTransactionDto>();
		Query qry = em.createNamedQuery("listSavingTransFrom");
		qry.setParameter("id", id);
		qry.setParameter("date", date);
		List<SavingTransaction> listSavingTrans = qry.getResultList();
		for (SavingTransaction trans : listSavingTrans) {
			result.add(createSavingTransactionToDto(trans));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SavingDto getByCode(long company, String code) {
		Query qry = em.createNamedQuery("getSavingByCode");
		qry.setParameter("company", company);
		qry.setParameter("code", code);
		List<Saving> listSaving = qry.getResultList();
		if (listSaving.size() > 0) {
			return createSavingToDto(listSaving.get(0));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SavingDto getByRefId(long company, long branch, long refId) {
		Query qry = em.createNamedQuery("getSavingByRefId");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("refId", refId);
		List<Saving> listSaving = qry.getResultList();
		if (listSaving.size() > 0) {
			return createSavingToDto(listSaving.get(0));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long isExistByRefId(long company, long branch, long refId) {
		Query qry = em.createNamedQuery("getSavingByRefId");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("refId", refId);
		List<Saving> listSaving = qry.getResultList();
		if (listSaving.size() > 0) {
			return listSaving.get(0).getId();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long isExistByRefId(long company, long refId) {
		Query qry = em.createNamedQuery("getAllSavingByRefId");
		qry.setParameter("company", company);
		qry.setParameter("refId", refId);
		List<Saving> listSaving = qry.getResultList();
		if (listSaving.size() > 0) {
			return listSaving.get(0).getId();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long isProductExistByRefId(long company, long refId) {
		Query qry = em.createNamedQuery("getSavingProductByRefId");
		qry.setParameter("company", company);
		qry.setParameter("refId", refId);
		List<SavingProduct> listSavingProduct = qry.getResultList();
		if (listSavingProduct.size() > 0) {
			return listSavingProduct.get(0).getId();
		}
		return 0;
	}

	@Override
	public void close(long id, Date closing, String closingReason) {
		Saving saving = em.find(Saving.class, id);
		saving.setActive(0);
		saving.setClosing(closing);
		saving.setClosingReason(closingReason);
		em.persist(saving);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SavingDto> listByCustomer(long company, long idCustomer) {
		List<SavingDto> savingList = new ArrayList<SavingDto>();
		Query qry = em.createNamedQuery("listSavingByCustomer");
		qry.setParameter("company", company);
		qry.setParameter("idCustomer", idCustomer);
		List<Saving> result = qry.getResultList();
		for (Saving saving : result) {
			savingList.add(createSavingToDto(saving));
		}
		return savingList;
	}

	@Override
	public SavingInformationDto getInformation(long id) {
		Saving saving = em.find(Saving.class, id);
		SavingInformationDto info = new SavingInformationDto();
		info.setId(saving.getId());
		info.setCompany(saving.getCompany());
		info.setBranch(saving.getBranch());
		info.setCode(saving.getCode());
		info.setBegin(saving.getRegistration());
		info.setProduct(saving.getProduct().getId());
		info.setSchema(saving.getProduct().getSchema());
		info.setProductCode(saving.getProduct().getCode());
		info.setProductName(saving.getProduct().getName());
		info.setCustomer(saving.getCustomer().getId());
		info.setName(saving.getCustomer().getName());
		info.setCity(saving.getCustomer().getCity());
		info.setSharing(saving.getProduct().getSharing());
		info.setHasShare(saving.getProduct().getHasShare());
		info.setMinSharable(saving.getProduct().getMinSharable());
		info.setOnClose(saving.getOnClose());
		info.setCoa1(saving.getProduct().getCoa1());
		info.setCoa2(saving.getProduct().getCoa2());
		info.setCoa3(saving.getProduct().getCoa3());
		info.setZakat(saving.getZakat());
		return info;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getTotalTransBeforeNuc(long id) {
		Query qry = em
				.createQuery("select max(x.id) from SavingTransaction x where x.saving.id=:id and x.nuc<>0");
		qry.setParameter("id", id);
		Long transId = (Long) qry.getSingleResult();
		qry = em.createNamedQuery("getTotalSavingTransBeforeTransId");
		qry.setParameter("savingId", id);
		qry.setParameter("transId", transId);
		List<Object[]> result = qry.getResultList();
		return result;
	}

	/*
	 * @Override public double getTotalTransDay(long id, int direction, Date
	 * date) { Query qry = em.createNamedQuery("getTotalSavingTransDay");
	 * qry.setParameter("savingId", id); qry.setParameter("direction",
	 * direction); qry.setParameter("date", date); Double result = (Double)
	 * qry.getSingleResult(); if (result == null) { result = 0D; } return
	 * result; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<SavingTransactionDto> listTransWithoutNuc(long id) {
		Query qry = em
				.createQuery("select max(x.id) from SavingTransaction x where x.saving.id=:id and x.nuc<>0");
		qry.setParameter("id", id);
		Long transId = (Long) qry.getSingleResult();
		if (transId == null)
			transId = 0L;
		//
		List<SavingTransactionDto> result = new ArrayList<SavingTransactionDto>();
		qry = em.createNamedQuery("listSavingTransWithoutNuc");
		qry.setParameter("id", id);
		qry.setParameter("transId", transId);
		List<SavingTransaction> listSavingTrans = qry.getResultList();
		for (SavingTransaction trans : listSavingTrans) {
			result.add(createSavingTransactionToDto(trans));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> listIdTransacting(FindSavingDto findSavingDto, Date date) {
		Query qry = em.createNamedQuery("listSavingIdTransacting");
		qry.setParameter("company", findSavingDto.getCompany());
		qry.setParameter("date", date);
		List<Long> result = qry.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SavingTransactionDto> listTransRange(long id, Date beginDate,
			Date endDate) {
		List<SavingTransactionDto> result = new ArrayList<SavingTransactionDto>();
		Query qry = em.createNamedQuery("listSavingTransRange");
		qry.setParameter("id", id);
		qry.setParameter("beginDate", beginDate);
		qry.setParameter("endDate", endDate);
		List<SavingTransaction> listSavingTrans = qry.getResultList();
		for (SavingTransaction trans : listSavingTrans) {
			result.add(createSavingTransactionToDto(trans));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SavingTransactionDto> listTrans(long company, Date beginDate,
			Date endDate) {
		List<SavingTransactionDto> result = new ArrayList<SavingTransactionDto>();
		Query qry = em.createNamedQuery("listSavingTrans");
		qry.setParameter("company", company);
		qry.setParameter("beginDate", beginDate);
		qry.setParameter("endDate", endDate);
		List<SavingTransaction> listSavingTrans = qry.getResultList();
		for (SavingTransaction trans : listSavingTrans) {
			result.add(createSavingTransactionToDto(trans));
		}
		return result;
	}

	@Override
	public void saveNUC(long id, int nuc) {
		SavingTransaction trans = em.find(SavingTransaction.class, id);
		trans.setNuc(nuc);
		em.persist(trans);
	}

	@Override
	public void setupAllNUCBefore(long id, Date date) {
		Query qry = em
				.createQuery("update SavingTransaction x set x.nuc=1 where x.saving.id=:id and x.date<:date");
		qry.setParameter("id", id);
		qry.setParameter("date", date);
		qry.executeUpdate();
	}

	@Override
	public int getLastNuc(long id) {
		int nuc = 0;
		Query qry = em
				.createQuery("select max(x.id) from SavingTransaction x where x.saving.id=:id and x.nuc<>0");
		qry.setParameter("id", id);
		Long idFound = (Long) qry.getSingleResult();
		if (idFound != null) {
			qry = em.createQuery("select x.nuc from SavingTransaction x where x.id=:id");
			qry.setParameter("id", idFound);
			nuc = (Integer) qry.getSingleResult();
		}
		return nuc;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SavingTransInfoDto> listBallance(long company, long branch,
			Date date) {
		// Ambil product
		Map<Long, SavingProduct> productMap = new HashMap<Long, SavingProduct>();
		for (SavingProduct product : _listSavingProduct(company)) {
			productMap.put(product.getId(), product);
		}
		//
		Map<Long, SavingTransInfoDto> savingMap = new HashMap<Long, SavingTransInfoDto>();
		//
		String strQry = (branch == 0) ? "listSavingBallance1"
				: "listSavingBallance2";
		Query qry = em.createNamedQuery(strQry);
		qry.setParameter("company", company);
		qry.setParameter("date", date);
		if (branch != 0) {
			qry.setParameter("branch", branch);
		}
		//
		List<Object[]> objects = qry.getResultList();
		long id = 0;
		String code = "";
		String name = "";
		String city = "";
		long product = 0;
		int direction = 0;
		double value = 0;
		for (Object[] object : objects) {
			id = (Long) object[0];
			code = (String) object[1];
			name = (String) object[2];
			city = (String) object[3];
			product = (Long) object[4];
			direction = (Integer) object[5];
			value = (Double) object[6];
			SavingTransInfoDto info = savingMap.get(id);
			if (info == null) {
				info = new SavingTransInfoDto();
				info.setId(id);
				info.setCode(code);
				SavingInformationDto detail = getInformation(id);
				info.setCompany(detail.getCompany());
				info.setBranch(detail.getBranch());
				info.setBegin(detail.getBegin());
				info.setEnd(date);
				info.setProduct(product);
				SavingProduct savingProduct = productMap.get(info.getProduct());
				info.setProductCode(savingProduct.getCode());
				info.setProductName(savingProduct.getName());
				info.setSchema(savingProduct.getSchema());
				info.setName(name);
				info.setCity(city);
				info.setValue((direction == 1) ? value : -value);
			} else {
				info.setValue(info.getValue()
						+ ((direction == 1) ? value : -value));
			}
			savingMap.put(id, info);
		}
		List<SavingTransInfoDto> result = new ArrayList<SavingTransInfoDto>();
		result.addAll(savingMap.values());
		return result;
	}

	private double calculateBallance(List<Object[]> subResult) {
		double totalValue = 0;
		for (Object[] object : subResult) {
			int direction = (Integer) object[0];
			double value = (Double) object[1];
			totalValue += (direction == 1) ? value : -value;
		}
		return totalValue;
	}

	@SuppressWarnings("unchecked")
	@Override
	public double getBallance(long id, Date date, boolean includeDate) {
		String strQry = includeDate ? "getTotalSavingTransUntil"
				: "getTotalSavingTransBeforeDate";
		Query qry = em.createNamedQuery(strQry);
		qry.setParameter("savingId", id);
		qry.setParameter("date", date);
		List<Object[]> subResult = qry.getResultList();
		return calculateBallance(subResult);
	}

	@Override
	public double getWithdrawalBallance(long id, Date date, boolean repayment) {
		double ballance = getBallance(id, date, true);
		// Minimal sisa
		Saving saving = em.find(Saving.class, id);
		if (saving == null) {
			return 0;
		}
		//
		double block = repayment ? getSavingBlockValueExcept(id, 1)
				: getSavingBlockValue(id);
		//
		return (ballance - saving.getProduct().getMinValue() - block);
	}

	@Override
	public void startCloseSaving(long id, String reason) {
		Saving saving = em.find(Saving.class, id);
		saving.setClosingReason(reason);
		saving.setOnClose(1);
		em.persist(saving);
	}

	@Override
	public void closeSaving(long id, Date closing) {
		Saving saving = em.find(Saving.class, id);
		saving.setActive(0);
		saving.setClosing(closing);
		em.persist(saving);
	}

	@SuppressWarnings("unchecked")
	@Override
	public long getCodeCounter(long company, String prefix) {
		long result = 1;
		Query qry = em.createNamedQuery("getSavingCodeCounter");
		qry.setParameter("company", company);
		qry.setParameter("prefix", prefix);
		List<SavingCode> codes = qry.getResultList();
		SavingCode code = null;
		if (codes.size() > 0) {
			code = codes.get(0);
			result = code.getCounter();
		} else {
			code = new SavingCode();
			code.setCompany(company);
			code.setPrefix(prefix);
		}
		code.setCounter(result + 1);
		em.persist(code);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long isSavingTransExist(long company, long branch, long refId) {
		Query qry = em.createNamedQuery("getSavingTransByRefId");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("refId", refId);
		List<SavingTransaction> listSaving = qry.getResultList();
		if (listSaving.size() > 0) {
			return listSaving.get(0).getId();
		}
		return 0;
	}

	@Override
	public long saveSavingBlock(SavingBlockirDto dto) {
		SavingBlockir block = new SavingBlockir();
		block.setId(dto.getId());
		block.setDescription(dto.getDescription());
		block.setType(dto.getType());
		block.setValue(dto.getValue());
		Saving saving = em.find(Saving.class, dto.getSaving());
		block.setSaving(saving);
		if (block.getId() == 0) {
			em.persist(block);
		} else {
			em.merge(block);
		}
		return block.getId();
	}

	private SavingBlockirDto createSavingBlockToDto(SavingBlockir block) {
		SavingBlockirDto dto = new SavingBlockirDto();
		dto.setId(block.getId());
		dto.setDescription(block.getDescription());
		dto.setType(block.getType());
		dto.setValue(block.getValue());
		dto.setSaving(block.getSaving().getId());
		return dto;
	}

	@Override
	public SavingBlockirDto getSavingBlock(long id) {
		SavingBlockir block = em.find(SavingBlockir.class, id);
		return createSavingBlockToDto(block);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SavingBlockirDto> listSavingBlock(long id) {
		List<SavingBlockirDto> result = new ArrayList<SavingBlockirDto>();
		Query qry = em.createNamedQuery("listSavingBlock");
		qry.setParameter("savingId", id);
		List<SavingBlockir> blocks = qry.getResultList();
		for (SavingBlockir block : blocks) {
			result.add(createSavingBlockToDto(block));
		}
		return result;
	}

	@Override
	public double getSavingBlockValue(long id) {
		Query qry = em.createNamedQuery("getTotalBlock");
		qry.setParameter("savingId", id);
		Double value = (Double) qry.getSingleResult();
		return value == null ? 0 : value;
	}

	@Override
	public double getSavingBlockValueExcept(long id, int type) {
		Query qry = em.createNamedQuery("getTotalBlockExceptType");
		qry.setParameter("savingId", id);
		qry.setParameter("type", type);
		Double value = (Double) qry.getSingleResult();
		return value == null ? 0 : value;
	}

	@Override
	public void removeSavingBlock(long id) {
		SavingBlockir block = em.find(SavingBlockir.class, id);
		em.remove(block);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SavingPrintCodeRefDto> listPrintCode(long company) {
		List<SavingPrintCodeRefDto> result = new ArrayList<SavingPrintCodeRefDto>();
		Query qry = em.createNamedQuery("listPrintCode");
		qry.setParameter("company", company);
		List<SavingPrintCodeRef> refs = qry.getResultList();
		for (SavingPrintCodeRef ref : refs) {
			SavingPrintCodeRefDto dto = new SavingPrintCodeRefDto();
			dto.setType(ref.getType());
			dto.setCode(ref.getCode());
			result.add(dto);
		}
		return result;
	}
}
