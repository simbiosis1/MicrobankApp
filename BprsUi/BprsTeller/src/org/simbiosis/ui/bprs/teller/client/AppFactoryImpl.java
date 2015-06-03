package org.simbiosis.ui.bprs.teller.client;

import org.kembang.module.client.mvp.KembangClientFactoryImpl;
import org.simbiosis.ui.bprs.teller.client.cashtrans.CashTransEditor;
import org.simbiosis.ui.bprs.teller.client.cashtrans.CashTransViewer;
import org.simbiosis.ui.bprs.teller.client.cashtrans.ICashTrans;
import org.simbiosis.ui.bprs.teller.client.htvault.HtVaultEditor;
import org.simbiosis.ui.bprs.teller.client.htvault.HtVaultViewer;
import org.simbiosis.ui.bprs.teller.client.htvault.IHtVault;
import org.simbiosis.ui.bprs.teller.client.kolektif.IUploadCollective;
import org.simbiosis.ui.bprs.teller.client.kolektif.UploadCollective;
import org.simbiosis.ui.bprs.teller.client.savingdeposit.DepositEditor;
import org.simbiosis.ui.bprs.teller.client.savingdeposit.DepositViewer;
import org.simbiosis.ui.bprs.teller.client.savingdeposit.IDeposit;
import org.simbiosis.ui.bprs.teller.client.savingprint.ISavingPrint;
import org.simbiosis.ui.bprs.teller.client.savingprint.SavingPrint;
import org.simbiosis.ui.bprs.teller.client.savingtrans.ISavingTransList;
import org.simbiosis.ui.bprs.teller.client.savingtrans.SavingTransList;
import org.simbiosis.ui.bprs.teller.client.savingwd.IWithdrawal;
import org.simbiosis.ui.bprs.teller.client.savingwd.WithdrawalEditor;
import org.simbiosis.ui.bprs.teller.client.savingwd.WithdrawalViewer;
import org.simbiosis.ui.bprs.teller.client.tellertrans.ITellerTransList;
import org.simbiosis.ui.bprs.teller.client.tellertrans.TellerTransList;
import org.simbiosis.ui.bprs.teller.client.vault.IVault;
import org.simbiosis.ui.bprs.teller.client.vault.VaultEditor;
import org.simbiosis.ui.bprs.teller.client.vault.VaultViewer;

public class AppFactoryImpl extends KembangClientFactoryImpl implements
		AppFactory {

	static final DepositViewer SETOR_TUNAI_VIEWER = new DepositViewer();
	static final DepositEditor SETOR_TUNAI_EDITOR = new DepositEditor();
	static final WithdrawalViewer TARIK_TUNAI_VIEWER = new WithdrawalViewer();
	static final WithdrawalEditor TARIK_TUNAI_EDITOR = new WithdrawalEditor();
	static final CashTransEditor KAS_EDITOR = new CashTransEditor();
	static final CashTransViewer KAS_VIEWER = new CashTransViewer();
	static final VaultViewer VAULT_VIEWER = new VaultViewer();
	static final VaultEditor VAULT_EDITOR = new VaultEditor();
	static final HtVaultViewer HTVAULT_VIEWER = new HtVaultViewer();
	static final HtVaultEditor HTVAULT_EDITOR = new HtVaultEditor();
	static final TellerTransList LIST_TRANSAKSI_TELLER = new TellerTransList();
	static final SavingPrint CETAK_TABUNGAN = new SavingPrint();
	static final SavingTransList SAVING_TRANS_LIST = new SavingTransList();
	static final UploadCollective UPLOAD_COLLECTIVE = new UploadCollective();

	@Override
	public IDeposit getSetorTunaiEditor() {
		return SETOR_TUNAI_EDITOR;
	}

	@Override
	public IDeposit getSetorTunaiViewer() {
		return SETOR_TUNAI_VIEWER;
	}

	@Override
	public IWithdrawal getTarikTunaiViewer() {
		return TARIK_TUNAI_VIEWER;
	}

	@Override
	public IWithdrawal getTarikTunaiEditor() {
		return TARIK_TUNAI_EDITOR;
	}

	@Override
	public ITellerTransList getListTransaksiTeller() {
		return LIST_TRANSAKSI_TELLER;
	}

	@Override
	public ICashTrans getKasEditor() {
		return KAS_EDITOR;
	}

	@Override
	public ICashTrans getKasViewer() {
		return KAS_VIEWER;
	}

	@Override
	public IVault getVaultEditor() {
		return VAULT_EDITOR;
	}

	@Override
	public IVault getVaultViewer() {
		return VAULT_VIEWER;
	}

	@Override
	public IHtVault getHtVaultEditor() {
		return HTVAULT_EDITOR;
	}

	@Override
	public IHtVault getHtVaultViewer() {
		return HTVAULT_VIEWER;
	}

	@Override
	public ISavingPrint getCetakTabungan() {
		return CETAK_TABUNGAN;
	}

	@Override
	public ISavingTransList getSavingTransList() {
		return SAVING_TRANS_LIST;
	}

	@Override
	public IUploadCollective getUploadCollective() {
		return UPLOAD_COLLECTIVE;
	}
}
