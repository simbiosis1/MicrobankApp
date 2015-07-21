package org.simbiosis.ui.bprs.teller.client;

import org.kembang.module.client.mvp.KembangClientFactory;
import org.simbiosis.ui.bprs.teller.client.cashtrans.ICashTrans;
import org.simbiosis.ui.bprs.teller.client.htvault.IHtVault;
import org.simbiosis.ui.bprs.teller.client.kolektif.IUploadCollective;
import org.simbiosis.ui.bprs.teller.client.loanrep.ILoanRepayment;
import org.simbiosis.ui.bprs.teller.client.savingdeposit.IDeposit;
import org.simbiosis.ui.bprs.teller.client.savingprint.ISavingPrint;
import org.simbiosis.ui.bprs.teller.client.savingtrans.ISavingTransList;
import org.simbiosis.ui.bprs.teller.client.savingwd.IWithdrawal;
import org.simbiosis.ui.bprs.teller.client.tellertrans.ITellerTransList;
import org.simbiosis.ui.bprs.teller.client.vault.IVault;

public interface AppFactory extends KembangClientFactory {

	IDeposit getSetorTunaiEditor();

	IDeposit getSetorTunaiViewer();

	IWithdrawal getTarikTunaiViewer();

	IWithdrawal getTarikTunaiEditor();

	ICashTrans getKasEditor();

	ICashTrans getKasViewer();

	IVault getVaultEditor();

	IVault getVaultViewer();

	IHtVault getHtVaultEditor();

	IHtVault getHtVaultViewer();

	ITellerTransList getListTransaksiTeller();

	ISavingPrint getCetakTabungan();

	ISavingTransList getSavingTransList();

	IUploadCollective getUploadCollective();

	ILoanRepayment getLoanRepaymentEditor();

	ILoanRepayment getLoanRepaymentViewer();

}
