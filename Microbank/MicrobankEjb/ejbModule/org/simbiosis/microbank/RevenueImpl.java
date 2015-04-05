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

import org.simbiosis.microbank.model.Deposit;
import org.simbiosis.microbank.model.DepositRevSharing;
import org.simbiosis.microbank.model.Revenue;
import org.simbiosis.microbank.model.RevenueSharing;
import org.simbiosis.microbank.model.RevenueSource;

@Stateless
@Remote(IRevenue.class)
public class RevenueImpl implements IRevenue {

	@PersistenceContext(unitName = "MicrobankEjb", type = PersistenceContextType.TRANSACTION)
	EntityManager em;

	@Override
	public long saveRevenue(RevenueDto dto) {
		Revenue revenue = new Revenue();
		revenue.setId(dto.getId());
		revenue.setCompany(dto.getCompany());
		revenue.setBranch(dto.getBranch());
		revenue.setMonth(dto.getMonth());
		revenue.setYear(dto.getYear());
		revenue.setProduct(dto.getProduct());
		revenue.setType(dto.getType());
		revenue.setValue(dto.getValue());
		em.persist(revenue);
		return revenue.getId();
	}

	@Override
	public RevenueDto getRevenue(long id) {
		Revenue revenue = em.find(Revenue.class, id);
		RevenueDto dto = new RevenueDto();
		dto.setId(revenue.getId());
		dto.setCompany(revenue.getCompany());
		dto.setBranch(revenue.getBranch());
		dto.setMonth(revenue.getMonth());
		dto.setYear(revenue.getYear());
		dto.setProduct(revenue.getProduct());
		dto.setType(revenue.getType());
		dto.setValue(revenue.getValue());
		return dto;
	}

	@Override
	public long saveRevenueSharing(RevenueSharingDto dto) {
		RevenueSharing rev = new RevenueSharing();
		rev.setId(dto.getId());
		rev.setCompany(dto.getCompany());
		rev.setBranch(dto.getBranch());
		rev.setMonth(dto.getMonth());
		rev.setYear(dto.getYear());
		rev.setSaving(dto.getSaving());
		rev.setAccount(dto.getAccount());
		rev.setType(dto.getType());
		rev.setStartValue(dto.getStartValue());
		rev.setEndValue(dto.getEndValue());
		rev.setAverageValue(dto.getAverageValue());
		rev.setSharing(dto.getSharing());
		rev.setTotalSharing(dto.getTotalSharing());
		rev.setCustomerSharing(dto.getCustomerSharing());
		rev.setTax(dto.getTax());
		rev.setZakat(dto.getZakat());
		em.persist(rev);
		return rev.getId();
	}

	RevenueSharingDto createRevenueSharingToDto(RevenueSharing rev) {
		RevenueSharingDto dto = new RevenueSharingDto();
		dto.setId(rev.getId());
		dto.setCompany(rev.getCompany());
		dto.setBranch(rev.getBranch());
		dto.setMonth(rev.getMonth());
		dto.setYear(rev.getYear());
		dto.setSaving(rev.getSaving());
		dto.setAccount(rev.getAccount());
		dto.setType(rev.getType());
		dto.setStartValue(rev.getStartValue());
		dto.setEndValue(rev.getEndValue());
		dto.setAverageValue(rev.getAverageValue());
		dto.setSharing(rev.getSharing());
		dto.setTotalSharing(rev.getTotalSharing());
		dto.setCustomerSharing(rev.getCustomerSharing());
		dto.setTax(rev.getTax());
		dto.setZakat(rev.getZakat());
		return dto;
	}

