package org.simbiosis.microbank;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.simbiosis.microbank.model.Customer;
import org.simbiosis.microbank.model.CustomerRep;
import org.simbiosis.microbank.model.RefOccupationType;
import org.simbiosis.microbank.model.RefProvCity;

@Stateless
@Remote(ICustomer.class)
public class CustomerImpl implements ICustomer {

	@PersistenceContext(unitName = "MicrobankEjb", type = PersistenceContextType.TRANSACTION)
	EntityManager em;

	@Override
	public String getMaxCustomerCode(long company, long branch,
			String prefixCode) {
		Query qry = em.createNamedQuery("getMaxCustomerCode");
		qry.setParameter("prefixCode", prefixCode + "%");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		String code = (String) qry.getSingleResult();
		return code;
	}

	@Override
	public long saveCustomer(CustomerDto customerDto) {
		Customer customer = CustomerHelper.createCustomerFromDto(customerDto);
		if (customerDto.getId() == 0) {
			em.persist(customer);
		} else {
			em.merge(customer);
		}
		return customer.getId();
	}

	@Override
	public CustomerDto getCustomer(long id) {
		Customer customer = em.find(Customer.class, id);
		return CustomerHelper.createCustomerToDto(customer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerDto getCustomerByRefId(long company, long branch, long refId) {
		Query qry = em.createNamedQuery("getCustomerByRefId");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("refId", refId);
		List<Customer> customers = qry.getResultList();
		if (customers.size() > 0) {
			return CustomerHelper.createCustomerToDto(customers.get(0));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long isCustomerExistByRefId(long company, long branch, long refId) {
		Query qry = em.createNamedQuery("getCustomerByRefId");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("refId", refId);
		List<Customer> customers = qry.getResultList();
		if (customers.size() > 0) {
			return customers.get(0).getId();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long isCustomerExistByRefId(long company, long refId) {
		Query qry = em.createNamedQuery("getAllCustomerByRefId");
		qry.setParameter("company", company);
		qry.setParameter("refId", refId);
		List<Customer> customers = qry.getResultList();
		if (customers.size() > 0) {
			return customers.get(0).getId();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDto> findCustomer(FindCustomerDto findCustomer) {
		List<CustomerDto> customerList = new ArrayList<CustomerDto>();
		String strQuery = "select x from Customer x where x.company=:company ";
		if (findCustomer.getBranch() != 0) {
			strQuery += "and x.branch=:branch ";
		}
		if (findCustomer.isWithName()) {
			strQuery += "and upper(x.name) like :name ";
		}
		if (findCustomer.isWithDob()) {
			strQuery += "and x.dob=:dob ";
		}
		Query qry = em.createQuery(strQuery);
		qry.setParameter("company", findCustomer.getCompany());
		if (findCustomer.getBranch() != 0) {
			qry.setParameter("branch", findCustomer.getBranch());
		}
		if (findCustomer.isWithName()) {
			String name = findCustomer.getName().toUpperCase();
			if (name.isEmpty()) {
				name = "---";
			} else {
				name = "%" + name + "%";
			}
			qry.setParameter("name", name);
		}
		if (findCustomer.isWithDob()) {
			qry.setParameter("dob", findCustomer.getDob());
		}
		List<Customer> result = qry.getResultList();
		for (Customer customer : result) {
			customerList.add(CustomerHelper.createCustomerToDto(customer));
		}
		return customerList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initReplicatedCustomer(long company, long branch, long refId) {
		Query qry = em.createNamedQuery("listCustomerByRefIdMax");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("refId", refId);
		List<Customer> customers = qry.getResultList();
		for (Customer customer : customers) {
			CustomerRep customerRep = new CustomerRep();
			customerRep.setCompany(company);
			customerRep.setBranch(branch);
			customerRep.setCustomer(customer);
			em.persist(customerRep);
		}
	}

	@Override
	public void replicateCustomer(long refId) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDto> getCustomerNotReplicated(long company, long branch) {
		Query qry = em.createNamedQuery("getMinNotReplicatedCustomer");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		Long refId = (Long) qry.getSingleResult();
		List<CustomerDto> customerList = new ArrayList<CustomerDto>();
		qry = em.createNamedQuery("listCustomerByRefIdMin");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("refId", refId);
		List<Customer> customers = qry.getResultList();
		for (Customer customer : customers) {
			customerList.add(CustomerHelper.createCustomerToDto(customer));
		}
		return customerList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initReplicatedCustomer(long company, long branch,
			long refIdMin, long refIdMax) {
		Query qry = em.createNamedQuery("listCustomerByRefIdRange");
		qry.setParameter("company", company);
		qry.setParameter("branch", branch);
		qry.setParameter("refIdMin", refIdMin);
		qry.setParameter("refIdMax", refIdMax);
		List<Customer> customers = qry.getResultList();
		for (Customer customer : customers) {
			CustomerRep customerRep = new CustomerRep();
			customerRep.setCompany(company);
			customerRep.setBranch(branch);
			customerRep.setCustomer(customer);
			em.persist(customerRep);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDto> listCifByName(long company, String name) {
		List<CustomerDto> customerList = new ArrayList<CustomerDto>();
		Query qry = em.createNamedQuery("listCif");
		qry.setParameter("company", company);
		qry.setParameter("name", name);
		List<Customer> result = qry.getResultList();
		for (Customer customer : result) {
			customerList.add(CustomerHelper.createCustomerToDto(customer));
		}
		return customerList;
	}
	

	@SuppressWarnings("unchecked")
	List<RefProvCity> _listSavingCity(long company) {
		Query qry = em.createNamedQuery("listCity");
		List<RefProvCity> result = qry.getResultList();
		return result;
	}

	@Override
	public List<RefProvCityDto> listCity(long company) {
		List<RefProvCityDto> productList = new ArrayList<RefProvCityDto>();
		for (RefProvCity product : _listSavingCity(company)) {
			productList.add(createSavingCityToDto(product));
		}
		return productList;
	}

	@SuppressWarnings("unchecked")
	List<RefProvCity> _listSavingProvinsi(long company) {
		Query qry = em.createNamedQuery("listProvinsi");
		List<RefProvCity> result = qry.getResultList();
		return result;
	}

	@Override
	public List<RefProvCityDto> listProvinsi(long company) {
		List<RefProvCityDto> productList = new ArrayList<RefProvCityDto>();
		for (RefProvCity product : _listSavingProvinsi(company)) {
			productList.add(createSavingCityToDto(product));
		}
		return productList;
	}

	RefProvCityDto createSavingCityToDto(RefProvCity product) {
		RefProvCityDto dto = new RefProvCityDto();
		dto.setId(product.getId());
		dto.setName(product.getName());

		return dto;
	}
	
	@SuppressWarnings("unchecked")
	List<RefOccupationType> _listJenisPekerjaan(long company) {
		Query qry = em.createNamedQuery("listJenisPekerjaan");
		List<RefOccupationType> result = qry.getResultList();
		return result;
	}

	@Override
	public List<RefOccupationTypeDto> listJenisPekerjaan(long company) {
		List<RefOccupationTypeDto> productList = new ArrayList<RefOccupationTypeDto>();
		for (RefOccupationType product : _listJenisPekerjaan(company)) {
			productList.add(createJenisPekerjaanToDto(product));
		}
		return productList;
	}
	
	RefOccupationTypeDto createJenisPekerjaanToDto(RefOccupationType product) {
		RefOccupationTypeDto dto = new RefOccupationTypeDto();
		dto.setId(product.getId());
		dto.setName(product.getName());
		return dto;
	}

}
