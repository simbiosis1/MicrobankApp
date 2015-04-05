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

import org.simbiosis.microbank.model.Customer;
import org.simbiosis.microbank.model.Deposit;
import org.simbiosis.microbank.model.DepositCode;
import org.simbiosis.microbank.model.DepositProduct;
import org.simbiosis.microbank.model.DepositTransaction;
import org.simbiosis.microbank.model.Saving;

@Stateless
@Remote(IDeposit.class)
public class DepositImpl implements IDeposit {

	@PersistenceContext(unitName = "MicrobankEjb", type = PersistenceContextType.TRANSACTION)
	EntityManager em;

	Deposit createDepositFromDto(DepositDto dto) {
		Deposit deposit = new Deposit();
		deposit.setId(dto.getId());
		deposit.setRefId(dto.getRefId());
		deposit.setCode(dto.getCode());
		deposit.setRefCode(dto.getRefCode());
		deposit.setBilyet(dto.getBilyet());
		deposit.setRegistration(dto.getRegistration());
		deposit.setValue(dto.getValue());
		deposit.setSpecialRate(dto.getSpecialRate());
		deposit.setAro(dto.getAro());
		deposit.setBegin(dto.getBegin());
		deposit.setEnd(dto.getEnd());
		deposit.setCompany(dto.getCompany());
		deposit.setBranch(dto.getBranch());
		deposit.setActive(dto.getActive());
		deposit.setZakat(dto.getZakat());
		Saving saving = em.find(Saving.class, dto.getSaving());
		deposit.setSaving(saving);
		Customer customer = em.find(Customer.class, dto.getCustomer());
		deposit.setCustomer(customer);
		DepositProduct depositProduct = em.find(DepositProduct.class,
				dto.getProduct());
		deposit.setProduct(depositProduct);
		if (deposit.getId() == 0) {
			deposit.setRate(depositProduct.getSharing());
		} else {
			deposit.setRate(dto.getRate());
		}
		return deposit;
	}

	DepositDto createDepositToDto(Deposit deposit) {
		DepositDto dto = new DepositDto();
		dto.setId(deposit.getId());
		dto.setRefId(deposit.getRefId());
		dto.setCode(deposit.getCode());
		dto.setRefCode(deposit.getRefCode());
		dto.setBilyet(deposit.getBilyet());
		dto.setRegistration(deposit.getRegistration());
		dto.setValue(deposit.getValue());
		dto.setRate(deposit.getRate());
		dto.setSpecialRate(deposit.getSpecialRate());
		dto.setAro(deposit.getAro());
		dto.setBegin(deposit.getBegin());
		dto.setEnd(deposit.getEnd());
		dto.setSaving(deposit.getSaving().getId());
		dto.setCompany(deposit.getCompany());
		dto.setBranch(deposit.getBranch());
		dto.setCustomer(deposit.getCustomer().getId());
		dto.setProduct(deposit.getProduct().getId());
		dto.setActive(deposit.getActive());
		dto.setZakat(deposit.getZakat());
		return dto;
	}

	DepositProduct createDepositProductFromDto(DepositProductDto dto) {
		DepositProduct product = new DepositProduct();
		product.setId(dto.getId());
		product.setRefId(dto.getRefId());
		product.setCode(dto.getCode());
		product.setName(dto.getName());
		product.setCompany(dto.getCompany());
		product.setSharing(dto.getSharing());
		product.setTerm(dto.getTerm());
		product.setCoa1(dto.getCoa1());
		product.setCoa2(dto.getCoa2());
		product.setCoa3(dto.getCoa3());
		return product;
	}

	DepositProductDto createDepositProductToDto(DepositProduct product) {
		DepositProductDto dto = new DepositProductDto();
		dto.setId(product.getId());
		dto.setRefId(product.getRefId());
		dto.setCode(product.getCode());
		dto.setName(product.getName());
		dto.setCompany(product.getCompany());
		dto.setSharing(product.getSharing());
		dto.setTerm(product.getTerm());
		dto.setCoa1(product.getCoa1());
		dto.setCoa2(product.getCoa2());
		dto.setCoa3(product.getCoa3());
		return dto;
	}

	DepositTransaction createDepositTransactionFromDto(
			DepositTransactionDto transDto) {
		DepositTransaction trans = new DepositTransaction();
		trans.setId(transDto.getId());
		trans.setRefId(transDto.getRefId());
		trans.setCode(transDto.getCode());
		trans.setRefCode(transDto.getRefCode());
		trans.setDate(transDto.getDate());
		trans.setDescription(transDto.getDescription());
		trans.setDirection(transDto.getDirection());
		trans.setValue(transDto.getValue());
		Deposit saving = em.find(Deposit.class, transDto.getDeposit());
		trans.setDeposit(saving);
		trans.setCompany(transDto.getCompany());
		trans.setBranch(transDto.getBranch());
		return trans;
	}

