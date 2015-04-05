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

import org.simbiosis.microbank.model.Teller;
import org.simbiosis.microbank.model.TellerTransaction;
import org.simbiosis.microbank.model.TellerTransactionCode;
import org.simbiosis.microbank.model.Vault;
import org.simbiosis.microbank.model.VaultTransaction;
import org.simbiosis.microbank.model.VaultTransactionItem;

@Stateless
@Remote(ITeller.class)
public class TellerImpl implements ITeller {

	@PersistenceContext(unitName = "MicrobankEjb", type = PersistenceContextType.TRANSACTION)
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public long getTransCodeCounter(long company, String prefix) {
		long result = 1;
		Query qry = em.createNamedQuery("getTellerTransCodeCounter");
		qry.setParameter("company", company);
		qry.setParameter("prefix", prefix);
		List<TellerTransactionCode> codes = qry.getResultList();
		TellerTransactionCode code = null;
		if (codes.size() > 0) {
			code = codes.get(0);
			result = code.getCounter();
		} else {
			code = new TellerTransactionCode();
			code.setCompany(company);
			code.setPrefix(prefix);
		}
		code.setCounter(result + 1);
		em.persist(code);
		return result;
	}

	@Override
	public String getMaxVaultTransCode(long company, long branch,
			String prefixCode) {
		Query qry = em.createNamedQuery("getMaxVaultTransCode");
		qry.setParameter("prefixCode", prefixCode + "%");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		String code = (String) qry.getSingleResult();
		return code;
	}

	Teller createTellerFromDto(TellerDto dto) {
		Teller teller = new Teller();
		teller.setId(dto.getId());
		teller.setCode(dto.getCode());
		teller.setUser(dto.getUser());
		teller.setCoa(dto.getCoa());
		teller.setCompany(dto.getCompany());
		teller.setBranch(dto.getBranch());
		teller.setSubBranch(dto.getSubBranch());
		return teller;
	}

	TellerDto createTellerToDto(Teller teller) {
		TellerDto dto = new TellerDto();
		dto.setId(teller.getId());
		dto.setCode(teller.getCode());
		dto.setUser(teller.getUser());
		dto.setCoa(teller.getCoa());
		dto.setCompany(teller.getCompany());
		dto.setBranch(teller.getBranch());
		dto.setSubBranch(teller.getSubBranch());
		return dto;
	}

	TellerTransaction createTellerTransactionFromDto(
			TellerTransactionDto transDto) {
		TellerTransaction trans = new TellerTransaction();
		trans.setId(transDto.getId());
		trans.setRefId(transDto.getRefId());
		trans.setCode(transDto.getCode());
		trans.setRefCode(transDto.getRefCode());
		trans.setDate(transDto.getDate());
		trans.setTimestamp(transDto.getTimestamp());
		trans.setDescription(transDto.getDescription());
		trans.setDirection(transDto.getDirection());
		trans.setValue(transDto.getValue());
		trans.setType(transDto.getType());
		trans.setAccountId(transDto.getAccountId());
		trans.setTransId(transDto.getTransId());
		Teller teller = em.find(Teller.class, transDto.getTeller());
		trans.setTeller(teller);
		trans.setCompany(transDto.getCompany());
		trans.setBranch(transDto.getBranch());
		trans.setSubBranch(transDto.getSubBranch());
		return trans;
	}

	TellerTransactionDto createTellerTransactionToDto(TellerTransaction trans) {
		TellerTransactionDto transDto = new TellerTransactionDto();
		transDto.setId(trans.getId());
		transDto.setRefId(trans.getRefId());
		transDto.setCode(trans.getCode());
		transDto.setRefCode(trans.getRefCode());
		transDto.setDate(trans.getDate());
		transDto.setTimestamp(trans.getTimestamp());
		transDto.setDescription(trans.getDescription());
		transDto.setDirection(trans.getDirection());
		transDto.setValue(trans.getValue());
		transDto.setType(trans.getType());
		transDto.setAccountId(trans.getAccountId());
		transDto.setTeller(trans.getTeller().getId());
		transDto.setTransId(trans.getTransId());
		transDto.setCompany(trans.getCompany());
		transDto.setBranch(trans.getBranch());
		transDto.setSubBranch(trans.getSubBranch());
		return transDto;
	}

