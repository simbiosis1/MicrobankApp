package org.simbiosis.bp.micbank;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.simbiosis.bp.gl.GlTransMsg;
import org.simbiosis.bp.gl.IGlTransMessaging;
import org.simbiosis.gl.ICoa;
import org.simbiosis.gl.model.GlTrans;
import org.simbiosis.gl.model.GlTransItem;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.SavingTransactionDto;
import org.simbiosis.system.BranchDto;
import org.simbiosis.system.ISystem;

@Stateless
@LocalBean
public class SavingTrans {

	@EJB(lookup = "java:global/SystemEar/SystemEjb/SystemImpl")
	ISystem system;
	@EJB(lookup = "java:global/GlEar/GlEjb/CoaImpl")
	ICoa iCoa;
	
	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/GlTransMessaging")
	IGlTransMessaging glMsg;

	public void createJournalGL(SavingTransactionDto transDto,
			SavingInformationDto infoSaving, long coa) {
		//
		String description = transDto.getDescription().toUpperCase();
		// Buat transaksi utama
		GlTrans glTrans = new GlTrans();
		glTrans.setDate(transDto.getDate());
		glTrans.setCode(transDto.getCode());
		glTrans.setCompany(transDto.getCompany());
		glTrans.setBranch(transDto.getBranch());
		glTrans.setDescription(description);
		glTrans.setType(1);
		glTrans.setReleased(1);
		//
		GlTransItem itemDto = new GlTransItem();
		itemDto.setDescription(description);
		itemDto.setDirection(transDto.getDirection() == 1 ? 2 : 1);
		itemDto.setValue(transDto.getValue());
		itemDto.setCoa(iCoa.get(infoSaving.getCoa1()));
		glTrans.getItems().add(itemDto);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription(description);
		itemDto.setDirection(transDto.getDirection());
		itemDto.setValue(transDto.getValue());
		itemDto.setCoa(iCoa.get(coa));
		glTrans.getItems().add(itemDto);
		//
		GlTransMsg transMsg = new GlTransMsg();
		transMsg.setIdSource(transDto.getId());
		transMsg.setQueueName("java:jboss/queue/transTellerIn");
		transMsg.setGlTransDto(glTrans);
		glMsg.sendGlTrans(transMsg);
	}

	public void createRAKJournalGL(SavingTransactionDto transDto,
			SavingInformationDto infoSaving, Long otherBranch, long coa) {
		// // Buat transaksi RAK
		BranchDto savingBranch = system.getBranch(infoSaving.getBranch());
		BranchDto userBranch = system.getBranch(otherBranch);

		String description = transDto.getDescription().toUpperCase();

		// Buat transaksi di tabungan
		GlTrans glTrans = new GlTrans();
		glTrans.setDate(transDto.getDate());
		glTrans.setCode(transDto.getCode());
		glTrans.setCompany(transDto.getCompany());
		glTrans.setBranch(infoSaving.getBranch());
		glTrans.setDescription(description);
		glTrans.setType(1);
		glTrans.setReleased(1);
		//
		GlTransItem itemDto = new GlTransItem();
		itemDto.setDescription(description);
		itemDto.setDirection(transDto.getDirection() == 1 ? 2 : 1);
		itemDto.setValue(transDto.getValue());
		itemDto.setCoa(iCoa.get(infoSaving.getCoa1()));
		glTrans.getItems().add(itemDto);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription(description);
		itemDto.setDirection(transDto.getDirection());
		itemDto.setValue(transDto.getValue());
		itemDto.setCoa(iCoa.get(userBranch.getParam1()));
		glTrans.getItems().add(itemDto);
		//
		GlTransMsg transMsg = new GlTransMsg();
		transMsg.setIdSource(transDto.getId());
		transMsg.setQueueName("java:jboss/queue/transTellerIn");
		transMsg.setGlTransDto(glTrans);
		glMsg.sendGlTrans(transMsg);

		// Buat transaksi di user
		glTrans = new GlTrans();
		glTrans.setDate(transDto.getDate());
		glTrans.setCode(transDto.getCode());
		glTrans.setCompany(transDto.getCompany());
		glTrans.setBranch(otherBranch);
		glTrans.setDescription(description);
		glTrans.setType(1);
		glTrans.setReleased(1);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription(description);
		itemDto.setDirection(transDto.getDirection() == 1 ? 2 : 1);
		itemDto.setValue(transDto.getValue());
		itemDto.setCoa(iCoa.get(savingBranch.getParam1()));
		glTrans.getItems().add(itemDto);
		//
		itemDto = new GlTransItem();
		itemDto.setDescription(description);
		itemDto.setDirection(transDto.getDirection());
		itemDto.setValue(transDto.getValue());
		itemDto.setCoa(iCoa.get(coa));
		glTrans.getItems().add(itemDto);
		//
		transMsg = new GlTransMsg();
		transMsg.setIdSource(transDto.getId());
		transMsg.setQueueName("java:jboss/queue/transTellerIn");
		transMsg.setGlTransDto(glTrans);
		glMsg.sendGlTrans(transMsg);

	}

}
