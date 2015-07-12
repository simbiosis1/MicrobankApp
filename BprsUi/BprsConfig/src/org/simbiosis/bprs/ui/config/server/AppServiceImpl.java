package org.simbiosis.bprs.ui.config.server;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.kembang.module.shared.SimpleBranchDv;
import org.kembang.module.shared.UserDv;
import org.simbiosis.bp.gl.IGlBp;
import org.simbiosis.bp.micbank.IDepositBp;
import org.simbiosis.bp.micbank.ILoanBp;
import org.simbiosis.bp.micbank.ISavingBp;
import org.simbiosis.bp.micbank.ITellerBp;
import org.simbiosis.bp.system.ISystemBp;
import org.simbiosis.bprs.ui.config.client.rpc.AppService;
import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.ConfigDv;
import org.simbiosis.bprs.ui.config.shared.ProductDv;
import org.simbiosis.bprs.ui.config.shared.SubBranchDv;
import org.simbiosis.bprs.ui.config.shared.TellerDv;
import org.simbiosis.gl.model.Coa;
import org.simbiosis.microbank.DepositProductDto;
import org.simbiosis.microbank.LoanProductDto;
import org.simbiosis.microbank.SavingProductDto;
import org.simbiosis.microbank.TellerDto;
import org.simbiosis.system.BranchDto;
import org.simbiosis.system.ConfigDto;
import org.simbiosis.system.SubBranchDto;
import org.simbiosis.system.UserDto;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class AppServiceImpl extends RemoteServiceServlet implements AppService {

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/TellerBp")
	ITellerBp tellerBp;
	@EJB(lookup = "java:global/SystemBpEar/SystemBpEjb/SystemBp")
	ISystemBp systemBp;
	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/GlBp")
	IGlBp glBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/SavingBp")
	ISavingBp savingBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/DepositBp")
	IDepositBp depositBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/LoanBp")
	ILoanBp loanBp;

	DecimalFormat nf = new DecimalFormat("#,##0.00");

	String savingSchema[] = { "", "WADIAH", "MUDHARABAH" };
	String loanSchema[] = { "", "MURABAHAH", "SALAM", "ISTISHNA", "IJARAH",
			"MUDHARABAH", "MUSYARAKAH", "QARDH", "MULTIJASA", "", "",
			"KONVENSIONAL" };
	String bonus[] = { "TIDAK", "YA" };

	public AppServiceImpl() {
	}

	@Override
	public List<SimpleBranchDv> listTerm(String key)
			throws IllegalArgumentException {
		List<SimpleBranchDv> result = new ArrayList<SimpleBranchDv>();
		SimpleBranchDv dv = new SimpleBranchDv();
		dv.setId(1L);
		dv.setName("1 BULAN");
		result.add(dv);
		dv = new SimpleBranchDv();
		dv.setId(3L);
		dv.setName("3 BULAN");
		result.add(dv);
		dv = new SimpleBranchDv();
		dv.setId(6L);
		dv.setName("6 BULAN");
		result.add(dv);
		dv = new SimpleBranchDv();
		dv.setId(12L);
		dv.setName("12 BULAN");
		result.add(dv);

		return result;
	}

	@Override
	public List<SubBranchDv> listSubBranch(String key)
			throws IllegalArgumentException {
		List<SubBranchDv> result = new ArrayList<SubBranchDv>();
		List<SubBranchDto> dtos = systemBp.listSubBranch(key);
		int i = 1;
		for (SubBranchDto dto : dtos) {
			SubBranchDv dv = new SubBranchDv();
			dv.setNr(i++);
			dv.setId(dto.getId());
			dv.setCode(dto.getCode());
			dv.setName(dto.getName());
			dv.setAddress(dto.getAddress());
			dv.setBranch(dto.getBranch());
			dv.setStrBranch(dto.getBranchName());
			result.add(dv);
		}
		return result;
	}

	@Override
	public List<UserDv> listUsers(String key) throws IllegalArgumentException {
		List<UserDv> result = new ArrayList<UserDv>();
		List<UserDto> users = systemBp.listUsers(key);
		for (UserDto user : users) {
			UserDv dv = new UserDv();
			dv.setId(user.getId());
			dv.setRealName(user.getRealName());
			result.add(dv);
		}
		return result;
	}

	@Override
	public void saveTeller(String key, TellerDv dv)
			throws IllegalArgumentException {
		TellerDto dto = new TellerDto();
		dto.setId(dv.getId());
		dto.setCode(dv.getCode() != null ? dv.getCode().toUpperCase() : "");
		dto.setUser(dv.getUser());
		dto.setCoa(dv.getCoa());
		dto.setBranch(dv.getBranch());
		dto.setSubBranch(dv.getSubBranch());
		tellerBp.saveTeller(key, dto);
	}

	@Override
	public List<TellerDv> listTeller(String key)
			throws IllegalArgumentException {
		List<TellerDv> result = new ArrayList<TellerDv>();
		List<TellerDto> dtos = tellerBp.listTeller(key);
		int nr = 1;
		for (TellerDto dto : dtos) {
			TellerDv dv = new TellerDv();
			dv.setNr(nr++);
			dv.setId(dto.getId());
			dv.setCode(dto.getCode());
			dv.setBranch(dto.getBranch());
			if (dv.getBranch() == 0) {
				dv.setStrBranch("SEMUA CABANG");
			} else {
				BranchDto bdto = systemBp.getBranch(dv.getBranch());
				dv.setStrBranch(bdto.getCode() + " - " + bdto.getName());
			}
			dv.setSubBranch(dto.getSubBranch());
			if (dv.getSubBranch() == 0) {
				dv.setStrSubBranch("SEMUA KAS");
			} else {
				SubBranchDto sbdto = systemBp.getSubBranch(dv.getSubBranch());
				dv.setStrSubBranch(sbdto.getName());
			}
			dv.setName(dto.getName());
			dv.setCoa(dto.getCoa());
			Coa cdto = glBp.getCoa(dv.getCoa());
			dv.setStrCoa(cdto.getDescription());
			dv.setUser(dto.getUser());
			UserDto userDto = systemBp.getUser(dv.getUser());
			dv.setStrUser(userDto.getRealName());
			result.add(dv);
		}
		return result;
	}

	@Override
	public void saveLoanProduct(String key, ProductDv dv)
			throws IllegalArgumentException {
		LoanProductDto dto = new LoanProductDto();
		dto.setId(dv.getId());
		dto.setCoa1(dv.getCoa1());
		dto.setCoa2(dv.getCoa2());
		dto.setCoa3(dv.getCoa3());
		dto.setCoa4(dv.getCoa4());
		dto.setCoa5(dv.getCoa5());
		dto.setCoa6(dv.getCoa6());
		dto.setName(dv.getName() != null ? dv.getName().toUpperCase() : "");
		dto.setCode(dv.getCode() != null ? dv.getCode().toUpperCase() : "");
		dto.setSchema(dv.getSchema());
		dto.setProfitShared(dv.getHasShare() ? 1 : 0);
		loanBp.saveLoanProduct(key, dto);
	}

	@Override
	public List<ProductDv> listLoanProduct(String key)
			throws IllegalArgumentException {
		List<ProductDv> result = new ArrayList<ProductDv>();
		List<LoanProductDto> products = loanBp.listLoanProduct(key);
		int nr = 1;
		for (LoanProductDto product : products) {
			ProductDv dv = new ProductDv();
			dv.setNr(nr++);
			dv.setId(product.getId());
			dv.setCoa1(product.getCoa1());
			Coa coaDto = glBp.getCoa(product.getCoa1());
			dv.setStrCoa1(coaDto.getCode() + " - " + coaDto.getDescription());
			dv.setCoa2(product.getCoa2());
			if (product.getSchema() < 5 || product.getSchema() == 8) {
				Coa dto2 = glBp.getCoa(product.getCoa2());
				dv.setStrCoa2(dto2.getCode() + " - " + dto2.getDescription());
			}
			dv.setCoa3(product.getCoa3());
			Coa dto3 = glBp.getCoa(product.getCoa3());
			dv.setStrCoa3(dto3.getCode() + " - " + dto3.getDescription());
			dv.setCoa4(product.getCoa4());
			if (product.getSchema() < 5 || product.getSchema() == 8) {
				Coa dto4 = glBp.getCoa(product.getCoa4());
				dv.setStrCoa4(dto4.getCode() + " - " + dto4.getDescription());
			}
			dv.setCoa5(product.getCoa5());
			dv.setCoa6(product.getCoa6());
			if (product.getSchema() < 4 || product.getSchema() == 8) {
				Coa dto5 = glBp.getCoa(product.getCoa5());
				dv.setStrCoa5(dto5.getCode() + " - " + dto5.getDescription());
				Coa dto6 = glBp.getCoa(product.getCoa6());
				dv.setStrCoa6(dto6.getCode() + " - " + dto6.getDescription());
			}
			dv.setName(product.getName());
			dv.setCode(product.getCode());
			dv.setSchema(product.getSchema());
			dv.setStrSchema(loanSchema[product.getSchema()]);
			dv.setHasShare(product.getProfitShared() == 1);
			dv.setStrHasShare(dv.getHasShare() ? "YA" : "TIDAK");
			result.add(dv);
		}
		return result;
	}

	@Override
	public void saveDepProduct(String key, ProductDv dv)
			throws IllegalArgumentException {
		DepositProductDto dto = new DepositProductDto();
		dto.setId(dv.getId());
		dto.setCoa1(dv.getCoa1());
		dto.setCoa2(dv.getCoa2());
		dto.setCoa3(dv.getCoa3());
		dto.setCode(dv.getCode());
		dto.setName(dv.getName() != null ? dv.getName().toUpperCase() : "");
		dto.setSharing(dv.getSharing());
		dto.setCode(dv.getCode() != null ? dv.getCode().toUpperCase() : "");
		dto.setTerm(Integer.parseInt(String.valueOf(dv.getTerm())));
		depositBp.saveDepositProduct(key, dto);
	}

	@Override
	public List<ProductDv> listDepProduct(String key)
			throws IllegalArgumentException {
		List<ProductDv> result = new ArrayList<ProductDv>();
		List<DepositProductDto> products = depositBp.listDepositProduct(key);
		int nr = 1;
		for (DepositProductDto product : products) {
			ProductDv dv = new ProductDv();
			dv.setNr(nr++);
			dv.setId(product.getId());
			dv.setCoa1(product.getCoa1());
			Coa dto = glBp.getCoa(product.getCoa1());
			dv.setStrCoa1(dto.getCode() + " - " + dto.getDescription());
			dv.setCoa2(product.getCoa2());
			Coa dto2 = glBp.getCoa(product.getCoa2());
			dv.setStrCoa2(dto2.getCode() + " - " + dto2.getDescription());
			dv.setCoa3(product.getCoa3());
			Coa dto3 = glBp.getCoa(product.getCoa3());
			dv.setStrCoa3(dto3.getCode() + " - " + dto3.getDescription());
			dv.setName(product.getName());
			dv.setSharing(product.getSharing());
			dv.setTerm(Long.parseLong(String.valueOf(product.getTerm())));
			dv.setStrTerm(String.valueOf(product.getTerm() + " BULAN"));
			dv.setCode(product.getCode());
			result.add(dv);
		}
		return result;
	}

	@Override
	public void saveSavingProduct(String key, ProductDv dv)
			throws IllegalArgumentException {
		SavingProductDto dto = new SavingProductDto();
		dto.setId(dv.getId());
		dto.setCoa1(dv.getCoa1());
		dto.setCoa2(dv.getCoa2());
		dto.setCoa3(dv.getCoa3());
		dto.setCoa4(dv.getCoa4());
		dto.setName(dv.getName() != null ? dv.getName().toUpperCase() : "");
		dto.setCode(dv.getCode() != null ? dv.getCode().toUpperCase() : "");
		dto.setSchema(dv.getSchema());
		dto.setSharing(dto.getSchema() == 1 ? 0 : dv.getSharing());
		dto.setHasShare(dv.getHasShare() ? 1 : 0);
		dto.setMinValue(dv.getMinValue());
		dto.setMinSharable(dv.getMinSharable());
		dto.setCloseAdmin(dv.getCloseAdmin());
		dto.setMonthlyAdmin(dv.getMonthlyAdmin());
		savingBp.saveSavingProduct(key, dto);
	}

	@Override
	public List<ProductDv> listSavingProduct(String key)
			throws IllegalArgumentException {
		List<ProductDv> result = new ArrayList<ProductDv>();
		List<SavingProductDto> products = savingBp.listSavingProduct(key);
		int nr = 1;
		for (SavingProductDto product : products) {
			ProductDv dv = new ProductDv();
			dv.setNr(nr);
			dv.setId(product.getId());
			dv.setCoa1(product.getCoa1());
			Coa dto = glBp.getCoa(product.getCoa1());
			dv.setStrCoa1(dto.getCode() + " - " + dto.getDescription());
			dv.setCoa2(product.getCoa2());
			Coa dto2 = glBp.getCoa(product.getCoa2());
			dv.setStrCoa2(dto2.getCode() + " - " + dto2.getDescription());
			dv.setCoa3(product.getCoa3());
			Coa dto3 = glBp.getCoa(product.getCoa3());
			dv.setStrCoa3(dto3.getCode() + " - " + dto3.getDescription());
			dv.setCoa4(product.getCoa4());
			Coa dto4 = glBp.getCoa(product.getCoa4());
			dv.setStrCoa4(dto4.getCode() + " - " + dto4.getDescription());
			dv.setName(product.getName());
			dv.setCode(product.getCode());
			dv.setSchema(product.getSchema());
			dv.setStrSchema(savingSchema[product.getSchema()]);
			dv.setSharing(product.getSharing());
			dv.setHasShare(product.getHasShare() == 1);
			dv.setStrHasShare(bonus[product.getHasShare()]);
			dv.setMinValue(product.getMinValue());
			dv.setMinSharable(product.getMinSharable());
			dv.setCloseAdmin(product.getCloseAdmin());
			dv.setMonthlyAdmin(product.getMonthlyAdmin());
			result.add(dv);
			nr++;
		}
		return result;
	}

	private CoaDv createCoaDv(Coa coa) {
		CoaDv coaDv = new CoaDv();
		coaDv.setId(coa.getId());
		coaDv.setCode(coa.getCode());
		coaDv.setDescription(coa.getDescription());
		return coaDv;
	}

	@Override
	public List<CoaDv> listCoaForTransaction(String key) {
		List<CoaDv> returnList = new ArrayList<CoaDv>();
		for (Coa coa : glBp.listCoaForTransaction(key)) {
			returnList.add(createCoaDv(coa));
		}
		return returnList;
	}

	String getCoaDescription(long id) {
		Coa coa = glBp.getCoa(id);
		return coa == null ? "-" : (coa.getCode() + " - " + coa
				.getDescription());
	}

	@Override
	public ConfigDv loadConfig(String key) {
		ConfigDv config = new ConfigDv();
		ConfigDto dto = systemBp.getConfig(key, "vault.coa");
		config.setVault(dto == null ? 0 : dto.getLongValue());
		config.setStrVault(getCoaDescription(config.getVault()));
		//
		dto = systemBp.getConfig(key, "teller.RabCoa");
		config.setRab(dto == null ? 0 : dto.getLongValue());
		config.setStrRab(getCoaDescription(config.getRab()));
		//
		return config;
	}

	@Override
	public void saveConfig(String key, ConfigDv config) {
		ConfigDto dto = new ConfigDto();
		dto.setKey("vault.coa");
		dto.setLongValue(config.getVault());
		systemBp.saveConfig(key, dto);
		//
		dto = new ConfigDto();
		dto.setKey("teller.RabCoa");
		dto.setLongValue(config.getRab());
		systemBp.saveConfig(key, dto);
		//
	}
}
