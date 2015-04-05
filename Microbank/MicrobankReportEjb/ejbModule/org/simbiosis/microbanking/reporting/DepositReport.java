package org.simbiosis.microbanking.reporting;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.simbiosis.microbank.FindDepositDto;
import org.simbiosis.microbank.IDeposit;
import org.simbiosis.microbank.IDepositReport;
import org.simbiosis.microbank.model.DepositRpt;

/**
 * Session Bean implementation class CoreBankingReport
 */
@Stateless
@Remote(IDepositReport.class)
public class DepositReport implements IDepositReport {

	@PersistenceContext(unitName = "MicrobankReportEjb", type = PersistenceContextType.TRANSACTION)
	EntityManager em;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/DepositImpl")
	IDeposit deposit;

	/**
	 * Default constructor.
	 */
	public DepositReport() {
	}

	@Override
	public List<Long> listDepositId(Date date) {
		FindDepositDto findSavingDto = new FindDepositDto();
		findSavingDto.setCompany(2);
		findSavingDto.setActive(1);
		List<Long> result = deposit.listDepositId(findSavingDto, date);
		return result;
	}

	@Override
	public void createDailyDeposit(DepositRpt deposit) {
		//
		em.persist(deposit);
	}

	@Override
	public void createMonthlyDeposit(long id, int month, int year) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepositRpt> listDailyDeposit(long company, long branch,
			Date date) {
		String strQry = branch == 0 ? "listDailyDeposit1" : "listDailyDeposit2";
		Query qry = em.createNamedQuery(strQry);
		if (branch == 0) {
			qry.setParameter("company", company);
		} else {
			qry.setParameter("branch", branch);
		}
		qry.setParameter("pos", date);
		List<DepositRpt> result = qry.getResultList();
		return result;
	}

	@Override
	public void deleteDailyDeposit(long company, Date pos) {
		Query qry = em.createNamedQuery("deleteDailyDeposit");
		qry.setParameter("company", company);
		qry.setParameter("pos", pos);
		qry.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepositRpt> listDailyDepositByProduct(long company,
			long branch, Date date, long product) {
		String strQry = "";
		if (branch == 0) {
			if (product != 0) {
				strQry = "listDailyDepositByProduct";
			} else {
				strQry = "listDailyDeposit1";
			}
		} else {
			if (product != 0) {
				strQry = "listDailyDepositByProduct2";
			} else {
				strQry = "listDailyDeposit2";
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
		List<DepositRpt> result = qry.getResultList();
		return result;
	}

}
