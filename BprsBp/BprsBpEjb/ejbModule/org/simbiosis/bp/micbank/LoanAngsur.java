package org.simbiosis.bp.micbank;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.simbiosis.bp.gl.GlTransMsg;
import org.simbiosis.bp.gl.IGlTransMessaging;
import org.simbiosis.gl.ICoa;
import org.simbiosis.gl.model.GlTrans;
import org.simbiosis.gl.model.GlTransItem;
import org.simbiosis.microbank.ILoan;
import org.simbiosis.microbank.ISaving;
import org.simbiosis.microbank.LoanInformationDto;
import org.simbiosis.microbank.LoanTransactionDto;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.system.BranchDto;
import org.simbiosis.system.ISystem;

@Stateless
@LocalBean
public class LoanAngsur {

	@EJB(lookup = "java:global/SystemEar/SystemEjb/SystemImpl")
	ISystem system;
	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/SavingImpl")
	ISaving saving;
	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/LoanImpl")
	ILoan loan;
	@EJB(lookup = "java:global/GlEar/GlEjb/CoaImpl")
	ICoa iCoa;
	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/GlTransMessaging")
	IGlTransMessaging glMsg;

	public void createAngsurMbhGL(LoanTransactionDto transDto,
			LoanInformationDto infoLoan, long savingId) {
		SavingInformationDto infoSaving = saving.getInformation(savingId);
		long akunPenampung = 0;
		// Kalo tabungan dan pembiayaan tidak di satu tempat
		if (infoSaving.getBranch() != transDto.getBranch()) {
			// Buat transaksi RAK
			BranchDto branch = system.getBranch(infoLoan.getBranch());
			//
			GlTrans glTrans = new GlTrans();
			glTrans.setDate(transDto.getDate());
			glTrans.setCode(transDto.getCode());
			glTrans.setCompany(transDto.getCompany());
			glTrans.setBranch(infoSaving.getBranch());
			glTrans.setDescription(transDto.getDescription());
			glTrans.setType(1);
			glTrans.setReleased(1);
			//
			GlTransItem itemDto = new GlTransItem();
			itemDto.setDescription(transDto.getDescription());
			itemDto.setDirection(2);
			itemDto.setValue(transDto.getPrincipal() + transDto.getMargin());
			itemDto.setCoa(iCoa.get(branch.getParam1()));
			glTrans.getItems().add(itemDto);
			//
			itemDto = new GlTransItem();
			itemDto.setDescription(transDto.getDescription());
			itemDto.setDirection(1);
			itemDto.setValue(transDto.getPrincipal() + transDto.getMargin());
			itemDto.setCoa(iCoa.get(infoSaving.getCoa1()));
			glTrans.getItems().add(itemDto);
			//
			GlTransMsg transMsg = new GlTransMsg();
			transMsg.setIdSource(transDto.getId());
			transMsg.setQueueName("java:jboss/queue/transTellerIn");
			transMsg.setGlTransDto(glTrans);
			glMsg.sendGlTrans(transMsg);
			//
			branch = system.getBranch(infoSaving.getBranch());
			akunPenampung = branch.getParam1();
		} else {
			akunPenampung = infoSaving.getCoa1();
		}
		// Buat transaksi utama
		GlTrans glTrans = new GlTrans();
		glTrans.setDate(transDto.getDate());
		glTrans.setCode(transDto.getCode());
		glTrans.setCompany(transDto.getCompany());
		glTrans.setBranch(transDto.getBranch());
		glTrans.setDescription(transDto.getDescription());
		glTrans.setType(1);
		glTrans.setReleased(1);
		//
		GlTransItem itemDto = new GlTransItem();
		itemDto.setDescription(transDto.getDescription());
		itemDto.setDirection(1);
		itemDto.setValue(transDto.getPrincipal() + transDto.getMargin());
		itemDto.setCoa(iCoa.get(akunPenampung));
		glTrans.getItems().add(itemDto);
		//
		double loanMarginDiscount = transDto.getMargin()
				+ (transDto.getDiscount() > 0 ? transDto.getDiscount() : 0);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription(transDto.getDescription());
		itemDto.setDirection(2);
		itemDto.setValue(transDto.getPrincipal() + loanMarginDiscount);
		itemDto.setCoa(iCoa.get(infoLoan.getCoa1()));
		glTrans.getItems().add(itemDto);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription(transDto.getDescription());
		itemDto.setDirection(1);
		itemDto.setValue(loanMarginDiscount);
		itemDto.setCoa(iCoa.get(infoLoan.getCoa2()));
		glTrans.getItems().add(itemDto);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription(transDto.getDescription());
		itemDto.setDirection(2);
		itemDto.setValue(loanMarginDiscount);
		itemDto.setCoa(iCoa.get(infoLoan.getCoa3()));
		glTrans.getItems().add(itemDto);
		//
		if (transDto.getDiscount() > 0) {
			itemDto = new GlTransItem();
			itemDto.setDescription(transDto.getDescription());
			itemDto.setDirection(1);
			itemDto.setValue(transDto.getDiscount());
			itemDto.setCoa(iCoa.get(infoLoan.getCoa4()));
			glTrans.getItems().add(itemDto);
		}
		//
		GlTransMsg transMsg = new GlTransMsg();
		transMsg.setIdSource(transDto.getId());
		transMsg.setQueueName("java:jboss/queue/transTellerIn");
		transMsg.setGlTransDto(glTrans);
		glMsg.sendGlTrans(transMsg);
	}

