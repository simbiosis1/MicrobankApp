package org.simbiosis.microbanking.reporting;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.simbiosis.microbank.FindSavingDto;
import org.simbiosis.microbank.ISaving;
import org.simbiosis.microbank.ISavingReport;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.SavingTransInfoDto;
import org.simbiosis.microbank.model.SavingRpt;
import org.simbiosis.microbank.model.SavingRptPk;

@Stateless
@Remote(ISavingReport.class)
public class SavingReport implements ISavingReport {
	@PersistenceContext(unitName = "MicrobankReportEjb", type = PersistenceContextType.TRANSACTION)
	EntityManager em;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/SavingImpl")
	ISaving saving;

	@Override
	public List<SavingTransInfoDto> listSavingId(long company, long branch,
			Date date) {
		return saving.listBallance(company, branch, date);
	}

	@Override
	public List<Long> listSavingIdTransacting(Date date) {
		FindSavingDto findSavingDto = new FindSavingDto();
		findSavingDto.setCompany(2);
		findSavingDto.setActive(1);
		List<Long> result = saving.listIdTransacting(findSavingDto, date);
		return result;
	}

	@Override
	public void createStartSaving(int month, int year, SavingTransInfoDto trans) {
		SavingRpt saving = new SavingRpt();
		SavingRptPk id = new SavingRptPk();
		id.setPos(new Date());
		id.setRefId(trans.getId());
		saving.setId(id);
		saving.setType(2);
		saving.setMonth(month);
		saving.setYear(year);
		saving.setCode(trans.getCode());
		saving.setSchema(trans.getSchema());
		saving.setName(trans.getName());
		saving.setCompany(trans.getCompany());
		saving.setBranch(trans.getBranch());
		saving.setProduct(trans.getProduct());
		saving.setProductCode(trans.getProductCode());
		saving.setProductName(trans.getProductName());
		//
		saving.setValPrev(0);
		saving.setTransDebet(0);
		saving.setTransCredit(trans.getValue());
		saving.setValAfter(trans.getValue());
		em.persist(saving);
	}

