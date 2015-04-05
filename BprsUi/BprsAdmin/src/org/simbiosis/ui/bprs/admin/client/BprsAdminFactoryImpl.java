package org.simbiosis.ui.bprs.admin.client;

import org.kembang.module.client.mvp.KembangClientFactoryImpl;
import org.simbiosis.ui.bprs.admin.client.deposit.DepositEditor;
import org.simbiosis.ui.bprs.admin.client.deposit.DepositViewer;
import org.simbiosis.ui.bprs.admin.client.deposit.IDeposit;
import org.simbiosis.ui.bprs.admin.client.loan.ILoan;
import org.simbiosis.ui.bprs.admin.client.loan.LoanEditor;
import org.simbiosis.ui.bprs.admin.client.loan.LoanViewer;
import org.simbiosis.ui.bprs.admin.client.savingjournal.ISavingJournal;
import org.simbiosis.ui.bprs.admin.client.savingjournal.SavingJournalEditor;
import org.simbiosis.ui.bprs.admin.client.savingjournal.SavingJournalViewer;
import org.simbiosis.ui.bprs.admin.client.transfer.ITransfer;
import org.simbiosis.ui.bprs.admin.client.transfer.TransferEditor;
import org.simbiosis.ui.bprs.admin.client.transfer.TransferViewer;

public class BprsAdminFactoryImpl extends KembangClientFactoryImpl implements
		BprsAdminFactory {

	static final LoanViewer PEMBIAYAAN_VIEWER = new LoanViewer();
	static final LoanEditor PEMBIAYAAN_EDITOR = new LoanEditor();
	static final DepositViewer DEPOSITO_VIEWER = new DepositViewer();
	static final DepositEditor DEPOSITO_EDITOR = new DepositEditor();
	static final SavingJournalViewer SAVING_JOURNAL_VIEWER = new SavingJournalViewer();
	static final SavingJournalEditor SAVING_JOURNAL_EDITOR = new SavingJournalEditor();
	static final TransferViewer TRANSFER_VIEWER = new TransferViewer();
	static final TransferEditor TRANSFER_EDITOR = new TransferEditor();

	@Override
	public ILoan getPembiayaanViewer() {
		return PEMBIAYAAN_VIEWER;
	}

	@Override
	public ILoan getPembiayaanEditor() {
		return PEMBIAYAAN_EDITOR;
	}

	@Override
	public IDeposit getDepositoViewer() {
		return DEPOSITO_VIEWER;
	}

	@Override
	public IDeposit getDepositoEditor() {
		return DEPOSITO_EDITOR;
	}

	@Override
	public ISavingJournal getSavingJournalEditor() {
		return SAVING_JOURNAL_EDITOR;
	}

	@Override
	public ISavingJournal getSavingJournalViewer() {
		return SAVING_JOURNAL_VIEWER;
	}

	@Override
	public ITransfer getTransferViewer() {
		return TRANSFER_VIEWER;
	}

	@Override
	public ITransfer getTransferEditor() {
		return TRANSFER_EDITOR;
	}

}
