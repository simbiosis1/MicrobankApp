package org.simbiosis.report.loan.ui.server;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.kembang.module.shared.UserDv;
import org.simbiosis.bp.micbank.IDepositBp;
import org.simbiosis.bp.micbank.ILoanBp;
import org.simbiosis.bp.micbank.ISavingBp;
import org.simbiosis.bp.system.ISystemBp;
import org.simbiosis.microbank.DepositProductDto;
import org.simbiosis.microbank.LoanProductDto;
import org.simbiosis.microbank.SavingProductDto;
import org.simbiosis.report.loan.ui.client.rpc.ReportSrv;
import org.simbiosis.report.loan.ui.shared.DataDv;
import org.simbiosis.system.UserDto;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ReportSrvImpl extends RemoteServiceServlet implements ReportSrv {

	@EJB(lookup = "java:global/SystemBpEar/SystemBpEjb/SystemBp")
	ISystemBp iSystemBp;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/LoanBp")
	ILoanBp loanBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/SavingBp")
	ISavingBp savingBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/DepositBp")
	IDepositBp depositBp;

	public ReportSrvImpl() {
	}

	@Override
	public List<UserDv> listAo(String key, long branch)
			throws IllegalArgumentException {
		List<UserDv> result = new ArrayList<UserDv>();
		List<Long> users = loanBp.listAoLoanId(key, branch);
		boolean found = false;
		UserDto userDto = iSystemBp.getUserFromSession(key);
		for (Long user : users) {
			UserDto ao = iSystemBp.getUser(user);
			if (userDto.getLevel() <= 5 && found == false) {
				UserDv dv = new UserDv();
				dv.setId(0L);
				dv.setRealName("SELURUH AO");
				result.add(dv);
				found = true;
			}
			if (ao.getLevel() >= userDto.getLevel()) {
				if (userDto.getLevel() == 7) {
					if (userDto.getId() == ao.getId()) {
						UserDv dv = new UserDv();
						dv.setId(user);
						dv.setRealName(ao.getRealName());
						result.add(dv);
					}
				} else {
					UserDv dv = new UserDv();
					dv.setId(user);
					dv.setRealName(ao.getRealName());
					result.add(dv);
				}
			}
		}
		return result;
	}

	@Override
	public List<DataDv> loadListLoanProduct(String key) {
		List<DataDv> result = new ArrayList<DataDv>();
		List<LoanProductDto> financingProductList = loanBp.listLoanProduct(key);
		DataDv dataDv = new DataDv();
		dataDv.setId(0L);
		dataDv.setNama("SELURUH PRODUK");
		result.add(dataDv);
		for (LoanProductDto financingProduct : financingProductList) {
			dataDv = new DataDv();
			dataDv.setId(financingProduct.getId());
			dataDv.setNama(financingProduct.getName());
			result.add(dataDv);
		}
		return result;
	}

	@Override
	public List<DataDv> loadListSavingProduct(String key) {
		List<DataDv> result = new ArrayList<DataDv>();
		List<SavingProductDto> savingProductList = savingBp
				.listSavingProduct(key);
		DataDv dataDv = new DataDv();
		dataDv.setId(0L);
		dataDv.setNama("SELURUH PRODUK");
		result.add(dataDv);
		for (SavingProductDto savingProduct : savingProductList) {
			dataDv = new DataDv();
			dataDv.setId(savingProduct.getId());
			dataDv.setNama(savingProduct.getName());
			result.add(dataDv);
		}
		return result;
	}

	@Override
	public List<DataDv> loadListDepositProduct(String key) {
		List<DataDv> result = new ArrayList<DataDv>();
		List<DepositProductDto> financingProductList = depositBp
				.listDepositProduct(key);
		DataDv dataDv = new DataDv();
		dataDv.setId(0L);
		dataDv.setNama("SELURUH PRODUK");
		result.add(dataDv);
		for (DepositProductDto financingProduct : financingProductList) {
			dataDv = new DataDv();
			dataDv.setId(financingProduct.getId());
			dataDv.setNama(financingProduct.getName());
			result.add(dataDv);
		}
		return result;
	}

}