	@Override
	public void createDailySaving(SavingRpt saving) {
		em.persist(saving);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateDailySaving(long id, Date date) {
		// Get saving value
		Double transDebet = 0D; // saving.getTotalTransDay(id, 1, date);
		Double transCredit = 0D;// saving.getTotalTransDay(id, 2, date);
		Query qry = em.createNamedQuery("getDailySaving");
		qry.setParameter("pos", date);
		qry.setParameter("refId", id);
		SavingRpt currentSaving = null;
		List<SavingRpt> listSaving = qry.getResultList();
		if (listSaving.size() > 0) {
			currentSaving = listSaving.get(0);
		} else {
			// Get saving information
			SavingInformationDto savingInfo = saving.getInformation(id);
			// Combine it
			SavingRptPk idPK = new SavingRptPk();
			idPK.setPos(date);
			idPK.setRefId(savingInfo.getId());
			currentSaving = new SavingRpt();
			currentSaving.setId(idPK);
			currentSaving.setType(1);
			currentSaving.setCode(savingInfo.getCode());
			currentSaving.setSchema(savingInfo.getSchema());
			currentSaving.setName(savingInfo.getName());
			currentSaving.setCompany(savingInfo.getCompany());
			currentSaving.setBranch(savingInfo.getBranch());
			currentSaving.setProduct(savingInfo.getProduct());
			currentSaving.setProductCode(savingInfo.getProductCode());
			currentSaving.setProductName(savingInfo.getProductName());
			//
			currentSaving.setValPrev(0);
		}
		currentSaving.setTransDebet(transDebet);
		currentSaving.setTransCredit(transCredit);
		currentSaving.setValAfter(currentSaving.getValPrev()
				+ currentSaving.getTransDebet()
				- currentSaving.getTransCredit());
		em.persist(currentSaving);
	}

	@Override
	public void createMonthlySaving(long id, int month, int year) {
		// Combine it
		SavingRpt saving = new SavingRpt();
		saving.setId(new SavingRptPk());
		saving.setType(2);
		saving.setMonth(month);
		saving.setYear(year);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SavingRpt> listDailySaving(long company, long branch, Date date) {
		String strQry = branch == 0 ? "listDailySaving1" : "listDailySaving2";
		Query qry = em.createNamedQuery(strQry);
		if (branch == 0) {
			qry.setParameter("company", company);
		} else {
			qry.setParameter("branch", branch);
		}
		qry.setParameter("pos", date);
		List<SavingRpt> result = qry.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SavingRpt> listDailySavingBySchema(long company, Date date,
			int schema) {
		Query qry = em.createNamedQuery("listDailySavingBySchema");
		qry.setParameter("company", company);
		qry.setParameter("pos", date);
		qry.setParameter("schema", schema);
		List<SavingRpt> result = qry.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SavingRpt> listDailySavingByProduct(long company, long branch,
			Date date, long product) {
		String strQry = "";
		if (branch == 0) {
			if (product != 0) {
				strQry = "listDailySavingByProduct";
			} else {
				strQry = "listDailySaving1";
			}
		} else {
			if (product != 0) {
				strQry = "listDailySavingByProduct2";
			} else {
				strQry = "listDailySaving2";
			}

		}
		Query qry = em.createNamedQuery(strQry);
		if (branch != 0) {
			qry.setParameter("branch", branch);
		} else {
			qry.setParameter("company", company);
		}
		if (product != 0) {
			qry.setParameter("product", product);
		}
		qry.setParameter("pos", date);
		List<SavingRpt> result = qry.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SavingRpt> listDailySavingAtRange(long company, Date beginDate,
			Date endDate) {
		HashMap<Long, SavingRpt> savingMap = new HashMap<Long, SavingRpt>();
		Query qry = em.createNamedQuery("listDailySaving");
		qry.setParameter("company", company);
		qry.setParameter("pos", beginDate);
		List<SavingRpt> listSaving = qry.getResultList();
		for (SavingRpt saving : listSaving) {
			SavingRpt currentSaving = savingMap.get(saving.getId().getRefId());
			if (currentSaving == null) {
				savingMap.put(saving.getId().getRefId(), saving);
			}
		}
		//
		qry = em.createNamedQuery("listDailySaving");
		qry.setParameter("company", company);
		qry.setParameter("pos", endDate);
		listSaving = qry.getResultList();
		for (SavingRpt saving : listSaving) {
			SavingRpt currentSaving = savingMap.get(saving.getId().getRefId());
			if (currentSaving == null) {
				saving.setValPrev(0);
				savingMap.put(saving.getId().getRefId(), saving);
			} else {
				currentSaving.setValAfter(saving.getValAfter());
			}
		}
		List<SavingRpt> result = (List<SavingRpt>) savingMap.values();
		for (SavingRpt saving : result) {
			qry = em.createNamedQuery("sumTransSaving");
			qry.setParameter("company", company);
			qry.setParameter("beginDate", beginDate);
			qry.setParameter("endDate", endDate);
			qry.setParameter("refId", saving.getId().getRefId());
			Object[] hasil = (Object[]) qry.getSingleResult();
			Double transDebet = (Double) hasil[1];
			Double transCredit = (Double) hasil[2];
			saving.setTransDebet(transDebet);
			saving.setTransCredit(transCredit);
		}
		Collections.sort(result, new Comparator<SavingRpt>() {

			@Override
			public int compare(SavingRpt o1, SavingRpt o2) {
				String code1 = o1.getCode().toUpperCase();
				String code2 = o2.getCode().toUpperCase();
				return code1.compareTo(code2);
			}
		});
		return result;
	}

	@Override
	public void deleteDailySaving(long company, Date pos) {
		Query qry = em.createNamedQuery("deleteDailySaving");
		qry.setParameter("company", company);
		qry.setParameter("pos", pos);
		qry.executeUpdate();
	}

}