	public void createAngsurIjarahGL(LoanTransactionDto transDto,
			LoanInformationDto infoLoan, long savingId) {
		SavingInformationDto infoSaving = saving.getInformation(savingId);
		long akunPenampung = 0;
		// Kalo tabungan dan pembiayaan tidak di satu tempat
		if (infoSaving.getBranch() != transDto.getBranch()) {
			// Buat transaksi RAK
			BranchDto branch = system.getBranch(infoLoan.getBranch());
			//
			GlTrans glTrans = new GlTrans();
			glTrans.setDate(transDto.getDate());
			glTrans.setCode(transDto.getCode());
			glTrans.setCompany(transDto.getCompany());
			glTrans.setBranch(infoSaving.getBranch());
			glTrans.setDescription(transDto.getDescription());
			glTrans.setType(1);
			glTrans.setReleased(1);
			//
			GlTransItem itemDto = new GlTransItem();
			itemDto.setDescription(transDto.getDescription());
			itemDto.setDirection(2);
			itemDto.setValue(transDto.getPrincipal() + transDto.getMargin());
			itemDto.setCoa(iCoa.get(branch.getParam1()));
			glTrans.getItems().add(itemDto);
			//
			itemDto = new GlTransItem();
			itemDto.setDescription(transDto.getDescription());
			itemDto.setDirection(1);
			itemDto.setValue(transDto.getPrincipal() + transDto.getMargin());
			itemDto.setCoa(iCoa.get(infoSaving.getCoa1()));
			glTrans.getItems().add(itemDto);
			//
			GlTransMsg transMsg = new GlTransMsg();
			transMsg.setIdSource(transDto.getId());
			transMsg.setQueueName("java:jboss/queue/transTellerIn");
			transMsg.setGlTransDto(glTrans);
			glMsg.sendGlTrans(transMsg);
			//
			branch = system.getBranch(infoSaving.getBranch());
			akunPenampung = branch.getParam1();
		} else {
			akunPenampung = infoSaving.getCoa1();
		}
		// Buat transaksi utama
		GlTrans glTrans = new GlTrans();
		glTrans.setDate(transDto.getDate());
		glTrans.setCode(transDto.getCode());
		glTrans.setCompany(transDto.getCompany());
		glTrans.setBranch(transDto.getBranch());
		glTrans.setDescription(transDto.getDescription());
		glTrans.setType(1);
		glTrans.setReleased(1);
		//
		GlTransItem itemDto = new GlTransItem();
		itemDto.setDescription(transDto.getDescription());
		itemDto.setDirection(1);
		itemDto.setValue(transDto.getPrincipal() + transDto.getMargin());
		itemDto.setCoa(iCoa.get(akunPenampung));
		glTrans.getItems().add(itemDto);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription(transDto.getDescription());
		itemDto.setDirection(2);
		itemDto.setValue(transDto.getPrincipal());
		itemDto.setCoa(iCoa.get(infoLoan.getCoa1()));
		glTrans.getItems().add(itemDto);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription(transDto.getDescription());
		itemDto.setDirection(2);
		itemDto.setValue(transDto.getPrincipal() + transDto.getMargin()
				+ (transDto.getDiscount() > 0 ? transDto.getDiscount() : 0));
		itemDto.setCoa(iCoa.get(infoLoan.getCoa2()));
		glTrans.getItems().add(itemDto);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription(transDto.getDescription());
		itemDto.setDirection(1);
		itemDto.setValue(transDto.getPrincipal());
		itemDto.setCoa(iCoa.get(infoLoan.getCoa3()));
		glTrans.getItems().add(itemDto);
		//
		if (transDto.getDiscount() > 0) {
			itemDto = new GlTransItem();
			itemDto.setDescription(transDto.getDescription());
			itemDto.setDirection(1);
			itemDto.setValue(transDto.getDiscount());
			itemDto.setCoa(iCoa.get(infoLoan.getCoa4()));
			glTrans.getItems().add(itemDto);
		}
		//
		GlTransMsg transMsg = new GlTransMsg();
		transMsg.setIdSource(transDto.getId());
		transMsg.setQueueName("java:jboss/queue/transTellerIn");
		transMsg.setGlTransDto(glTrans);
		glMsg.sendGlTrans(transMsg);
	}

