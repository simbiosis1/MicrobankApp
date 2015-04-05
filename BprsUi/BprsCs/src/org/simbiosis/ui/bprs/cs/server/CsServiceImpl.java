package org.simbiosis.ui.bprs.cs.server;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.simbiosis.bp.micbank.ICustomerBp;
import org.simbiosis.bp.micbank.IDepositBp;
import org.simbiosis.bp.micbank.ISavingBp;
import org.simbiosis.bp.micbank.ITellerBp;
import org.simbiosis.microbank.CustomerDto;
import org.simbiosis.microbank.DepositDto;
import org.simbiosis.microbank.DepositProductDto;
import org.simbiosis.microbank.FindDepositDto;
import org.simbiosis.microbank.RefOccupationTypeDto;
import org.simbiosis.microbank.RefProvCityDto;
import org.simbiosis.microbank.SavingBlockirDto;
import org.simbiosis.microbank.SavingDto;
import org.simbiosis.microbank.SavingProductDto;
import org.simbiosis.ui.bprs.common.shared.CustomerDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.DepositDv;
import org.simbiosis.ui.bprs.common.shared.SavingDv;
import org.simbiosis.ui.bprs.cs.client.rpc.CsService;
import org.simbiosis.ui.bprs.cs.shared.SavingBlockirDataDv;
import org.simbiosis.ui.bprs.cs.shared.SavingBlockirDv;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class CsServiceImpl extends RemoteServiceServlet implements CsService {

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/CustomerBp")
	ICustomerBp customerBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/SavingBp")
	ISavingBp savingBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/DepositBp")
	IDepositBp depositBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/TellerBp")
	ITellerBp tellerBp;

	DecimalFormat df = new DecimalFormat("#,##0.00");

	public CsServiceImpl() {
	}

	CustomerDto createCustomerDvToDto(CustomerDv dv) {
		CustomerDto dto = new CustomerDto();
		dto.setId(dv.getId());
		dto.setCode(dv.getCode());
		dto.setRegistration(dv.getRegistration());
		dto.setName(dv.getName() != null ? dv.getName().toUpperCase() : "");
		dto.setTitle(dv.getTitle() != null ? dv.getTitle().toUpperCase() : "");
		dto.setSex(dv.getSex());
		dto.setPob(dv.getPob() != null ? dv.getPob().toUpperCase() : "");
		dto.setDob(dv.getDob());
		dto.setIdType(dv.getIdType());
		dto.setIdCode(dv.getIdCode());
		dto.setMotherName(dv.getMotherName() != null ? dv.getMotherName()
				.toUpperCase() : "");
		dto.setSpouseName(dv.getSpouseName() != null ? dv.getSpouseName()
				.toUpperCase() : "");
		dto.setAddress(dv.getAddress() != null ? dv.getAddress().toUpperCase()
				: "");
		dto.setVillage(dv.getVillage() != null ? dv.getVillage().toUpperCase()
				: "");
		dto.setDistrict(dv.getDistrict() != null ? dv.getDistrict()
				.toUpperCase() : "");
		dto.setCity(dv.getCity());
		dto.setPostCode(dv.getPostCode());
		dto.setProvince(dv.getProvince() != null ? dv.getProvince()
				.toUpperCase() : "");
		dto.setPhone(dv.getPhone());
		dto.setHandphone(dv.getHandphone());
		dto.setOccupation(dv.getOccupation() != null ? dv.getOccupation()
				.toUpperCase() : "");
		dto.setOfficeName(dv.getOfficeName() != null ? dv.getOfficeName()
				.toUpperCase() : "");
		dto.setOfficeAddress(dv.getOfficeAddress() != null ? dv
				.getOfficeAddress().toUpperCase() : "");
		dto.setOfficeCity(dv.getOfficeCity() != null ? dv.getOfficeCity()
				.toUpperCase() : "");
		dto.setIncome(dv.getIncome());
		dto.setTaxable(dv.getTaxable() ? 1 : 0);
		dto.setTaxNr(dv.getTaxNr());
		dto.setDescendant(dv.getDescendant() != null ? dv.getDescendant()
				.toUpperCase() : "");
		dto.setDescAddress(dv.getDescAddress() != null ? dv.getDescAddress()
				.toUpperCase() : "");
		dto.setType(dv.getType());
		dto.setBankRel(dv.getBankRel() ? 1 : 0);
		return dto;
	}

	@Override
	public Long saveSaving(String key, SavingDv savingDv) {
		CustomerDv customerDv = savingDv.getCustomer();
		long customerId = customerDv.getId();
		if (customerId == 0) {
			customerDv.setRegistration(savingDv.getRegistration());
			customerId = customerBp.saveCustomer(key,
					createCustomerDvToDto(customerDv));
		}
		SavingDto dto = null;
		if (savingDv.getId() == 0) {
			dto = new SavingDto();
			dto.setProduct(savingDv.getProduct());
			dto.setCustomer(customerId);
			dto.setRegistration(savingDv.getRegistration());
		} else {
			dto = savingBp.getSaving(savingDv.getId());
			dto.setCustomer(customerId);
			dto.setCode(savingDv.getCode());
			dto.setProduct(savingDv.getProduct());
		}
		dto.setZakat(savingDv.getZakat() ? 1 : 0);
		return savingBp.saveSaving(key, dto);
	}

	@Override
	public List<DataDv> loadCommonListSaving(String key) {
		List<DataDv> result = new ArrayList<DataDv>();
		List<SavingProductDto> savingProductList = savingBp
				.listSavingProduct(key);
		for (SavingProductDto savingProduct : savingProductList) {
			DataDv dataDv = new DataDv();
			dataDv.setId(savingProduct.getId());
			dataDv.setNama(savingProduct.getName());
			result.add(dataDv);
		}
		return result;
	}

	@Override
	public Long saveCustomer(String key, CustomerDv customerDv) {
		return customerBp.saveCustomer(key, createCustomerDvToDto(customerDv));
	}

	@Override
	public List<DataDv> loadCommonListDeposit(String key) {
		List<DataDv> result = new ArrayList<DataDv>();
		List<DepositProductDto> dtos = depositBp.listDepositProduct(key);
		for (DepositProductDto dto : dtos) {
			DataDv dv = new DataDv();
			dv.setId(dto.getId());
			dv.setNama(dto.getName());
			dv.setRate(dto.getSharing());
			dv.setStrRate(df.format(dv.getRate()));
			result.add(dv);
		}
		return result;
	}

	@Override
	public Long saveDeposit(String key, DepositDv dv) {
		DepositDto dto = new DepositDto();
		dto.setId(dv.getId());
		dto.setProduct(dv.getProduct());
		dto.setCustomer(dv.getCustomer().getId());
		dto.setSaving(dv.getSaving().getId());
		dto.setValue(Double.parseDouble(dv.getStrValue().replace(",", "")));
		dto.setSpecialRate(Double.parseDouble(dv.getStrSpecialRate().replace(
				",", "")));
		dto.setAro(dv.getAro() ? 1 : 0);
		dto.setZakat(dv.getZakat() ? 1 : 0);
		dto.setBilyet(dv.getBilyet());
		dto.setCode(dv.getCode());
		dto.setRegistration(dv.getRegistration());
		return depositBp.saveDeposit(key, dto);
	}

	@Override
	public List<DataDv> findDeposit(String key, Boolean hasCode,
			Boolean hasName, Boolean hasDob, String code, String name, Date dob) {
		List<DataDv> dataList = new ArrayList<DataDv>();
		int i = 1;
		FindDepositDto findDepositDto = new FindDepositDto();
		if (hasCode) {
			findDepositDto.setWithCode(true);
			findDepositDto.setCode(code);
		}
		if (hasName) {
			findDepositDto.setWithName(true);
			findDepositDto.setName(name);
		}
		for (DepositDto depositDto : depositBp.findDeposit(key, findDepositDto)) {
			CustomerDto customerDto = customerBp.getCustomer(depositDto
					.getCustomer());
			DataDv data = new DataDv();
			data.setId(depositDto.getId());
			data.setNr(i++);
			data.setKode(depositDto.getCode());
			DepositProductDto depositProduct = depositBp
					.getDepositProduct(depositDto.getProduct());
			data.setProduk(depositDto.getProduct());
			data.setStrProduk(depositProduct.getName());
			data.setNama(customerDto.getName());
			data.setAlamat(customerDto.getAddress() + ", "
					+ customerDto.getCity() + " " + customerDto.getPostCode());
			dataList.add(data);
		}
		return dataList;
	}

	@Override
	public void closeSaving(long id, String reason) {
		savingBp.startCloseSaving(id, reason);
	}

	@Override
	public List<DataDv> loadCommonListProvinsi(String key) {
		List<DataDv> result = new ArrayList<DataDv>();
		List<RefProvCityDto> savingProductList = customerBp
				.listSavingProvinsi(key);
		for (RefProvCityDto savingProduct : savingProductList) {
			DataDv dataDv = new DataDv();
			dataDv.setId(savingProduct.getId());
			dataDv.setNama(savingProduct.getName());
			result.add(dataDv);
		}
		return result;
	}

	@Override
	public List<DataDv> loadCommonListCity(String key) {
		List<DataDv> result = new ArrayList<DataDv>();
		List<RefProvCityDto> savingProductList = customerBp.listSavingCity(key);
		for (RefProvCityDto savingProduct : savingProductList) {
			DataDv dataDv = new DataDv();
			dataDv.setId(savingProduct.getId());
			dataDv.setNama(savingProduct.getName());
			result.add(dataDv);
		}
		return result;
	}

	@Override
	public List<DataDv> loadCommonListJenisPekerjaan(String key) {
		List<DataDv> result = new ArrayList<DataDv>();
		List<RefOccupationTypeDto> savingProductList = customerBp
				.listSavingJenisPekerjaan(key);
		for (RefOccupationTypeDto savingProduct : savingProductList) {
			DataDv dataDv = new DataDv();
			dataDv.setId(savingProduct.getId());
			dataDv.setNama(savingProduct.getName());
			result.add(dataDv);
		}
		return result;
	}

	@Override
	public List<SavingBlockirDv> listBlockir(Long id) {
		List<SavingBlockirDv> result = new ArrayList<SavingBlockirDv>();
		List<SavingBlockirDto> blockirs = savingBp.listBlockir(id);
		for (SavingBlockirDto blockir : blockirs) {
			SavingBlockirDv dv = new SavingBlockirDv();
			dv.setId(blockir.getId());
			dv.setType(blockir.getType() - 1);
			dv.setDescription(blockir.getDescription());
			dv.setValue(blockir.getValue());
			result.add(dv);
		}
		return result;
	}

	@Override
	public void saveBlockir(SavingBlockirDataDv data) {
		Map<Long, SavingBlockirDto> dvExist = new HashMap<Long, SavingBlockirDto>();
		List<SavingBlockirDto> toBlock = new ArrayList<SavingBlockirDto>();
		for (SavingBlockirDv dv : data.getBlockir()) {
			SavingBlockirDto dto = new SavingBlockirDto();
			dto.setSaving(data.getSaving().getId());
			dto.setId(dv.getId());
			dto.setType(dv.getType() + 1);
			dto.setDescription(dv.getDescription() != null ? dv
					.getDescription().toUpperCase() : "");
			dto.setValue(dv.getValue());
			toBlock.add(dto);
			// Masukkan kalo id tidak 0
			if (dv.getId() != 0) {
				dvExist.put(dv.getId(), dto);
			}
		}
		// Cari yang di hapus
		List<SavingBlockirDto> blockirs = savingBp.listBlockir(data.getSaving()
				.getId());
		for (SavingBlockirDto blockir : blockirs) {
			if (dvExist.get(blockir.getId()) == null) {
				savingBp.removeBlockir(blockir.getId());
			}
		}
		// Simpan
		for (SavingBlockirDto blockir : toBlock){
			savingBp.saveBlockir(blockir);
		}
	}

}
