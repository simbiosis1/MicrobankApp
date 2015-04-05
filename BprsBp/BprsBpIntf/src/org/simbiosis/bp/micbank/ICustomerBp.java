package org.simbiosis.bp.micbank;

import java.util.List;

import org.simbiosis.microbank.RefProvCityDto;
import org.simbiosis.microbank.CustomerDto;
import org.simbiosis.microbank.FindCustomerDto;
import org.simbiosis.microbank.RefOccupationTypeDto;

public interface ICustomerBp {
	long saveCustomer(String key, CustomerDto customerDto);

	CustomerDto getCustomer(long id);

	List<CustomerDto> findCustomer(String key, FindCustomerDto findCustomer);
	
	List<RefProvCityDto> listSavingProvinsi(String key);

	List<RefProvCityDto> listSavingCity(String key);

	List<RefOccupationTypeDto> listSavingJenisPekerjaan(String key);
}