	public void createAngsurQordhGL(LoanTransactionDto transDto,
			LoanInformationDto infoLoan, long savingId) {
		SavingInformationDto infoSaving = saving.getInformation(savingId);
		long akunPenampung = 0;
		// Kalo tabungan dan pembiayaan tidak di satu tempat
		if (infoSaving.getBranch() != transDto.getBranch()) {
			// Buat transaksi RAK
			BranchDto branch = system.getBranch(infoLoan.getBranch());
			//
			GlTrans glTrans = new GlTrans();
			glTrans.setDate(transDto.getDate());
			glTrans.setCode(transDto.getCode());
			glTrans.setCompany(transDto.getCompany());
			glTrans.setBranch(infoSaving.getBranch());
			glTrans.setDescription(transDto.getDescription());
			glTrans.setType(1);
			glTrans.setReleased(1);
			//
			GlTransItem itemDto = new GlTransItem();
			itemDto.setDescription(transDto.getDescription());
			itemDto.setDirection(2);
			itemDto.setValue(transDto.getPrincipal() + transDto.getMargin());
			itemDto.setCoa(iCoa.get(branch.getParam1()));
			glTrans.getItems().add(itemDto);
			//
			itemDto = new GlTransItem();
			itemDto.setDescription(transDto.getDescription());
			itemDto.setDirection(1);
			itemDto.setValue(transDto.getPrincipal() + transDto.getMargin());
			itemDto.setCoa(iCoa.get(infoSaving.getCoa1()));
			glTrans.getItems().add(itemDto);
			//
			GlTransMsg transMsg = new GlTransMsg();
			transMsg.setIdSource(transDto.getId());
			transMsg.setQueueName("java:jboss/queue/transTellerIn");
			transMsg.setGlTransDto(glTrans);
			glMsg.sendGlTrans(transMsg);
			//
			branch = system.getBranch(infoSaving.getBranch());
			akunPenampung = branch.getParam1();
		} else {
			akunPenampung = infoSaving.getCoa1();
		}
		// Buat transaksi utama
		GlTrans glTrans = new GlTrans();
		glTrans.setDate(transDto.getDate());
		glTrans.setCode(transDto.getCode());
		glTrans.setCompany(transDto.getCompany());
		glTrans.setBranch(transDto.getBranch());
		glTrans.setDescription(transDto.getDescription());
		glTrans.setType(1);
		glTrans.setReleased(1);
		//
		GlTransItem itemDto = new GlTransItem();
		itemDto.setDescription(transDto.getDescription());
		itemDto.setDirection(1);
		itemDto.setValue(transDto.getPrincipal() + transDto.getMargin());
		itemDto.setCoa(iCoa.get(akunPenampung));
		glTrans.getItems().add(itemDto);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription(transDto.getDescription());
		itemDto.setDirection(2);
		itemDto.setValue(transDto.getPrincipal());
		itemDto.setCoa(iCoa.get(infoLoan.getCoa1()));
		glTrans.getItems().add(itemDto);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription(transDto.getDescription());
		itemDto.setDirection(2);
		itemDto.setValue(transDto.getMargin());
		itemDto.setCoa(iCoa.get(infoLoan.getCoa3()));
		glTrans.getItems().add(itemDto);
		//
		GlTransMsg transMsg = new GlTransMsg();
		transMsg.setIdSource(transDto.getId());
		transMsg.setQueueName("java:jboss/queue/transTellerIn");
		transMsg.setGlTransDto(glTrans);
		glMsg.sendGlTrans(transMsg);
	}

}
