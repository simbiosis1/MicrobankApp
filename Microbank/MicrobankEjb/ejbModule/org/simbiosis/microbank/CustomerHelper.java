package org.simbiosis.microbank;

import org.simbiosis.microbank.model.Customer;

public class CustomerHelper {

	public static Customer createCustomerFromDto(CustomerDto dto) {
		Customer customer = new Customer();
		customer.setId(dto.getId());
		customer.setRefId(dto.getRefId());
		customer.setCode(dto.getCode());
		customer.setRefCode(dto.getRefCode());
		customer.setRegistration(dto.getRegistration());
		customer.setName(dto.getName());
		customer.setAlias(dto.getAlias());
		customer.setTitle(dto.getTitle());
		customer.setSex(dto.getSex());
		customer.setPob(dto.getPob());
		customer.setDob(dto.getDob());
		customer.setIdType(dto.getIdType());
		customer.setIdCode(dto.getIdCode());
		customer.setMotherName(dto.getMotherName());
		customer.setSpouseName(dto.getSpouseName());
		customer.setAddress(dto.getAddress());
		customer.setDistrict(dto.getDistrict());
		customer.setVillage(dto.getVillage());
		customer.setArea1(dto.getArea1());
		customer.setArea2(dto.getArea2());
		customer.setCity(dto.getCity());
		customer.setPostCode(dto.getPostCode());
		customer.setProvince(dto.getProvince());
		customer.setPhone(dto.getPhone());
		customer.setHandphone(dto.getHandphone());
		customer.setCompany(dto.getCompany());
		customer.setBranch(dto.getBranch());
		customer.setType(dto.getType());
		customer.setOccupation(dto.getOccupation());
		customer.setOfficeName(dto.getOfficeName());
		customer.setOfficeAddress(dto.getOfficeAddress());
		customer.setOfficeCity(dto.getOfficeCity());
		customer.setIncome(dto.getIncome());
		customer.setTaxable(dto.getTaxable());
		customer.setTaxNr(dto.getTaxNr());
		customer.setDescendant(dto.getDescendant());
		customer.setDescAddress(dto.getDescAddress());
		customer.setOtherCode(dto.getOtherCode());
		customer.setOtherStatus(dto.getOtherStatus());
		customer.setBankRel(dto.getBankRel());
		return customer;
	}

	public static CustomerDto createCustomerToDto(Customer customer) {
		CustomerDto dto = new CustomerDto();
		dto.setId(customer.getId());
		dto.setRefId(customer.getRefId());
		dto.setCode(customer.getCode());
		dto.setRefCode(customer.getRefCode());
		dto.setRegistration(customer.getRegistration());
		dto.setName(customer.getName());
		dto.setAlias(customer.getAlias());
		dto.setTitle(customer.getTitle());
		dto.setSex(customer.getSex());
		dto.setPob(customer.getPob());
		dto.setDob(customer.getDob());
		dto.setIdType(customer.getIdType());
		dto.setIdCode(customer.getIdCode());
		dto.setMotherName(customer.getMotherName());
		dto.setSpouseName(customer.getSpouseName());
		dto.setAddress(customer.getAddress());
		dto.setDistrict(customer.getDistrict());
		dto.setVillage(customer.getVillage());
		dto.setArea1(customer.getArea1());
		dto.setArea2(customer.getArea2());
		dto.setCity(customer.getCity());
		dto.setPostCode(customer.getPostCode());
		dto.setProvince(customer.getProvince());
		dto.setPhone(customer.getPhone());
		dto.setHandphone(customer.getHandphone());
		dto.setCompany(customer.getCompany());
		dto.setBranch(customer.getBranch());
		dto.setType(customer.getType());
		dto.setOccupation(customer.getOccupation());
		dto.setOfficeName(customer.getOfficeName());
		dto.setOfficeAddress(customer.getOfficeAddress());
		dto.setOfficeCity(customer.getOfficeCity());
		dto.setIncome(customer.getIncome());
		dto.setTaxable(customer.getTaxable());
		dto.setTaxNr(customer.getTaxNr());
		dto.setDescendant(customer.getDescendant());
		dto.setDescAddress(customer.getDescAddress());
		dto.setOtherStatus(customer.getOtherStatus());
		dto.setOtherCode(customer.getOtherCode());
		dto.setBankRel(customer.getBankRel());
		return dto;
	}

}
