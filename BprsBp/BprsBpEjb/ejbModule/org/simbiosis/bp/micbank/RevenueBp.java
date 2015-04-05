package org.simbiosis.bp.micbank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.simbiosis.bp.gl.GlTransMsg;
import org.simbiosis.bp.gl.IGlTransMessaging;
import org.simbiosis.gl.ICoa;
import org.simbiosis.gl.ILedger;
import org.simbiosis.gl.model.Coa;
import org.simbiosis.gl.model.GlTrans;
import org.simbiosis.gl.model.GlTransItem;
import org.simbiosis.microbank.DepositInformationDto;
import org.simbiosis.microbank.DepositRevSharingDto;
import org.simbiosis.microbank.IDeposit;
import org.simbiosis.microbank.IRevenue;
import org.simbiosis.microbank.RevenueDto;
import org.simbiosis.microbank.RevenueSharingDto;
import org.simbiosis.system.ISystem;
import org.simbiosis.system.UserDto;

@Stateless
@Remote(IRevenueBp.class)
public class RevenueBp implements IRevenueBp {

	@EJB(lookup = "java:global/SystemEar/SystemEjb/SystemImpl")
	ISystem iSystem;
	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/RevenueImpl")
	IRevenue iRevenue;
	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/DepositImpl")
	IDeposit iDeposit;
	@EJB(lookup = "java:global/GlEar/GlEjb/CoaImpl")
	ICoa iCoa;
	@EJB(lookup = "java:global/GlEar/GlEjb/LedgerImpl")
	ILedger iLedger;

	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/GlTransMessaging")
	IGlTransMessaging glMsg;

	@Override
	public void saveDepositRevSharing(DepositRevSharingDto revenue) {
		iRevenue.saveDepositRevSharing(revenue);
		//
		DepositInformationDto di = iDeposit.getDepositInformation(revenue
				.getDeposit());
		createJournalGL(revenue, di);
	}

	public void createJournalGL(DepositRevSharingDto revenue,
			DepositInformationDto di) {
		//
		String description = "CADANGAN BAHAS DEPOSITO - " + di.getName() + " ("
				+ di.getCode() + ")";
		// Buat transaksi utama
		GlTrans glTrans = new GlTrans();
		glTrans.setDate(revenue.getCalcDate());
		glTrans.setCode("CAD-" + di.getCode());
		glTrans.setCompany(di.getCompany());
		glTrans.setBranch(di.getBranch());
		glTrans.setDescription(description);
		glTrans.setType(1);
		glTrans.setReleased(1);
		//
		GlTransItem itemDto = new GlTransItem();
		itemDto.setDescription(description);
		itemDto.setDirection(2);
		itemDto.setValue(revenue.getCustomerSharing());
		itemDto.setCoa(iCoa.get(di.getCoa3()));
		glTrans.getItems().add(itemDto);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription(description);
		itemDto.setDirection(1);
		itemDto.setValue(revenue.getCustomerSharing());
		itemDto.setCoa(iCoa.get(di.getCoa2()));
		glTrans.getItems().add(itemDto);
		//
		GlTransMsg transMsg = new GlTransMsg();
		transMsg.setIdSource(1);
		transMsg.setQueueName("java:jboss/queue/transTellerIn");
		transMsg.setGlTransDto(glTrans);
		glMsg.sendGlTrans(transMsg);
	}

	@Override
	public List<RevenueDto> listRevenueByProduct(String key, Date beginDate,
			Date endDate) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			return iRevenue.listRevenueByProduct(user.getCompany(), beginDate,
					endDate);
		}
		return new ArrayList<RevenueDto>();
	}

	@Override
	public List<RevenueDto> listRevenueByCoa(String key, Date beginDate,
			Date endDate) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			List<RevenueDto> result = iRevenue.listRevenueByCoa(user
					.getCompany());
			for (RevenueDto dto : result) {
				Coa coa = iCoa.get(dto.getProduct());
				Double value = iLedger.getSumGlTrans(coa.getId(), 2, beginDate,
						endDate);
				dto.setProductName(coa.getDescription());
				dto.setValue(value);
			}
			return result;
		}
		return new ArrayList<RevenueDto>();
	}

	@Override
	public List<DepositRevSharingDto> listDepositRevSharing(String key,
			Date date) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			return iRevenue.listDepositRevSharing(user.getCompany(), date);
		}
		return new ArrayList<DepositRevSharingDto>();
	}

	@Override
	public List<RevenueSharingDto> listRevenueSharing(String key, int month,
			int year, long id) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			return iRevenue.listRevenueSharing(user.getCompany(), month, year,
					id);
		}
		return new ArrayList<RevenueSharingDto>();
	}

	@Override
	public void saveRevenueSharing(RevenueSharingDto revenue) {
		iRevenue.saveRevenueSharing(revenue);
	}

	@Override
	public void saveRevenue(RevenueDto revenue) {
		iRevenue.saveRevenue(revenue);
	}

	@Override
	public void saveDepositRevSharingStatus(long id, int status) {
		iRevenue.saveDepositRevSharingStatus(id, status);
	}

}