	DepositTransactionDto createDepositTransactionToDto(DepositTransaction trans) {
		DepositTransactionDto transDto = new DepositTransactionDto();
		transDto.setId(trans.getId());
		transDto.setRefId(trans.getRefId());
		transDto.setCode(trans.getCode());
		transDto.setRefCode(trans.getRefCode());
		transDto.setDate(trans.getDate());
		transDto.setDescription(trans.getDescription());
		transDto.setDirection(trans.getDirection());
		transDto.setValue(trans.getValue());
		transDto.setDeposit(trans.getDeposit().getId());
		transDto.setCompany(trans.getCompany());
		transDto.setBranch(trans.getBranch());
		return transDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long getCodeCounter(long company, String prefix) {
		long result = 1;
		Query qry = em.createNamedQuery("getDepositCodeCounter");
		qry.setParameter("company", company);
		qry.setParameter("prefix", prefix);
		List<DepositCode> codes = qry.getResultList();
		DepositCode code = null;
		if (codes.size() > 0) {
			code = codes.get(0);
			result = code.getCounter();
		} else {
			code = new DepositCode();
			code.setCompany(company);
			code.setPrefix(prefix);
		}
		code.setCounter(result + 1);
		em.persist(code);
		return result;
	}

	@Override
	public long saveDepositProduct(DepositProductDto dto) {
		DepositProduct product = createDepositProductFromDto(dto);
		if (dto.getId() == 0) {
			em.persist(product);
		} else {
			em.merge(product);
		}
		return product.getId();
	}

	@Override
	public DepositProductDto getDepositProduct(long id) {
		DepositProduct depositProduct = em.find(DepositProduct.class, id);
		return createDepositProductToDto(depositProduct);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepositProductDto> listDepositProduct(long company) {
		List<DepositProductDto> depositProductList = new ArrayList<DepositProductDto>();
		Query qry = em.createNamedQuery("listDepositProduct");
		qry.setParameter("company", company);
		List<DepositProduct> result = qry.getResultList();
		for (DepositProduct depositProduct : result) {
			depositProductList.add(createDepositProductToDto(depositProduct));
		}
		return depositProductList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long isDepositProductExistByRefId(long company, long refId) {
		Query qry = em.createNamedQuery("getDepositProductByRefId");
		qry.setParameter("company", company);
		qry.setParameter("refId", refId);
		List<DepositProduct> listDepositProduct = qry.getResultList();
		if (listDepositProduct.size() > 0) {
			return listDepositProduct.get(0).getId();
		}
		return 0;
	}

	@Override
	public long saveDeposit(DepositDto depositDto) {
		Deposit deposit = createDepositFromDto(depositDto);
		if (depositDto.getId() == 0) {
			em.persist(deposit);
		} else {
			em.merge(deposit);
		}
		return deposit.getId();
	}

	@Override
	public DepositDto getDeposit(long id) {
		Deposit saving = em.find(Deposit.class, id);
		return createDepositToDto(saving);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepositDto> listDeposit(FindDepositDto findDepositDto) {
		List<DepositDto> savingList = new ArrayList<DepositDto>();
		Query qry = null;
		if (findDepositDto.getBranch() == 0) {
			qry = em.createNamedQuery("listDepositByCompany");
			qry.setParameter("company", findDepositDto.getCompany());
		} else {
			qry = em.createNamedQuery("listDepositByCompanyBranch");
			qry.setParameter("company", findDepositDto.getCompany());
			qry.setParameter("branch", findDepositDto.getBranch());
		}
		List<Deposit> result = qry.getResultList();
		for (Deposit saving : result) {
			savingList.add(createDepositToDto(saving));
		}
		return savingList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepositDto> findDeposit(FindDepositDto findDepositDto) {
		List<DepositDto> savingList = new ArrayList<DepositDto>();
		String strQuery = "select x from Deposit x where x.active=1 and x.company=:company ";
		if (findDepositDto.getBranch() != 0) {
			strQuery += "and x.branch=:branch ";
		}
		if (findDepositDto.isWithName()) {
			strQuery += "and upper(x.customer.name) like :name ";
		}
		if (findDepositDto.isWithCode()) {
			strQuery += "and x.code=:code ";
		}
		Query qry = em.createQuery(strQuery);
		qry.setParameter("company", findDepositDto.getCompany());
		if (findDepositDto.getBranch() != 0) {
			qry.setParameter("branch", findDepositDto.getBranch());
		}
		if (findDepositDto.isWithCode()) {
			qry.setParameter("code", findDepositDto.getCode());
		}
		if (findDepositDto.isWithName()) {
			String name = findDepositDto.getName().toUpperCase();
			if (name.isEmpty()) {
				name = "---";
			} else {
				name = "%" + name + "%";
			}
			qry.setParameter("name", name);
		}
		List<Deposit> result = qry.getResultList();
		for (Deposit saving : result) {
			savingList.add(createDepositToDto(saving));
		}
		return savingList;
	}

	@Override
	public void closeDeposit(long id, Date closing) {
		Deposit deposit = em.find(Deposit.class, id);
		deposit.setActive(0);
		deposit.setClosing(closing);
		em.persist(deposit);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DepositDto getDepositByRefId(long company, long branch, long refId) {
		Query qry = em.createNamedQuery("getDepositByRefId");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("refId", refId);
		List<Deposit> listDeposit = qry.getResultList();
		if (listDeposit.size() > 0) {
			return createDepositToDto(listDeposit.get(0));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long isDepositExistByRefId(long company, long branch, long refId) {
		String strQry = (branch == 0) ? "getDepositByRefId1"
				: "getDepositByRefId2";
		Query qry = em.createNamedQuery(strQry);
		if (branch == 0) {
			qry.setParameter("company", company);
		} else {
			qry.setParameter("branch", branch);
		}
		qry.setParameter("refId", refId);
		List<Deposit> listDeposit = qry.getResultList();
		if (listDeposit.size() > 0) {
			return listDeposit.get(0).getId();
		}
		return 0;
	}

	@Override
	public String getMaxDepositTransCode(long company, long branch,
			String prefixCode) {
		Query qry = em.createNamedQuery("getMaxDepositTransCode");
		qry.setParameter("prefixCode", prefixCode + "%");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		String code = (String) qry.getSingleResult();
		return code;
	}

	@Override
	public long saveDepositTransaction(DepositTransactionDto transDto) {
		DepositTransaction trans = createDepositTransactionFromDto(transDto);
		if (transDto.getId() == 0) {
			em.persist(trans);
		} else {
			em.merge(trans);
		}
		return trans.getId();
	}

	@Override
	public DepositTransactionDto getDepositTransaction(long id) {
		DepositTransaction trans = em.find(DepositTransaction.class, id);
		return createDepositTransactionToDto(trans);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepositTransactionDto> listDepositTransaction(long id, Date date) {
		List<DepositTransactionDto> result = new ArrayList<DepositTransactionDto>();
		Query qry = em.createNamedQuery("listDepositTransaction");
		qry.setParameter("id", id);
		qry.setParameter("date", date);
		List<DepositTransaction> listTrans = qry.getResultList();
		for (DepositTransaction trans : listTrans) {
			result.add(createDepositTransactionToDto(trans));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> listDepositId(FindDepositDto findDepositDto, Date date) {
		String strQry = (findDepositDto.getBranch() == 0) ? "listDepositId1"
				: "listDepositId2";
		Query qry = em.createNamedQuery(strQry);
		qry.setParameter("date", date);
		if (findDepositDto.getBranch() == 0) {
			qry.setParameter("company", findDepositDto.getCompany());
		} else {
			qry.setParameter("branch", findDepositDto.getBranch());
		}
		List<Long> result = qry.getResultList();
		return result;
	}

	@Override
	public DepositInformationDto getDepositInformation(long id) {
		Deposit deposit = em.find(Deposit.class, id);
		DepositInformationDto info = new DepositInformationDto();
		info.setId(deposit.getId());
		info.setCompany(deposit.getCompany());
		info.setBranch(deposit.getBranch());
		info.setCode(deposit.getCode());
		info.setProduct(deposit.getProduct().getId());
		info.setProductCode(deposit.getProduct().getCode());
		info.setProductName(deposit.getProduct().getName());
		info.setSharing(deposit.getRate());
		info.setCustomer(deposit.getCustomer().getId());
		info.setName(deposit.getCustomer().getName());
		info.setCity(deposit.getCustomer().getCity());
		info.setRegistration(deposit.getRegistration());
		info.setBegin(deposit.getBegin());
		info.setEnd(deposit.getEnd());
		info.setValue(deposit.getValue());
		info.setSaving(deposit.getSaving().getId());
		info.setCoa1(deposit.getProduct().getCoa1());
		info.setCoa2(deposit.getProduct().getCoa2());
		info.setCoa3(deposit.getProduct().getCoa3());
		info.setZakat(deposit.getZakat());
		return info;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long getDepositByCode(long company, String code) {
		Query qry = em.createNamedQuery("getDepositByCode");
		qry.setParameter("company", company);
		qry.setParameter("code", code);
		List<Deposit> deposits = qry.getResultList();
		if (deposits.size() > 0) {
			return deposits.get(0).getId();
		}
		return 0;
	}

}
