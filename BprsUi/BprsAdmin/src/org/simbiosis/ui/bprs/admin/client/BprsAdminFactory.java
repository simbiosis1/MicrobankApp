package org.simbiosis.ui.bprs.admin.client;

import org.kembang.module.client.mvp.KembangClientFactory;
import org.simbiosis.ui.bprs.admin.client.deposit.IDeposit;
import org.simbiosis.ui.bprs.admin.client.loan.ILoan;
import org.simbiosis.ui.bprs.admin.client.savingjournal.ISavingJournal;
import org.simbiosis.ui.bprs.admin.client.transfer.ITransfer;
import org.simbiosis.ui.bprs.admin.client.uploadcollective.IUploadCollective;

public interface BprsAdminFactory extends KembangClientFactory {
	ILoan getPembiayaanViewer();

	ILoan getPembiayaanEditor();

	IDeposit getDepositoViewer();

	IDeposit getDepositoEditor();

	ISavingJournal getSavingJournalEditor();

	ISavingJournal getSavingJournalViewer();

	ITransfer getTransferViewer();

	ITransfer getTransferEditor();

	IUploadCollective getUploadCollective();
}
