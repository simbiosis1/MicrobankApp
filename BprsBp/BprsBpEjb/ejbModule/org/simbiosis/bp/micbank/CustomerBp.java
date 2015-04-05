package org.simbiosis.bp.micbank;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.simbiosis.microbank.CustomerDto;
import org.simbiosis.microbank.FindCustomerDto;
import org.simbiosis.microbank.ICustomer;
import org.simbiosis.microbank.RefOccupationTypeDto;
import org.simbiosis.microbank.RefProvCityDto;
import org.simbiosis.system.BranchDto;
import org.simbiosis.system.ISystem;
import org.simbiosis.system.SubBranchDto;
import org.simbiosis.system.UserDto;

@Stateless
@Remote(ICustomerBp.class)
public class CustomerBp implements ICustomerBp {

	@EJB(lookup = "java:global/SystemEar/SystemEjb/SystemImpl")
	ISystem system;
	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/CustomerImpl")
	ICustomer customer;

	String fillers[] = { "", "0", "00", "000", "0000", "00000", "000000" };
	int lengths[] = { 7, 6 };

	String createCustomerCode(long company, long branch, String prefixCode) {
		// Buat kode baru
		String myPrefixCode = prefixCode;
		// Cari dulu yang sudah ada
		String code = customer
				.getMaxCustomerCode(company, branch, myPrefixCode);
		if (code != null) {
			int subStr = prefixCode.length();
			int number = Integer.parseInt(code.substring(subStr)) + 1;
			String numberCode = "" + number;
			code = fillers[lengths[0] - numberCode.length()] + numberCode;
		} else {
			code = fillers[lengths[1]] + "1";
		}
		return myPrefixCode + code;
	}

	@Override
	public long saveCustomer(String key, CustomerDto customerDto) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			customerDto.setCompany(user.getCompany());
			customerDto.setBranch(user.getBranch());
			if (customerDto.getId() == 0) {
				BranchDto branchDto = system.getBranch(user.getBranch());
				SubBranchDto subBranchDto = system.getSubBranch(user
						.getSubBranch());
				String customerCode = createCustomerCode(
						customerDto.getCompany(), customerDto.getBranch(),
						branchDto.getCode() + subBranchDto.getCode());
				customerDto.setCode(customerCode);
			}
			return customer.saveCustomer(customerDto);
		}
		return 0;
	}

	@Override
	public CustomerDto getCustomer(long id) {
		return customer.getCustomer(id);
	}

	@Override
	public List<CustomerDto> findCustomer(String key,
			FindCustomerDto findCustomer) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			findCustomer.setCompany(user.getCompany());
			return customer.findCustomer(findCustomer);
		}
		return new ArrayList<CustomerDto>();
	}

	@Override
	public List<RefProvCityDto> listSavingProvinsi(String key) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			return customer.listProvinsi(user.getCompany());
		}
		return new ArrayList<RefProvCityDto>();
	}

	@Override
	public List<RefProvCityDto> listSavingCity(String key) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			return customer.listCity(user.getCompany());
		}
		return new ArrayList<RefProvCityDto>();
	}

	@Override
	public List<RefOccupationTypeDto> listSavingJenisPekerjaan(String key) {
		UserDto user = system.getUserFromSession(key);
		if (user != null) {
			return customer.listJenisPekerjaan(user.getCompany());
		}
		return new ArrayList<RefOccupationTypeDto>();
	}

}