	@Override
	public long saveTellerTransaction(TellerTransactionDto tellerTransactionDto) {
		TellerTransaction tellerTransaction = createTellerTransactionFromDto(tellerTransactionDto);
		if (tellerTransaction.getId() == 0) {
			em.persist(tellerTransaction);
		} else {
			em.merge(tellerTransaction);
		}
		return tellerTransaction.getId();
	}

	@Override
	public TellerTransactionDto getTellerTransaction(long id) {
		TellerTransaction tellerTransaction = em.find(TellerTransaction.class,
				id);
		return createTellerTransactionToDto(tellerTransaction);
	}

	@Override
	public void saveTellerTransactionRef(long id, long idRef) {
		TellerTransaction tellerTransaction = em.find(TellerTransaction.class,
				id);
		if (tellerTransaction != null) {
			tellerTransaction.setTransId(idRef);
			em.persist(tellerTransaction);
		}
	}

	@Override
	public long saveTeller(TellerDto tellerDto) {
		Teller teller = createTellerFromDto(tellerDto);
		if (tellerDto.getId() == 0) {
			em.persist(teller);
		} else {
			em.merge(teller);
		}
		return teller.getId();
	}

	@Override
	public TellerDto getTeller(long id) {
		Teller teller = em.find(Teller.class, id);
		return createTellerToDto(teller);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TellerDto> listTeller(long company, long branch, long subBranch) {
		List<TellerDto> tellerList = new ArrayList<TellerDto>();
		Query qry = null;
		if (branch == 0) {
			qry = em.createNamedQuery("listTellerByCompany");
			qry.setParameter("company", company);
		} else {
			qry = em.createNamedQuery("listTellerByBranch");
			qry.setParameter("branch", branch);
		}
		List<Teller> result = qry.getResultList();
		for (Teller teller : result) {
			tellerList.add(createTellerToDto(teller));
		}
		return tellerList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TellerDto> listTellerBySubBranch(long subBranch) {
		List<TellerDto> tellerList = new ArrayList<TellerDto>();
		Query qry = em.createNamedQuery("listTellerBySubBranch");
		qry.setParameter("subBranch", subBranch);
		List<Teller> result = qry.getResultList();
		for (Teller teller : result) {
			tellerList.add(createTellerToDto(teller));
		}
		return tellerList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TellerDto getTellerByUser(long userId) {
		Query qry = em.createNamedQuery("getTellerByUser");
		qry.setParameter("user", userId);
		List<Teller> result = qry.getResultList();
		return (result.size() > 0) ? createTellerToDto(result.get(0)) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TellerTransactionDto> listTellerTransactionByTeller(
			long tellerId, Date date) {
		List<TellerTransactionDto> transList = new ArrayList<TellerTransactionDto>();
		Teller teller = em.find(Teller.class, tellerId);
		Query qry = null;
		if (teller.getBranch() == 0) {
			qry = em.createNamedQuery("listTellerTransactionByCompany");
			qry.setParameter("company", teller.getCompany());
			qry.setParameter("date", date);
		} else {
			qry = em.createNamedQuery("listTellerTransactionByTeller");
			qry.setParameter("tellerId", tellerId);
			qry.setParameter("date", date);
		}
		List<TellerTransaction> result = qry.getResultList();
		for (TellerTransaction tellerTransaction : result) {
			transList.add(createTellerTransactionToDto(tellerTransaction));
		}
		return transList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long isTellerTransactionExistByRefId(long company, long branch,
			long refId) {
		Query qry = em.createNamedQuery("getTellerTransactionByRefId");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("refId", refId);
		List<TellerTransaction> result = qry.getResultList();
		return (result.size() > 0) ? result.get(0).getId() : 0;
	}

	VaultTransaction createVaultTransactionFromDto(VaultTransactionDto transDto) {
		VaultTransaction trans = new VaultTransaction();
		trans.setId(transDto.getId());
		trans.setCode(transDto.getCode());
		trans.setRefCode(transDto.getRefCode());
		trans.setDate(transDto.getDate());
		trans.setDirection(transDto.getDirection());
		trans.setStatus(transDto.getStatus());
		Teller teller = em.find(Teller.class, transDto.getTeller());
		trans.setTeller(teller);
		trans.setValue(transDto.getValue());
		trans.setCompany(transDto.getCompany());
		trans.setBranch(transDto.getBranch());
		trans.setSubBranch(transDto.getSubBranch());
		for (VaultTransactionItemDto itemDto : transDto.getItems()) {
			if (itemDto.getAmount() != 0) {
				VaultTransactionItem item = new VaultTransactionItem();
				item.setId(itemDto.getId());
				item.setValue(itemDto.getValue());
				item.setAmount(itemDto.getAmount());
				item.setType(itemDto.getType());
				item.setVaultTransaction(trans);
				trans.getItems().add(item);
			}
		}
		return trans;
	}

	VaultTransactionDto createVaultTransactionToDto(VaultTransaction trans) {
		VaultTransactionDto transDto = new VaultTransactionDto();
		transDto.setId(trans.getId());
		transDto.setCode(trans.getCode());
		transDto.setRefCode(trans.getRefCode());
		transDto.setDate(trans.getDate());
		transDto.setDirection(trans.getDirection());
		transDto.setStatus(trans.getStatus());
		transDto.setTeller(trans.getTeller().getId());
		transDto.setValue(trans.getValue());
		transDto.setCompany(trans.getCompany());
		transDto.setBranch(trans.getBranch());
		transDto.setSubBranch(trans.getSubBranch());
		for (VaultTransactionItem item : trans.getItems()) {
			VaultTransactionItemDto itemDto = new VaultTransactionItemDto();
			itemDto.setId(item.getId());
			itemDto.setValue(item.getValue());
			itemDto.setAmount(item.getAmount());
			itemDto.setType(item.getType());
			itemDto.setVaultTransaction(transDto.getId());
			transDto.getItems().add(itemDto);
		}
		return transDto;
	}

	@SuppressWarnings("unchecked")
	Vault getVaultValue(long company, long branch, int type, double value) {
		Query qry = em.createNamedQuery("getVaultValue");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("type", type);
		qry.setParameter("value", value);
		List<Vault> result = qry.getResultList();
		return (result.size() > 0) ? result.get(0) : null;
	}

	@Override
	public long saveVaultTransaction(VaultTransactionDto transDto) {
		VaultTransaction trans = createVaultTransactionFromDto(transDto);
		if (transDto.getId() == 0) {
			em.persist(trans);
		} else {
			em.merge(trans);
			// Masukkan ke sistem vault jika memang tinggal otorisasi
			boolean in = (trans.getDirection() == 1);
			for (VaultTransactionItem item : trans.getItems()) {
				Vault vault = getVaultValue(trans.getCompany(),
						trans.getBranch(), item.getType(), item.getValue());
				if (vault == null) {
					vault = new Vault();
					vault.setCompany(trans.getCompany());
					vault.setBranch(trans.getBranch());
					vault.setType(item.getType());
					vault.setValue(item.getValue());
					vault.setAmount(0);
				}
				vault.setAmount(in ? (vault.getAmount() + item.getAmount())
						: (vault.getAmount() - item.getAmount()));
				em.persist(vault);
			}
		}
		//
		return trans.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public VaultTransactionDto getVaultInProcess(long company, long branch,
			long tellerId, Date date) {
		Query qry = em.createNamedQuery("getVaultTransByTeller");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("tellerId", tellerId);
		qry.setParameter("date", date);
		List<VaultTransaction> result = qry.getResultList();
		if (result.size() > 0) {
			return createVaultTransactionToDto(result.get(0));
		}
		return null;
	}

	@Override
	public VaultTransactionDto getVaultTransaction(long id) {
		VaultTransaction trans = em.find(VaultTransaction.class, id);
		return createVaultTransactionToDto(trans);
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
	public double getBallance(long id, Date date) {
		Query qry = em.createNamedQuery("getTellerBallance");
		qry.setParameter("teller", id);
		qry.setParameter("date", date);
		List<Object[]> subResult = qry.getResultList();
		return calculateBallance(subResult);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TellerTransactionDto> listTellerTransaction(long company,
			long branch, Date startDate, Date endDate) {
		List<TellerTransactionDto> result = new ArrayList<TellerTransactionDto>();
		String strQry = branch == 0 ? "listTellerTransaction1"
				: "listTellerTransaction2";
		Query qry = em.createNamedQuery(strQry);
		;
		qry.setParameter("startDate", startDate);
		qry.setParameter("endDate", endDate);
		if (branch == 0) {
			qry.setParameter("company", company);
		} else {
			qry.setParameter("branch", branch);
		}
		List<TellerTransaction> transs = qry.getResultList();
		for (TellerTransaction trans : transs) {
			result.add(createTellerTransactionToDto(trans));
		}
		return result;
	}

}
