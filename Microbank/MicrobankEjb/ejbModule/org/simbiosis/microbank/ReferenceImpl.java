package org.simbiosis.microbank;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.simbiosis.microbank.model.FinancialRef;

@Stateless
@Remote(IReference.class)
public class ReferenceImpl implements IReference {

	@PersistenceContext(unitName = "MicrobankEjb", type = PersistenceContextType.TRANSACTION)
	EntityManager em;

	String groups[] = { "", "AKTIVA", "PASIVA" };
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FinancialRefDto> listFinancialReportRef(int scheme, int type) {
		List<FinancialRefDto> result = new ArrayList<FinancialRefDto>();
		Query qry = em.createNamedQuery("listFinancialRef");
		qry.setParameter("scheme", scheme);
		qry.setParameter("type", type);
		List<FinancialRef> finRefs = qry.getResultList();
		for (FinancialRef finRef : finRefs) {
			FinancialRefDto rpt = new FinancialRefDto();
			rpt.setNumber(finRef.getNumber());
			rpt.setOrder(finRef.getOrder());
			rpt.setCode(finRef.getCode());
			rpt.setDescription(finRef.getDescription());
			rpt.setGroup(groups[finRef.getType()]);
			result.add(rpt);
		}
		return result;
	}

}
