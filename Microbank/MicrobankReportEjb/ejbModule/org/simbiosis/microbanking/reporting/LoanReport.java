package org.simbiosis.microbanking.reporting;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.simbiosis.microbank.ILoanReport;
import org.simbiosis.microbank.model.LoanRpt;

@Stateless
@Remote(ILoanReport.class)
public class LoanReport implements ILoanReport {

	@PersistenceContext(unitName = "MicrobankReportEjb", type = PersistenceContextType.TRANSACTION)
	EntityManager em;

	@Override
	public void createDailyLoan(LoanRpt loan) {
		em.merge(loan);
	}

	@Override
	public void createMonthlyLoan(long id, int month, int year) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanRpt> listDailyLoanByQuality(long company, long branch,
			Date pos, int quality) {
		String strQry = "";
		if (branch == 0) {
			if (quality == 0) {
				strQry = "listDailyLoan1";
			} else if (quality == 1) {
				strQry = "listDailyLoanByQuality1";
			} else if (quality >= 2) {
				strQry = "listDailyLoanByQuality2";
			}
		} else {
			if (quality == 0) {
				strQry = "listDailyLoan2";
			} else if (quality == 1) {
				strQry = "listDailyLoanByQuality3";
			} else if (quality >= 2) {
				strQry = "listDailyLoanByQuality4";
			}
		}
		Query qry = em.createNamedQuery(strQry);
		if (branch == 0) {
			qry.setParameter("company", company);
		} else {
			qry.setParameter("branch", branch);
		}
		qry.setParameter("pos", pos);
		List<LoanRpt> result = qry.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanRpt> listDailyLoan(long company, long branch, Date pos) {
		String strQry = branch == 0 ? "listDailyLoan1" : "listDailyLoan2";
		Query qry = em.createNamedQuery(strQry);
		if (branch == 0) {
			qry.setParameter("company", company);
		} else {
			qry.setParameter("branch", branch);
		}
		qry.setParameter("pos", pos);
		List<LoanRpt> result = qry.getResultList();
		return result;
	}

	@Override
	public List<LoanRpt> listDailyLoanBySchema(long company, Date date,
			int schema) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanRpt> listDailyLoanByProduct(long company, long branch,
			Date date, long product, int quality) {
		String strQry = "";
		if (branch == 0) {
			if (product == 0) {
				if (quality == 0) {
					strQry = "listDailyLoan1";
				} else if (quality == 1) {
					strQry = "listDailyLoanByQuality1";
				} else if (quality >= 2) {
					strQry = "listDailyLoanByQuality2";
				}
			} else {
				if (quality == 0) {
					strQry = "listDailyLoanByProduct1";
				} else if (quality == 1) {
					strQry = "listDailyLoanByProduct2";
				} else if (quality >= 2) {
					strQry = "listDailyLoanByProduct3";
				}
			}
		} else {
			if (product == 0) {
				if (quality == 0) {
					strQry = "listDailyLoan2";
				} else if (quality == 1) {
					strQry = "listDailyLoanByQuality3";
				} else if (quality >= 2) {
					strQry = "listDailyLoanByQuality4";
				}
			} else {
				if (quality == 0) {
					strQry = "listDailyLoanByProduct4";
				} else if (quality == 1) {
					strQry = "listDailyLoanByProduct5";
				} else if (quality >= 2) {
					strQry = "listDailyLoanByProduct6";
				}
			}
		}
		Query qry = em.createNamedQuery(strQry);
		if (product != 0) {
			qry.setParameter("product", product);
		}
		if (branch != 0) {
			qry.setParameter("branch", branch);
		} else {
			qry.setParameter("company", company);
		}
		qry.setParameter("pos", date);
		List<LoanRpt> result = qry.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanRpt> listDailyLoanByAo(long company, Date pos, long ao,
			int quality) {
		String strQry = "listDailyLoanByAo1";
		if (ao == 0) {
			if (quality == 1) {
				strQry = "listDailyLoanByAo4";
			} else if (quality == 2) {
				strQry = "listDailyLoanByAo5";
			}
		} else {
			if (quality == 0) {
				strQry = "listDailyLoanByAo6";
			} else if (quality == 1) {
				strQry = "listDailyLoanByAo2";
			} else if (quality == 2) {
				strQry = "listDailyLoanByAo3";
			}
		}
		Query qry = em.createNamedQuery(strQry);
		qry.setParameter("pos", pos);
		if (ao != 0) {
			qry.setParameter("ao", ao);
		}
		List<LoanRpt> result = qry.getResultList();
		return result;
	}

	@Override
	public void deleteDailyLoan(long company, Date pos) {
		Query qry = em.createNamedQuery("deleteDailyLoan");
		qry.setParameter("company", company);
		qry.setParameter("pos", pos);
		qry.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanRpt> listLoanBilling(long company, long branch, Date pos,
			long ao) {
		String strQry = "listDailyLoan4";
		if (branch == 0 && ao == 0) {
			strQry = "listDailyLoan1";
		} else if (branch != 0 && ao == 0) {
			strQry = "listDailyLoan2";
		} else if (branch == 0 && ao != 0) {
			strQry = "listDailyLoan3";
		}
		Query qry = em.createNamedQuery(strQry);
		if (branch == 0) {
			qry.setParameter("company", company);
		} else {
			qry.setParameter("branch", branch);
		}
		if (ao != 0) {
			qry.setParameter("ao", ao);
		}
		qry.setParameter("pos", pos);
		List<LoanRpt> result = qry.getResultList();
		//
		Iterator<LoanRpt> iter = result.iterator();
		while (iter.hasNext()) {
			LoanRpt current = iter.next();
			if (current.getDueOs() == 0) {
				iter.remove();
			} else {
				em.detach(current);
				if (current.getSavingBallance() > current.getOsTotal()) {
					current.setSavingBallance(current.getOsTotal());
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LoanRpt getDailyLoan(long loan, Date pos) {
		Query qry = em.createNamedQuery("getDailyLoan");
		qry.setParameter("loan", loan);
		qry.setParameter("pos", pos);
		List<LoanRpt> result = qry.getResultList();
		return (result.size() > 0) ? result.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LoanRpt getDailyLoanByCode(long company, String code, Date pos) {
		Query qry = em.createNamedQuery("getDailyLoanByCode");
		qry.setParameter("company", company);
		qry.setParameter("code", code);
		qry.setParameter("pos", pos);
		List<LoanRpt> result = qry.getResultList();
		return (result.size() > 0) ? result.get(0) : null;
	}

	@Override
	public int getCustomerQuality(long customer, Date pos) {
		Query qry = em.createNamedQuery("getMaxQuality");
		qry.setParameter("customer", customer);
		qry.setParameter("pos", pos);
		Integer quality = (Integer) qry.getSingleResult();
		return quality == null ? 0 : quality;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setCustomerQuality(long customer, Date pos, int quality) {
		Query qry = em.createNamedQuery("listCustomerLoan");
		qry.setParameter("customer", customer);
		qry.setParameter("pos", pos);
		List<LoanRpt> loans = qry.getResultList();
		for (LoanRpt loan : loans) {
			loan.setQuality(quality);
			em.persist(loan);
		}
	}

}