	@Override
	public RevenueSharingDto getRevenueSharing(long id) {
		RevenueSharing rev = em.find(RevenueSharing.class, id);
		return createRevenueSharingToDto(rev);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RevenueDto> listRevenueByProduct(long company, Date beginDate,
			Date endDate) {
		List<RevenueDto> revList = new ArrayList<RevenueDto>();
		Query qry = em.createNamedQuery("listLoanRevenueByProduct");
		qry.setParameter("company", company);
		qry.setParameter("beginDate", beginDate);
		qry.setParameter("endDate", endDate);
		List<Object[]> listRevenue = qry.getResultList();
		for (Object[] object : listRevenue) {
			RevenueDto rev = new RevenueDto();
			rev.setCompany(company);
			rev.setProduct((Long) object[0]);
			rev.setProductName((String) object[2]);
			rev.setType(1);
			rev.setValue((Double) object[3]);
			revList.add(rev);
		}
		return revList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RevenueDto> listRevenueByCoa(long company) {
		List<RevenueDto> revList = new ArrayList<RevenueDto>();
		Query qry = em.createNamedQuery("listRevenueByCoa");
		qry.setParameter("company", company);
		List<RevenueSource> listRevenue = qry.getResultList();
		for (RevenueSource object : listRevenue) {
			RevenueDto rev = new RevenueDto();
			rev.setCompany(company);
			rev.setProduct(object.getCoa());
			rev.setType(2);
			rev.setValue(0);
			revList.add(rev);
		}
		return revList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RevenueSharingDto> listRevenueSharing(long company, int month,
			int year, long id) {
		List<RevenueSharingDto> result = new ArrayList<RevenueSharingDto>();
		Query qry = em.createNamedQuery("listRevenueSharing");
		qry.setParameter("company", company);
		qry.setParameter("month", month);
		qry.setParameter("year", year);
		qry.setParameter("id", id);
		List<RevenueSharing> rsList = qry.getResultList();
		for (RevenueSharing rev : rsList) {
			result.add(createRevenueSharingToDto(rev));
		}
		return result;
	}

	@Override
	public long saveDepositRevSharing(DepositRevSharingDto dto) {
		DepositRevSharing drs = new DepositRevSharing();
		drs.setId(dto.getId());
		drs.setCompany(dto.getCompany());
		drs.setDate(dto.getDate());
		drs.setStatus(dto.getStatus());
		Deposit deposit = em.find(Deposit.class, dto.getDeposit());
		drs.setDeposit(deposit);
		drs.setCustomerSharing(dto.getCustomerSharing());
		drs.setZakat(dto.getZakat());
		drs.setTax(dto.getTax());
		em.persist(drs);
		return drs.getId();
	}

	DepositRevSharingDto createDepositRevSharingToDto(DepositRevSharing drs) {
		DepositRevSharingDto dto = new DepositRevSharingDto();
		dto.setId(drs.getId());
		dto.setDeposit(drs.getDeposit().getId());
		dto.setCompany(drs.getCompany());
		dto.setDate(drs.getDate());
		dto.setSaving(drs.getDeposit().getSaving().getId());
		dto.setCustomerSharing(drs.getCustomerSharing());
		dto.setZakat(drs.getZakat());
		dto.setTax(drs.getTax());
		return dto;
	}

	@Override
	public DepositRevSharingDto getDepositRevSharing(long id) {
		DepositRevSharing drs = em.find(DepositRevSharing.class, id);
		return createDepositRevSharingToDto(drs);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepositRevSharingDto> listDepositRevSharing(long company,
			Date date) {
		List<DepositRevSharingDto> listDepSharing = new ArrayList<DepositRevSharingDto>();
		Query qry = em.createNamedQuery("listDepositRevSharing");
		qry.setParameter("company", company);
		qry.setParameter("date", date);
		List<DepositRevSharing> result = qry.getResultList();
		for (DepositRevSharing drs : result) {
			listDepSharing.add(createDepositRevSharingToDto(drs));
		}
		return listDepSharing;
	}

	@Override
	public void saveDepositRevSharingStatus(long id, int status) {
		DepositRevSharing drs = em.find(DepositRevSharing.class, id);
		drs.setStatus(status);
		em.persist(drs);
	}

}
