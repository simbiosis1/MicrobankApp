package org.simbiosis.microbank;

import java.util.List;

public interface ICustomer {
	String getMaxCustomerCode(long company, long branch, String prefixCode);

	long saveCustomer(CustomerDto customerDto);

	CustomerDto getCustomer(long id);

	List<CustomerDto> findCustomer(FindCustomerDto findCustomer);

	CustomerDto getCustomerByRefId(long company, long branch, long id);

	long isCustomerExistByRefId(long company, long branch, long refId);

	long isCustomerExistByRefId(long company, long refId);

	void initReplicatedCustomer(long company, long branch, long refId);

	void initReplicatedCustomer(long company, long branch, long refIdMin,
			long refIdMax);

	void replicateCustomer(long refId);

	List<CustomerDto> getCustomerNotReplicated(long company, long branch);

	//
	List<CustomerDto> listCifByName(long company, String name);
	
	List<RefProvCityDto> listCity(long company);

	List<RefProvCityDto> listProvinsi(long company);

	List<RefOccupationTypeDto> listJenisPekerjaan(long company);

}
